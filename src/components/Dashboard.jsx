import { Link } from "react-router-dom";

export default function Dashboard() {
  return (
    <div className="dashboard-page">
      {/* Welcome Header */}
      <div className="dashboard-header">
        <div className="dashboard-header-content">
          <div className="dashboard-greeting">
            <div className="dashboard-avatar">
              <span>👤</span>
            </div>
            <div>
              <h1 className="dashboard-welcome">Welcome back! 👋</h1>
              <p className="dashboard-welcome-sub">Here's what's happening with your career journey</p>
            </div>
          </div>
        </div>
      </div>

      {/* Stats Row */}
      <div className="dashboard-content">
        <div className="dashboard-stats">
          <div className="stat-card stat-card-blue">
            <div className="stat-icon">🎯</div>
            <div className="stat-info">
              <span className="stat-number">12</span>
              <span className="stat-label">Career Fairs Available</span>
            </div>
          </div>
          <div className="stat-card stat-card-purple">
            <div className="stat-icon">🏢</div>
            <div className="stat-info">
              <span className="stat-number">48</span>
              <span className="stat-label">Company Booths</span>
            </div>
          </div>
          <div className="stat-card stat-card-green">
            <div className="stat-icon">📄</div>
            <div className="stat-info">
              <span className="stat-number">3</span>
              <span className="stat-label">Resumes Uploaded</span>
            </div>
          </div>
          <div className="stat-card stat-card-orange">
            <div className="stat-icon">💬</div>
            <div className="stat-info">
              <span className="stat-number">5</span>
              <span className="stat-label">Chat Sessions</span>
            </div>
          </div>
        </div>

        {/* Quick Actions */}
        <h2 className="dashboard-section-title">Quick Actions</h2>
        <div className="dashboard-actions">
          <Link to="/career-fairs" className="action-card">
            <div className="action-icon action-icon-blue">🎪</div>
            <div className="action-text">
              <h3>Register for Career Fairs</h3>
              <p>Browse and register for upcoming virtual career fairs</p>
            </div>
            <span className="action-arrow">→</span>
          </Link>

          <Link to="/booths" className="action-card">
            <div className="action-icon action-icon-purple">🏛️</div>
            <div className="action-text">
              <h3>Visit Company Booths</h3>
              <p>Explore companies and learn about opportunities</p>
            </div>
            <span className="action-arrow">→</span>
          </Link>

          <div className="action-card">
            <div className="action-icon action-icon-green">📎</div>
            <div className="action-text">
              <h3>Submit Resume</h3>
              <p>Upload your resume for recruiters to review</p>
            </div>
            <span className="action-arrow">→</span>
          </div>

          <div className="action-card">
            <div className="action-icon action-icon-orange">💬</div>
            <div className="action-text">
              <h3>Live Chat with Recruiters</h3>
              <p>Connect directly with hiring managers in real-time</p>
            </div>
            <span className="action-arrow">→</span>
          </div>
        </div>

        {/* Upcoming Events */}
        <h2 className="dashboard-section-title">Upcoming Events</h2>
        <div className="dashboard-events">
          <div className="event-card">
            <div className="event-date">
              <span className="event-month">MAR</span>
              <span className="event-day">15</span>
            </div>
            <div className="event-info">
              <h4>Tech Industry Career Fair</h4>
              <p>50+ tech companies hiring for engineering roles</p>
            </div>
            <span className="event-badge event-badge-live">Upcoming</span>
          </div>
          <div className="event-card">
            <div className="event-date">
              <span className="event-month">MAR</span>
              <span className="event-day">22</span>
            </div>
            <div className="event-info">
              <h4>Business & Finance Summit</h4>
              <p>Top financial firms and consulting companies</p>
            </div>
            <span className="event-badge">Open</span>
          </div>
          <div className="event-card">
            <div className="event-date">
              <span className="event-month">APR</span>
              <span className="event-day">05</span>
            </div>
            <div className="event-info">
              <h4>Healthcare Professionals Expo</h4>
              <p>Hospitals and healthcare organizations recruiting</p>
            </div>
            <span className="event-badge">Open</span>
          </div>
        </div>
      </div>
    </div>
  );
}