import http from './http';

const {GET_BUY_OPTIONS_ENDPOINT} = require('./configApi');

async function getOptionsByDeal(dealId) {
  const {data} = await http.get(
    GET_BUY_OPTIONS_ENDPOINT + (dealId ? '/deal/' + dealId : '0')
  );

  return data;
}

async function getOptionsActiveByDeal(dealId) {
  const {data} = await http.get(
    GET_BUY_OPTIONS_ENDPOINT + (dealId ? '/active/deal/' + dealId : '0')
  );

  return data;
}

async function getOptionsById(id) {
  const {data} = await http.get(`${GET_BUY_OPTIONS_ENDPOINT}/${id}`);

  return data;
}


async function putOption(buyo) {
  const {data} = await http.put(`${GET_BUY_OPTIONS_ENDPOINT}`, buyo);

  return data;
}

async function postOption(buyo) {  
  const {data} = await http.post(GET_BUY_OPTIONS_ENDPOINT, buyo);
  
  return data;    
}

async function deleteOption(id) {
  const {data} = await http.delete(`${GET_BUY_OPTIONS_ENDPOINT}/${id}`);

  return data;
}


async function buyOption(id) {
  const {data} = await http.post(`${GET_BUY_OPTIONS_ENDPOINT}/buy/${id}`);

  return data;
}

export default {
  getOptionsByDeal,
  getOptionsById,
  putOption,
  postOption,
  deleteOption,
  buyOption,
  getOptionsActiveByDeal
};
