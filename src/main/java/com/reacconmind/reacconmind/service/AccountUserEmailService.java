package com.reacconmind.reacconmind.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.reacconmind.reacconmind.model.AccountUserEmail;
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

    public void save(AccountUserEmail accountUserEmail) {
        accountUserEmail.setPassword(passwordEncoder.encode(accountUserEmail.getPassword()));
        accountUserEmailRepository.save(accountUserEmail);
    }

    public Optional<AccountUserEmail> findUserByEmail(String email) {
        return accountUserEmailRepository.findUserByEmail(email);
    }

    public AccountUserEmail findByEmail(String email) {
        return accountUserEmailRepository.findByEmail(email);
    }

}
