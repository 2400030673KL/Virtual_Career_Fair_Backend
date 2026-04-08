package com.virtualcareerfair.service;

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
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class InMemoryStore {
    private final Map<String, RegisteredUser> usersByEmail = new LinkedHashMap<>();
    private final Map<String, Booth> boothsById = new LinkedHashMap<>();
    private final Map<String, CareerFair> fairsById = new LinkedHashMap<>();
    private final Map<String, ResumeApplication> resumesById = new LinkedHashMap<>();
    private final Map<String, List<ChatMessage>> chatByBoothId = new LinkedHashMap<>();
    private final List<Registration> registrations = new ArrayList<>();

    private static final String ADMIN_EMAIL = "admin@virtualcareerfair.com";
    private static final String ADMIN_PASSWORD = "Admin@123";

    public InMemoryStore() {
        seedUsers();
        seedBooths();
        seedCareerFairs();
        seedResumes();
        seedRegistrations();
    }

    public synchronized AuthResponse register(AuthRequest request) {
        String emailKey = normalize(request.email());
        if (usersByEmail.containsKey(emailKey)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }

        RegisteredUser user = new RegisteredUser(
                UUID.randomUUID().toString(),
                request.name(),
                request.email(),
                request.password(),
                request.role().toLowerCase());
        usersByEmail.put(emailKey, user);
        return successResponse(user, "Registration successful");
    }

    public synchronized AuthResponse login(LoginRequest request) {
        RegisteredUser user = usersByEmail.get(normalize(request.email()));
        if (user == null || !user.password().equals(request.password()) || !user.role().equalsIgnoreCase(request.role())) {
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

    public synchronized List<CareerFair> listCareerFairs() {
        return new ArrayList<>(fairsById.values());
    }

    public synchronized CareerFair getCareerFair(String id) {
        CareerFair fair = fairsById.get(id);
        if (fair == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Career fair not found");
        }
        return fair;
    }

    public synchronized CareerFair saveCareerFair(CareerFair fair) {
        String id = isBlank(fair.id()) ? nextId("fair") : fair.id();
        CareerFair stored = new CareerFair(
                id,
                fair.name(),
                fair.date(),
                fair.time(),
                fair.location(),
                fair.description(),
                copyList(fair.companies()),
                fair.status(),
                fair.registrations(),
                fair.category());
        fairsById.put(id, stored);
        return stored;
    }

    public synchronized void deleteCareerFair(String id) {
        if (fairsById.remove(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Career fair not found");
        }
    }

    public synchronized List<Booth> listBooths() {
        return new ArrayList<>(boothsById.values());
    }

    public synchronized Booth getBooth(String id) {
        Booth booth = boothsById.get(id);
        if (booth == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Booth not found");
        }
        return booth;
    }

    public synchronized Booth saveBooth(Booth booth) {
        String id = isBlank(booth.id()) ? nextId("booth") : booth.id();
        Booth stored = new Booth(
                id,
                booth.name(),
                booth.industry(),
                booth.logo(),
                booth.tagline(),
                booth.description(),
                booth.openRoles(),
                booth.location(),
                booth.type(),
                booth.rating(),
                booth.employees(),
                copyList(booth.positions()),
                copyList(booth.perks()),
                booth.color() == null ? new ColorPalette("#1a73e8", "#e8f0fe", "#c6dafc") : new ColorPalette(booth.color().primary(), booth.color().light(), booth.color().border()));
        boothsById.put(id, stored);
        return stored;
    }

    public synchronized void deleteBooth(String id) {
        if (boothsById.remove(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Booth not found");
        }
    }

    public synchronized List<ResumeApplication> listResumes() {
        return new ArrayList<>(resumesById.values());
    }

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
        resumesById.put(resume.id(), resume);
        registrations.add(new Registration(nextId("registration"), request.name(), request.email(), request.company(), "submitted", nowStamp()));
        return resume;
    }

    public synchronized ResumeApplication updateResumeStatus(String id, ResumeStatusRequest request) {
        ResumeApplication existing = resumesById.get(id);
        if (existing == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resume not found");
        }
        ResumeApplication updated = new ResumeApplication(
                existing.id(),
                existing.name(),
                existing.email(),
                existing.position(),
                existing.company(),
                existing.summary(),
                existing.fileName(),
                existing.date(),
                request.status());
        resumesById.put(id, updated);
        return updated;
    }

    public synchronized List<ChatMessage> listMessages(String boothId) {
        List<ChatMessage> messages = chatByBoothId.get(boothId);
        if (messages == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(messages);
    }

    public synchronized ChatMessage addMessage(String boothId, ChatMessageRequest request) {
        ChatMessage message = new ChatMessage(nextId("chat"), boothId, request.sender(), request.message(), nowStamp());
        List<ChatMessage> messages = chatByBoothId.get(boothId);
        if (messages == null) {
            messages = new ArrayList<>();
            chatByBoothId.put(boothId, messages);
        }
        messages.add(message);
        return message;
    }

    public synchronized List<Registration> listRegistrations() {
        return new ArrayList<>(registrations);
    }

    private AuthResponse successResponse(RegisteredUser user, String message) {
        return new AuthResponse(true, message, UUID.randomUUID().toString(), new UserSummary(user.id(), user.name(), user.email(), user.role()));
    }

    private void seedUsers() {
        RegisteredUser student = new RegisteredUser("user-1", "Student User", "student@demo.com", "Password123", "student");
        RegisteredUser recruiter = new RegisteredUser("user-2", "Recruiter User", "recruiter@demo.com", "Password123", "recruiter");
        usersByEmail.put(normalize(student.email()), student);
        usersByEmail.put(normalize(recruiter.email()), recruiter);
    }

    private void seedBooths() {
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
        registrations.add(new Registration(nextId("registration"), "Ravi", "ravi@example.com", "Google Booth", "confirmed", nowStamp()));
        registrations.add(new Registration(nextId("registration"), "Anjali", "anjali@example.com", "Amazon Booth", "confirmed", nowStamp()));
        registrations.add(new Registration(nextId("registration"), "Teja", "teja@example.com", "Microsoft Booth", "confirmed", nowStamp()));
    }

    private void seedResume(ResumeCreateRequest request) {
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
        resumesById.put(resume.id(), resume);
    }

    private List<String> listOf(String... values) {
        List<String> items = new ArrayList<>();
        if (values != null) {
            Collections.addAll(items, values);
        }
        return items;
    }

    private List<String> copyList(List<String> values) {
        if (values == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(values);
    }

    private String normalize(String email) {
        return email == null ? "" : email.trim().toLowerCase();
    }

    private String nextId(String prefix) {
        return prefix + "-" + UUID.randomUUID().toString();
    }

    private String nowStamp() {
        return OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
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