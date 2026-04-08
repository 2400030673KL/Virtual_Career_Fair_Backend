import { useState } from "react";

export default function Chat() {
  const [msg, setMsg] = useState("");
  const [messages, setMessages] = useState([]);

  return (
    <>
      <h4>Live Chat</h4>
      <div className="chat-box">
        {messages.map((m, i) => (
          <p key={i}>{m}</p>
        ))}
      </div>
      <input value={msg} onChange={(e) => setMsg(e.target.value)} />
      <button onClick={() => { setMessages([...messages, msg]); setMsg(""); }}>
        Send
      </button>
    </>
  );
}