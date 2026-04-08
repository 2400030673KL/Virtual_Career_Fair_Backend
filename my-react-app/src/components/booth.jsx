import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import Chat from "./Chat";
import ResumeUpload from "./ResumeUpload";
import { api } from "../lib/api";

export default function Booth() {
  const { id } = useParams();
  const [booth, setBooth] = useState(null);
  const [error, setError] = useState("");

  useEffect(() => {
    setError("");
    setBooth(null);

    api.get(`/booths/${id}`)
      .then(setBooth)
      .catch((requestError) => setError(requestError.message || "Booth not found"));
  }, [id]);

  if (error) {
    return (
      <div className="center-wrapper">
        <div className="card">
          <h2>Company Booth</h2>
          <p>{error}</p>
        </div>
      </div>
    );
  }

  if (!booth) {
    return (
      <div className="center-wrapper">
        <div className="card">
          <h2>Company Booth</h2>
          <p>Loading booth details...</p>
        </div>
      </div>
    );
  }

  return (
    <div className="center-wrapper">
      <div className="card">
        <h2>{booth.name} Booth</h2>
        <p>{booth.tagline}</p>
        <ResumeUpload company={booth.name} />
        <Chat boothId={booth.id} senderLabel={booth.name} />
      </div>
    </div>
  );
}