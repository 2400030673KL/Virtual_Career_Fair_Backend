package com.virtualcareerfair.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtualcareerfair.dto.AdminLoginRequest;
import com.virtualcareerfair.dto.AuthRequest;
import com.virtualcareerfair.dto.AuthResponse;
import com.virtualcareerfair.dto.Booth;
import com.virtualcareerfair.dto.CareerFair;
import com.virtualcareerfair.dto.ChatMessage;
import com.virtualcareerfair.dto.ChatMessageRequest;
import com.virtualcareerfair.dto.ColorPalette;
import com.virtualcareerfair.dto.LoginRequest;
import com.virtualcareerfair.dto.Registration;
import com.virtualcareerfair.dto.ResumeApplication;
import com.virtualcareerfair.dto.ResumeCreateRequest;
import com.virtualcareerfair.dto.ResumeStatusRequest;
import com.virtualcareerfair.dto.UserSummary;
import io.jsonwebtoken.JwtException;
import jakarta.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class InMemoryStore {
    private static final String ADMIN_EMAIL = "admin@virtualcareerfair.com";
    private static final String ADMIN_PASSWORD = "Admin@123";

    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;
    private final JwtService jwtService;

    public InMemoryStore(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper, JwtService jwtService) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
        this.jwtService = jwtService;
    }

    @PostConstruct
    public void initialize() {
        seedUsers();
        seedBooths();
        seedCareerFairs();
        seedResumes();
        seedRegistrations();
    }

    @Transactional
    public synchronized AuthResponse register(AuthRequest request) {
        String emailKey = normalize(request.email());
        String role = normalizeRole(request.role());
        if (userExists(emailKey)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }

        RegisteredUser user = new RegisteredUser(
                UUID.randomUUID().toString(),
                request.name(),
                emailKey,
                request.password().trim(),
                role);
        jdbcTemplate.update(
                "INSERT INTO users (id, name, email, password, role) VALUES (?, ?, ?, ?, ?)",
                user.id(), user.name(), user.email(), user.password(), user.role());
        return successResponse(user, "Registration successful");
    }

    public synchronized AuthResponse login(LoginRequest request) {
        String email = normalize(request.email());
        String password = request.password() == null ? "" : request.password().trim();
        String role = normalizeRole(request.role());
        RegisteredUser user = getUserByEmail(email);
        if (user == null || !user.password().equals(password) || !user.role().equalsIgnoreCase(role)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
        return successResponse(user, "Login successful");
    }

    public synchronized AuthResponse adminLogin(AdminLoginRequest request) {
        if (!ADMIN_EMAIL.equalsIgnoreCase(request.email()) || !ADMIN_PASSWORD.equals(request.password())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid admin credentials");
        }
        RegisteredUser admin = new RegisteredUser("admin", "Administrator", ADMIN_EMAIL, ADMIN_PASSWORD, "admin");
        return successResponse(admin, "Admin login successful");
    }

    public synchronized AuthResponse authenticate(String token) {
        if (isBlank(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing token");
        }

        try {
            UserSummary user = jwtService.parseUser(token);
            return new AuthResponse(true, "Token valid", token, user);
        } catch (JwtException | IllegalArgumentException exception) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired token");
        }
    }

    public synchronized List<CareerFair> listCareerFairs() {
        return jdbcTemplate.query(
                "SELECT * FROM career_fairs ORDER BY name",
                (resultSet, rowNum) -> mapCareerFair(
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getString("date_value"),
                        resultSet.getString("time_value"),
                        resultSet.getString("location"),
                        resultSet.getString("description"),
                        resultSet.getString("companies_json"),
                        resultSet.getString("status"),
                        resultSet.getInt("registrations"),
                        resultSet.getString("category")));
    }

    public synchronized CareerFair getCareerFair(String id) {
        List<CareerFair> fairs = jdbcTemplate.query(
                "SELECT * FROM career_fairs WHERE id = ?",
                (resultSet, rowNum) -> mapCareerFair(
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getString("date_value"),
                        resultSet.getString("time_value"),
                        resultSet.getString("location"),
                        resultSet.getString("description"),
                        resultSet.getString("companies_json"),
                        resultSet.getString("status"),
                        resultSet.getInt("registrations"),
                        resultSet.getString("category")),
                id);
        if (fairs.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Career fair not found");
        }
        return fairs.get(0);
    }

    @Transactional
    public synchronized CareerFair saveCareerFair(CareerFair fair) {
        String id = isBlank(fair.id()) ? nextId("fair") : fair.id();
        jdbcTemplate.update(
                "INSERT INTO career_fairs (id, name, date_value, time_value, location, description, companies_json, status, registrations, category) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE name = VALUES(name), date_value = VALUES(date_value), time_value = VALUES(time_value), location = VALUES(location), description = VALUES(description), companies_json = VALUES(companies_json), status = VALUES(status), registrations = VALUES(registrations), category = VALUES(category)",
                id, fair.name(), fair.date(), fair.time(), fair.location(), fair.description(), writeJson(fair.companies()), fair.status(), fair.registrations(), fair.category());
        return mapCareerFair(id, fair.name(), fair.date(), fair.time(), fair.location(), fair.description(), writeJson(fair.companies()), fair.status(), fair.registrations(), fair.category());
    }

    public synchronized void deleteCareerFair(String id) {
        int updated = jdbcTemplate.update("DELETE FROM career_fairs WHERE id = ?", id);
        if (updated == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Career fair not found");
        }
    }

    public synchronized List<Booth> listBooths() {
        return jdbcTemplate.query(
                "SELECT * FROM booths ORDER BY name",
                (resultSet, rowNum) -> mapBooth(
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getString("industry"),
                        resultSet.getString("logo"),
                        resultSet.getString("tagline"),
                        resultSet.getString("description"),
                        resultSet.getInt("open_roles"),
                        resultSet.getString("location"),
                        resultSet.getString("type"),
                        resultSet.getDouble("rating"),
                        resultSet.getString("employees"),
                        resultSet.getString("positions_json"),
                        resultSet.getString("perks_json"),
                        resultSet.getString("color_json")));
    }

    public synchronized Booth getBooth(String id) {
        List<Booth> booths = jdbcTemplate.query(
                "SELECT * FROM booths WHERE id = ?",
                (resultSet, rowNum) -> mapBooth(
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getString("industry"),
                        resultSet.getString("logo"),
                        resultSet.getString("tagline"),
                        resultSet.getString("description"),
                        resultSet.getInt("open_roles"),
                        resultSet.getString("location"),
                        resultSet.getString("type"),
                        resultSet.getDouble("rating"),
                        resultSet.getString("employees"),
                        resultSet.getString("positions_json"),
                        resultSet.getString("perks_json"),
                        resultSet.getString("color_json")),
                id);
        if (booths.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Booth not found");
        }
        return booths.get(0);
    }

    @Transactional
    public synchronized Booth saveBooth(Booth booth) {
        String id = isBlank(booth.id()) ? nextId("booth") : booth.id();
        ColorPalette color = booth.color() == null ? new ColorPalette("#1a73e8", "#e8f0fe", "#c6dafc") : booth.color();
        jdbcTemplate.update(
                "INSERT INTO booths (id, name, industry, logo, tagline, description, open_roles, location, type, rating, employees, positions_json, perks_json, color_json) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE name = VALUES(name), industry = VALUES(industry), logo = VALUES(logo), tagline = VALUES(tagline), description = VALUES(description), open_roles = VALUES(open_roles), location = VALUES(location), type = VALUES(type), rating = VALUES(rating), employees = VALUES(employees), positions_json = VALUES(positions_json), perks_json = VALUES(perks_json), color_json = VALUES(color_json)",
                id, booth.name(), booth.industry(), booth.logo(), booth.tagline(), booth.description(), booth.openRoles(), booth.location(), booth.type(), booth.rating(), booth.employees(), writeJson(booth.positions()), writeJson(booth.perks()), writeJson(color));
        return mapBooth(id, booth.name(), booth.industry(), booth.logo(), booth.tagline(), booth.description(), booth.openRoles(), booth.location(), booth.type(), booth.rating(), booth.employees(), writeJson(booth.positions()), writeJson(booth.perks()), writeJson(color));
    }

    public synchronized void deleteBooth(String id) {
        int updated = jdbcTemplate.update("DELETE FROM booths WHERE id = ?", id);
        if (updated == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Booth not found");
        }
    }

    public synchronized List<ResumeApplication> listResumes() {
        return jdbcTemplate.query(
                "SELECT * FROM resumes ORDER BY date_value DESC",
            (resultSet, rowNum) -> new ResumeApplication(
                resultSet.getString("id"),
                resultSet.getString("name"),
                resultSet.getString("email"),
                resultSet.getString("position"),
                resultSet.getString("company"),
                resultSet.getString("summary"),
                resultSet.getString("file_name"),
                resultSet.getString("date_value"),
                resultSet.getString("status")));
    }

    @Transactional
    public synchronized ResumeApplication saveResume(ResumeCreateRequest request) {
        ResumeApplication resume = new ResumeApplication(
                nextId("resume"),
                request.name(),
                request.email(),
                request.position(),
                request.company(),
                request.summary(),
                request.fileName(),
                LocalDate.now().toString(),
                "pending");
        jdbcTemplate.update(
                "INSERT INTO resumes (id, name, email, position, company, summary, file_name, date_value, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                resume.id(), resume.name(), resume.email(), resume.position(), resume.company(), resume.summary(), resume.fileName(), resume.date(), resume.status());
        jdbcTemplate.update(
                "INSERT INTO registrations (id, name, email, target, status, registered_at) VALUES (?, ?, ?, ?, ?, ?)",
                nextId("registration"), request.name(), request.email(), request.company(), "submitted", nowStamp());
        return resume;
    }

    @Transactional
    public synchronized ResumeApplication updateResumeStatus(String id, ResumeStatusRequest request) {
        ResumeApplication existing = jdbcTemplate.query(
                "SELECT * FROM resumes WHERE id = ?",
            (resultSet, rowNum) -> new ResumeApplication(
                resultSet.getString("id"),
                resultSet.getString("name"),
                resultSet.getString("email"),
                resultSet.getString("position"),
                resultSet.getString("company"),
                resultSet.getString("summary"),
                resultSet.getString("file_name"),
                resultSet.getString("date_value"),
                resultSet.getString("status")),
                id).stream().findFirst().orElse(null);
        if (existing == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resume not found");
        }

        jdbcTemplate.update("UPDATE resumes SET status = ? WHERE id = ?", request.status(), id);
        return new ResumeApplication(existing.id(), existing.name(), existing.email(), existing.position(), existing.company(), existing.summary(), existing.fileName(), existing.date(), request.status());
    }

    public synchronized List<ChatMessage> listMessages(String boothId) {
        return jdbcTemplate.query(
                "SELECT * FROM chat_messages WHERE booth_id = ? ORDER BY timestamp_value ASC",
                (resultSet, rowNum) -> new ChatMessage(resultSet.getString("id"), resultSet.getString("booth_id"), resultSet.getString("sender"), resultSet.getString("message"), resultSet.getString("timestamp_value")),
                boothId);
    }

    @Transactional
    public synchronized ChatMessage addMessage(String boothId, ChatMessageRequest request) {
        ChatMessage message = new ChatMessage(nextId("chat"), boothId, request.sender(), request.message(), nowStamp());
        jdbcTemplate.update(
                "INSERT INTO chat_messages (id, booth_id, sender, message, timestamp_value) VALUES (?, ?, ?, ?, ?)",
                message.id(), message.boothId(), message.sender(), message.message(), message.timestamp());
        return message;
    }

    public synchronized List<Registration> listRegistrations() {
        return jdbcTemplate.query(
                "SELECT * FROM registrations ORDER BY registered_at DESC",
                (resultSet, rowNum) -> new Registration(resultSet.getString("id"), resultSet.getString("name"), resultSet.getString("email"), resultSet.getString("target"), resultSet.getString("status"), resultSet.getString("registered_at")));
    }

    private void seedUsers() {
        if (countRows("users") > 0) {
            return;
        }
        saveUser(new RegisteredUser("user-1", "Student User", "student@demo.com", "Password123", "student"));
        saveUser(new RegisteredUser("user-2", "Recruiter User", "recruiter@demo.com", "Password123", "recruiter"));
    }

    private void seedBooths() {
        if (countRows("booths") > 0) {
            return;
        }
        saveBooth(new Booth("0", "Google", "Technology", "🔵", "Organize the world's information",
                "Google LLC is a multinational technology company specializing in Internet-related services and products. Join one of the most innovative teams in the world.",
                24, "Mountain View, CA", "Full-time / Internship", 4.8, "180,000+",
                listOf("Software Engineer", "Data Scientist", "Product Manager", "UX Designer"),
                listOf("Remote Work", "Health Insurance", "401(k)", "Free Meals"),
                new ColorPalette("#4285F4", "#e8f0fe", "#c6dafc")));
        saveBooth(new Booth("1", "Amazon", "E-Commerce & Cloud", "🟠", "Work hard. Have fun. Make history.",
                "Amazon is guided by four principles: customer obsession, passion for invention, commitment to operational excellence, and long-term thinking.",
                38, "Seattle, WA", "Full-time / Contract", 4.5, "1,500,000+",
                listOf("Cloud Architect", "Full Stack Developer", "DevOps Engineer", "Solutions Architect"),
                listOf("Stock Options", "Relocation", "Learning Budget", "Flex Hours"),
                new ColorPalette("#FF9900", "#fff4e6", "#ffe0b2")));
        saveBooth(new Booth("2", "Microsoft", "Software & Cloud", "🟢", "Empower every person and organization",
                "Microsoft enables digital transformation for the era of an intelligent cloud and an intelligent edge. Build what matters at global scale.",
                31, "Redmond, WA", "Full-time / Internship", 4.7, "220,000+",
                listOf("Azure Engineer", "AI Researcher", "Program Manager", "Security Analyst"),
                listOf("Hybrid Work", "Wellness Benefits", "Tuition Aid", "Parental Leave"),
                new ColorPalette("#00A4EF", "#e6f7ff", "#b3e5fc")));
        saveBooth(new Booth("3", "Meta", "Social Technology", "🔷", "Building the future of connection",
                "Meta builds technologies that help people connect, find communities, and grow businesses. Join us to shape the metaverse and next-gen social platforms.",
                19, "Menlo Park, CA", "Full-time", 4.4, "86,000+",
                listOf("React Developer", "ML Engineer", "VR Developer", "Data Analyst"),
                listOf("Equity", "Free Meals", "Fitness Center", "Childcare"),
                new ColorPalette("#0668E1", "#e7f0ff", "#bbd5fc")));
        saveBooth(new Booth("4", "Apple", "Consumer Electronics", "⚫", "Think different.",
                "Apple designs, manufactures, and markets smartphones, personal computers, tablets, wearables, and accessories. Innovation is at the core of everything we do.",
                15, "Cupertino, CA", "Full-time", 4.9, "164,000+",
                listOf("iOS Developer", "Hardware Engineer", "Design Lead", "Machine Learning"),
                listOf("Product Discounts", "RSUs", "Health Benefits", "Education"),
                new ColorPalette("#333333", "#f5f5f5", "#e0e0e0")));
        saveBooth(new Booth("5", "Tesla", "Automotive & Energy", "🔴", "Accelerating sustainable energy",
                "Tesla's mission is to accelerate the world's transition to sustainable energy. Work on cutting-edge electric vehicles, battery technology, and solar energy.",
                22, "Austin, TX", "Full-time / Co-op", 4.3, "127,000+",
                listOf("Embedded Systems", "Autopilot Engineer", "Battery Researcher", "Manufacturing"),
                listOf("Stock Options", "EV Discount", "Innovation Culture", "Growth"),
                new ColorPalette("#CC0000", "#fef2f2", "#fecaca")));
    }

    private void seedCareerFairs() {
        if (countRows("career_fairs") > 0) {
            return;
        }
        saveCareerFair(new CareerFair("1", "Tech Career Fair 2026", "2026-03-25", "10:00 AM - 4:00 PM", "Virtual",
                "Connect with leading tech companies including Google, Microsoft, Amazon, and more. Explore software engineering, data science, and AI roles.",
                listOf("Google", "Microsoft", "Amazon", "Meta", "Apple"), "upcoming", 1245, "Technology"));
        saveCareerFair(new CareerFair("2", "IT Jobs Expo 2026", "2026-04-10", "9:00 AM - 3:00 PM", "Virtual",
                "The largest virtual IT job expo bringing together top employers and talented professionals. Roles in cybersecurity, cloud computing, and DevOps.",
                listOf("IBM", "Oracle", "Cisco", "Salesforce", "SAP"), "upcoming", 890, "Information Technology"));
        saveCareerFair(new CareerFair("3", "Finance & Banking Career Fair", "2026-05-15", "11:00 AM - 5:00 PM", "Virtual",
                "Meet recruiters from the world's leading financial institutions. Opportunities in investment banking, fintech, risk management, and consulting.",
                listOf("Goldman Sachs", "JP Morgan", "Morgan Stanley", "Deloitte", "EY"), "upcoming", 675, "Finance"));
        saveCareerFair(new CareerFair("4", "Healthcare & Life Sciences Fair", "2026-06-20", "10:00 AM - 4:00 PM", "Virtual",
                "Explore career opportunities in healthcare, pharmaceuticals, and biotechnology with leading organizations worldwide.",
                listOf("Pfizer", "Johnson & Johnson", "Mayo Clinic", "Merck", "Abbott"), "upcoming", 520, "Healthcare"));
        saveCareerFair(new CareerFair("5", "Startup & Innovation Career Fair", "2026-07-08", "12:00 PM - 6:00 PM", "Virtual",
                "Connect with fast-growing startups and innovative companies. Perfect for those looking to make an impact in a dynamic work environment.",
                listOf("Stripe", "Notion", "Figma", "Vercel", "Supabase"), "upcoming", 430, "Startups"));
        saveCareerFair(new CareerFair("6", "Engineering & Manufacturing Expo", "2026-08-12", "9:00 AM - 3:00 PM", "Virtual",
                "Discover opportunities in engineering, manufacturing, and industrial design. Meet recruiters from top engineering firms and manufacturers.",
                listOf("Tesla", "Boeing", "SpaceX", "Siemens", "GE"), "upcoming", 780, "Engineering"));
    }

    private void seedResumes() {
        if (countRows("resumes") > 0) {
            return;
        }
        seedResume(new ResumeCreateRequest("Alice Johnson", "alice.johnson@email.com", "Software Engineer", "Google",
                "Computer Science graduate with 2+ years of experience in full-stack development. Proficient in React, Node.js, Python, and cloud technologies. Built scalable microservices handling 10K+ requests/sec. Strong background in data structures and algorithms.",
                "alice_johnson_resume.pdf"));
        seedResume(new ResumeCreateRequest("Bob Martinez", "bob.martinez@email.com", "Data Scientist", "Amazon",
                "M.S. in Data Science with expertise in machine learning, NLP, and statistical modeling. Published 3 research papers. Experience with TensorFlow, PyTorch, and Spark. Led a team that improved recommendation accuracy by 35%.",
                "bob_martinez_resume.pdf"));
        seedResume(new ResumeCreateRequest("Catherine Lee", "catherine.lee@email.com", "UX Designer", "Meta",
                "Creative UX/UI designer with 4 years of experience in product design. Skilled in Figma, Adobe XD, and user research methodologies. Redesigned an e-commerce platform resulting in 28% increase in conversions.",
                "catherine_lee_resume.pdf"));
        seedResume(new ResumeCreateRequest("David Kim", "david.kim@email.com", "Cloud Architect", "Microsoft",
                "AWS and Azure certified cloud architect with 5 years of experience. Designed multi-region architectures for Fortune 500 companies. Expert in Kubernetes, Docker, and serverless computing. Reduced infrastructure costs by 40%.",
                "david_kim_resume.pdf"));
        seedResume(new ResumeCreateRequest("Emily Chen", "emily.chen@email.com", "Product Manager", "Apple",
                "MBA with 3 years of product management experience in consumer tech. Led cross-functional teams of 15+ members. Launched 2 products with 1M+ downloads. Strong analytical skills and data-driven decision-making.",
                "emily_chen_resume.pdf"));
    }

    private void seedRegistrations() {
        if (countRows("registrations") > 0) {
            return;
        }
        jdbcTemplate.update("INSERT INTO registrations (id, name, email, target, status, registered_at) VALUES (?, ?, ?, ?, ?, ?)", nextId("registration"), "Ravi", "ravi@example.com", "Google Booth", "confirmed", nowStamp());
        jdbcTemplate.update("INSERT INTO registrations (id, name, email, target, status, registered_at) VALUES (?, ?, ?, ?, ?, ?)", nextId("registration"), "Anjali", "anjali@example.com", "Amazon Booth", "confirmed", nowStamp());
        jdbcTemplate.update("INSERT INTO registrations (id, name, email, target, status, registered_at) VALUES (?, ?, ?, ?, ?, ?)", nextId("registration"), "Teja", "teja@example.com", "Microsoft Booth", "confirmed", nowStamp());
    }

    private void saveUser(RegisteredUser user) {
        jdbcTemplate.update("INSERT INTO users (id, name, email, password, role) VALUES (?, ?, ?, ?, ?)", user.id(), user.name(), user.email(), user.password(), user.role());
    }

    private void seedResume(ResumeCreateRequest request) {
        ResumeApplication resume = new ResumeApplication(nextId("resume"), request.name(), request.email(), request.position(), request.company(), request.summary(), request.fileName(), LocalDate.now().toString(), "pending");
        jdbcTemplate.update("INSERT INTO resumes (id, name, email, position, company, summary, file_name, date_value, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", resume.id(), resume.name(), resume.email(), resume.position(), resume.company(), resume.summary(), resume.fileName(), resume.date(), resume.status());
    }

    private CareerFair mapCareerFair(String id, String name, String date, String time, String location, String description, String companiesJson, String status, int registrations, String category) {
        return new CareerFair(id, name, date, time, location, description, readStringList(companiesJson), status, registrations, category);
    }

    private Booth mapBooth(String id, String name, String industry, String logo, String tagline, String description, int openRoles, String location, String type, double rating, String employees, String positionsJson, String perksJson, String colorJson) {
        return new Booth(id, name, industry, logo, tagline, description, openRoles, location, type, rating, employees, readStringList(positionsJson), readStringList(perksJson), readColorPalette(colorJson));
    }

    private boolean userExists(String email) {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users WHERE LOWER(email) = LOWER(?)", Integer.class, email);
        return count != null && count > 0;
    }

    private RegisteredUser getUserByEmail(String email) {
        List<RegisteredUser> users = jdbcTemplate.query(
                "SELECT * FROM users WHERE LOWER(email) = LOWER(?)",
                (resultSet, rowNum) -> new RegisteredUser(resultSet.getString("id"), resultSet.getString("name"), resultSet.getString("email"), resultSet.getString("password"), resultSet.getString("role")),
                email);
        return users.isEmpty() ? null : users.get(0);
    }

    private int countRows(String tableName) {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM " + tableName, Integer.class);
        return count == null ? 0 : count;
    }

    private List<String> readStringList(String json) {
        if (json == null || json.isBlank()) {
            return new ArrayList<>();
        }
        try {
            return objectMapper.readValue(json, new TypeReference<List<String>>() {});
        } catch (JsonProcessingException exception) {
            return new ArrayList<>();
        }
    }

    private ColorPalette readColorPalette(String json) {
        if (json == null || json.isBlank()) {
            return new ColorPalette("#1a73e8", "#e8f0fe", "#c6dafc");
        }
        try {
            return objectMapper.readValue(json, ColorPalette.class);
        } catch (JsonProcessingException exception) {
            return new ColorPalette("#1a73e8", "#e8f0fe", "#c6dafc");
        }
    }

    private String writeJson(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException exception) {
            throw new IllegalStateException("Failed to serialize database payload", exception);
        }
    }

    private List<String> listOf(String... values) {
        List<String> items = new ArrayList<>();
        if (values != null) {
            Collections.addAll(items, values);
        }
        return items;
    }

    private String normalize(String email) {
        return email == null ? "" : email.trim().toLowerCase();
    }

    private String normalizeRole(String role) {
        return role == null ? "" : role.trim().toLowerCase();
    }

    private String nextId(String prefix) {
        return prefix + "-" + UUID.randomUUID();
    }

    private String nowStamp() {
        return OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private AuthResponse successResponse(RegisteredUser user, String message) {
        UserSummary userSummary = new UserSummary(user.id(), user.name(), user.email(), user.role());
        return new AuthResponse(true, message, jwtService.generateToken(userSummary), userSummary);
    }

    private static final class RegisteredUser {
        private final String id;
        private final String name;
        private final String email;
        private final String password;
        private final String role;

        private RegisteredUser(String id, String name, String email, String password, String role) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.password = password;
            this.role = role;
        }

        private String id() {
            return id;
        }

        private String name() {
            return name;
        }

        private String email() {
            return email;
        }

        private String password() {
            return password;
        }

        private String role() {
            return role;
        }
    }
}