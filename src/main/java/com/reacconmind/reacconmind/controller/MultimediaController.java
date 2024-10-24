package com.reacconmind.reacconmind.controller;

import com.reacconmind.reacconmind.service.MultimediaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/multimedia")
public class MultimediaController {

    @Autowired
    private MultimediaService multimediaService;

    @Operation(summary = "Sube un archivo multimedia",
            description = "Este endpoint permite subir un archivo multimedia y devuelve la URL del archivo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Archivo subido exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error al subir el archivo"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadFile(
            @Parameter(description = "Archivo multimedia a subir", required = true)
            @RequestParam("file") MultipartFile file) {
        try {
            // Cambiado 'upload' a 'uploadFile'
            String fileUrl = multimediaService.uploadFile(file);
            return ResponseEntity.ok(fileUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file: " + e.getMessage());
        }
    }
}
