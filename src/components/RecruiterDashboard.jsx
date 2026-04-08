import { useState, useEffect } from "react";
import ResumeDetail from "./ResumeDetail";

// Seed some demo resumes so the dashboard isn't empty on first visit
const DEMO_RESUMES = [
    {
        id: "demo-1",
        name: "Alice Johnson",
        email: "alice.johnson@email.com",
        position: "Software Engineer",
        company: "Google",
        summary:
            "Computer Science graduate with 2+ years of experience in full-stack development. Proficient in React, Node.js, Python, and cloud technologies. Built scalable microservices handling 10K+ requests/sec. Strong background in data structures and algorithms.",
        fileName: "alice_johnson_resume.pdf",
        date: "2026-02-20",
        status: "pending",
    },
    {
        id: "demo-2",
        name: "Bob Martinez",
        email: "bob.martinez@email.com",
        position: "Data Scientist",
        company: "Amazon",
        summary:
            "M.S. in Data Science with expertise in machine learning, NLP, and statistical modeling. Published 3 research papers. Experience with TensorFlow, PyTorch, and Spark. Led a team that improved recommendation accuracy by 35%.",
        fileName: "bob_martinez_resume.pdf",
        date: "2026-02-21",
        status: "pending",
    },
    {
        id: "demo-3",
        name: "Catherine Lee",
        email: "catherine.lee@email.com",
        position: "UX Designer",
        company: "Meta",
        summary:
            "Creative UX/UI designer with 4 years of experience in product design. Skilled in Figma, Adobe XD, and user research methodologies. Redesigned an e-commerce platform resulting in 28% increase in conversions.",
        fileName: "catherine_lee_resume.pdf",
        date: "2026-02-22",
        status: "pending",
    },
    {
        id: "demo-4",
        name: "David Kim",
        email: "david.kim@email.com",
        position: "Cloud Architect",
        company: "Microsoft",
        summary:
            "AWS and Azure certified cloud architect with 5 years of experience. Designed multi-region architectures for Fortune 500 companies. Expert in Kubernetes, Docker, and serverless computing. Reduced infrastructure costs by 40%.",
        fileName: "david_kim_resume.pdf",
        date: "2026-02-23",
        status: "pending",
    },
    {
        id: "demo-5",
        name: "Emily Chen",
        email: "emily.chen@email.com",
        position: "Product Manager",
        company: "Apple",
        summary:
            "MBA with 3 years of product management experience in consumer tech. Led cross-functional teams of 15+ members. Launched 2 products with 1M+ downloads. Strong analytical skills and data-driven decision-making.",
        fileName: "emily_chen_resume.pdf",
        date: "2026-02-24",
        status: "pending",
    },
];

function initResumes() {
    const stored = localStorage.getItem("submitted_resumes");
    if (stored) {
        const parsed = JSON.parse(stored);
        if (parsed.length > 0) return parsed;
    }
    localStorage.setItem("submitted_resumes", JSON.stringify(DEMO_RESUMES));
    return DEMO_RESUMES;
}

