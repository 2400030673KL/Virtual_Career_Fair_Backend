import { useNavigate } from "react-router-dom";

export default function AdminLogin() {
  const navigate = useNavigate();

  return (
    <div className="center-wrapper">
      <div className="card">
        <h2>Admin Login</h2>
        <input placeholder="Admin Email" />
        <input type="password" placeholder="Password" />
        <button onClick={() => navigate("/admin/dashboard")}>
          Login
        </button>
      </div>
    </div>
  );
}