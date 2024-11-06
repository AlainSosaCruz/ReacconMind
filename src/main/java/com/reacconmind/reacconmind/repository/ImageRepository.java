package com.reacconmind.reacconmind.repository;

import com.reacconmind.reacconmind.dto.ImageDTO;
import com.reacconmind.reacconmind.model.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {

    // Consulta personalizada para seleccionar solo los campos necesarios y devolverlos como ImageDTO
    @Query("SELECT new com.reacconmind.reacconmind.dto.ImageDTO(i.idImage, i.url, i.thumbnail, p.idPublication, u.userName) " +
            "FROM Image i " +
            "JOIN i.publication p " +
            "JOIN p.user u")
    Page<ImageDTO> findAllImages(Pageable pageable);

}
