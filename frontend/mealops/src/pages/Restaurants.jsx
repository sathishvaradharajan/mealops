import { useEffect, useState } from "react";
import api from "../api/axios";
import { Link } from "react-router-dom";

import {
  Card,
  CardHeader,
  CardTitle,
  CardContent,
} from "@/components/ui/card";

import { Button } from "@/components/ui/button";

function Restaurants() {
  const [restaurants, setRestaurants] = useState([]);

  const fetchRestaurants = async () => {
    try {
      const res = await api.get("/restaurants");
      setRestaurants(res.data);
    } catch (err) {
      console.error(err);
      alert("Failed to load restaurants");
    }
  };

  useEffect(() => {
    fetchRestaurants();
  }, []);

  return (
    <div className="min-h-screen p-6 bg-gray-50 flex justify-center">
      <div className="w-full max-w-4xl space-y-8">
        <h1 className="text-3xl font-bold">Restaurants</h1>

        {restaurants.length === 0 ? (
          <p className="text-gray-600">No restaurants found.</p>
        ) : (
          <div className="grid grid-cols-1 sm:grid-cols-2 gap-6">
            {restaurants.map((r) => (
              <Card key={r.id} className="shadow-sm">
                <CardHeader>
                  <CardTitle className="text-xl font-semibold">
                    {r.name}
                  </CardTitle>
                </CardHeader>

                <CardContent>
                  <Button className="w-full" asChild>
                    <Link to={`/restaurants/${r.id}/menu`}>
                      View Menu
                    </Link>
                  </Button>
                </CardContent>
              </Card>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}

export default Restaurants;
