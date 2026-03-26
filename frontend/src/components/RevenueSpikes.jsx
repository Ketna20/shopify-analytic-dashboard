const cardStyle = {
  background: "white",
  padding: "20px",
  borderRadius: "12px",
  boxShadow: "0 4px 12px rgba(0,0,0,0.06)"
};

const RevenueSpikes = ({ data }) => {
  if (!data || data.length === 0) return null;

  return (
    <div style={cardStyle}>
      <h3>Revenue Spikes</h3>
      {data.map((spike, index) => (
        <div key={index}>
          <p>
            {spike.date} — ${spike.revenue} (↑ {spike.spikeRatio.toFixed(2)}x)
          </p>
        </div>
      ))}
    </div>
  );
};

export default RevenueSpikes;