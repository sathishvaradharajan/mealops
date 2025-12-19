import { useNavigate } from "react-router-dom";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";

function Dashboard() {
  const navigate = useNavigate();
  const role = localStorage.getItem("role");

  return (
    <div className="min-h-screen flex items-center justify-center px-4">

      <Card className="w-full max-w-md bg-white/90 dark:bg-white/10 backdrop-blur-lg border border-white/20 shadow-xl rounded-2xl">
        <CardHeader>
          <CardTitle className="text-2xl text-center font-bold text-gray-900 dark:text-white">
            Dashboard
          </CardTitle>
        </CardHeader>

        <CardContent className="space-y-5">

          <div className="text-center">
            <p className="text-gray-500 dark:text-gray-300 text-sm">Logged in as</p>
            <p className="font-semibold text-lg text-gray-900 dark:text-white">{role}</p>
          </div>

          <div className="space-y-3">
            {role === "ADMIN" && (
              <Button
                className="w-full bg-black text-white hover:bg-gray-800 rounded-lg"
                onClick={() => navigate("/admin/payment")}
              >
                Manage Payment Method
              </Button>
            )}

            <Button
              className="w-full bg-black text-white hover:bg-gray-800 rounded-lg"
              onClick={() => navigate("/restaurants")}
            >
              View Restaurants
            </Button>

            <Button
              className="w-full bg-black text-white hover:bg-gray-800 rounded-lg"
              onClick={() => navigate("/orders")}
            >
              View Orders
            </Button>

            <Button
              variant="destructive"
              className="w-full rounded-lg"
              onClick={() => {
                localStorage.clear();
                navigate("/");
              }}
            >
              Logout
            </Button>
          </div>

        </CardContent>
      </Card>

    </div>
  );
}

export default Dashboard;
