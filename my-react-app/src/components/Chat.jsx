import { useEffect, useState } from "react";
import { api } from "../lib/api";

export default function Chat({ boothId = "general", senderLabel = "Visitor" }) {
  const [msg, setMsg] = useState("");
  const [messages, setMessages] = useState([]);

  const loadMessages = () => {
    api.get(`/chat/${boothId}`)
      .then(setMessages)
      .catch(() => setMessages([]));
  };

  useEffect(() => {
    loadMessages();
    const intervalId = window.setInterval(loadMessages, 5000);
    return () => window.clearInterval(intervalId);
  }, [boothId]);

  const handleSend = async () => {
    const trimmed = msg.trim();
    if (!trimmed) {
      return;
    }

    try {
      const message = await api.post(`/chat/${boothId}`, {
        sender: senderLabel,
        message: trimmed,
      });
      setMessages((current) => [...current, message]);
      setMsg("");
    } catch {
      setMessages((current) => [...current, {
        id: `local-${Date.now()}`,
        boothId,
        sender: senderLabel,
        message: trimmed,
        timestamp: new Date().toISOString(),
      }]);
      setMsg("");
    }
  };

  return (
    <>
      <h4>Live Chat</h4>
      <div className="chat-box">
        {messages.map((message) => (
          <p key={message.id}>
            <strong>{message.sender}:</strong> {message.message}
          </p>
        ))}
      </div>
      <input value={msg} onChange={(e) => setMsg(e.target.value)} placeholder="Type a message" />
      <button onClick={handleSend}>
        Send
      </button>
    </>
  );
}