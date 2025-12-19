import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import api from "../api/axios";

import {
  Card,
  CardHeader,
  CardTitle,
  CardContent,
} from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Separator } from "@/components/ui/separator";

function Menu() {
  const { id } = useParams();
  const [menuItems, setMenuItems] = useState([]);
  const [cart, setCart] = useState([]);

  const fetchMenu = async () => {
    try {
      const res = await api.get(`/restaurants/${id}/menu`);
      setMenuItems(res.data);
    } catch (err) {
      console.error(err);
      alert("Failed to load menu");
    }
  };
const [defaultPayment, setDefaultPayment] = useState("");

const fetchDefaultPayment = async () => {
  try {
    const res = await api.get("/orders/payment/default");
    setDefaultPayment(res.data.defaultMethod);
  } catch (err) {
    console.error(err);
  }
};
  useEffect(() => {
    fetchMenu();
    fetchDefaultPayment();
  }, []);

  const addToCart = (item) => {
    setCart((prev) => {
      const existing = prev.find((i) => i.id === item.id);
      if (existing) {
        return prev.map((i) =>
          i.id === item.id ? { ...i, quantity: i.quantity + 1 } : i
        );
      }
      return [...prev, { ...item, quantity: 1 }];
    });
  };

  const placeOrder = async () => {
    try {
      const payload = {
        restaurantId: Number(id),
        paymentMethod: defaultPayment,
        items: cart.map((item) => ({
          menuItemId: item.id,
          quantity: item.quantity,
        })),
      };

      const res = await api.post("/orders/create", payload);
      const orderId = res.data.orderId;
      const role = localStorage.getItem("role");

        window.location.href = `/orders/${orderId}/checkout`;
    } catch (err) {
      console.error(err);
      alert("Order failed");
    }
  };

  return (
    <div className="min-h-screen p-6 bg-gray-50 flex justify-center">
      <div className="w-full max-w-4xl space-y-8">

        {/* MENU SECTION */}
        <Card>
          <CardHeader>
            <CardTitle className="text-xl font-semibold">Menu</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            {menuItems.length === 0 ? (
              <p>No menu items.</p>
            ) : (
              <div className="space-y-4">
                {menuItems.map((item) => (
                  <div
                    key={item.id}
                    className="flex justify-between items-center p-3 bg-gray-100 rounded-md"
                  >
                    <div>
                      <p className="font-semibold">{item.name}</p>
                      <p className="text-gray-700">₹{item.price}</p>
                    </div>

                    <Button onClick={() => addToCart(item)}>Add</Button>
                  </div>
                ))}
              </div>
            )}
          </CardContent>
        </Card>

        {/* CART SECTION */}
        <Card>
          <CardHeader>
            <CardTitle className="text-xl font-semibold">Cart</CardTitle>
          </CardHeader>

          <CardContent className="space-y-4">
            {cart.length === 0 ? (
              <p>Cart is empty</p>
            ) : (
              <div className="space-y-3">
                {cart.map((item, index) => (
                  <div
                    key={index}
                    className="flex justify-between items-center p-3 bg-gray-100 rounded-md"
                  >
                    <p>
                      {item.name} —{" "}
                      <span className="font-semibold">Qty: {item.quantity}</span>
                    </p>
                  </div>
                ))}
              </div>
            )}

            <Separator />

            <Button
              className="w-full"
              disabled={cart.length === 0}
              onClick={placeOrder}
            >
              Place Order
            </Button>
          </CardContent>
        </Card>
      </div>
    </div>
  );
}

export default Menu;
