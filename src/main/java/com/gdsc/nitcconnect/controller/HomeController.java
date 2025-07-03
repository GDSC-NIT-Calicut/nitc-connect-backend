package com.gdsc.nitcconnect.controller;

import com.gdsc.nitcconnect.model.User;
import com.gdsc.nitcconnect.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
public class HomeController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public HomeController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String home(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser");

        CsrfToken csrf = (CsrfToken) request.getAttribute("_csrf");
        String csrfInput = csrf != null
                ? "<input type='hidden' name='" + csrf.getParameterName() + "' value='" + csrf.getToken() + "'/>"
                : "";

        if (isLoggedIn) {
            return """
                <html>
                    <body>
                        <h1>Welcome to the homepage!</h1>
                        <p>You are logged in.</p>
                        <form action="/logout" method="POST">
                            %s
                            <button type="submit">Logout</button>
                        </form>
                    </body>
                </html>
            """.formatted(csrfInput);
        } else {
            return """
                <html>
                    <body>
                        <h1>Welcome to the homepage!</h1>
                        <p>You are not logged in.</p>

                        <!-- Google login -->
                        <p><a href="/oauth2/authorization/google">Login with Google</a></p>

                        <hr>

                        <!-- Email login form -->
                        <form method="POST" action="/auth/email/login">
                            <h3>Login with Email</h3>
                            <label>Email: <input type="email" name="email" required /></label><br>
                            <label>Password: <input type="password" name="password" required /></label><br>
                            %s
                            <button type="submit">Send 2FA Code</button>
                        </form>

                        <br>

                        <!-- 2FA verification form -->
                        <form method="POST" action="/auth/email/verify">
                            <h3>Enter 2FA Code</h3>
                            <label>Email: <input type="email" name="email" required /></label><br>
                            <label>Code: <input type="text" name="code" required /></label><br>
                            %s
                            <button type="submit">Verify</button>
                        </form>

                        <br>

                        <!-- Registration form -->
                        <form method="POST" action="/register">
                            <h3>Register</h3>
                            <label>Name: <input type="text" name="name" required /></label><br>
                            <label>Email: <input type="email" name="email" required /></label><br>
                            <label>Password: <input type="password" name="password" required /></label><br>
                            %s
                            <button type="submit">Register</button>
                        </form>
                    </body>
                </html>
            """.formatted(csrfInput, csrfInput, csrfInput);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestParam String name,
                                          @RequestParam String email,
                                          @RequestParam String password) {

        if (!email.toLowerCase().endsWith("@nitc.ac.in")) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Only @nitc.ac.in email addresses are allowed to register"));
        }

        if (userRepository.findByEmail(email).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "Email is already registered"));
        }

        String hashed = passwordEncoder.encode(password);
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(hashed);
        user.setGoogleId(null);  // Not a Google user
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("message", "Registration successful. You can now log in."));
    }

}
