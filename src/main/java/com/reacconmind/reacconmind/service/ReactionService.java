package com.reacconmind.reacconmind.service;

import com.reacconmind.reacconmind.model.Reaction;
import com.reacconmind.reacconmind.model.ReactionPK;
import com.reacconmind.reacconmind.repository.ReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReactionService {

    @Autowired
    private ReactionRepository reactionRepository;

    // Método para guardar o actualizar una reacción
    public Reaction saveReaction(Reaction reaction) {
        return reactionRepository.save(reaction);
    }

    // Método para obtener todas las reacciones con paginación
    public Page<Reaction> getAllReactions(Pageable pageable) {
        return reactionRepository.findAll(pageable);
    }

    // Método para obtener reacciones por usuario con paginación
    public Page<Reaction> getReactionsByUser(Integer idUser, Pageable pageable) {
        return reactionRepository.findById_IdUser(idUser, pageable);
    }

    // Método para obtener reacciones por publicación con paginación
    public Page<Reaction> getReactionsByPublication(Integer idPublication, Pageable pageable) {
        return reactionRepository.findById_IdPublication(idPublication, pageable);
    }

    // Método para eliminar una reacción por su clave primaria compuesta
    public void deleteReaction(ReactionPK reactionPK) {
        reactionRepository.deleteById(reactionPK);
    }
}
