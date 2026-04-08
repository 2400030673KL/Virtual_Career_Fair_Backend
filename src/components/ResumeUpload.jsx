import { useState } from "react";

export default function ResumeUpload() {
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    position: "",
    summary: "",
    fileName: "",
  });
  const [submitted, setSubmitted] = useState(false);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleFileChange = (e) => {
    if (e.target.files[0]) {
      setFormData({ ...formData, fileName: e.target.files[0].name });
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    const newResume = {
      id: "resume-" + Date.now(),
      ...formData,
      company: "Career Fair Booth",
      date: new Date().toISOString().split("T")[0],
      status: "pending",
    };

    const existing = JSON.parse(
      localStorage.getItem("submitted_resumes") || "[]"
    );
    existing.push(newResume);
    localStorage.setItem("submitted_resumes", JSON.stringify(existing));

    setSubmitted(true);
    setTimeout(() => setSubmitted(false), 3000);
    setFormData({ name: "", email: "", position: "", summary: "", fileName: "" });
  };

  return (
    <div className="resume-upload-section">
      <h3 className="resume-upload-title">📄 Submit Your Resume</h3>
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
        <button type="submit" className="ru-submit-btn">
          Submit Resume
        </button>
      </form>
    </div>
  );
}