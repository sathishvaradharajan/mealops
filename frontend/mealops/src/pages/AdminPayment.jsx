import { useEffect, useState } from "react";
import api from "../api/axios";

import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";

import { Button } from "@/components/ui/button";
import {
  Select,
  SelectTrigger,
  SelectContent,
  SelectItem,
  SelectValue,
} from "@/components/ui/select";

function AdminPayment() {
  const [currentMethod, setCurrentMethod] = useState("");
  const [newMethod, setNewMethod] = useState("");

  const loadCurrent = async () => {
    try {
      const res = await api.get("/orders/payment/default");
      setCurrentMethod(res.data.defaultMethod);
    } catch (err) {
      console.error(err);
      alert("Failed to load current payment method");
    }
  };

  const updateMethod = async () => {
    if (!newMethod) {
      alert("Select a payment method");
      return;
    }

    try {
      await api.put(`/orders/payment/default?method=${newMethod}`);
      alert("Default payment method updated!");
      loadCurrent();
    } catch (err) {
      console.error(err);
      alert("Update failed");
    }
  };

  useEffect(() => {
    loadCurrent();
  }, []);

  return (
    <div className="min-h-screen flex justify-center items-start p-6 bg-gray-50">
      <Card className="w-full max-w-lg shadow-lg">
        <CardHeader>
          <CardTitle className="text-xl font-semibold text-center">
            Admin: Default Payment Method
          </CardTitle>
        </CardHeader>

        <CardContent className="space-y-6">
          <p className="text-center">
            <strong>Current Default:</strong> {currentMethod || "Loading..."}
          </p>

          {/* Select Dropdown */}
          <div className="space-y-2">
            <p className="font-medium">Choose new default:</p>

            <Select onValueChange={setNewMethod}>
              <SelectTrigger>
                <SelectValue placeholder="Select payment method" />
              </SelectTrigger>

              <SelectContent>
                <SelectItem value="UPI">UPI</SelectItem>
                <SelectItem value="CARD">CARD</SelectItem>
                <SelectItem value="NETBANKING">NETBANKING</SelectItem>
              </SelectContent>
            </Select>
          </div>

          <div className="flex justify-center gap-4 mt-6">
            <Button className="px-8" onClick={updateMethod}>
              Update
            </Button>

            <Button
              variant="secondary"
              className="px-8 bg-gray-200 text-black hover:bg-gray-300"
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

export default AdminPayment;
