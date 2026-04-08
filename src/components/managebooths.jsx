export default function ManageBooths() {
  return (
    <div className="center-wrapper">
      <div className="card">
        <h2>Manage Company Booths</h2>
        <input placeholder="Company Name" />
        <button>Add Booth</button>

        <ul className="list">
          <li>Google</li>
          <li>Amazon</li>
          <li>Microsoft</li>
        </ul>
      </div>
    </div>
  );
}