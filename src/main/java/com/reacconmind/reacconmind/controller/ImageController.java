package com.reacconmind.reacconmind.controller;

import com.reacconmind.reacconmind.dto.ImageDTO;
import com.reacconmind.reacconmind.model.Image;
import com.reacconmind.reacconmind.model.Publication;
import com.reacconmind.reacconmind.repository.ImageRepository;
import com.reacconmind.reacconmind.repository.PublicationRepository;
import com.reacconmind.reacconmind.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Tag(name = "Image", description = "API for managing images, including upload, retrieval, update, and deletion.")
@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private PublicationRepository publicationRepository;

    @Operation(summary = "Upload an image file", description = "Upload an image file to Firebase and receive the image URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Image uploaded successfully", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Image upload failed")
    })
    @PostMapping(value = "/upload-image", consumes = { "multipart/form-data" })
    public ResponseEntity<Map<String, String>> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "thumbnail", required = false) String thumbnail,
            @RequestParam(value = "publicationId", required = false) Integer publicationId) {

        try {
            // Subir la imagen y obtener la URL
            String url = imageService.upload(file);

            // Crear nueva instancia de Image
            Image image = new Image();
            image.setUrl(url);
            image.setThumbnail(thumbnail);

            // Asociar la publicación si se proporciona publicationId
            if (publicationId != null) {
                Optional<Publication> publication = publicationRepository.findById(publicationId);
                publication.ifPresent(image::setPublication);
            }

            // Guardar la imagen en la base de datos
            imageRepository.save(image);

            // Respuesta con la URL de la imagen
            Map<String, String> response = new HashMap<>();
            response.put("url", url);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Image upload failed"));
        }
    }


    //@Operation(summary = "Get all images", description = "Retrieve all images from the database")
    //@ApiResponses(value = {
    //    @ApiResponse(responseCode = "200", description = "Images retrieved successfully", content = @Content(schema = @Schema(implementation = Image.class))),
    //      @ApiResponse(responseCode = "500", description = "Failed to retrieve images")
    //})
    //@GetMapping("/images")
    //public ResponseEntity<List<Image>> getAllImages() {
    //try {
    //List<Image> images = imageRepository.findAll();
    //  return ResponseEntity.ok(images);
    //} catch (Exception e) {
    //  e.printStackTrace();
    //    return ResponseEntity.status(500).build();
    //  }
    //}

    @Operation(summary = "Get image by ID", description = "Retrieve an image by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Image retrieved successfully", content = @Content(schema = @Schema(implementation = Image.class))),
            @ApiResponse(responseCode = "404", description = "Image not found"),
            @ApiResponse(responseCode = "500", description = "Failed to retrieve image")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Image> getImageById(@PathVariable int id) {
        Optional<Image> imageOptional = imageRepository.findById(id);
        if (imageOptional.isPresent()) {
            return ResponseEntity.ok(imageOptional.get());
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @Operation(summary = "Delete image by ID", description = "Delete an image by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Image deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Image not found"),
            @ApiResponse(responseCode = "500", description = "Failed to delete image")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteImageById(@PathVariable int id) {
        try {
            Optional<Image> imageOptional = imageRepository.findById(id);
            if (imageOptional.isPresent()) {
                Image image = imageOptional.get();

                // Eliminar la imagen de Firebase
                if (image.getUrl() != null && !image.getUrl().isEmpty()) {
                    imageService.deleteFromFirebase(image.getUrl());
                }

                // Eliminar la imagen de la base de datos
                imageRepository.deleteById(id);

                Map<String, String> response = new HashMap<>();
                response.put("message", "Image deleted successfully");
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(404).body(Map.of("error", "Image not found"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Failed to delete image"));
        }
    }

    @Operation(summary = "Update image by ID", description = "Update an image's URL and other details by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Image updated successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Image not found"),
            @ApiResponse(responseCode = "500", description = "Failed to update image")
    })
    @PutMapping(value = "/{id}", consumes = { "multipart/form-data" })
    public ResponseEntity<Map<String, String>> updateImageById(
            @PathVariable int id,
            @RequestParam("file") MultipartFile file) {
        try {
            Optional<Image> imageOptional = imageRepository.findById(id);
            if (imageOptional.isPresent()) {
                Image image = imageOptional.get();

                // Eliminar la imagen anterior de Firebase
                if (image.getUrl() != null && !image.getUrl().isEmpty()) {
                    imageService.deleteFromFirebase(image.getUrl());
                }

                // Subir la nueva imagen y obtener la URL
                String newUrl = imageService.upload(file);
                image.setUrl(newUrl); // Actualizar la URL

                // Guardar los cambios en la base de datos
                imageRepository.save(image);

                Map<String, String> response = new HashMap<>();
                response.put("message", "Image updated successfully");
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(404).body(Map.of("error", "Image not found"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Failed to update image"));
        }
    }

    @Operation(summary = "Get images for pagination", description = "Retrieve all images in DTO format")
    @ApiResponse(responseCode = "200", description = "Images retrieved successfully", content = @Content(schema = @Schema(implementation = ImageDTO.class)))
    @GetMapping("")
    public ResponseEntity<List<ImageDTO>> findAllImages(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ImageDTO> imagePage = imageRepository.findAllImages(pageable);

        // Retornar solo el contenido de las imágenes
        return ResponseEntity.ok(imagePage.getContent());
    }
}
