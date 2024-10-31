package com.reacconmind.reacconmind.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.reacconmind.reacconmind.model.AccountUserEmail;
import com.reacconmind.reacconmind.repository.AccountUserEmailRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountUserEmailRepository userEmailRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
        throws UsernameNotFoundException {
        AccountUserEmail user = userEmailRepository
            .findUserByEmail(username)
            .orElseThrow(() ->
                new UsernameNotFoundException(
                    "User not found with email: " + username
                )
            );
        return org.springframework.security.core.userdetails.User.withUsername(
            user.getEmail()
        )
            .password(user.getPassword())
            .roles("USER")
            .build();
    }
}
