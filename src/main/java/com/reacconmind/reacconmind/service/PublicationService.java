package com.reacconmind.reacconmind.service;

import com.reacconmind.reacconmind.model.Publication;
import com.reacconmind.reacconmind.repository.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PublicationService {

    @Autowired
    private PublicationRepository repo;

    // Obtener todas las publicaciones con paginación
    public Page<Publication> getAll(Pageable pageable) {
        return repo.findAll(pageable);
    }

    // Guardar una nueva publicación
    public void save(Publication publication) {
        repo.save(publication);
    }

    // Obtener una publicación por su ID
    public Publication getByIdPublication(Integer idPublication) {
        return repo.findById(idPublication).orElse(null); // Devuelve null si no se encuentra
    }

    // Eliminar una publicación por su ID
    public void delete(Integer idPublication) {
        repo.deleteById(idPublication);
    }
}