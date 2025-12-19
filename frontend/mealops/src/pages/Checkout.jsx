import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import api from "../api/axios";

import { Card, CardHeader, CardContent, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Separator } from "@/components/ui/separator";

function Checkout() {
  const { id } = useParams();
  const [order, setOrder] = useState(null);

  // Fetch order
  const fetchOrder = async () => {
    try {
      const res = await api.get(`/orders/${id}`);
      setOrder(res.data);
    } catch (err) {
      console.error(err);
      alert("Failed to load order");
    }
  };

  // Complete payment
  const handleCheckout = async () => {
    try {
      const res = await api.post(`/orders/${id}/checkout`);
      setOrder(res.data);
    } catch (err) {
      console.error(err);
      alert("Payment failed");
    }
  };

  // Cancel order
  const cancelOrder = async () => {
    try {
      const res = await api.post(`/orders/cancel/${id}`);
      setOrder(res.data);
    } catch (err) {
      console.error(err);
      alert("Cancel failed");
    }
  };

  useEffect(() => {
    fetchOrder();
  }, []);

  if (!order) return <p className="text-center p-6">Loading...</p>;

  const isPaid = order.status === "PAID";
  const isCanceled = order.status === "CANCELED";
  const isCreated = order.status === "CREATED";

  return (
    <div className="min-h-screen flex justify-center items-start p-6 bg-gray-50">
      <Card className="w-full max-w-2xl shadow-lg">
        <CardHeader>
          <CardTitle className="text-2xl font-semibold">Checkout</CardTitle>
        </CardHeader>

        <CardContent className="space-y-6">

          {/* Order Info */}
          <div className="space-y-1">
            <p><strong>Order ID:</strong> {order.orderId}</p>
            <p><strong>Restaurant:</strong> {order.restaurantName}</p>
            <p><strong>Payment Method:</strong> {order.paymentMethod}</p>
            <p>
              <strong>Status:</strong>{" "}
              <span
                className={
                  isPaid
                    ? "text-green-600 font-semibold"
                    : isCanceled
                    ? "text-red-600 font-semibold"
                    : "text-yellow-600 font-semibold"
                }
              >
                {order.status}
              </span>
            </p>
          </div>

          <Separator />

          {/* Items */}
          <div>
            <h3 className="text-lg font-semibold mb-3">Items</h3>
            <div className="space-y-2">
              {order.items.map((item, index) => (
                <div
                  key={index}
                  className="flex justify-between items-center p-3 bg-gray-100 rounded-md"
                >
                  <div>
                    <p className="font-medium">{item.itemName}</p>
                    <p className="text-sm text-gray-600">Qty: {item.quantity}</p>
                  </div>

                  <p className="font-semibold text-gray-800">₹{item.price}</p>
                </div>
              ))}
            </div>
          </div>

          <Separator />

          {/* Total */}
          <div className="text-right text-xl font-semibold">
            Total: ₹{order.totalAmount}
          </div>

          {/* CANCELED: no buttons */}
          {isCanceled && (
            <p className="text-red-700 font-semibold text-center text-lg">
              Order Canceled ✘
            </p>
          )}

          {/* CREATED: show complete + cancel */}
          {isCreated && (
            <div className="flex gap-3 justify-end mt-3">
              <Button className="px-6" onClick={handleCheckout}>
                Complete Payment
              </Button>

              <Button
                variant="destructive"
                className="px-6"
                onClick={cancelOrder}
              >
                Cancel Order
              </Button>
            </div>
          )}

          {/* PAID: show cancel only */}
          {isPaid && (
            <>
              <p className="text-green-700 font-semibold text-center text-lg">
                Payment Completed ✔
              </p>
              <div className="flex justify-end mt-3">
                <Button
                  variant="destructive"
                  className="px-6"
                  onClick={cancelOrder}
                >
                  Cancel Order
                </Button>
              </div>
            </>
          )}
        </CardContent>
      </Card>
    </div>
  );
}

export default Checkout;
