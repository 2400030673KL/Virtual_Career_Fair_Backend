# Railway MySQL + Backend Setup

## 1) Deploy services on Railway

1. Create a Railway project.
2. Add a MySQL service in the same project.
3. Deploy this backend service from the `backend` folder.

Railway automatically injects MySQL variables such as `MYSQLHOST`, `MYSQLPORT`, `MYSQLDATABASE`, `MYSQLUSER`, `MYSQLPASSWORD`, and often `MYSQL_URL`.

This backend already supports both:
- `DATABASE_URL`, `DATABASE_USERNAME`, `DATABASE_PASSWORD`
- Railway-native `MYSQL_*` variables

## 2) Required backend variables

Set these in Railway backend service:

- `JWT_SECRET` = long random secret (at least 32 bytes)
- `JWT_EXPIRATION_MS` = `86400000` (or preferred)
- `CORS_ORIGIN` = frontend domain (can be comma-separated)

Examples:
- `https://your-frontend.vercel.app`
- `https://your-frontend.vercel.app,https://your-alt-domain.app`

## 3) Frontend API URL

Set frontend environment variable:

- `VITE_API_BASE_URL=https://<your-backend-domain>/api`

## 4) Connect MySQL Workbench to Railway DB

In Railway MySQL service, open connection details and use these values in MySQL Workbench:

- Connection Method: Standard TCP/IP
- Hostname: `MYSQLHOST`
- Port: `MYSQLPORT`
- Username: `MYSQLUSER`
- Password: `MYSQLPASSWORD`
- Default Schema: `MYSQLDATABASE`

If Railway provides only `MYSQL_URL`, parse host/port/database/user from it or use Railway's individual variable view.

## 5) Verification checklist

1. Backend deploy logs show app started on port from `PORT`.
2. Call backend health-like endpoint: `GET /api/booths` returns data.
3. Signup/login works and returns token.
4. MySQL Workbench can connect and tables exist (`users`, `booths`, `career_fairs`, `resumes`, `chat_messages`, `registrations`).