export default function RecruiterDashboard() {
    const [resumes, setResumes] = useState([]);
    const [selectedResume, setSelectedResume] = useState(null);
    const [filterStatus, setFilterStatus] = useState("all");
    const [searchTerm, setSearchTerm] = useState("");

    useEffect(() => {
        setResumes(initResumes());
    }, []);

    const handleStatusUpdate = (id, newStatus) => {
        const updated = resumes.map((r) =>
            r.id === id ? { ...r, status: newStatus } : r
        );
        setResumes(updated);
        localStorage.setItem("submitted_resumes", JSON.stringify(updated));
        setSelectedResume((prev) => (prev ? { ...prev, status: newStatus } : null));
    };

    const stats = {
        total: resumes.length,
        pending: resumes.filter((r) => r.status === "pending").length,
        accepted: resumes.filter((r) => r.status === "accepted").length,
        rejected: resumes.filter((r) => r.status === "rejected").length,
    };

    const filtered = resumes.filter((r) => {
        const matchStatus = filterStatus === "all" || r.status === filterStatus;
        const matchSearch =
            r.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
            r.position.toLowerCase().includes(searchTerm.toLowerCase()) ||
            r.email.toLowerCase().includes(searchTerm.toLowerCase());
        return matchStatus && matchSearch;
    });

    function formatDate(dateStr) {
        return new Date(dateStr).toLocaleDateString("en-US", {
            month: "short",
            day: "numeric",
            year: "numeric",
        });
    }

    return (
        <div className="recruiter-page">
            {/* Header */}
            <div className="recruiter-header">
                <div className="recruiter-header-content">
                    <div className="recruiter-greeting">
                        <div className="recruiter-avatar">
                            <span>👔</span>
                        </div>
                        <div>
                            <h1 className="recruiter-welcome">Recruiter Dashboard</h1>
                            <p className="recruiter-welcome-sub">
                                Review and manage candidate applications
                            </p>
                        </div>
                    </div>
                </div>
            </div>

            <div className="recruiter-content">
                {/* Stats */}
                <div className="recruiter-stats">
                    <div className="r-stat-card r-stat-total">
                        <div className="r-stat-icon">📄</div>
                        <div className="r-stat-info">
                            <span className="r-stat-number">{stats.total}</span>
                            <span className="r-stat-label">Total Resumes</span>
                        </div>
                    </div>
                    <div className="r-stat-card r-stat-pending">
                        <div className="r-stat-icon">⏳</div>
                        <div className="r-stat-info">
                            <span className="r-stat-number">{stats.pending}</span>
                            <span className="r-stat-label">Pending Review</span>
                        </div>
                    </div>
                    <div className="r-stat-card r-stat-accepted">
                        <div className="r-stat-icon">✅</div>
                        <div className="r-stat-info">
                            <span className="r-stat-number">{stats.accepted}</span>
                            <span className="r-stat-label">Accepted</span>
                        </div>
                    </div>
                    <div className="r-stat-card r-stat-rejected">
                        <div className="r-stat-icon">❌</div>
                        <div className="r-stat-info">
                            <span className="r-stat-number">{stats.rejected}</span>
                            <span className="r-stat-label">Rejected</span>
                        </div>
                    </div>
                </div>

                {/* Controls */}
                <div className="recruiter-controls">
                    <div className="recruiter-search-bar">
                        <span className="recruiter-search-icon">🔍</span>
                        <input
                            type="text"
                            placeholder="Search by name, position, or email..."
                            value={searchTerm}
                            onChange={(e) => setSearchTerm(e.target.value)}
                            className="recruiter-search-input"
                        />
                    </div>
                    <div className="recruiter-filter-row">
                        {["all", "pending", "accepted", "rejected"].map((status) => (
                            <button
                                key={status}
                                className={`recruiter-filter-btn ${filterStatus === status ? "active" : ""}`}
                                onClick={() => setFilterStatus(status)}
                            >
                                {status.charAt(0).toUpperCase() + status.slice(1)}
                            </button>
                        ))}
                    </div>
                </div>

                {/* Resume Cards */}
                {filtered.length > 0 ? (
                    <div className="resume-grid">
                        {filtered.map((resume) => (
                            <div
                                key={resume.id}
                                className="resume-card"
                                onClick={() => setSelectedResume(resume)}
                            >
                                <div className="resume-card-top">
                                    <div className="resume-applicant-avatar">
                                        {resume.name
                                            .split(" ")
                                            .map((n) => n[0])
                                            .join("")}
                                    </div>
                                    <span className={`resume-status-badge status-${resume.status}`}>
                                        {resume.status === "pending" && "⏳ "}
                                        {resume.status === "accepted" && "✅ "}
                                        {resume.status === "rejected" && "❌ "}
                                        {resume.status.charAt(0).toUpperCase() + resume.status.slice(1)}
                                    </span>
                                </div>
                                <h3 className="resume-name">{resume.name}</h3>
                                <p className="resume-position">{resume.position}</p>
                                <p className="resume-email">✉️ {resume.email}</p>
                                <div className="resume-card-meta">
                                    <span className="resume-date">📅 {formatDate(resume.date)}</span>
                                    <span className="resume-file">📎 {resume.fileName}</span>
                                </div>
                                <p className="resume-summary-preview">
                                    {resume.summary.length > 120
                                        ? resume.summary.substring(0, 120) + "..."
                                        : resume.summary}
                                </p>
                                <button className="resume-view-btn">View Details →</button>
                            </div>
                        ))}
                    </div>
                ) : (
                    <div className="resume-empty">
                        <span className="resume-empty-icon">📋</span>
                        <h3>No resumes found</h3>
                        <p>No applications match your current filters</p>
                    </div>
                )}
            </div>

            {/* Detail Modal */}
            {selectedResume && (
                <ResumeDetail
                    resume={selectedResume}
                    onClose={() => setSelectedResume(null)}
                    onStatusUpdate={handleStatusUpdate}
                />
            )}
        </div>
    );
}
