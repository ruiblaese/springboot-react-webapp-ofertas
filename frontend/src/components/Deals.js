import React, {Component} from 'react';
import {withRouter} from 'react-router';
import PropTypes from 'prop-types';

import dayjs from 'dayjs';

import dealsService from '../services/dealService';

class Deals extends Component {
  constructor(props) {
    super(props);
    this.state = {listDeals: []};
  }

  async componentDidMount() {
    const retorno = await dealsService.getDeals();    
    this.setState({
      listDeals: retorno,
    });
  }

  btnNewClick(event) {
    this.props.history.push('/deal/');
  }

  btnEditClick(event, item) {
    this.props.history.push('/deal/' + item.id);
  }

  async btnDeleteClick(event, item) {
    await dealsService.deleteDeal(item.id);
    const retorno = await dealsService.getDeals();
    this.setState({
      listDeals: retorno,
    });
    //alert('excluindo' + JSON.stringify(item));
  }

  btnUploadCsv(event) {
    this.props.history.push('/uploadcsv/');
  }

  render = () => (
    <React.Fragment>
      <div className="container">
        <div className="row">
          <button
            onClick={e => {
              this.btnNewClick(e);
            }}
            type="button"
            className="btn btn-primary"
            id="btnNew"
            name="btnNew"
          >
            Nova Oferta
          </button>          
        </div>
        <br />
        <div className="row">
          {this.state.listDeals ? (
            <table className="table table-hover">
              <thead>
                <tr>
                  <th scope="col">#</th>
                  <th scope="col">Titulo</th>
                  <th scope="col">Texto</th>
                  <th scope="col">Cadastro</th>
                  <th scope="col">Pub. Inicio</th>
                  <th scope="col">Pub. Final</th>
                  <th scope="col">Qtde Vendida</th>
                  <th scope="col"></th>
                  <th scope="col"></th>
                </tr>
              </thead>
              <tbody>
                {this.state.listDeals
                  ? this.state.listDeals.map((item, index) => (
                      <tr key={item.id}>
                        <th scope="row">{item.id}</th>
                        <td>{item.title}</td>
                        <td>{item.text}</td>                        
                        <td>{dayjs(item.createDate).format('DD-MM-YYYY')}</td>
                        <td>{dayjs(item.publishDate).format('DD-MM-YYYY')}</td>
                        <td>{dayjs(item.endDate).format('DD-MM-YYYY')}</td>
                        <td>{item.totalSold}</td>  
                        <td>
                          <input
                            onClick={e => {
                              this.btnEditClick(e, item);
                            }}
                            className="btn btn-primary btn-sm"
                            type="button"
                            value="Editar"
                            name="btnEdit"
                            id="btnEdit"                            
                          />
                          <input
                            onClick={e => {
                              this.btnDeleteClick(e, item);
                            }}
                            className="btn btn-danger btn-sm"
                            type="button"
                            value="Excluir"
                            name="btnDelete"
                            id="btnDelete"
                            disabled // TODO desenvolver exclusao
                          />
                        </td>
                      </tr>
                    ))
                  : ''}
              </tbody>
            </table>
          ) : (
            ''
          )}
        </div>
      </div>
    </React.Fragment>
  );

  static propTypes = {
    location: PropTypes.object.isRequired,
    history: PropTypes.object.isRequired,
  };
}

export default withRouter(Deals);
