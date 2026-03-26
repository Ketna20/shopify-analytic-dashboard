import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api', // Adjust the base URL as needed
  headers: {
    'Content-Type': 'application/json',
  },
});

export const getTopProducts = () => api.get("/analytics/top-products");
export const getRevenue = () => api.get("/analytics/revenue");
export const getKPIs = () => api.get("/analytics/kpis");
export const getRevenueSpikes = () => api.get("/analytics/revenue-spikes");
export const getMomentumProducts = () =>
  api.get("/analytics/momentum-products");