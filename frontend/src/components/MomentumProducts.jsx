
const cardStyle = {
  background: "white",
  padding: "20px",
  borderRadius: "12px",
  boxShadow: "0 4px 12px rgba(0,0,0,0.06)"
};


const MomentumProducts = ({ data }) => {
  if (!data || data.length === 0) {
    return <div>No momentum products</div>;
  } else {
    console.log("MomentumProducts data:", data);
  }

  return (
    <div style={cardStyle}>
      <h3>🔥 Momentum Products</h3>

      {data.map((item, index) => (
        <div key={index} className="row">
          <span> {item.productName} </span>

          <span>
            {item.momentum ? item.momentum : "0.00"} x 
          </span>

          <span>
            ${item.recentRevenue}
          </span>
        </div>
      ))}
    </div>
  );
};

export default MomentumProducts;