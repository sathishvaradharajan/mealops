import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import api from "../api/axios";

import {
  Card,
  CardHeader,
  CardTitle,
  CardContent,
} from "@/components/ui/card";

import { Button } from "@/components/ui/button";
import { Separator } from "@/components/ui/separator";

function OrderDetails() {
  const { id } = useParams();
  const [order, setOrder] = useState(null);

  const loadOrder = async () => {
    try {
      const res = await api.get(`/orders/${id}`);
      setOrder(res.data);
    } catch (err) {
      console.error(err);
      alert("Failed to load order details");
    }
  };

  const cancelOrder = async () => {
    try {
      const res = await api.post(`/orders/cancel/${id}`);
      setOrder(res.data);
      alert("Order canceled successfully!");
    } catch (err) {
      console.error(err);
      alert("Cancel failed");
    }
  };

  useEffect(() => {
    loadOrder();
  }, []);

  if (!order)
    return <p className="text-center p-6 text-gray-600">Loading...</p>;

  return (
    <div className="min-h-screen flex justify-center items-start p-6 bg-gray-50">
      <Card className="w-full max-w-2xl shadow-md">
        <CardHeader>
          <CardTitle className="text-2xl font-semibold text-center">
            Order Details
          </CardTitle>
        </CardHeader>

        <CardContent className="space-y-6">
          {/* Order Info */}
          <div className="space-y-1">
            <p><strong>Order ID:</strong> {order.orderId}</p>

            <p>
              <strong>Status:</strong>{" "}
              <span
                className={
                  order.status === "PAID"
                    ? "text-green-600 font-semibold"
                    : order.status === "CANCELED"
                    ? "text-red-600 font-semibold"
                    : "text-yellow-600 font-semibold"
                }
              >
                {order.status}
              </span>
            </p>

            <p><strong>Restaurant:</strong> {order.restaurantName}</p>
            <p><strong>Payment Method:</strong> {order.paymentMethod}</p>
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
                    <p className="text-sm text-gray-600">
                      Qty: {item.quantity}
                    </p>
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

          {/* Actions */}
          <div className="space-y-3">
            {/* Cancel Order only if PAID and not CANCELED */}
            {order.status === "PAID" && (
              <Button
                variant="destructive"
                className="w-full"
                onClick={cancelOrder}
              >
                Cancel Order
              </Button>
            )}

            <Button
              variant="secondary"
              className="w-full"
              onClick={() => window.history.back()}
            >
              Back
            </Button>
          </div>
        </CardContent>
      </Card>
    </div>
  );
}

export default OrderDetails;
