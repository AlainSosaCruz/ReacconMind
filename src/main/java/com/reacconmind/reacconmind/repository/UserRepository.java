package com.reacconmind.reacconmind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reacconmind.reacconmind.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

  @Query("SELECT u FROM User u WHERE u.userName = :userName")
  User findByUserName(@Param("userName") String userName);

  @Query("SELECT u FROM User u WHERE u.accountUserEmail.email = :email")
  User findByEmail(@Param("email") String email);
}
