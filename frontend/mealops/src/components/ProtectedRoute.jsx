import { Navigate, useLocation } from "react-router-dom";

function ProtectedRoute({ children, allowedRoles }) {
  const token = localStorage.getItem("token");
  const role = (localStorage.getItem("role") || "").toUpperCase();
  const location = useLocation();

  // Not logged in → redirect
  if (!token) {
    return <Navigate to="/" replace />;
  }

  // Role-restricted route
  if (allowedRoles && !allowedRoles.includes(role)) {
    return <Navigate to="/dashboard" replace />;
  }

  // Members cannot access checkout
  if (location.pathname.includes("/checkout") && role === "MEMBER") {
    return <Navigate to="/orders" replace />;
  }

  return children;
}

export default ProtectedRoute;
