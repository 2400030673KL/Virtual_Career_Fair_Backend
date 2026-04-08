import Chat from "./Chat";
import ResumeUpload from "./ResumeUpload";

export default function Booth() {
  return (
    <div className="center-wrapper">
      <div className="card">
        <h2>Company Booth</h2>
        <ResumeUpload />
        <Chat />
      </div>
    </div>
  );
}