package com.reacconmind.reacconmind.service;

import com.reacconmind.reacconmind.model.Comment;
import com.reacconmind.reacconmind.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    // Método para obtener todos los comentarios
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    // Método para obtener comentarios con paginación
    public Page<Comment> getAllComments(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    // Método para obtener un comentario por su ID
    public Comment getCommentById(Integer id) {
        return commentRepository.findById(id).orElse(null);
    }

    // Método para guardar o actualizar un comentario
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    // Método para eliminar un comentario
    public void deleteComment(Integer id) {
        commentRepository.deleteById(id);
    }
}
