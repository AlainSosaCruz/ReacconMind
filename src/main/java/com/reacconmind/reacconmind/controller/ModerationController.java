package com.reacconmind.reacconmind.controller;

import com.reacconmind.reacconmind.dto.ModerationDTO;
import com.reacconmind.reacconmind.model.Moderation;
import com.reacconmind.reacconmind.model.ModerationPK;
import com.reacconmind.reacconmind.model.Publication;
import com.reacconmind.reacconmind.model.User;
import com.reacconmind.reacconmind.service.ModerationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for handling content moderation in the ReacconMind platform.
 * Provides endpoints for content moderation, including text and image moderation,
 * with pagination support and specific moderation type filtering.
 */
@RestController
@Tag(name = "Moderation", description = "API for content moderation management")
@RequestMapping("reacconMind/moderation")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class ModerationController {

    @Autowired
    private ModerationService moderationService;

    @Operation(summary = "Get all moderations with pagination",
            description = "Retrieves a paginated list of moderations with sorting options.")
    @ApiResponse(responseCode = "200", description = "List of moderations retrieved successfully",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Moderation.class))))
    @GetMapping
    public ResponseEntity<Page<Moderation>> getAllModerations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idModeration") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Moderation> moderations = moderationService.getAllModerations(pageable);

        return new ResponseEntity<>(moderations, HttpStatus.OK);
    }

    @Operation(summary = "Get moderations by publication ID",
            description = "Retrieves all moderations associated with a specific publication.")
    @ApiResponse(responseCode = "200", description = "Moderations retrieved successfully")
    @GetMapping("/publication/{publicationId}")
    public ResponseEntity<List<Moderation>> getModerationsByPublication(@PathVariable Publication publication) {
        List<Moderation> moderations = moderationService.getModerationsByPublication(publication);
        return ResponseEntity.ok(moderations);
    }

    @Operation(summary = "Get moderations by user ID",
            description = "Retrieves all moderations associated with a specific user.")
    @ApiResponse(responseCode = "200", description = "Moderations retrieved successfully")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Moderation>> getModerationsByUser(@PathVariable User userId) {
        List<Moderation> moderations = moderationService.getModerationsByUser(userId);
        return ResponseEntity.ok(moderations);
    }

    @Operation(summary = "Get moderation by ID",
            description = "Retrieves a specific moderation by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Moderation found"),
            @ApiResponse(responseCode = "404", description = "Moderation not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Moderation> getModerationById(@PathVariable ModerationPK id) {
        Optional<Moderation> moderation = moderationService.getModerationById(id);
        return moderation.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Moderate text content",
            description = "Submits text content for moderation and creates a moderation record.")
    @ApiResponse(responseCode = "200", description = "Content moderated successfully",
            content = @Content(schema = @Schema(implementation = Moderation.class)))
    @PostMapping("/text")
    public ResponseEntity<Moderation> moderateText(@RequestBody ModerationDTO moderationDTO) {
        Moderation moderation = moderationService.moderateText(
                moderationDTO.getPublicationId(),
                moderationDTO.getContent(),
                moderationDTO.getUserId()
        );
        return new ResponseEntity<>(moderation, HttpStatus.OK);
    }

    @Operation(summary = "Moderate image content",
            description = "Submits image content for moderation and creates a moderation record.")
    @ApiResponse(responseCode = "200", description = "Image moderated successfully",
            content = @Content(schema = @Schema(implementation = Moderation.class)))
    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Moderation> moderateImage(
            @RequestParam("publicationId") int publicationId,
            @RequestParam("userId") int userId,
            @RequestParam("image") MultipartFile image) {

        Moderation moderation = moderationService.moderateImage(publicationId, image, userId);
        return new ResponseEntity<>(moderation, HttpStatus.OK);
    }
}
