package com.gdsc.nitcconnect.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.web.csrf.CsrfToken;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser");

        CsrfToken csrf = (CsrfToken) request.getAttribute("_csrf");
        String csrfInput = "<input type='hidden' name='" + csrf.getParameterName() +
                "' value='" + csrf.getToken() + "'/>";

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
                    <a href="/oauth2/authorization/google">Login with Google</a>
                </body>
            </html>
        """;
        }
    }

}
