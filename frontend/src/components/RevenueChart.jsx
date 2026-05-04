import {
  AreaChart,
  Area,
  XAxis,
  YAxis,
  Tooltip,
  CartesianGrid,
  ResponsiveContainer,
} from "recharts";

const CustomTooltip = ({ active, payload, label }) => {
  if (active && payload && payload.length) {
    return (
      <div style={{
        background: "#252525",
        border: "1px solid #2a2a2a",
        borderRadius: "8px",
        padding: "10px 14px",
      }}>
        <p style={{ color: "#a0a0a0", fontSize: "12px", marginBottom: "4px" }}>{label}</p>
        <p style={{ color: "#00a47c", fontWeight: 700, fontSize: "16px" }}>
          ${Number(payload[0].value).toFixed(2)}
        </p>
      </div>
    );
  }
  return null;
};

const RevenueChart = ({ data }) => {
  return (
    <div>
      <h3>Revenue Over Time</h3>
      <ResponsiveContainer width="100%" height={280}>
        <AreaChart data={data} margin={{ top: 10, right: 10, left: 0, bottom: 0 }}>
          <defs>
            <linearGradient id="greenGradient" x1="0" y1="0" x2="0" y2="1">
              <stop offset="5%" stopColor="#008060" stopOpacity={0.3} />
              <stop offset="95%" stopColor="#008060" stopOpacity={0} />
            </linearGradient>
          </defs>
          <CartesianGrid strokeDasharray="3 3" stroke="#2a2a2a" />
          <XAxis dataKey="date" tick={{ fill: "#a0a0a0", fontSize: 11 }} axisLine={false} tickLine={false} />
          <YAxis tick={{ fill: "#a0a0a0", fontSize: 11 }} axisLine={false} tickLine={false} tickFormatter={(v) => `$${v}`} />
          <Tooltip content={<CustomTooltip />} />
          <Area
            type="monotone"
            dataKey="revenue"
            stroke="#008060"
            strokeWidth={2}
            fill="url(#greenGradient)"
            dot={{ fill: "#008060", strokeWidth: 0, r: 3 }}
            activeDot={{ fill: "#00a47c", r: 5, strokeWidth: 0 }}
          />
        </AreaChart>
      </ResponsiveContainer>
    </div>
  );
};

export default RevenueChart;
