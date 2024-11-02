package com.reacconmind.reacconmind.repository;

import com.reacconmind.reacconmind.dto.ReactionDTO;
import com.reacconmind.reacconmind.model.Reaction;
import com.reacconmind.reacconmind.model.ReactionPK;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, ReactionPK> {
    // Método para encontrar reacciones por usuario con paginación
    Page<Reaction> findById_IdUser(Integer idUser, Pageable pageable);

    // Método para encontrar reacciones por publicación con paginación
    Page<Reaction> findById_IdPublication(Integer idPublication, Pageable pageable);



    @Query("SELECT new com.reacconmind.reacconmind.dto.ReactionDTO(u.userName, u.imageProfile) " +
            "FROM Reaction r " +
            "JOIN r.user u")
    Page<ReactionDTO> findAllReactions(Pageable pageable);


}
