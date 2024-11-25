package com.reacconmind.reacconmind.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reacconmind.reacconmind.model.AccountUserEmail;
import com.reacconmind.reacconmind.model.PasswordResetToken;
import com.reacconmind.reacconmind.model.User;
import com.reacconmind.reacconmind.repository.AccountUserEmailRepository;
import com.reacconmind.reacconmind.repository.PasswordResetTokenRepository;
import com.reacconmind.reacconmind.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PasswordResetTokenService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private AccountUserEmailRepository accountUserEmailRepository;
    @Autowired
    private AccountUserEmailService accountUserEmailService;

    public Optional<PasswordResetToken> getToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public Optional<AccountUserEmail> getUserByToken(String token) {
        return tokenRepository
                .findByToken(token)
                .map(PasswordResetToken::getAccountUserEmail);
    }

    public String createPasswordResetToken(String email) {
        AccountUserEmail user = accountUserEmailRepository.findByEmails(email);

        if (user == null) {
            return "User not found for email";
        }

        String token = TokenGenerator.generateToken();
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setAccountUserEmail(user);

        tokenRepository.save(resetToken);

        emailService.sendEmail(
                user.getEmail(),
                "Password Reset Request",
                token);

        return "Request processed";
    }

    public boolean validatePasswordResetToken(String token, String password) {
        Optional<PasswordResetToken> optionalToken = tokenRepository.findByToken(token);

        if (!optionalToken.isPresent()) {
            throw new RuntimeException("Token not found");
        }

        PasswordResetToken resetToken = optionalToken.get();

        if (resetToken.isUsed()) {
            throw new RuntimeException("Token has already been used");
        }

        LocalDateTime now = LocalDateTime.now();
        java.sql.Date currentDate = java.sql.Date.valueOf(now.toLocalDate());

        if (resetToken.getExpirationDate().before(currentDate)) {
            throw new RuntimeException("Token has expired");
        }

        AccountUserEmail user = resetToken.getAccountUserEmail();
        user.setPassword(password);
        accountUserEmailService.save(user);

        resetToken.setUsed(true);
        tokenRepository.save(resetToken);
        return true;
    }
}
