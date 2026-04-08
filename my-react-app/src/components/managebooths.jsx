import { useEffect, useState } from "react";
import { api } from "../lib/api";

const fallbackBooths = [
  { id: "0", name: "Google" },
  { id: "1", name: "Amazon" },
  { id: "2", name: "Microsoft" },
];

export default function ManageBooths() {
  const [booths, setBooths] = useState(fallbackBooths);
  const [companyName, setCompanyName] = useState("");
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    api.get("/booths")
      .then(setBooths)
      .catch(() => setBooths(fallbackBooths));
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    const payload = {
      name: companyName,
      industry: "Technology",
      logo: "🏢",
      tagline: "New booth",
      description: "Newly created booth",
      openRoles: 0,
      location: "Virtual",
      type: "Full-time",
      rating: 0,
      employees: "N/A",
      positions: ["TBD"],
      perks: ["TBD"],
      color: { primary: "#1a73e8", light: "#e8f0fe", border: "#c6dafc" },
    };

    try {
      const createdBooth = await api.post("/booths", payload);
      setBooths((current) => [createdBooth, ...current]);
      setCompanyName("");
    } catch {
      setBooths((current) => [
        { id: `local-${Date.now()}`, name: companyName },
        ...current,
      ]);
      setCompanyName("");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="center-wrapper">
      <div className="card">
        <h2>Manage Company Booths</h2>
        <form onSubmit={handleSubmit}>
          <input placeholder="Company Name" value={companyName} onChange={(e) => setCompanyName(e.target.value)} />
          <button type="submit" disabled={loading || !companyName}>
            {loading ? "Adding..." : "Add Booth"}
          </button>
        </form>

        <ul className="list">
          {booths.map((booth) => (
            <li key={booth.id}>{booth.name}</li>
          ))}
        </ul>
      </div>
    </div>
  );
}