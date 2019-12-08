
const BASE_URL = process.env.REACT_APP_API_BASE_URL ? process.env.REACT_APP_API_BASE_URL: 'http://localhost:8080/';

//login
export const SIGNUP_ENDPOINT = BASE_URL + 'signin';

//usuario
export const GET_USERS_ENDPOINT = BASE_URL +'user';

//Oferta
export const GET_DEALS_ENDPOINT = BASE_URL +'deal';

//Opcao de oferta
export const GET_BUY_OPTIONS_ENDPOINT = BASE_URL + 'option';

export default {
  SIGNUP_ENDPOINT,
  GET_USERS_ENDPOINT,
  GET_DEALS_ENDPOINT,    
  GET_BUY_OPTIONS_ENDPOINT,
};
