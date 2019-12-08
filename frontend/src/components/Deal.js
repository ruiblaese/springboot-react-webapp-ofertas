import React, { Component } from 'react';
import { withRouter } from 'react-router';
import PropTypes from 'prop-types';

import dayjs from 'dayjs';

import dealsService from './../services/dealService';
import buyOptionsService from './../services/buyOptionsService';

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
        title: '',    
        normalPrice: 0,
        percentageDiscount: 0,
        salePrice: 0,
        quantityCupom: 0,
        quantitySold: 0,
        startDate: null,
        endDate: null
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
          listBuyOptions = await buyOptionsService.getBuyOptionsByDeal(deal.id);
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

  btnSalvarClick = async event => {
    event.preventDefault();

    let msg = '';

    if (!this.state.dealForm.title) {
      msg += 'Preencha o campo Nome\n';
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
            <label htmlFor="inputName">Titulo</label>
            <input
              type="text"
              className="form-control"
              placeholder="Titulo"
              onChange={e => {
                this.handleOnChange(e);
              }}
              id="inputName"
              name="title"
              value={this.state.dealForm.title}
            />
          </div>
          <div className="form-group">
            <label htmlFor="inputText">Texto</label>
            <input
              type="text"
              className="form-control"
              placeholder="Titulo"
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
              <label htmlFor="inputName">Data Publicação</label>
              <input
                type="text"
                className="form-control"
                placeholder="Titulo"
                onChange={e => {
                  this.handleOnChange(e);
                }}
                id="inputPublishDate"
                name="publishDate"
                value={this.state.dealForm.publishDate}
              />
            </div>
            <div className="form-group col-sm-6">
              <label htmlFor="inputName">Data Publicação</label>
              <input
                type="text"
                className="form-control"
                placeholder="Titulo"
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
                  <label htmlFor="inputId">ID</label>
                  <input
                    type="text"
                    className="form-control form-control-sm"
                    placeholder="0"
                    id="inputId"
                    name="userId"
                    value={this.state.dealId}
                    disabled
                  />
                </div>
              </div>
              <div className="form-group">
                <label htmlFor="inputName">Titulo</label>
                <input
                  type="text"
                  className="form-control form-control-sm"
                  placeholder="Titulo"
                  onChange={e => {
                    this.handleOnChange(e);
                  }}
                  id="inputName"
                  name="buyoTitle"
                  value={this.state.buyoForm.title}
                />
              </div>

              <div className="input-group">
                <div className="form-group col-sm-4">
                  <label htmlFor="inputName">Valor</label>
                  <input
                    type="number"
                    placeholder="0"
                    className="form-control form-control-sm"
                    onChange={e => {
                      this.handleOnChange(e);
                    }}
                    id="inputPublishDate"
                    name="normalPrice"
                    value={this.state.buyoForm.normalPrice}
                  />
                </div>
                <div className="form-group col-sm-4">
                  <label htmlFor="inputName">% Desconto</label>
                  <input
                    type="number"
                    placeholder="0"
                    className="form-control form-control-sm"
                    onChange={e => {
                      this.handleOnChange(e);
                    }}
                    id="inputEndDate"
                    name="percentageDiscount"
                    value={this.state.buyoForm.percentageDiscount}
                  />
                </div>
                <div className="form-group col-sm-4">
                  <label htmlFor="inputName">Valor Venda</label>
                  <input
                    type="number"
                    placeholder="0"
                    className="form-control form-control-sm"
                    onChange={e => {
                      this.handleOnChange(e);
                    }}
                    id="inputEndDate"
                    name="salePrice"
                    value={this.state.buyoForm.salePrice}
                  />
                </div>
              </div>

              <div className="input-group">
                <div className="form-group col-sm-6">
                  <label htmlFor="inputName">Quantidade</label>
                  <input
                    type="number"
                    placeholder="0"
                    className="form-control form-control-sm"
                    onChange={e => {
                      this.handleOnChange(e);
                    }}
                    id="inputquantityCupom"
                    name="quantityCupom"
                    value={this.state.buyoForm.quantityCupom}
                  />
                </div>
                <div className="form-group col-sm-6">
                  <label htmlFor="inputName">Quantidade Vendida</label>
                  <input
                    type="number"
                    placeholder="0"
                    className="form-control form-control-sm"
                    onChange={e => {
                      this.handleOnChange(e);
                    }}
                    id="inputEndDate"
                    name="quantitySold"
                    value={this.state.buyoForm.quantitySold}
                  />
                </div>
              </div>

              <div className="input-group">
                <div className="form-group col-sm-6">
                  <label htmlFor="inputName">Data Inicio</label>
                  <input
                    type="text"
                    className="form-control form-control-sm"

                    onChange={e => {
                      this.handleOnChange(e);
                    }}
                    id="inputPublishDate"
                    name="buyoStarthDate"
                    value={this.state.buyoForm.starthDate}
                  />
                </div>
                <div className="form-group col-sm-6">
                  <label htmlFor="inputName">Data Final</label>
                  <input
                    type="text"
                    className="form-control form-control-sm"
                    onChange={e => {
                      this.handleOnChange(e);
                    }}
                    id="inputEndDate"
                    name="buyoEndDate"
                    value={this.state.buyoForm.endDate}
                  />
                </div>
              </div>
              <div className="form-group">
                <button
                  onClick={e => {
                    this.btnSalvarClick(e);
                  }}
                  type="submit"
                  className="btn btn-primary btn-sm"
                >
                  Salvar
            </button>
                <button
                  onClick={e => {
                    this.btnCancelarClick(e);
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
                        <td>{dayjs(item.startDate).format('DD-MM-YYYY')}</td>
                        <td>{dayjs(item.endDate).format('DD-MM-YYYY')}</td>
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
