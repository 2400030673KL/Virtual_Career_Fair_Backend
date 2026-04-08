import { useEffect, useState } from "react";
import { api } from "../lib/api";

const fallbackFairs = [
  { id: "1", name: "Tech Career Fair", date: "2026-03-25" },
  { id: "2", name: "IT Jobs Expo", date: "2026-04-10" },
];

export default function ManageFairs() {
  const [fairs, setFairs] = useState(fallbackFairs);
  const [name, setName] = useState("");
  const [date, setDate] = useState("");
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    api.get("/fairs")
      .then(setFairs)
      .catch(() => setFairs(fallbackFairs));
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    const payload = {
      name,
      date,
      time: "10:00 AM - 4:00 PM",
      location: "Virtual",
      description: "Newly created career fair",
      companies: ["TBD"],
      status: "upcoming",
      registrations: 0,
      category: "General",
    };

    try {
      const createdFair = await api.post("/fairs", payload);
      setFairs((current) => [createdFair, ...current]);
      setName("");
      setDate("");
    } catch {
      setFairs((current) => [
        { id: `local-${Date.now()}`, name, date },
        ...current,
      ]);
      setName("");
      setDate("");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="center-wrapper">
      <div className="card">
        <h2>Manage Career Fairs</h2>
        <form onSubmit={handleSubmit}>
          <input placeholder="Career Fair Name" value={name} onChange={(e) => setName(e.target.value)} />
          <input type="date" value={date} onChange={(e) => setDate(e.target.value)} />
          <button type="submit" disabled={loading || !name || !date}>
            {loading ? "Adding..." : "Add Career Fair"}
          </button>
        </form>

        <ul className="list">
          {fairs.map((fair) => (
            <li key={fair.id}>{fair.name} – {new Date(fair.date).toLocaleDateString("en-US", {
              day: "2-digit",
              month: "short",
              year: "numeric",
            })}</li>
          ))}
        </ul>
      </div>
    </div>
  );
}