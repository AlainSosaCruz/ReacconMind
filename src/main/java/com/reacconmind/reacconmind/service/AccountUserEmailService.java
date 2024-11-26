package com.reacconmind.reacconmind.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.reacconmind.reacconmind.dto.AccountUserEmailAddDTO;
import com.reacconmind.reacconmind.model.AccountUserEmail;
import com.reacconmind.reacconmind.model.User;
import com.reacconmind.reacconmind.repository.AccountUserEmailRepository;
import java.util.Optional;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AccountUserEmailService {

    @Autowired
    private AccountUserEmailRepository accountUserEmailRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    @Lazy
    private UserService userService;

    public AccountUserEmail save(AccountUserEmail accountUserEmail) {
        accountUserEmail.setPassword(passwordEncoder.encode(accountUserEmail.getPassword()));
        return accountUserEmailRepository.save(accountUserEmail);
    }

    public void saveEmail(AccountUserEmailAddDTO accountUserEmail) {
        AccountUserEmail email = convertFromDTO(accountUserEmail);
        accountUserEmailRepository.save(email);
    }

    public Optional<AccountUserEmail> findUserByEmail(String email) {
        return accountUserEmailRepository.findUserByEmail(email);
    }

    public AccountUserEmail findByEmail(String email) {
        Optional<AccountUserEmail> optionalAccount = accountUserEmailRepository.findByEmail(email);

        if (optionalAccount.isPresent()) {
            return optionalAccount.get();
        } else {
            throw new RuntimeException("User not found with email: " + email);
        }
    }

    private AccountUserEmail convertFromDTO(AccountUserEmailAddDTO account) {
        AccountUserEmail email = new AccountUserEmail();
        email.setEmail(account.getEmail());
        email.setPassword(passwordEncoder.encode(account.getPassword()));

        User user = userService.getByIdUser(account.getIdUser());
        email.setIdUser(user);
        return email;
    }

}
