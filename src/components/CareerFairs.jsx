import { useState } from "react";
import { Link } from "react-router-dom";

const careerFairsData = [
  {
    id: 1,
    name: "Tech Career Fair 2026",
    date: "2026-03-25",
    time: "10:00 AM - 4:00 PM",
    location: "Virtual",
    description:
      "Connect with leading tech companies including Google, Microsoft, Amazon, and more. Explore software engineering, data science, and AI roles.",
    companies: ["Google", "Microsoft", "Amazon", "Meta", "Apple"],
    status: "upcoming",
    registrations: 1245,
    category: "Technology",
  },
  {
    id: 2,
    name: "IT Jobs Expo 2026",
    date: "2026-04-10",
    time: "9:00 AM - 3:00 PM",
    location: "Virtual",
    description:
      "The largest virtual IT job expo bringing together top employers and talented professionals. Roles in cybersecurity, cloud computing, and DevOps.",
    companies: ["IBM", "Oracle", "Cisco", "Salesforce", "SAP"],
    status: "upcoming",
    registrations: 890,
    category: "Information Technology",
  },
  {
    id: 3,
    name: "Finance & Banking Career Fair",
    date: "2026-05-15",
    time: "11:00 AM - 5:00 PM",
    location: "Virtual",
    description:
      "Meet recruiters from the world's leading financial institutions. Opportunities in investment banking, fintech, risk management, and consulting.",
    companies: ["Goldman Sachs", "JP Morgan", "Morgan Stanley", "Deloitte", "EY"],
    status: "upcoming",
    registrations: 675,
    category: "Finance",
  },
  {
    id: 4,
    name: "Healthcare & Life Sciences Fair",
    date: "2026-06-20",
    time: "10:00 AM - 4:00 PM",
    location: "Virtual",
    description:
      "Explore career opportunities in healthcare, pharmaceuticals, and biotechnology with leading organizations worldwide.",
    companies: ["Pfizer", "Johnson & Johnson", "Mayo Clinic", "Merck", "Abbott"],
    status: "upcoming",
    registrations: 520,
    category: "Healthcare",
  },
  {
    id: 5,
    name: "Startup & Innovation Career Fair",
    date: "2026-07-08",
    time: "12:00 PM - 6:00 PM",
    location: "Virtual",
    description:
      "Connect with fast-growing startups and innovative companies. Perfect for those looking to make an impact in a dynamic work environment.",
    companies: ["Stripe", "Notion", "Figma", "Vercel", "Supabase"],
    status: "upcoming",
    registrations: 430,
    category: "Startups",
  },
  {
    id: 6,
    name: "Engineering & Manufacturing Expo",
    date: "2026-08-12",
    time: "9:00 AM - 3:00 PM",
    location: "Virtual",
    description:
      "Discover opportunities in engineering, manufacturing, and industrial design. Meet recruiters from top engineering firms and manufacturers.",
    companies: ["Tesla", "Boeing", "SpaceX", "Siemens", "GE"],
    status: "upcoming",
    registrations: 780,
    category: "Engineering",
  },
];

function getCategoryColor(category) {
  const colors = {
    Technology: { bg: "#eff6ff", text: "#2563eb", border: "#bfdbfe" },
    "Information Technology": { bg: "#faf5ff", text: "#7c3aed", border: "#ddd6fe" },
    Finance: { bg: "#ecfdf5", text: "#059669", border: "#a7f3d0" },
    Healthcare: { bg: "#fef2f2", text: "#dc2626", border: "#fecaca" },
    Startups: { bg: "#fff7ed", text: "#ea580c", border: "#fed7aa" },
    Engineering: { bg: "#f0fdf4", text: "#16a34a", border: "#bbf7d0" },
  };
  return colors[category] || { bg: "#f3f4f6", text: "#6b7280", border: "#d1d5db" };
}

