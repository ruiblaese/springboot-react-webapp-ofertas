import http from './http';

const {GET_DEALS_ENDPOINT} = require('./configApi');

async function getDeals() {
  const {data} = await http.get(GET_DEALS_ENDPOINT);

  return data;
}

async function getDealById(id) {
  const {data} = await http.get(`${GET_DEALS_ENDPOINT}/${id}`);

  return data;
}

async function putDeal(deal) {
  const {data} = await http.put(
    `${GET_DEALS_ENDPOINT}`,
    deal
  );

  return data;
}

async function postDeal(deal) {
  const {data} = await http.post(GET_DEALS_ENDPOINT, deal);

  return data;
}

async function deleteDeal(id) {
  const {data} = await http.delete(`${GET_DEALS_ENDPOINT}/${id}`);

  return data;
}

export default {
  getDeals,
  getDealById,
  putDeal,
  postDeal,
  deleteDeal,
};
