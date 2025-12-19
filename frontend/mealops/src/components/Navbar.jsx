import { Link, useNavigate, useLocation } from "react-router-dom";
import { useEffect, useState } from "react";
import { Button } from "@/components/ui/button";

function Navbar() {
  const navigate = useNavigate();
  const location = useLocation();
  const [role, setRole] = useState("");

  const updateRole = () => {
    const raw = localStorage.getItem("role");
    const normalized = raw?.trim().toUpperCase().replace("ROLE_", "") || "";
    setRole(normalized);
  };

  useEffect(() => updateRole(), []);
  useEffect(() => updateRole(), [location.pathname]);
  useEffect(() => {
    window.addEventListener("auth-change", updateRole);
    return () => window.removeEventListener("auth-change", updateRole);
  }, []);

  if (!localStorage.getItem("token")) return null;

  const logout = () => {
    localStorage.clear();
    window.dispatchEvent(new Event("auth-change"));
    navigate("/");
  };

  const isActive = (path) =>
    location.pathname.startsWith(path)
      ? "font-bold text-blue-400"
      : "text-white/80 hover:text-white";

  return (
    <nav className="flex items-center gap-6 px-6 py-4 bg-black border-b border-white/20 text-white shadow-lg">
      <Link to="/dashboard" className={isActive("/dashboard")}>
        Dashboard
      </Link>

      <Link to="/restaurants" className={isActive("/restaurants")}>
        Restaurants
      </Link>

      <Link to="/orders" className={isActive("/orders")}>
        Orders
      </Link>

      {role === "ADMIN" && (
        <Link to="/admin/payment" className={isActive("/admin/payment")}>
          Payment Settings
        </Link>
      )}
      <div className="flex-1"></div>
      <Button
        className="bg-white text-black hover:bg-gray-200 font-semibold"
        onClick={logout}
      >
        Logout
      </Button>
    </nav>
  );
}

export default Navbar;
