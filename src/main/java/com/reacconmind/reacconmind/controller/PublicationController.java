package com.reacconmind.reacconmind.controller;

import com.reacconmind.reacconmind.dto.PublicationDTO;
import com.reacconmind.reacconmind.model.Publication;
import com.reacconmind.reacconmind.repository.PublicationRepository;
import com.reacconmind.reacconmind.service.PublicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Publication", description = "API for managing user publications")
@RestController
@RequestMapping("/publications")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT })
public class PublicationController {

    @Autowired
    private PublicationService service;

    //@Operation(summary = "Get paginated publications")
    //@ApiResponse(responseCode = "200", description = "Found Publications", content = {
      //      @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Publication.class))) })
    //@GetMapping
    //public ResponseEntity<Page<Publication>> getAll(
      //      @RequestParam(defaultValue = "0") int page,
        //    @RequestParam(defaultValue = "10") int size) {
        //Pageable pageable = PageRequest.of(page, size);
        //Page<Publication> publications = service.getAll(pageable);
        //return new ResponseEntity<>(publications, HttpStatus.OK);
    //}

    @Operation(summary = "Get a publication by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publication found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Publication.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid publication ID supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Publication not found", content = @Content) })
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Publication publication = service.getByIdPublication(id);
        return new ResponseEntity<>(publication, HttpStatus.OK);
    }

    @Operation(summary = "Create a new publication")
    @ApiResponse(responseCode = "200", description = "Publication created", content = @Content)
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Publication publication) {
        service.save(publication);
        return new ResponseEntity<>("Publication created", HttpStatus.OK);
    }

    @Operation(summary = "Update an existing publication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publication updated", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid publication ID supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Publication not found", content = @Content) })
    @PutMapping("{id}")
    public ResponseEntity<?> update(@RequestBody Publication publication, @PathVariable Integer id) {
        Publication existingPublication = service.getByIdPublication(id);
        publication.setIdPublication(existingPublication.getIdPublication());
        service.save(publication);
        return new ResponseEntity<>("Publication updated", HttpStatus.OK);
    }

    @Operation(summary = "Delete a publication by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publication deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Publication not found", content = @Content) })
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        service.delete(id);
        return new ResponseEntity<>("Publication deleted", HttpStatus.OK);
    }

    @Autowired
    private PublicationRepository publicationRepository;

    @Operation(summary = "Get publications with pagination", description = "Retrieve a paginated list of publications. Specify the page number and page size to get a subset of publications.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of publications", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PublicationDTO.class)))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid page number or page size provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("")
    public ResponseEntity<List<PublicationDTO>> getAllPublications(
            @Parameter(description = "The page number to retrieve. Default is 0 (first page).", required = false, example = "1")
            @RequestParam(value = "page", defaultValue = "0") int page,

            @Parameter(description = "The number of publications per page. Default is 10.", required = false, example = "5")
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<PublicationDTO> publicationPage = publicationRepository.findAllPublications(pageable);

        // Retornar solo el contenido de las publicaciones
        return ResponseEntity.ok(publicationPage.getContent());
    }
}