import { useState } from "react";
import { Link } from "react-router-dom";

const companiesData = [
  {
    id: 0,
    name: "Google",
    industry: "Technology",
    logo: "🔵",
    tagline: "Organize the world's information",
    description:
      "Google LLC is a multinational technology company specializing in Internet-related services and products. Join one of the most innovative teams in the world.",
    openRoles: 24,
    location: "Mountain View, CA",
    type: "Full-time / Internship",
    rating: 4.8,
    employees: "180,000+",
    positions: ["Software Engineer", "Data Scientist", "Product Manager", "UX Designer"],
    perks: ["Remote Work", "Health Insurance", "401(k)", "Free Meals"],
    color: { primary: "#4285F4", light: "#e8f0fe", border: "#c6dafc" },
  },
  {
    id: 1,
    name: "Amazon",
    industry: "E-Commerce & Cloud",
    logo: "🟠",
    tagline: "Work hard. Have fun. Make history.",
    description:
      "Amazon is guided by four principles: customer obsession, passion for invention, commitment to operational excellence, and long-term thinking.",
    openRoles: 38,
    location: "Seattle, WA",
    type: "Full-time / Contract",
    rating: 4.5,
    employees: "1,500,000+",
    positions: ["Cloud Architect", "Full Stack Developer", "DevOps Engineer", "Solutions Architect"],
    perks: ["Stock Options", "Relocation", "Learning Budget", "Flex Hours"],
    color: { primary: "#FF9900", light: "#fff4e6", border: "#ffe0b2" },
  },
  {
    id: 2,
    name: "Microsoft",
    industry: "Software & Cloud",
    logo: "🟢",
    tagline: "Empower every person and organization",
    description:
      "Microsoft enables digital transformation for the era of an intelligent cloud and an intelligent edge. Build what matters at global scale.",
    openRoles: 31,
    location: "Redmond, WA",
    type: "Full-time / Internship",
    rating: 4.7,
    employees: "220,000+",
    positions: ["Azure Engineer", "AI Researcher", "Program Manager", "Security Analyst"],
    perks: ["Hybrid Work", "Wellness Benefits", "Tuition Aid", "Parental Leave"],
    color: { primary: "#00A4EF", light: "#e6f7ff", border: "#b3e5fc" },
  },
  {
    id: 3,
    name: "Meta",
    industry: "Social Technology",
    logo: "🔷",
    tagline: "Building the future of connection",
    description:
      "Meta builds technologies that help people connect, find communities, and grow businesses. Join us to shape the metaverse and next-gen social platforms.",
    openRoles: 19,
    location: "Menlo Park, CA",
    type: "Full-time",
    rating: 4.4,
    employees: "86,000+",
    positions: ["React Developer", "ML Engineer", "VR Developer", "Data Analyst"],
    perks: ["Equity", "Free Meals", "Fitness Center", "Childcare"],
    color: { primary: "#0668E1", light: "#e7f0ff", border: "#bbd5fc" },
  },
  {
    id: 4,
    name: "Apple",
    industry: "Consumer Electronics",
    logo: "⚫",
    tagline: "Think different.",
    description:
      "Apple designs, manufactures, and markets smartphones, personal computers, tablets, wearables, and accessories. Innovation is at the core of everything we do.",
    openRoles: 15,
    location: "Cupertino, CA",
    type: "Full-time",
    rating: 4.9,
    employees: "164,000+",
    positions: ["iOS Developer", "Hardware Engineer", "Design Lead", "Machine Learning"],
    perks: ["Product Discounts", "RSUs", "Health Benefits", "Education"],
    color: { primary: "#333333", light: "#f5f5f5", border: "#e0e0e0" },
  },
  {
    id: 5,
    name: "Tesla",
    industry: "Automotive & Energy",
    logo: "🔴",
    tagline: "Accelerating sustainable energy",
    description:
      "Tesla's mission is to accelerate the world's transition to sustainable energy. Work on cutting-edge electric vehicles, battery technology, and solar energy.",
    openRoles: 22,
    location: "Austin, TX",
    type: "Full-time / Co-op",
    rating: 4.3,
    employees: "127,000+",
    positions: ["Embedded Systems", "Autopilot Engineer", "Battery Researcher", "Manufacturing"],
    perks: ["Stock Options", "EV Discount", "Innovation Culture", "Growth"],
    color: { primary: "#CC0000", light: "#fef2f2", border: "#fecaca" },
  },
];

function StarRating({ rating }) {
  const fullStars = Math.floor(rating);
  const hasHalf = rating % 1 >= 0.3;
  return (
    <span className="booth-stars">
      {"★".repeat(fullStars)}
      {hasHalf && "½"}
      <span className="booth-rating-num">{rating}</span>
    </span>
  );
}

