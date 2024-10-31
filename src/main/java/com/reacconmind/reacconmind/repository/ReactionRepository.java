package com.reacconmind.reacconmind.repository;

import com.reacconmind.reacconmind.model.Reaction;
import com.reacconmind.reacconmind.model.ReactionPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, ReactionPK> {
    List<Reaction> findById_IdUser(Integer idUser);  // Correcto acceso a idUser dentro de ReactionPK
    List<Reaction> findById_IdPublication(Integer idPublication);  // Correcto acceso a idPublication dentro de ReactionPK
}
