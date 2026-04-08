import { useState } from "react";
import { api } from "../lib/api";

export default function ResumeUpload({ company = "Career Fair Booth" }) {
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    position: "",
    summary: "",
    fileName: "",
  });
  const [submitted, setSubmitted] = useState(false);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleFileChange = (e) => {
    if (e.target.files[0]) {
      setFormData({ ...formData, fileName: e.target.files[0].name });
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setLoading(true);

    try {
      await api.post("/resumes", {
        ...formData,
        company,
      });

      setSubmitted(true);
      window.setTimeout(() => setSubmitted(false), 3000);
      setFormData({ name: "", email: "", position: "", summary: "", fileName: "" });
    } catch (requestError) {
      setError(requestError.message || "Unable to submit resume");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="resume-upload-section">
      <h3 className="resume-upload-title">📄 Submit Your Resume</h3>
      <p className="resume-upload-company">Applying to {company}</p>
      {error && <div className="form-error">{error}</div>}
      {submitted && (
        <div className="resume-success-msg">
          ✅ Resume submitted successfully! Recruiters can now review it.
        </div>
      )}
      <form onSubmit={handleSubmit} className="resume-upload-form">
        <div className="ru-form-group">
          <label>Full Name</label>
          <input
            type="text"
            name="name"
            placeholder="Your full name"
            value={formData.name}
            onChange={handleChange}
            required
          />
        </div>
        <div className="ru-form-group">
          <label>Email</label>
          <input
            type="email"
            name="email"
            placeholder="your.email@example.com"
            value={formData.email}
            onChange={handleChange}
            required
          />
        </div>
        <div className="ru-form-group">
          <label>Position Applying For</label>
          <input
            type="text"
            name="position"
            placeholder="e.g. Software Engineer"
            value={formData.position}
            onChange={handleChange}
            required
          />
        </div>
        <div className="ru-form-group">
          <label>Professional Summary</label>
          <textarea
            name="summary"
            placeholder="Brief summary of your skills and experience..."
            value={formData.summary}
            onChange={handleChange}
            rows={4}
            required
          />
        </div>
        <div className="ru-form-group">
          <label>Upload Resume</label>
          <input
            type="file"
            accept=".pdf,.doc,.docx"
            onChange={handleFileChange}
            required
          />
        </div>
        <button type="submit" className="ru-submit-btn" disabled={loading}>
          {loading ? "Submitting..." : "Submit Resume"}
        </button>
      </form>
    </div>
  );
}