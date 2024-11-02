package com.reacconmind.reacconmind.repository;

import com.reacconmind.reacconmind.model.Moderation;
import com.reacconmind.reacconmind.model.ModerationPK;
import com.reacconmind.reacconmind.model.Publication;
import com.reacconmind.reacconmind.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModerationRepository extends JpaRepository<Moderation, ModerationPK> {
    List<Moderation> findByIdPublication(Publication publication);
    List<Moderation> findByIdUser(User userId);
}
