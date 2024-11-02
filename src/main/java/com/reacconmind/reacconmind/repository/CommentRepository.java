package com.reacconmind.reacconmind.repository;

import com.reacconmind.reacconmind.dto.CommentDTO;
import com.reacconmind.reacconmind.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query("SELECT new com.reacconmind.reacconmind.dto.CommentDTO(c.idComment, u.userName, p.idPublication, p.content, c.contentComment) " +
            "FROM Comment c " +
            "JOIN c.user u " +
            "JOIN c.publication p")
    Page<CommentDTO> findAllComments(Pageable pageable);
}
