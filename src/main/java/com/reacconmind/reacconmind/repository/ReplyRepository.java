package com.reacconmind.reacconmind.repository;

import com.reacconmind.reacconmind.dto.ReplyDTO;
import com.reacconmind.reacconmind.model.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Integer> {

    // Consulta personalizada para seleccionar solo los campos necesarios y devolverlos como ReplyDTO
    @Query("SELECT new com.reacconmind.reacconmind.dto.ReplyDTO(r.idReply, u.idUser, u.userName, c.idComment, p.idPublication, p.content, c.contentComment, r.contentReply) " +
            "FROM Reply r " +
            "JOIN r.user u " +
            "JOIN r.comment c " +
            "JOIN c.publication p")
    Page<ReplyDTO> findAllReplies(Pageable pageable);
}
