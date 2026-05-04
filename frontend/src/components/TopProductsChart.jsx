import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  Tooltip,
  CartesianGrid,
  ResponsiveContainer,
  Cell,
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

const GREENS = ["#008060", "#00956f", "#00a47c", "#00b389", "#00c296"];

const TopProductsChart = ({ data }) => {
  return (
    <div>
      <h3>Top Products</h3>
      <ResponsiveContainer width="100%" height={280}>
        <BarChart data={data} margin={{ top: 10, right: 10, left: 0, bottom: 0 }}>
          <CartesianGrid strokeDasharray="3 3" stroke="#2a2a2a" vertical={false} />
          <XAxis dataKey="productName" tick={{ fill: "#a0a0a0", fontSize: 11 }} axisLine={false} tickLine={false} />
          <YAxis tick={{ fill: "#a0a0a0", fontSize: 11 }} axisLine={false} tickLine={false} tickFormatter={(v) => `$${v}`} />
          <Tooltip content={<CustomTooltip />} cursor={{ fill: "rgba(0,128,96,0.05)" }} />
          <Bar dataKey="revenue" radius={[6, 6, 0, 0]}>
            {data.map((_, index) => (
              <Cell key={index} fill={GREENS[index % GREENS.length]} />
            ))}
          </Bar>
        </BarChart>
      </ResponsiveContainer>
    </div>
  );
};

export default TopProductsChart;
