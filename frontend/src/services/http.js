import axios from 'axios';
import loginService from './loginService';

const http = axios.create({
  baseURL: process.env.REACT_APP_API_BASE_URL ? process.env.REACT_APP_API_BASE_URL : "http://localhost:8080/",
  timeout: 3000,
  headers: {
    Accept: 'application/json',
    'Content-Type': 'application/json'    
  },
});

http.interceptors.request.use(
  async config => {
    const token = loginService.userLogged() && loginService.userLogged().token;

    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  error => {
    return Promise.reject(error);
  }
);

http.interceptors.response.use(
  response => response.data,
  async error => {    
    if (error.response === undefined) {
      console.warn('The API server is down');
    }
    /*
    console.log(JSON.stringify(error,null,4));    
    console.log(JSON.stringify(error.response,null,4));
    console.log(JSON.stringify(error.response.data.errors,null,4));
    console.log(error.config.method);
    */

    if (error.response && error.response.status === 401) {
      loginService.logout();
    } else if (error.response && error.response.status === 403) {
      loginService.logout();
    }
    if ((error.config.method === 'post')||(error.config.method === 'put')||(error.config.method === 'delete') ){
      if ((error.response)&&(error.response.data)&&(error.response.data.errors)){        
        alert(String(error.response.data.errors).replace(/\b,\b/gi,"\n"));        
      }
    }
    return Promise.reject(error);
  }
);

export default http;

