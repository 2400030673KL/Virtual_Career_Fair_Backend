import { useEffect, useState } from "react";
import { api } from "../lib/api";

const fallbackRegistrations = [
  { id: "1", name: "Ravi", target: "Google Booth", status: "confirmed", registeredAt: "2026-04-01T10:00:00Z" },
  { id: "2", name: "Anjali", target: "Amazon Booth", status: "confirmed", registeredAt: "2026-04-01T10:05:00Z" },
  { id: "3", name: "Teja", target: "Microsoft Booth", status: "confirmed", registeredAt: "2026-04-01T10:10:00Z" },
];

export default function Registrations() {
  const [registrations, setRegistrations] = useState(fallbackRegistrations);

  useEffect(() => {
    api.get("/registrations")
      .then(setRegistrations)
      .catch(() => setRegistrations(fallbackRegistrations));
  }, []);

  return (
    <div className="center-wrapper">
      <div className="card">
        <h2>User Registrations</h2>
        <ul className="list">
          {registrations.map((registration) => (
            <li key={registration.id}>
              {registration.name} – {registration.target}
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
}