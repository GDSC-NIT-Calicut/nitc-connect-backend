package com.gdsc.nitcconnect.service;

import com.gdsc.nitcconnect.model.User;
import com.gdsc.nitcconnect.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.*;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.user.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String googleId = oAuth2User.getAttribute("sub");

        assert email != null;
        if (!email.endsWith("@nitc.ac.in")) {
            throw new OAuth2AuthenticationException(new OAuth2Error("invalid_email"),
                    "Only @nitc.ac.in accounts are allowed");
        }

        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isEmpty()) {
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setGoogleId(googleId);
            user.setCreatedAt(LocalDateTime.now());
            userRepository.save(user);
        }

        return oAuth2User;
    }
}
