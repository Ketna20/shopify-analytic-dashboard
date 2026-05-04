import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  headers: {
    'Content-Type': 'application/json',
  },
});

export const getTopProducts = (from, to) => api.get("/analytics/top-products", { params: { from, to } });
export const getRevenue = (from, to) => api.get("/analytics/revenue", { params: { from, to } });
export const getKPIs = (from, to) => api.get("/analytics/kpis", { params: { from, to } });
export const getRevenueSpikes = (from, to) => api.get("/analytics/revenue-spikes", { params: { from, to } });
export const getMomentumProducts = (from, to) => api.get("/analytics/momentum-products", { params: { from, to } });
export const syncOrders = (storeId) => api.post(`/orders/sync/${storeId}`);