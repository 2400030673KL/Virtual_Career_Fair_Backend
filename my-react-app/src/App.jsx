import { Routes, Route } from "react-router-dom";
import "./App.css";

/* ===== USER COMPONENTS ===== */
import Navbar from "./components/navbar";
import Home from "./components/Home";
import SignUp from "./components/SignUp";
import Login from "./components/login";
import Dashboard from "./components/Dashboard";
import Booths from "./components/booths";
import Booth from "./components/booth";
import CareerFairs from "./components/CareerFairs";
import RecruiterDashboard from "./components/RecruiterDashboard";

/* ===== ADMIN COMPONENTS ===== */
import AdminLogin from "./components/adminlogin";
import AdminDashboard from "./components/adminDashboard";
import ManageFairs from "./components/managefairs";
import ManageBooths from "./components/managebooths";
import Registrations from "./components/registrations";

export default function App() {
  return (
    <>
      {/* Top Navigation */}
      <Navbar />

      {/* Main Application Area */}
      <div className="app-container">
        <Routes>
          {/* ===== USER ROUTES ===== */}
          <Route path="/" element={<Home />} />
          <Route path="/career-fairs" element={<CareerFairs />} />
          <Route path="/signup" element={<SignUp />} />
          <Route path="/login" element={<Login />} />
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/booths" element={<Booths />} />
          <Route path="/booth/:id" element={<Booth />} />

          {/* ===== RECRUITER ROUTES ===== */}
          <Route path="/recruiter/dashboard" element={<RecruiterDashboard />} />

          {/* ===== ADMIN ROUTES ===== */}
          <Route path="/admin/login" element={<AdminLogin />} />
          <Route path="/admin/dashboard" element={<AdminDashboard />} />
          <Route path="/admin/fairs" element={<ManageFairs />} />
          <Route path="/admin/booths" element={<ManageBooths />} />
          <Route path="/admin/registrations" element={<Registrations />} />
        </Routes>
      </div>
    </>
  );
}