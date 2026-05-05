import { useEffect, useState } from "react";
import { getOrders } from "../services/api";
import "./OrdersModal.css";

const OrdersModal = ({ from, to, onClose }) => {
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [totalElements, setTotalElements] = useState(0);

  useEffect(() => {
    const fetchOrders = async () => {
      setLoading(true);
      try {
        const res = await getOrders(from, to, page);
        setOrders(res.data.content);
        setTotalPages(res.data.totalPages);
        setTotalElements(res.data.totalElements);
      } catch (err) {
        console.error("Failed to load orders:", err);
      } finally {
        setLoading(false);
      }
    };
    fetchOrders();
  }, [page, from, to]);

  const formatDate = (dateStr) => {
    if (!dateStr) return "—";
    return new Date(dateStr).toLocaleDateString("en-US", {
      year: "numeric", month: "short", day: "numeric",
    });
  };

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-box" onClick={(e) => e.stopPropagation()}>

        <div className="modal-header">
          <div>
            <h2 className="modal-title">Orders</h2>
            <p className="modal-subtitle">{totalElements} orders · {from} → {to}</p>
          </div>
          <button className="modal-close" onClick={onClose}>✕</button>
        </div>

        {loading ? (
          <div className="modal-loading">Loading orders...</div>
        ) : orders.length === 0 ? (
          <div className="modal-empty">No orders found for this date range.</div>
        ) : (
          <>
            <div className="modal-table-wrapper">
              <table className="orders-table">
                <thead>
                  <tr>
                    <th>Date</th>
                    <th>Order ID</th>
                    <th>Customer</th>
                    <th>Email</th>
                    <th>Total</th>
                  </tr>
                </thead>
                <tbody>
                  {orders.map((order, i) => (
                    <tr key={order.shopifyOrderId ?? i}>
                      <td>{formatDate(order.orderDate)}</td>
                      <td className="order-id">#{order.shopifyOrderId}</td>
                      <td>{order.customerName || "—"}</td>
                      <td className="order-email">{order.customerEmail || "—"}</td>
                      <td className="order-total">
                        {order.currency} ${Number(order.totalPrice).toFixed(2)}
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>

            <div className="modal-pagination">
              <button
                className="page-btn"
                onClick={() => setPage((p) => p - 1)}
                disabled={page === 0}
              >
                ← Prev
              </button>
              <span className="page-info">Page {page + 1} of {totalPages}</span>
              <button
                className="page-btn"
                onClick={() => setPage((p) => p + 1)}
                disabled={page >= totalPages - 1}
              >
                Next →
              </button>
            </div>
          </>
        )}
      </div>
    </div>
  );
};

export default OrdersModal;
