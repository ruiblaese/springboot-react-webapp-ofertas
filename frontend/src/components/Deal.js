import React, { Component } from 'react';
import { withRouter } from 'react-router';
import PropTypes from 'prop-types';

// import dayjs from 'dayjs';

import {dateValid} from '../helpers';
import dealsService from './../services/dealService';
import optionsService from './../services/optionsService';

class Deal extends Component {
  constructor(props) {
    super(props);
    this.state = {
      dealInvalid: false,
      dealId: 0,
      dealForm: {
        id: 0,
        title: '',
        text: ''
      },
      buyoForm: {
        id: 0,
        deal: 0,
        title: '',
        normalPrice: 0,
        percentageDiscount: 0,
        salePrice: 0,
        quantityCupom: 0,
        quantitySold: 0,
        startDate: '',
        endDate: ''
      },
      listBuyOptions: [],
    };
  }

  async componentDidMount() {
    if (this.props.match.params.id) {
      let listBuyOptions = [];
      let deal;
      try {
        deal = await dealsService.getDealById(
          this.props.match.params.id
        );
        if (deal.id) {
          listBuyOptions = await optionsService.getOptionsByDeal(deal.id);
        }
      } catch (error) {
        console.log(error);
      }
      if (deal) {
        this.setState({
          dealId: this.props.match.params.id,
          dealForm: deal,
          listBuyOptions: listBuyOptions,
        });
      } else {
        this.setState({ dealInvalid: true });
      }
    }
  }

  getListBuyo = async () => {
    let listBuyOptions = [];
      let deal;
      try {
        deal = await dealsService.getDealById(
          this.props.match.params.id
        );
        if (deal.id) {
          listBuyOptions = await optionsService.getOptionsByDeal(deal.id);
        }
      } catch (error) {
        console.log(error);
      }
      this.setState({       
        listBuyOptions: listBuyOptions,
      });
  }

  btnSalvarClick = async event => {
    event.preventDefault();

    let msg = '';

    if (!this.state.dealForm.title) {
      msg += 'Preencha o campo título\n';
    }
    if (!dateValid(this.state.dealForm.publishDate)){
      msg += 'Preencha o campo Data Publicação corretamente no formato dd-mm-aaaa\n';
    }
    if (!dateValid(this.state.dealForm.endDate)){
      msg += 'Preencha o campo data Final corretamente no formato dd-mm-aaaa\n';
    }    

    if (!msg) {
      if (this.state.dealForm.id) {
        await dealsService.putDeal(this.state.dealForm);
        this.props.history.push('/deals');
      } else {
        await dealsService.postDeal(this.state.dealForm);
        this.props.history.push('/deals');
      }
    } else {
      alert(msg);
    }
  };

  btnSalvarBuyoClick = async event => {
    event.preventDefault();
    let msg = '';

    if (!this.state.buyoForm.title) {
      msg += 'Preencha o campo título\n';
    }
    if (!dateValid(this.state.buyoForm.startDate)){
      msg += 'Preencha o campo Data Inicio corretamente no formato dd-mm-aaaa\n';
    }
    if (!dateValid(this.state.buyoForm.endDate)){
      msg += 'Preencha o campo data Final corretamente no formato dd-mm-aaaa\n';
    }    

    if (!msg) {
      let tmpBuyoForm = this.state.buyoForm;
      tmpBuyoForm.deal = this.state.dealId;
      if (this.state.buyoForm.id) {
        await optionsService.putOption(tmpBuyoForm);        
      } else {
        await optionsService.postOption(tmpBuyoForm);
      }
      await this.getListBuyo();
      this.btnCancelarClickBuyo(event);
    } else {
      alert(msg);
    }
  };


  btnCancelarClick = event => {
    event.preventDefault();
    this.props.history.push('/deals');
  };

  handleOnChange(event) {
    this.setState({
      ...this.state,
      dealForm: {
        ...this.state.dealForm,
        [event.target.name]: event.target.value ? event.target.value : '',
      },
    });
  }
  btnCancelarClickBuyo = event => {
    event.preventDefault();
    this.setState({
      ...this.state,
      buyoForm: {
        id: 0,
        title: '',
        normalPrice: 0,
        percentageDiscount: 0,
        salePrice: 0,
        quantityCupom: 0,
        quantitySold: 0,
        startDate: '',
        endDate: ''
      },
    });
  };

  handleOnChangeBuyo(event) {
    this.setState({
      ...this.state,
      buyoForm: {
        ...this.state.buyoForm,
        [event.target.name]: event.target.value ? event.target.value : '',
      },
    });    
  }

