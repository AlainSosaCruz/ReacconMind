package com.reacconmind.reacconmind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reacconmind.reacconmind.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
}