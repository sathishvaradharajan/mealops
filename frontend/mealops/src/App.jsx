import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard";
import ProtectedRoute from "./components/ProtectedRoute";
import Restaurants from "./pages/Restaurants";
import Menu from "./pages/Menu";
import Checkout from "./pages/Checkout";
import Orders from "./pages/Orders";
import OrderDetails from "./pages/OrderDetails";
import AdminPayment from "./pages/AdminPayment";
import Navbar from "./components/Navbar";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* Public Route */}
        <Route path="/" element={<Login />} />

        {/* Everything below shows Navbar when logged in */}
        <Route
          path="/dashboard"
          element={
            <ProtectedRoute>
              <Navbar />
              <Dashboard />
            </ProtectedRoute>
          }
        />

        <Route
          path="/restaurants"
          element={
            <ProtectedRoute>
              <Navbar />
              <Restaurants />
            </ProtectedRoute>
          }
        />

        <Route
          path="/restaurants/:id/menu"
          element={
            <ProtectedRoute>
              <Navbar />
              <Menu />
            </ProtectedRoute>
          }
        />

        <Route
          path="/orders"
          element={
            <ProtectedRoute>
              <Navbar />
              <Orders />
            </ProtectedRoute>
          }
        />

        <Route
          path="/orders/:id/details"
          element={
            <ProtectedRoute>
              <Navbar />
              <OrderDetails />
            </ProtectedRoute>
          }
        />

        <Route
          path="/orders/:id/checkout"
          element={
            <ProtectedRoute>
              <Navbar />
              <Checkout />
            </ProtectedRoute>
          }
        />

        {/* Admin-only route */}
        <Route
          path="/admin/payment"
          element={
            <ProtectedRoute allowedRoles={["ADMIN"]}>
              <Navbar />
              <AdminPayment />
            </ProtectedRoute>
          }
        />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
