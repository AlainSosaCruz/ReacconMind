package com.reacconmind.reacconmind.controller;

import com.reacconmind.reacconmind.service.MultimediaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Multimedia", description = "API for managing multimedia files, including upload.")
@RestController
@RequestMapping("/multimedia")
public class MultimediaController {

    @Autowired
    private MultimediaService multimediaService;

    @Operation(summary = "Upload a multimedia file",
            description = "This endpoint allows uploading a multimedia file and returns the URL of the file.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File uploaded successfully"),
            @ApiResponse(responseCode = "400", description = "Error uploading the file"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadFile(
            @Parameter(description = "Multimedia file to upload", required = true)
            @RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = multimediaService.uploadFile(file);
            return ResponseEntity.ok(fileUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file: " + e.getMessage());
        }
    }
}