  render = () => (
    <React.Fragment>
      <div className="container">
        <form>
          <p className="h2 mb-2">Cadastro de Oferta</p>

          <div className="form-row">
            <div className="form-group col-md-1">
              <label htmlFor="inputId">ID</label>
              <input
                type="text"
                className="form-control"
                placeholder="0"
                id="inputId"
                name="id"
                value={this.state.dealId}
                disabled
              />
            </div>
          </div>
          <div className="form-group">
            <label htmlFor="inputTitle">Título</label>
            <input
              type="text"
              className="form-control"
              placeholder="Titulo"
              onChange={e => {
                this.handleOnChange(e);
              }}
              id="inputTitle"
              name="title"
              value={this.state.dealForm.title}
            />
          </div>
          <div className="form-group">
            <label htmlFor="inputText">Texto de destaque</label>
            <input
              type="text"
              className="form-control"
              placeholder="Texto de destaque"
              onChange={e => {
                this.handleOnChange(e);
              }}
              id="inputText"
              name="text"
              value={this.state.dealForm.text}
            />
          </div>
          <div className="form-group">
            <label htmlFor="inputType">Tipo Oferta</label>
            <select
              className="custom-select"
              onChange={e => {
                this.handleOnChange(e);
              }}
              id="inputType"
              name="type"
              value={this.state.dealForm.type}
            >
              <option value="">Escolha...</option>
              <option value="LOCAL">LOCAL</option>
              <option value="PRODUTO">PRODUTO</option>
              <option value="VIAGEM">VIAGEM</option>
            </select>
          </div>
          <div className="input-group">
            <div className="form-group col-sm-6">
              <label htmlFor="inputPublishDate">Data Publicação</label>
              <input
                type="text"
                className="form-control"
                placeholder="00-00-0000"
                onChange={e => {
                  this.handleOnChange(e);
                }}
                id="inputPublishDate"
                name="publishDate"
                value={this.state.dealForm.publishDate}
              />
            </div>
            <div className="form-group col-sm-6">
              <label htmlFor="inputEndDate">Data Final</label>
              <input
                type="text"
                className="form-control"
                placeholder="00-00-0000"
                onChange={e => {
                  this.handleOnChange(e);
                }}
                id="inputEndDate"
                name="endDate"
                value={this.state.dealForm.endDate}
              />
            </div>
          </div>
          <div className="form-group">
            <button
              onClick={e => {
                this.btnSalvarClick(e);
              }}
              type="submit"
              className="btn btn-primary"
            >
              Salvar
            </button>
            <button
              onClick={e => {
                this.btnCancelarClick(e);
              }}
              type="submit"
              className="btn btn-secondary"
            >
              Cancelar
            </button>
          </div>
        </form>
        <br />

        {this.state.dealId ? (
          <React.Fragment>
            <div className="row">
              <div className="col-1"></div>
              <div className="col-11">
                <form>

                  <p className="h4 mb-4">Cadastro de Opção</p>
                  <hr />

                  <div className="form-row" hidden>
                    <div className="form-group col-md-1">
                      <label htmlFor="inputBuyoId">ID</label>
                      <input
                        type="text"
                        className="form-control form-control-sm"
                        placeholder="0"
                        id="inputBuyoId"
                        name="tmpId"
                        value={this.state.buyoForm.id}
                        disabled
                      />
                    </div>
                  </div>
                  <div className="form-group">
                    <label htmlFor="inputBuyoTitle">Título</label>
                    <input
                      type="text"
                      className="form-control form-control-sm"
                      placeholder="Titulo"
                      onChange={e => {
                        this.handleOnChangeBuyo(e);
                      }}
                      id="inputBuyoTitle"
                      name="title"
                      value={this.state.buyoForm.title}
                    />
                  </div>

                  <div className="input-group">
                    <div className="form-group col-sm-4">
                      <label htmlFor="inputBuyoNormalPrice">Valor</label>
                      <input
                        type="number"
                        placeholder="0"
                        className="form-control form-control-sm"
                        onChange={e => {
                          this.handleOnChangeBuyo(e);
                        }}
                        id="inputBuyoNormalPrice"
                        name="normalPrice"
                        value={this.state.buyoForm.normalPrice}
                      />
                    </div>
                    <div className="form-group col-sm-4">
                      <label htmlFor="inputPercentageDiscount">% Desconto</label>
                      <input
                        type="number"
                        placeholder="0"
                        className="form-control form-control-sm"
                        onChange={e => {
                          this.handleOnChangeBuyo(e);
                        }}
                        id="inputPercentageDiscount"
                        name="percentageDiscount"
                        value={this.state.buyoForm.percentageDiscount}                        
                      />
                    </div>
                    <div className="form-group col-sm-4">
                      <label htmlFor="inputSalePrice">Valor Venda</label>
                      <input
                        type="number"
                        placeholder="0"
                        className="form-control form-control-sm"
                        onChange={e => {
                          this.handleOnChangeBuyo(e);
                        }}
                        id="inputSalePrice"
                        name="salePrice"
                        value={this.state.buyoForm.salePrice}
                        disabled
                      />
                    </div>
                  </div>

                  <div className="input-group">
                    <div className="form-group col-sm-6">
                      <label htmlFor="inputQuantityCupom">Quantidade</label>
                      <input
                        type="number"
                        placeholder="0"
                        className="form-control form-control-sm"
                        onChange={e => {
                          this.handleOnChangeBuyo(e);
                        }}
                        id="inputQuantityCupom"
                        name="quantityCupom"
                        value={this.state.buyoForm.quantityCupom}
                      />
                    </div>
                    <div className="form-group col-sm-6">
                      <label htmlFor="inputQuantitySold">Quantidade Vendida</label>
                      <input
                        type="number"
                        placeholder="0"
                        className="form-control form-control-sm"
                        onChange={e => {
                          this.handleOnChangeBuyo(e);
                        }}
                        id="inputQuantitySold"
                        name="quantitySold"
                        value={this.state.buyoForm.quantitySold}
                        disabled
                      />
                    </div>
                  </div>

                  <div className="input-group">
                    <div className="form-group col-sm-6">
                      <label htmlFor="inputBuyoStartDate">Data Inicio</label>
                      <input
                        type="text"
                        className="form-control form-control-sm"
                        onChange={e => {
                          this.handleOnChangeBuyo(e);
                        }}
                        placeholder="00-00-0000"
                        id="inputBuyoStartDate"
                        name="startDate"
                        value={this.state.buyoForm.startDate}
                      />
                    </div>
                    <div className="form-group col-sm-6">
                      <label htmlFor="inputBuyoEndDate">Data Final</label>
                      <input
                        type="text"
                        className="form-control form-control-sm"
                        onChange={e => {
                          this.handleOnChangeBuyo(e);
                        }}
                        placeholder="00-00-0000"
                        id="inputBuyoEndDate"
                        name="endDate"
                        value={this.state.buyoForm.endDate}
                      />
                    </div>
                  </div>
                  <div className="form-group">
                    <button
                      onClick={e => {
                        this.btnSalvarBuyoClick(e);
                      }}
                      type="submit"
                      className="btn btn-primary btn-sm"
                    >
                      Salvar
                  </button>
                  <button
                      onClick={e => {
                        this.btnCancelarClickBuyo(e);
                      }}
                      type="submit"
                      className="btn btn-secondary btn-sm"
                    >
                      Cancelar
                  </button>
                  </div>
                </form>

              </div>
            </div>
            <br />

            <div className="row">
              {this.state.listBuyOptions ? (
                <React.Fragment>
                  <h4>Opções para essa Oferta</h4>
                  <table className="table table-hover">
                    <thead>
                      <tr>
                        <th hidden>
                          #
                    </th>
                        <th>Titulo</th>
                        <th>Qtde</th>
                        <th>Qtde Vendida</th>
                        <th>Preço Normal</th>
                        <th>% Desc.</th>
                        <th>Preço Atual</th>
                        <th>Inicio</th>
                        <th>Fim</th>
                      </tr>
                    </thead>
                    <tbody>
                      {this.state.listBuyOptions
                        ? this.state.listBuyOptions.map((item, index) =>

                          <tr key={item.id}>
                            <td hidden>
                              {item.id}
                            </td>
                            <td>
                              {item.title}
                            </td>
                            <td>
                              {item.quantityCupom}
                            </td>
                            <td>
                              {item.quantitySold}
                            </td>
                            <td>
                              {item.normalPrice.toLocaleString('pt-br', {
                                style: 'currency',
                                currency: 'BRL',
                              })}
                            </td>
                            <td>
                              {item.percentageDiscount.toLocaleString('pt-br', {
                                style: 'currency',
                                currency: 'BRL',
                              })}
                            </td>
                            <td>
                              {item.salePrice.toLocaleString('pt-br', {
                                style: 'currency',
                                currency: 'BRL',
                              })}
                            </td>
                            <td>{item.startDate}</td>
                            <td>{item.endDate}</td>
                          </tr>
                        )
                        : ''}
                    </tbody>
                  </table>
                </React.Fragment>
              ) : (
                  ''
                )}

            </div>
          </React.Fragment>
        ) : ('')}
      </div>

    </React.Fragment>
  );

  static propTypes = {
    location: PropTypes.object.isRequired,
    history: PropTypes.object.isRequired,
  };
}

export default withRouter(Deal);
