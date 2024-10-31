package com.reacconmind.reacconmind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

import com.reacconmind.reacconmind.model.ThemeBotType;
import com.reacconmind.reacconmind.model.ThemePreference;
import com.reacconmind.reacconmind.model.User;

public interface ThemePreferenceRepository extends JpaRepository<ThemePreference, Integer> {
    @Query("SELECT tp FROM ThemePreference tp WHERE tp.user.idUser = :idUser")
    List<ThemePreference> findPreferencesByUserId(@Param("idUser") Integer idUser);



}
