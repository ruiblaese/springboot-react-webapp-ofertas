import React, {Component} from 'react';
import {withRouter} from 'react-router';
import PropTypes from 'prop-types';

import dealsService from '../services/dealService';

class PublicDeals extends Component {
  constructor(props) {
    super(props);
    this.state = {listDeals: []};
  }

  async componentDidMount() {
    const retorno = await dealsService.getActiveDeals();    
    this.setState({
      listDeals: retorno,
    });
  }

  btnDealClick(event, item) {
    this.props.history.push('/oferta/' + item.id);
  }

  render = () => (
    <React.Fragment>
      <div className="container">
        <h1>Lista de Ofertas</h1>
        <br />
        <div className="row">
          {this.state.listDeals ? (
            <table className="table table-hover table-sm">
              <thead>
                <tr>
                  <th>#</th>
                  <th>Tipo</th>
                  <th>Titulo</th>
                  <th>Texto</th>                  
                  <th></th>
                </tr>
              </thead>
              <tbody>
                {this.state.listDeals
                  ? this.state.listDeals.map((item, index) => (
                      <tr key={item.id}>
                        <th scope="row">{item.id}</th>
                        <td>{item.type}</td>
                        <td>{item.title}</td>
                        <td>{item.text}</td>                        
                        
                        <td>
                          <input
                            onClick={e => {
                              this.btnDealClick(e, item);
                            }}
                            className="btn btn-primary btn-sm"
                            type="button"
                            value="Ver Oferta"
                            name="btnDeal"
                            id="btnDeal"                            
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

export default withRouter(PublicDeals);
