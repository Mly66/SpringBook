import axios from "axios";

const request = axios.create({
  timeout: 2000,
  baseURL: '/api',
});

export default request;
