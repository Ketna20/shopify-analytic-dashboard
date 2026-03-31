import { useEffect, useState } from "react";
import {
  getTopProducts,
  getRevenue,
  getKPIs,
  getRevenueSpikes,
  getMomentumProducts,
} from "../services/api";
import {
  demoKPIs,
  demoRevenue,
  demoTopProducts,
  demoRevenueSpikes,
  demoMomentumProducts,
} from "../data/demoData";
import RevenueChart from "../components/RevenueChart";
import TopProductsChart from "../components/TopProductsChart";
import KPICards from "../components/KPICards";
import RevenueSpikes from "../components/RevenueSpikes";
import MomentumProducts from "../components/MomentumProducts";
import Insights from "../components/Insights";
import "./Dashboard.css";

const Dashboard = () => {
  const DEMO_MODE = false;
  const [revenue, setRevenue] = useState([]);
  const [topProducts, setTopProducts] = useState([]);
  const [kpis, setKpis] = useState(null);
  const [spikes, setSpikes] = useState([]);
  const [momentumProducts, setMomentumProducts] = useState([]);

  const normalize = (res) => {
    if (!res) return [];

    // already array
    if (Array.isArray(res)) return res;

    // API style: { data: [...] }
    if (Array.isArray(res.data)) return res.data;

    // fallback (safe guard)
    return [];
  };

  useEffect(() => {
    const loadData = async () => {
      try {
        if (DEMO_MODE) {
          // 👉 Demo mode (instant UI)
          setRevenue(demoRevenue);
          setTopProducts(demoTopProducts);
          setKpis(demoKPIs);
          setSpikes(normalize(demoRevenueSpikes));
          setMomentumProducts(normalize(demoMomentumProducts));
        } else {
          // 👉 Real API mode
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
          setSpikes(normalize(spike.data));
          setMomentumProducts(normalize(momentum.data));
        }
      } catch (err) {
        console.error("Dashboard load error:", err);
      }
    };

    loadData();
  }, []);

  const getInsights = () => {
    if (!kpis || !revenue.length) return [];

    const insights = [];

    // ---- 1. Revenue trend (compare last 2 days)
    if (revenue.length >= 2) {
      const latest = revenue[revenue.length - 1].revenue;
      const previous = revenue[revenue.length - 2].revenue;

      const change = ((latest - previous) / previous) * 100;

      if (change > 20) {
        insights.push({
          type: "positive",
          message: `🔥 Revenue jumped ${change.toFixed(1)}% vs previous period`,
        });
      } else if (change < -20) {
        insights.push({
          type: "negative",
          message: `⚠️ Revenue dropped ${Math.abs(change).toFixed(1)}% — check ads or conversion`,
        });
      }
    }

    // ---- 2. Order vs Revenue mismatch (conversion issue)
    if (kpis.totalOrders > 0 && kpis.totalRevenue > 0) {
      const avg = kpis.totalRevenue / kpis.totalOrders;

      if (avg < 20) {
        insights.push({
          type: "warning",
          message: "⚠️ Low average order value — consider bundles or upsells",
        });
      }
    }

    // ---- 3. Momentum product (strong signal)
    if (momentumProducts.length > 0) {
      const top = momentumProducts[0];

      if (top.momentum > 2) {
        insights.push({
          type: "positive",
          message: `🚀 ${top.productName} trending (${top.momentum.toFixed(1)}x growth) — consider promoting it`,
        });
      }
    }

    // ---- 4. Revenue spikes (opportunity insight)
    if (spikes.length > 0) {
      insights.push({
        type: "info",
        message:
          "📈 Revenue spikes detected — likely campaign or viral traffic",
      });
    }

    // ---- 5. Top product concentration (risk insight)
    if (topProducts.length > 0) {
      insights.push({
        type: "info",
        message: `🏆 Top product: ${topProducts[0].productName}`,
      });

      if (topProducts.length > 1) {
        insights.push({
          type: "warning",
          message: "⚠️ Revenue heavily dependent on few products",
        });
      }
    }

    return insights;
  };

  console.log("DEMO SPIKES:", demoRevenueSpikes);
  console.log("DEMO MOMENTUM:", demoMomentumProducts);

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

      {/* ✅ DEMO BANNER HERE
      {DEMO_MODE && (
        <div className="demo-banner">🚀 Demo Mode — Sample Data</div>
      )} */}

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
