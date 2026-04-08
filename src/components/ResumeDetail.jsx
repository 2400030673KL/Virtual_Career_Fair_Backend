export default function ResumeDetail({ resume, onClose, onStatusUpdate }) {
    function formatDate(dateStr) {
        return new Date(dateStr).toLocaleDateString("en-US", {
            weekday: "long",
            month: "long",
            day: "numeric",
            year: "numeric",
        });
    }

    return (
        <div className="rd-overlay" onClick={onClose}>
            <div className="rd-modal" onClick={(e) => e.stopPropagation()}>
                {/* Close Button */}
                <button className="rd-close" onClick={onClose}>
                    ✕
                </button>

                {/* Header */}
                <div className="rd-header">
                    <div className="rd-avatar">
                        {resume.name
                            .split(" ")
                            .map((n) => n[0])
                            .join("")}
                    </div>
                    <div className="rd-header-info">
                        <h2 className="rd-name">{resume.name}</h2>
                        <p className="rd-position">{resume.position}</p>
                        <span className={`rd-status-badge status-${resume.status}`}>
                            {resume.status === "pending" && "⏳ "}
                            {resume.status === "accepted" && "✅ "}
                            {resume.status === "rejected" && "❌ "}
                            {resume.status.charAt(0).toUpperCase() + resume.status.slice(1)}
                        </span>
                    </div>
                </div>

                {/* Info Section */}
                <div className="rd-info-grid">
                    <div className="rd-info-item">
                        <span className="rd-info-icon">✉️</span>
                        <div>
                            <span className="rd-info-label">Email</span>
                            <span className="rd-info-value">{resume.email}</span>
                        </div>
                    </div>
                    <div className="rd-info-item">
                        <span className="rd-info-icon">🏢</span>
                        <div>
                            <span className="rd-info-label">Applied To</span>
                            <span className="rd-info-value">{resume.company || "N/A"}</span>
                        </div>
                    </div>
                    <div className="rd-info-item">
                        <span className="rd-info-icon">📅</span>
                        <div>
                            <span className="rd-info-label">Applied On</span>
                            <span className="rd-info-value">{formatDate(resume.date)}</span>
                        </div>
                    </div>
                    <div className="rd-info-item">
                        <span className="rd-info-icon">📎</span>
                        <div>
                            <span className="rd-info-label">Resume File</span>
                            <span className="rd-info-value">{resume.fileName}</span>
                        </div>
                    </div>
                </div>

                {/* Summary */}
                <div className="rd-summary-section">
                    <h3 className="rd-section-title">Professional Summary</h3>
                    <p className="rd-summary-text">{resume.summary}</p>
                </div>

                {/* Action Buttons */}
                <div className="rd-actions">
                    {resume.status === "pending" ? (
                        <>
                            <button
                                className="rd-btn rd-btn-accept"
                                onClick={() => onStatusUpdate(resume.id, "accepted")}
                            >
                                <span className="rd-btn-icon">✓</span>
                                Accept Candidate
                            </button>
                            <button
                                className="rd-btn rd-btn-reject"
                                onClick={() => onStatusUpdate(resume.id, "rejected")}
                            >
                                <span className="rd-btn-icon">✕</span>
                                Reject Candidate
                            </button>
                        </>
                    ) : (
                        <div className="rd-status-message">
                            <span
                                className={`rd-status-icon ${resume.status === "accepted" ? "accepted" : "rejected"}`}
                            >
                                {resume.status === "accepted" ? "✅" : "❌"}
                            </span>
                            <p>
                                This candidate has been{" "}
                                <strong>{resume.status}</strong>.
                            </p>
                            <button
                                className="rd-btn rd-btn-reset"
                                onClick={() => onStatusUpdate(resume.id, "pending")}
                            >
                                ↺ Reset to Pending
                            </button>
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
}
