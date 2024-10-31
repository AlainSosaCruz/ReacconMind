package com.reacconmind.reacconmind.repository;

import com.reacconmind.reacconmind.dto.PublicationDTO;
import com.reacconmind.reacconmind.model.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Integer> {

    // Consulta personalizada para seleccionar solo los campos necesarios y devolverlos con PublicationDTO
    @Query("SELECT new com.reacconmind.reacconmind.dto.PublicationDTO(p.idPublication,u.userName, b.name, b.theme, p.content) " +
            "FROM Publication p " +
            "JOIN p.user u " +
            "JOIN p.bot b")
    List<PublicationDTO> findAllPublications();
}


