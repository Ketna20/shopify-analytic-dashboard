import "./KPICards.css";

const KPICards = ({ data, onOrdersClick }) => {
  if (!data) return null;

  const kpis = [
    {
      title: "Total Revenue",
      value: `$${Number(data.totalRevenue).toFixed(2)}`,
      trend: "+12%",
      trendType: "positive",
    },
    {
      title: "Total Orders",
      value: data.totalOrders,
      trend: "+5%",
      trendType: "positive",
      clickable: true,
    },
    {
      title: "Avg Order Value",
      value: `$${Number(data.avgOrderValue).toFixed(2)}`,
      trend: "-2%",
      trendType: "negative",
    },
  ];

  return (
    <div className="kpi-container">
      {kpis.map((kpi, index) => (
        <div
          key={index}
          className={`kpi-card ${kpi.clickable ? "kpi-card--clickable" : ""}`}
          onClick={kpi.clickable ? onOrdersClick : undefined}
        >
          <div className="kpi-title">{kpi.title}</div>
          <div className="kpi-value">{kpi.value}</div>
          <div className={kpi.trendType === "positive" ? "trend green" : "trend red"}>
            {kpi.trend}
          </div>
          {kpi.clickable && (
            <div className="kpi-hint">Click to view orders</div>
          )}
        </div>
      ))}
    </div>
  );
};

export default KPICards;
