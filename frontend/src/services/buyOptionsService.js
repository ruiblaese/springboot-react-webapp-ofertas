import http from './http';

const {GET_BUY_OPTIONS_ENDPOINT} = require('./configApi');

async function getBuyOptionsByDeal(dealId) {
  const {data} = await http.get(
    GET_BUY_OPTIONS_ENDPOINT + (dealId ? '/deal/' + dealId : '0')
  );

  return data;
}

async function getBuyOptionsById(id) {
  const {data} = await http.get(`${GET_BUY_OPTIONS_ENDPOINT}/${id}`);

  return data;
}

export default {
  getBuyOptionsByDeal,
  getBuyOptionsById,
};
