import React, { Component } from 'react';
import { withRouter } from 'react-router';
import PropTypes from 'prop-types';

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
          listBuyOptions = await optionsService.getOptionsActiveByDeal(deal.id);
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
          listBuyOptions = await optionsService.getOptionsActiveByDeal(deal.id);
        }
      } catch (error) {
        console.log(error);
      }
      this.setState({       
        listBuyOptions: listBuyOptions,
      });
  }

  async btnBuyOptionClick(event, item) {
    await optionsService.buyOption(item.id);    
    await this.getListBuyo();
  }  
  
  render = () => (
    <React.Fragment>
      <div className="container">
        <form>
          <p className="h2 mb-2">Oferta</p>

          <div className="form-row" hidden>
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
              disabled
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
              disabled
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
              disabled
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
                disabled
              />
            </div>
            <div className="form-group col-sm-6">
              <label htmlFor="inputEndDate">Validade</label>
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
                disabled
              />
            </div>
          </div>          
        </form>
        <br />

        {this.state.dealId ? (
          <React.Fragment>
            
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
                        <th>Qtde Disponivel</th>
                        <th>Preço Normal</th>
                        <th>% Desc.</th>
                        <th>Preço Atual</th>
                        <th>Inicio</th>
                        <th>Fim</th>
                        <th></th>
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
                              {item.quantityCupom - item.quantitySold}
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
                            <td>
                              <input
                                onClick={e => {
                                  this.btnBuyOptionClick(e, item);
                                }}
                                className="btn btn-primary btn-sm"
                                type="button"
                                value="Comprar"
                                name="btnBuyOption"
                                id="btnBuyOption"  
                                disabled={(item.quantityCupom - item.quantitySold) <= 0}
                              />                        
                            </td>
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
