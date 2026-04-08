import { Link, useLocation } from "react-router-dom";
import { useState, useEffect } from "react";

export default function Navbar() {
  const [isDarkTheme, setIsDarkTheme] = useState(false);
  const [scrolled, setScrolled] = useState(false);
  const location = useLocation();

  useEffect(() => {
    const savedTheme = localStorage.getItem('theme');
    if (savedTheme === 'dark') {
      setIsDarkTheme(true);
      document.body.classList.add('dark-theme');
    }
  }, []);

  useEffect(() => {
    const handleScroll = () => setScrolled(window.scrollY > 10);
    window.addEventListener("scroll", handleScroll);
    return () => window.removeEventListener("scroll", handleScroll);
  }, []);

  const toggleTheme = () => {
    setIsDarkTheme(!isDarkTheme);
    if (!isDarkTheme) {
      document.body.classList.add('dark-theme');
      localStorage.setItem('theme', 'dark');
    } else {
      document.body.classList.remove('dark-theme');
      localStorage.setItem('theme', 'light');
    }
  };

  const navLinks = [
    { to: "/", label: "Home", icon: "🏠" },
    { to: "/career-fairs", label: "Career Fairs", icon: "📅" },
    { to: "/booths", label: "Booths", icon: "🏢" },
  ];

  return (
    <header className={`navbar ${scrolled ? "navbar-scrolled" : ""}`}>
      <div className="navbar-wrapper">
        {/* Logo */}
        <Link to="/" className="navbar-logo">
          <div className="logo-badge">
            <span className="logo-icon-text">VC</span>
          </div>
          <div className="navbar-brand">
            <span className="navbar-title">VirtualCareerFair</span>
            <span className="navbar-tagline">Connect & Grow</span>
          </div>
        </Link>

        {/* Center Nav Links */}
        <nav className="navbar-center">
          {navLinks.map((link) => (
            <Link
              key={link.to}
              to={link.to}
              className={`nav-item ${location.pathname === link.to ? "nav-item-active" : ""}`}
            >
              <span className="nav-item-icon">{link.icon}</span>
              <span className="nav-item-label">{link.label}</span>
              {location.pathname === link.to && <span className="nav-item-indicator" />}
            </Link>
          ))}
        </nav>

        {/* Right Actions */}
        <div className="navbar-actions">
          <div className="theme-toggle-container">
            <label className="theme-toggle">
              <input
                type="checkbox"
                checked={isDarkTheme}
                onChange={toggleTheme}
              />
              <span className="toggle-slider"></span>
              <span className="toggle-icon sun">☀️</span>
              <span className="toggle-icon moon">🌙</span>
            </label>
          </div>
          <Link to="/login" className="nav-login-btn">
            Login
          </Link>
          <Link to="/signup" className="nav-signup-btn">
            Sign Up
            <span className="signup-arrow">→</span>
          </Link>
        </div>
      </div>
    </header>
  );
}