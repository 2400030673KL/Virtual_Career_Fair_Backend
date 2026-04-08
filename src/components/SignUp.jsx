import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";

export default function SignUp() {
  const navigate = useNavigate();
  const [userType, setUserType] = useState("student");
  const [isPasswordFocus, setIsPasswordFocus] = useState(false);

  const [name, setName] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    localStorage.setItem("userType", userType);
    localStorage.setItem("userName", name);

    if (userType === "recruiter") {
      navigate("/recruiter/dashboard");
    } else {
      navigate("/booths");
    }
  };

  return (
    <div className="signup-page">
      <div className="signup-container">
        <div className="auth-bg-text">
          <h2 className="auth-bg-title">Start Your Journey</h2>
          <p className="auth-bg-subtitle">
            Join thousands of professionals who found their dream careers through our platform
          </p>
          <div className="auth-bg-features">
            <div className="auth-bg-feature">✓ Free to Join</div>
            <div className="auth-bg-feature">✓ Instant Access</div>
            <div className="auth-bg-feature">✓ Top Employers</div>
          </div>
        </div>

        <div className="signup-card">
          <div className="signup-icon">
            <span>💼</span>
          </div>

          <h2 className="signup-title">Create Your Account</h2>
          <p className="signup-subtitle">Join our virtual career fair platform</p>

          <form onSubmit={handleSubmit} className="signup-form">
            <div className="form-group">
              <label className="form-label">Full Name</label>
              <input
                type="text"
                placeholder="John Doe"
                className="form-input"
                value={name}
                onChange={(e) => setName(e.target.value)}
                required
              />
            </div>

            <div className="form-group">
              <label className="form-label">Email</label>
              <input
                type="email"
                placeholder="your.email@example.com"
                className="form-input"
                required
              />
            </div>

            <div className="form-group">
              <label className="form-label">Password</label>
              <input
                type="password"
                placeholder="••••••••"
                className="form-input"
                onFocus={() => setIsPasswordFocus(true)}
                onBlur={() => setIsPasswordFocus(false)}
                required
              />
              <p className="form-hint">Must be at least 6 characters</p>
            </div>

            <div className="form-group">
              <label className="form-label">I am a...</label>

              <div className="radio-group">
                <label className={`radio-option ${userType === 'student' ? 'selected' : ''}`}>
                  <input
                    type="radio"
                    name="userType"
                    value="student"
                    checked={userType === 'student'}
                    onChange={(e) => setUserType(e.target.value)}
                  />
                  <div className="radio-content">
                    <span className="radio-title">Student / Job Seeker</span>
                    <span className="radio-desc">Looking for career opportunities</span>
                  </div>
                </label>

                <label className={`radio-option ${userType === 'recruiter' ? 'selected' : ''}`}>
                  <input
                    type="radio"
                    name="userType"
                    value="recruiter"
                    checked={userType === 'recruiter'}
                    onChange={(e) => setUserType(e.target.value)}
                  />
                  <div className="radio-content">
                    <span className="radio-title">Recruiter</span>
                    <span className="radio-desc">Representing a company</span>
                  </div>
                </label>
              </div>
            </div>

            <button type="submit" className="signup-button">
              Sign Up
            </button>

            <p className="signup-footer">
              Already have an account? <Link to="/login">Login</Link>
            </p>
          </form>
        </div>
      </div>
    </div>
  );
}
