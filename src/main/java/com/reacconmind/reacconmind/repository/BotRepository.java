package com.reacconmind.reacconmind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reacconmind.reacconmind.model.Bot;
import java.util.Optional;


@Repository
public interface BotRepository extends JpaRepository<Bot, Integer> {
    Optional<Bot> findByIdBot(int idBot);
}
