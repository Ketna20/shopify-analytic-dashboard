import "./Insights.css";

const Insights = ({ insights }) => {
  if (!insights || insights.length === 0) {
    return <div className="card-style">No insights available</div>;
  }

  return (
    <div className="card-style">
      <h3>💡 Smart Insights</h3>

      {insights.map((insight, index) => (
        <div
          key={`${insight.message}-${index}`}
          className={`insight-item ${insight.type}`}
        >
          {insight.message}
        </div>
      ))}
    </div>
  );
};

export default Insights;
