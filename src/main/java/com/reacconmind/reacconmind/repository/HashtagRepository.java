package com.reacconmind.reacconmind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reacconmind.reacconmind.model.Hashtag;

public interface HashtagRepository extends JpaRepository<Hashtag, Integer> {
    @Query("SELECT h FROM Hashtag h WHERE h.name = :name")
    Hashtag findByName(@Param("name") String name);
}
