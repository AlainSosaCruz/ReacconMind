package com.reacconmind.reacconmind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

import com.reacconmind.reacconmind.model.AccountUserEmail;

public interface AccountUserEmailRepository extends JpaRepository<AccountUserEmail, Integer> {
    @Query("SELECT u FROM AccountUserEmail u WHERE u.email = :email")
    AccountUserEmail findByEmails(@Param("email") String email);

    @Query("SELECT u FROM AccountUserEmail u WHERE u.email = :email")
    Optional<AccountUserEmail> findUserByEmail(@Param("email") String email);

    public Optional<AccountUserEmail> findByEmail(String email);
}
