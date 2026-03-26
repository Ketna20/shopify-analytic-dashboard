import "./KPICards.css";

const KPICards = ({ data }) => {
  if (!data) return null;

  const kpis = [
    {
      title: "Total Revenue",
      value: `$${Number(data.totalRevenue).toFixed(2)}`,
      trend: "+12%", // UI only
      trendType: "positive",
      icon: "revenue",
    },
    {
      title: "Total Orders",
      value: data.totalOrders,
      trend: "+5%",
      trendType: "positive",
      icon: "orders",
    },
    {
      title: "Avg Order Value",
      value: `$${Number(data.avgOrderValue).toFixed(2)}`,
      trend: "-2%",
      trendType: "negative",
      icon: "avg",
    },
  ];

  return (
    // <div className="kpi-container">
    //   {kpis.map((kpi, index) => (
    //     <div key={index} className="kpi-card">
    //       <div className="kpi-title">{kpi.title}</div>
    //       <div className="kpi-value">{kpi.value}</div>
    //     </div>
    //   ))}
    // </div>
    <div className="kpi-container">
      {kpis.map((kpis, index) => (
        <div key={index} className="kpi-card">
          <div className="kpi-title">{kpis.title}</div>
          <div className="kpi-value">{kpis.value}</div>

          <div
            className={
              kpis.trendType === "positive" ? "trend green" : "trend red"
            }
          >
            {kpis.trend}
          </div>
        </div>
      ))}
    </div>
  );
};

export default KPICards;
