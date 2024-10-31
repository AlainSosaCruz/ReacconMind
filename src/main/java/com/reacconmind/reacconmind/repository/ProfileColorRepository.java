package com.reacconmind.reacconmind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

import com.reacconmind.reacconmind.model.ProfileColor;
import com.reacconmind.reacconmind.model.User;

public interface ProfileColorRepository extends JpaRepository<ProfileColor, Integer> {
    @Query("SELECT pf FROM ProfileColor pf WHERE pf.user.idUser = :idUser")
    List<ProfileColor> findProfileColorByUserId(@Param("idUser") Integer idUser);
    ProfileColor findByUser(User user);

}
