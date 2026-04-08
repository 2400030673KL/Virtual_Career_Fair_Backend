import { Link } from "react-router-dom";

export default function AdminDashboard() {
  return (
    <div className="center-wrapper">
      <div className="card">
        <h2>Admin Dashboard</h2>
        <ul className="list">
          <li><Link to="/admin/fairs">Manage Career Fairs</Link></li>
          <li><Link to="/admin/booths">Manage Company Booths</Link></li>
          <li><Link to="/admin/registrations">View Registrations</Link></li>
        </ul>
      </div>
    </div>
  );
}