function formatDate(dateStr) {
  const date = new Date(dateStr);
  const options = { weekday: "short", year: "numeric", month: "short", day: "numeric" };
  return date.toLocaleDateString("en-US", options);
}

export default function CareerFairs() {
  const [searchTerm, setSearchTerm] = useState("");
  const [selectedCategory, setSelectedCategory] = useState("All");

  const categories = ["All", ...new Set(careerFairsData.map((f) => f.category))];

  const filteredFairs = careerFairsData.filter((fair) => {
    const matchesSearch =
      fair.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
      fair.location.toLowerCase().includes(searchTerm.toLowerCase()) ||
      fair.description.toLowerCase().includes(searchTerm.toLowerCase());
    const matchesCategory =
      selectedCategory === "All" || fair.category === selectedCategory;
    return matchesSearch && matchesCategory;
  });

  return (
    <div className="career-fairs-page">
      {/* Header */}
      <div className="cf-header">
        <h1 className="cf-title">Career Fairs</h1>
        <p className="cf-subtitle">
          Explore upcoming virtual career fairs and connect with top employers
        </p>
      </div>

      {/* Search & Filter */}
      <div className="cf-controls">
        <div className="cf-search-bar">
          <span className="cf-search-icon">🔍</span>
          <input
            type="text"
            placeholder="Search career fairs by name, location, or description..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="cf-search-input"
          />
        </div>

        <div className="cf-category-filters">
          {categories.map((cat) => (
            <button
              key={cat}
              className={`cf-category-btn ${selectedCategory === cat ? "active" : ""}`}
              onClick={() => setSelectedCategory(cat)}
            >
              {cat}
            </button>
          ))}
        </div>
      </div>

      {/* Fair Cards */}
      {filteredFairs.length > 0 ? (
        <div className="cf-grid">
          {filteredFairs.map((fair) => {
            const catColor = getCategoryColor(fair.category);
            return (
              <div key={fair.id} className="cf-card">
                <div className="cf-card-header">
                  <span
                    className="cf-category-badge"
                    style={{
                      background: catColor.bg,
                      color: catColor.text,
                      border: `1px solid ${catColor.border}`,
                    }}
                  >
                    {fair.category}
                  </span>
                  <span className="cf-status-badge">● Upcoming</span>
                </div>

                <h3 className="cf-card-title">{fair.name}</h3>
                <p className="cf-card-desc">{fair.description}</p>

                <div className="cf-card-details">
                  <div className="cf-detail-row">
                    <span className="cf-detail-icon">📅</span>
                    <span>{formatDate(fair.date)}</span>
                  </div>
                  <div className="cf-detail-row">
                    <span className="cf-detail-icon">🕐</span>
                    <span>{fair.time}</span>
                  </div>
                  <div className="cf-detail-row">
                    <span className="cf-detail-icon">📍</span>
                    <span>{fair.location}</span>
                  </div>
                  <div className="cf-detail-row">
                    <span className="cf-detail-icon">👥</span>
                    <span>{fair.registrations.toLocaleString()} registered</span>
                  </div>
                </div>

                <div className="cf-companies">
                  <span className="cf-companies-label">Participating:</span>
                  <div className="cf-company-tags">
                    {fair.companies.slice(0, 3).map((company) => (
                      <span key={company} className="cf-company-tag">
                        {company}
                      </span>
                    ))}
                    {fair.companies.length > 3 && (
                      <span className="cf-company-more">
                        +{fair.companies.length - 3} more
                      </span>
                    )}
                  </div>
                </div>

                <div className="cf-card-actions">
                  <Link to={`/booths`} className="cf-btn-primary">
                    View Details
                  </Link>
                  <button className="cf-btn-secondary">Register</button>
                </div>
              </div>
            );
          })}
        </div>
      ) : (
        <div className="cf-empty">
          <span className="cf-empty-icon">📋</span>
          <h3>No career fairs found</h3>
          <p>Try adjusting your search or filter criteria</p>
        </div>
      )}
    </div>
  );
}