export default function Booths() {
  const [searchTerm, setSearchTerm] = useState("");
  const [selectedIndustry, setSelectedIndustry] = useState("All");

  const industries = ["All", ...new Set(companiesData.map((c) => c.industry))];

  const filtered = companiesData.filter((c) => {
    const matchSearch =
      c.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
      c.description.toLowerCase().includes(searchTerm.toLowerCase()) ||
      c.positions.some((p) => p.toLowerCase().includes(searchTerm.toLowerCase()));
    const matchIndustry =
      selectedIndustry === "All" || c.industry === selectedIndustry;
    return matchSearch && matchIndustry;
  });

  return (
    <div className="booths-page">
      {/* Header */}
      <div className="booths-header">
        <div className="booths-header-content">
          <h1 className="booths-title">Company Booths</h1>
          <p className="booths-subtitle">
            Explore virtual booths from top employers, discover open positions, and make connections
          </p>
        </div>
        <div className="booths-stats-row">
          <div className="booths-stat">
            <span className="booths-stat-num">{companiesData.length}</span>
            <span className="booths-stat-label">Companies</span>
          </div>
          <div className="booths-stat">
            <span className="booths-stat-num">
              {companiesData.reduce((sum, c) => sum + c.openRoles, 0)}
            </span>
            <span className="booths-stat-label">Open Roles</span>
          </div>
          <div className="booths-stat">
            <span className="booths-stat-num">{industries.length - 1}</span>
            <span className="booths-stat-label">Industries</span>
          </div>
        </div>
      </div>

      {/* Search and Filters */}
      <div className="booths-controls">
        <div className="booths-search-bar">
          <span className="booths-search-icon">🔍</span>
          <input
            type="text"
            placeholder="Search by company, role, or keyword..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="booths-search-input"
          />
        </div>
        <div className="booths-filter-row">
          {industries.map((ind) => (
            <button
              key={ind}
              className={`booths-filter-btn ${selectedIndustry === ind ? "active" : ""}`}
              onClick={() => setSelectedIndustry(ind)}
            >
              {ind}
            </button>
          ))}
        </div>
      </div>

      {/* Company Cards */}
      {filtered.length > 0 ? (
        <div className="booths-grid">
          {filtered.map((company) => (
            <div key={company.id} className="booth-card">
              {/* Card Top */}
              <div
                className="booth-card-banner"
                style={{ background: `linear-gradient(135deg, ${company.color.primary}22 0%, ${company.color.primary}08 100%)` }}
              >
                <div
                  className="booth-logo"
                  style={{
                    background: `linear-gradient(135deg, ${company.color.primary}, ${company.color.primary}cc)`,
                  }}
                >
                  <span className="booth-logo-text">
                    {company.name.substring(0, 2).toUpperCase()}
                  </span>
                </div>
                <div className="booth-badge-row">
                  <span
                    className="booth-industry-badge"
                    style={{
                      background: company.color.light,
                      color: company.color.primary,
                      borderColor: company.color.border,
                    }}
                  >
                    {company.industry}
                  </span>
                  <StarRating rating={company.rating} />
                </div>
              </div>

              {/* Card Body */}
              <div className="booth-card-body">
                <h3 className="booth-company-name">{company.name}</h3>
                <p className="booth-tagline">{company.tagline}</p>
                <p className="booth-desc">{company.description}</p>

                <div className="booth-info-grid">
                  <div className="booth-info-item">
                    <span className="booth-info-icon">📍</span>
                    <span>{company.location}</span>
                  </div>
                  <div className="booth-info-item">
                    <span className="booth-info-icon">👥</span>
                    <span>{company.employees}</span>
                  </div>
                  <div className="booth-info-item">
                    <span className="booth-info-icon">💼</span>
                    <span>{company.openRoles} open roles</span>
                  </div>
                  <div className="booth-info-item">
                    <span className="booth-info-icon">📋</span>
                    <span>{company.type}</span>
                  </div>
                </div>

                {/* Positions */}
                <div className="booth-positions">
                  <span className="booth-section-label">Open Positions</span>
                  <div className="booth-position-tags">
                    {company.positions.map((pos) => (
                      <span key={pos} className="booth-position-tag">
                        {pos}
                      </span>
                    ))}
                  </div>
                </div>

                {/* Perks */}
                <div className="booth-perks">
                  <span className="booth-section-label">Perks & Benefits</span>
                  <div className="booth-perk-tags">
                    {company.perks.map((perk) => (
                      <span key={perk} className="booth-perk-tag">
                        ✓ {perk}
                      </span>
                    ))}
                  </div>
                </div>
              </div>

              {/* Card Actions */}
              <div className="booth-card-footer">
                <Link to={`/booth/${company.id}`} className="booth-btn-primary">
                  Visit Booth
                </Link>
                <button className="booth-btn-outline">Save</button>
              </div>
            </div>
          ))}
        </div>
      ) : (
        <div className="booths-empty">
          <span className="booths-empty-icon">🏢</span>
          <h3>No booths found</h3>
          <p>Try adjusting your search or filter criteria</p>
        </div>
      )}
    </div>
  );
}