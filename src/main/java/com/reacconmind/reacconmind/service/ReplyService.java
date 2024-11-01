package com.reacconmind.reacconmind.service;

import com.reacconmind.reacconmind.model.Reply;
import com.reacconmind.reacconmind.repository.ReplyRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ReplyService {

    @Autowired
    private ReplyRepository repo;

    // Obtener todas las respuestas (replies) con paginación
    public Page<Reply> getAllReplies(Pageable pageable) {
        return repo.findAll(pageable);
    }

    // Guardar una nueva respuesta (reply)
    public void saveReply(Reply reply) {
        repo.save(reply);
    }

    // Obtener una respuesta (reply) por su ID
    public Reply getByIdReply(Integer idReply) {
        return repo.findById(idReply).orElse(null); // Devuelve null si no se encuentra
    }

    // Eliminar una respuesta (reply) por su ID
    public void deleteReply(Integer idReply) {
        repo.deleteById(idReply);
    }
}
