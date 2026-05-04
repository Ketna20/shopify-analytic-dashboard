import "./MomentumProducts.css";



const MomentumProducts = ({ data }) => {
  if (!data || data.length === 0) {
    return <div style={{ color: "#a0a0a0", fontSize: "13px" }}>No momentum products in this period</div>;
  }

  return (
    <div>
      <h3>🚀 Momentum Products</h3>

      {data.map((item, index) => (
        <div key={`${item.productName}-${index}`} className="momentum-card">
          <div className="momentum-header">
            <span className="momentum-name">{item.productName}</span>
            <span className="momentum-badge">🚀 Trending</span>
          </div>

          <div className="momentum-value">
            {Number(item.momentum || 0).toFixed(2)}x growth
          </div>

          <div className="momentum-details">
            ${item.pastRevenue} → ${item.recentRevenue}
          </div>
        </div>
      ))}
    </div>
  );
};

export default MomentumProducts;
