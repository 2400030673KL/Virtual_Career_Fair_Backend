import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { api } from "../lib/api";

export default function AdminLogin() {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async () => {
    setError("");
    setLoading(true);

    try {
      const response = await api.post("/auth/admin-login", { email, password });
      localStorage.setItem("authUser", JSON.stringify(response.user));
      localStorage.setItem("userType", "admin");
      navigate("/admin/dashboard");
    } catch (requestError) {
      setError(requestError.message || "Unable to sign in");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="center-wrapper">
      <div className="card">
        <h2>Admin Login</h2>
        {error && <div className="form-error">{error}</div>}
        <input placeholder="Admin Email" value={email} onChange={(e) => setEmail(e.target.value)} />
        <input type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} />
        <button onClick={handleSubmit} disabled={loading}>
          {loading ? "Signing in..." : "Login"}
        </button>
      </div>
    </div>
  );
}