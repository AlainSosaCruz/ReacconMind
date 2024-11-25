package com.reacconmind.reacconmind.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.reacconmind.reacconmind.dto.AccountUserEmailAddDTO;
import com.reacconmind.reacconmind.dto.Login;
import com.reacconmind.reacconmind.dto.UserAddDTO;
import com.reacconmind.reacconmind.model.AccountUserEmail;
import com.reacconmind.reacconmind.model.User;
import com.reacconmind.reacconmind.repository.AccountUserEmailRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AuthService {
    @Autowired
    private UserService userService;
    @Autowired
    private AccountUserEmailService accountUserEmailService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AccountUserEmailRepository accountUserEmailRepository;

    public User signup(UserAddDTO userDTO) {
        User userReturn = userService.saveUsser(userDTO);
        AccountUserEmail account = new AccountUserEmail();
        account.setEmail(userDTO.getEmail());
        account.setPassword(userDTO.getPassword());
        account.setIdUser(userReturn);
        accountUserEmailService.save(account);
        return userReturn;
    }

    public AccountUserEmail authenticate(Login userDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDTO.getEmail(),
                        userDTO.getPassword()));

        return accountUserEmailRepository.findByEmail(userDTO.getEmail())
                .orElseThrow();
    }
}
