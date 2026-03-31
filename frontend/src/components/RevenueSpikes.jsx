import "./RevenueSpikes.css";

const RevenueSpikes = ({ data }) => {
  if (!data || data.length === 0) return null;

  return (
    <div className="card-style">
      <h3>🔥 Revenue Spikes</h3>
      {data.map((spike, index) => (
        <div
          key={`${spike.date}-${spike.revenue}-${index}`}
          className="spike-card"
        >
          <div className="spike-header">
            <span className="spike-badge">🔥 Spike</span>
            <span className="spike-date">{spike.date}</span>
          </div>

          <div className="spike-value">${Number(spike.revenue).toFixed(2)}</div>

          <div className="spike-growth">
            +{Number(spike.increasePercentage || 0).toFixed(2)}%
          </div>
        </div>
      ))}
    </div>
  );
};

export default RevenueSpikes;
