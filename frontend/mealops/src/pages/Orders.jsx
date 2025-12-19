import { useEffect, useState } from "react";
import api from "../api/axios";
import { Button } from "@/components/ui/button";
import { Card, CardContent } from "@/components/ui/card";

function Orders() {
  const [orders, setOrders] = useState([]);

  const fetchOrders = async () => {
    try {
      const res = await api.get("/orders");
      setOrders(res.data);
    } catch (err) {
      console.error(err);
      alert("Failed to load orders");
    }
  };

  const role = localStorage.getItem("role");

  useEffect(() => {
    fetchOrders();
  }, []);

  return (
    <div className="min-h-screen p-6 flex justify-center">
      <div className="w-full max-w-3xl space-y-6">

        <h1 className="text-3xl font-bold mb-4 text-gray-900 dark:text-white">
          Your Orders
        </h1>

        {orders.map((o) => (
          <Card
            key={o.orderId}
            className="shadow-md border border-gray-200 dark:border-white/10 dark:bg-white/10 backdrop-blur-md"
          >
            <CardContent className="p-5 space-y-2">
              <p className="text-lg font-semibold">Order #{o.orderId}</p>
              <p>Status: <span className="font-medium">{o.status}</span></p>
              <p>Total: ₹{o.totalAmount}</p>

              <Button
                className="mt-3"
                onClick={() =>
                  (window.location.href =
                    role === "ADMIN" || role === "MANAGER"
                      ? `/orders/${o.orderId}/checkout`
                      : `/orders/${o.orderId}/details`)
                }
              >
                View Details
              </Button>
            </CardContent>
          </Card>
        ))}
      </div>
    </div>
  );
}

export default Orders;
