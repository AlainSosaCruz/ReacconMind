package com.reacconmind.reacconmind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reacconmind.reacconmind.model.Tendency;

public interface TendencyRepository extends JpaRepository<Tendency, Integer> {
    @Query("SELECT t FROM Tendency t WHERE t.name = :name")
    Tendency findByName(@Param("name") String name);
}
