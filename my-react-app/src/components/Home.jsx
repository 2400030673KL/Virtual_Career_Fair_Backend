import { Link } from "react-router-dom";

export default function Home() {
  return (
    <div className="home-page">
      {/* Hero Section */}
      <section className="hero-section">
        <div className="hero-content">
          <h1 className="hero-title">
            Connect with Your <span className="hero-gradient-text">Future Career</span>
          </h1>
          <p className="hero-subtitle">
            The premier virtual platform for career fairs and networking events. Meet recruiters,
            <br />
            explore opportunities, and take the next step in your career journey.
          </p>
          <div className="hero-buttons">
            <Link to="/signup" className="btn-primary">
              Get Started
              <span className="arrow">→</span>
            </Link>
            <Link to="/login" className="btn-secondary">
              Login
            </Link>
          </div>
        </div>
      </section>

      {/* Features Section */}
      <section className="features-section">
        <div className="features-header">
          <span className="section-badge">✨ Features</span>
          <h2 className="features-title">Everything You Need to Succeed</h2>
          <p className="features-subtitle">
            Our platform provides all the tools you need for a successful virtual career fair experience.
          </p>
        </div>

        <div className="features-grid">
          <div className="feature-card feature-card-1">
            <div className="feature-glow"></div>
            <div className="feature-icon">
              <span>📅</span>
            </div>
            <h3 className="feature-title">Virtual Career Fairs</h3>
            <p className="feature-description">
              Attend career fairs from anywhere, anytime. Connect with top companies in a digital environment.
            </p>
            <div className="feature-link">Learn more →</div>
          </div>

          <div className="feature-card feature-card-2">
            <div className="feature-glow"></div>
            <div className="feature-icon">
              <span>💼</span>
            </div>
            <h3 className="feature-title">Company Booths</h3>
            <p className="feature-description">
              Explore interactive company booths, learn about job opportunities, and connect with recruiters.
            </p>
            <div className="feature-link">Learn more →</div>
          </div>

          <div className="feature-card feature-card-3">
            <div className="feature-glow"></div>
            <div className="feature-icon">
              <span>💬</span>
            </div>
            <h3 className="feature-title">Live Chat</h3>
            <p className="feature-description">
              Communicate directly with recruiters through our real-time messaging system.
            </p>
            <div className="feature-link">Learn more →</div>
          </div>

          <div className="feature-card feature-card-4">
            <div className="feature-glow"></div>
            <div className="feature-icon">
              <span>📄</span>
            </div>
            <h3 className="feature-title">Resume Submission</h3>
            <p className="feature-description">
              Upload and submit your resume directly to companies you're interested in.
            </p>
            <div className="feature-link">Learn more →</div>
          </div>
        </div>
      </section>

      {/* Why Choose Section */}
      <section className="why-choose-section">
        <div className="why-choose-container">
          <div className="why-choose-content">
            <span className="section-badge section-badge-light">🏆 Why Us</span>
            <h2 className="why-choose-title">Why Choose Virtual Career Fair?</h2>
            <p className="why-choose-subtitle">
              Join thousands of students and job seekers who have found their dream careers through our innovative virtual platform.
            </p>

            <ul className="benefits-list">
              <li className="benefit-item">
                <span className="check-icon">✓</span>
                <span>Access career fairs from anywhere in the world</span>
              </li>
              <li className="benefit-item">
                <span className="check-icon">✓</span>
                <span>Connect with multiple companies in one place</span>
              </li>
              <li className="benefit-item">
                <span className="check-icon">✓</span>
                <span>Save time and travel costs</span>
              </li>
              <li className="benefit-item">
                <span className="check-icon">✓</span>
                <span>Get instant responses from recruiters</span>
              </li>
              <li className="benefit-item">
                <span className="check-icon">✓</span>
                <span>Build your professional network online</span>
              </li>
            </ul>

            <Link to="/signup" className="btn-create-account">
              Create Free Account →
            </Link>
          </div>

          <div className="stats-grid">
            <div className="home-stat-card">
              <div className="home-stat-number">500+</div>
              <div className="home-stat-label">Companies</div>
            </div>
            <div className="home-stat-card">
              <div className="home-stat-number">10K+</div>
              <div className="home-stat-label">Students</div>
            </div>
            <div className="home-stat-card">
              <div className="home-stat-number">100+</div>
              <div className="home-stat-label">Career Fairs</div>
            </div>
            <div className="home-stat-card">
              <div className="home-stat-number">5K+</div>
              <div className="home-stat-label">Jobs Posted</div>
            </div>
          </div>
        </div>
      </section>

      {/* CTA Section */}
      <section className="cta-section">
        <div className="cta-glow"></div>
        <div className="cta-content">
          <h2 className="cta-title">Ready to Start Your Career Journey?</h2>
          <p className="cta-subtitle">Join our platform today and connect with top employers</p>
          <div className="cta-buttons">
            <Link to="/signup" className="btn-cta-primary">Sign Up Now →</Link>
            <Link to="/booths" className="btn-cta-secondary">Explore Jobs</Link>
          </div>
        </div>
      </section>
    </div>
  );
}
