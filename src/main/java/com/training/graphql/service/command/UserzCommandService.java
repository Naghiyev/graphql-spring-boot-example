package com.training.graphql.service.command;


import com.training.graphql.datasource.problemz.entity.Userz;
import com.training.graphql.datasource.problemz.entity.UserzToken;
import com.training.graphql.exception.ProblemzAuthenticationException;
import com.training.graphql.repository.UserzRepository;
import com.training.graphql.repository.UserzTokenRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserzCommandService {

    @Autowired
    private UserzRepository userzRepository;

    @Autowired
    private UserzTokenRepository userzTokenRepository;

    private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();


    public UserzToken login(String username, String password) {
        var userzQueryResult = userzRepository.findByUsernameIgnoreCase(username);

        // Validate that the user exists and the provided password matches the stored hash
        if (userzQueryResult.isEmpty() ||
                !PASSWORD_ENCODER.matches(password, userzQueryResult.get().getHashedPassword())) {
            throw new ProblemzAuthenticationException();
        }

        var randomAuthToken = RandomStringUtils.randomAlphanumeric(40);

        return refreshToken(userzQueryResult.get().getId(), randomAuthToken);
    }

    private UserzToken refreshToken(UUID userId, String authToken) {
        var userzToken = new UserzToken();
        userzToken.setUserId(userId);
        userzToken.setAuthToken(authToken);

        var now = LocalDateTime.now();
        userzToken.setCreationTimestamp(now);
        userzToken.setExpiryTimestamp(now.plusHours(2));

        return userzTokenRepository.save(userzToken);
    }

    public Userz createUserz(Userz userz) {
        return userzRepository.save(userz);
    }

    public Optional<Userz> activateUser(String username, boolean isActive) {
        userzRepository.activateUser(username, isActive);

        return userzRepository.findByUsernameIgnoreCase(username);
    }

}
