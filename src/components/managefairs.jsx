export default function ManageFairs() {
  return (
    <div className="center-wrapper">
      <div className="card">
        <h2>Manage Career Fairs</h2>
        <input placeholder="Career Fair Name" />
        <input type="date" />
        <button>Add Career Fair</button>

        <ul className="list">
          <li>Tech Career Fair – 25 Mar 2026</li>
          <li>IT Jobs Expo – 10 Apr 2026</li>
        </ul>
      </div>
    </div>
  );
}