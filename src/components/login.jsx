import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";

export default function Login() {
  const navigate = useNavigate();
  const [step, setStep] = useState(1); // 1 = role selection, 2 = credentials
  const [userType, setUserType] = useState("");
  const [isPasswordFocus, setIsPasswordFocus] = useState(false);

  const handleRoleSelect = (role) => {
    setUserType(role);
    setStep(2);
  };

  const handleBack = () => {
    setStep(1);
    setUserType("");
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    localStorage.setItem("userType", userType);

    if (userType === "recruiter") {
      navigate("/recruiter/dashboard");
    } else {
      navigate("/booths");
    }
  };

  return (
    <div className="login-page">
      <div className="login-container">
        <div className="auth-bg-text">
          <h2 className="auth-bg-title">Welcome to VirtualCareerFair</h2>
          <p className="auth-bg-subtitle">
            Connect with top employers and discover your dream career opportunities
          </p>
          <div className="auth-bg-features">
            <div className="auth-bg-feature">✓ 500+ Companies</div>
            <div className="auth-bg-feature">✓ 10K+ Job Seekers</div>
            <div className="auth-bg-feature">✓ 100+ Career Fairs</div>
          </div>
        </div>

        <div className="login-card">
          {step === 1 ? (
            /* ===== STEP 1: Role Selection ===== */
            <>
              <div className="login-icon">
                <span>💼</span>
              </div>

              <h2 className="login-title">Welcome Back</h2>
              <p className="login-subtitle">How would you like to sign in?</p>

              <div className="login-role-options">
                <button
                  className="login-role-card"
                  onClick={() => handleRoleSelect("student")}
                >
                  <div className="login-role-icon login-role-student">🎓</div>
                  <div className="login-role-info">
                    <span className="login-role-title">Student / Job Seeker</span>
                    <span className="login-role-desc">
                      Browse companies and apply to positions
                    </span>
                  </div>
                  <span className="login-role-arrow">→</span>
                </button>

                <button
                  className="login-role-card"
                  onClick={() => handleRoleSelect("recruiter")}
                >
                  <div className="login-role-icon login-role-recruiter">👔</div>
                  <div className="login-role-info">
                    <span className="login-role-title">Recruiter</span>
                    <span className="login-role-desc">
                      Review submitted resumes and hire talent
                    </span>
                  </div>
                  <span className="login-role-arrow">→</span>
                </button>
              </div>

              <p className="login-footer">
                Don't have an account? <Link to="/signup">Sign up</Link>
              </p>
            </>
          ) : (
            /* ===== STEP 2: Credentials ===== */
            <>
              <button className="login-back-btn" onClick={handleBack}>
                ← Back
              </button>

              <div className="login-icon">
                <span>{userType === "recruiter" ? "👔" : "🎓"}</span>
              </div>

              <h2 className="login-title">
                {userType === "recruiter" ? "Recruiter Login" : "Student Login"}
              </h2>
              <p className="login-subtitle">
                {userType === "recruiter"
                  ? "Sign in to review candidate resumes"
                  : "Sign in to explore companies and apply"}
              </p>

              <form onSubmit={handleSubmit} className="login-form">
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
                </div>

                <button type="submit" className="login-button">
                  {userType === "recruiter"
                    ? "Sign in as Recruiter"
                    : "Sign in as Student"}
                </button>

                <p className="login-footer">
                  Don't have an account? <Link to="/signup">Sign up</Link>
                </p>
              </form>
            </>
          )}
        </div>
      </div>
    </div>
  );
}