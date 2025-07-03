package com.gdsc.nitcconnect.controller;

import com.gdsc.nitcconnect.model.User;
import com.gdsc.nitcconnect.repository.UserRepository;
import com.gdsc.nitcconnect.service.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/auth/email")
@RequiredArgsConstructor
public class EmailAuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final JavaMailSender mailSender;

    @PostMapping("/login")
    public ResponseEntity<String> loginWithEmail(
            @RequestParam("email") String email,
            @RequestParam("password") String password) {

        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        User user = userOpt.get();

        // User exists but has no password set (Google login user)
        if (user.getPassword() == null) {
            return ResponseEntity.status(403).body("You haven't set a password yet. Please log in with Google and set a password from your profile if you'd like to use email login.");
        }

        // Incorrect password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        // Valid email/password – proceed with 2FA
        String code = String.format("%06d", new Random().nextInt(999999));
        user.setEmail2FACode(code);
        user.setEmail2FACodeExpiry(LocalDateTime.now().plusMinutes(10));
        userRepository.save(user);

        sendCodeToEmail(email, code);
        return ResponseEntity.ok("2FA code sent to email");
    }


    @PostMapping("/verify")
    public ResponseEntity<String> verifyCode(
            @RequestParam("email") String email,
            @RequestParam("code") String code,
            HttpServletResponse response) {

        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body("Invalid or expired code");
        }

        User user = userOpt.get();
        if (user.getEmail2FACode() == null || !user.getEmail2FACode().equals(code)
                || user.getEmail2FACodeExpiry() == null || user.getEmail2FACodeExpiry().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(401).body("Invalid or expired code");
        }

        // Clear 2FA code
        user.setEmail2FACode(null);
        user.setEmail2FACodeExpiry(null);
        userRepository.save(user);

        // Generate and send JWT
        String token = jwtService.generateToken(user.getEmail());

        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // false if on localhost
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60); // 1 week
        response.addCookie(cookie);

        return ResponseEntity.ok("Login successful");
    }

    private void sendCodeToEmail(String to, String code) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setSubject("Your Login Verification Code");
        msg.setText("Your 6-digit login code is: " + code + "\nIt will expire in 10 minutes.");
        mailSender.send(msg);
    }
}
