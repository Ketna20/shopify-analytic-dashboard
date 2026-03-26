import { useEffect, useState } from "react";
import {
  getTopProducts,
  getRevenue,
  getKPIs,
  getRevenueSpikes,
  getMomentumProducts,
} from "../services/api";
import RevenueChart from "../components/RevenueChart";
import TopProductsChart from "../components/TopProductsChart";
import KPICards from "../components/KPICards";
import RevenueSpikes from "../components/RevenueSpikes";
import MomentumProducts from "../components/MomentumProducts";
import Insights from "../components/Insights";
import "./Dashboard.css";

const Dashboard = () => {
  const [revenue, setRevenue] = useState([]);
  const [topProducts, setTopProducts] = useState([]);
  const [kpis, setKpis] = useState(null);
  const [spikes, setSpikes] = useState([]);
  const [momentumProducts, setMomentumProducts] = useState([]);

  useEffect(() => {
    const loadData = async () => {
      try {
        const [rev, top, kpi, spike, momentum] = await Promise.all([
          getRevenue(),
          getTopProducts(),
          getKPIs(),
          getRevenueSpikes(),
          getMomentumProducts(),
        ]);

        setRevenue(rev.data);
        setTopProducts(top.data);
        setKpis(kpi.data);
        setSpikes(spike.data);
        setMomentumProducts(momentum.data);
      } catch (err) {
        console.error("Dashboard load error:", err);
      }
    };

    loadData();
  }, []);

  const getInsights = () => {
    if (!kpis) return [];

    const insights = [];

    // 1. Revenue insight
    if (kpis.totalRevenue > 1000) {
      insights.push("🔥 Strong revenue performance today");
    } else {
      insights.push("⚠️ Revenue is low — consider promotions or ads");
    }

    // 2. Orders insight
    if (kpis.totalOrders < 5) {
      insights.push("⚠️ Very low order volume detected");
    }

    // 3. Momentum product insight
    if (momentumProducts.length > 0) {
      const top = momentumProducts[0];
      if (top.momentum > 2) {
        insights.push(
          `🚀 ${top.productName} is trending (${top.momentum.toFixed(1)}x growth)`,
        );
      }
    }

    // 4. Revenue spike insight
    if (spikes.length > 0) {
      insights.push("📈 Revenue spike detected — investigate traffic source");
    }

    // 5. Top product dominance
    if (topProducts.length > 0) {
      insights.push(`🏆 Top product: ${topProducts[0].productName}`);
    }

    return insights;
  };

  return (
    <div className="container-style">
      <div className="header-container">
        <div>
          <h1 className="header-title">Shopify Analytics</h1>
          <p className="header-subtitle">Revenue & Product Insights</p>
        </div>

        <div className="header-actions">
          <select className="date-filter">
            <option>Today</option>
            <option>Last 7 Days</option>
            <option>Last 30 Days</option>
          </select>

          <button className="refresh-btn">Refresh</button>
        </div>
      </div>
      <KPICards data={kpis} />

      <div className="grid-style">
        <div className="card-style">
          <RevenueChart data={revenue} />
        </div>

        <div className="card-style">
          <TopProductsChart data={topProducts} />
        </div>

        <div className="card-style">
          <RevenueSpikes data={spikes} />
        </div>

        <div className="card-style">
          <MomentumProducts data={momentumProducts} />
        </div>
      </div>
      <div className="grid-style">
        <Insights insights={getInsights()} />
      </div>
    </div>
  );
};

export default Dashboard;
