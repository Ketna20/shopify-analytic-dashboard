
export const demoKPIs = {
  totalRevenue: 12480.5,
  totalOrders: 186,
  avgOrderValue: 67.1,
};

export const demoRevenue = [
  { date: "2026-03-17", revenue: 80 },
  { date: "2026-03-18", revenue: 120 },
  { date: "2026-03-19", revenue: 200 },
  { date: "2026-03-20", revenue: 300 },
  { date: "2026-03-21", revenue: 600 }, // spike
];

export const demoTopProducts = [
  { name: "Coffee Ethiopia", revenue: 800 },
  { name: "Coffee Brazil", revenue: 500 },
  { name: "Coffee Colombia", revenue: 300 },
];

export const demoRevenueSpikes = [
  {
    date: "2026-03-21",
    revenue: 600,
    increasePercentage: 100,
  },
];

export const demoMomentumProducts = [
  {
    productName: "Coffee Ethiopia",
    momentum: 7,
    pastRevenue: 100,
    recentRevenue: 700,
  },
  {
    productName: "Coffee Brazil",
    momentum: 3,
    pastRevenue: 150,
    recentRevenue: 450,
  },
];