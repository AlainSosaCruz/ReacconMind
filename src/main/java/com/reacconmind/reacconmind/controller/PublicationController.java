package com.reacconmind.reacconmind.controller;

import com.reacconmind.reacconmind.dto.PublicationDTO;
import com.reacconmind.reacconmind.model.Bot;
import com.reacconmind.reacconmind.model.Publication;
import com.reacconmind.reacconmind.model.User;
import com.reacconmind.reacconmind.repository.BotRepository;
import com.reacconmind.reacconmind.repository.PublicationRepository;
import com.reacconmind.reacconmind.repository.UserRepository;
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
import java.util.Optional;

@Tag(name = "Publication", description = "API for managing user publications")
@RestController
@RequestMapping("/publications")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT })
public class PublicationController {

    @Autowired
    private PublicationService service;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BotRepository botRepository;

    @Autowired
    private PublicationRepository publicationRepository;
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
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PublicationDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid publication ID supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Publication not found", content = @Content) })
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        // Busca la publicación
        Publication publication = service.getByIdPublication(id);

        // Verifica si la publicación existe
        if (publication == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Publication not found");
        }

        // Convierte la entidad `Publication` en `PublicationDTO`
        PublicationDTO publicationDTO = new PublicationDTO(publication);

        return ResponseEntity.ok(publicationDTO);
    }



    @Operation(summary = "Create a new publication")
    @ApiResponse(responseCode = "200", description = "Publication created", content = @Content)
    @PostMapping("/publications")
    public ResponseEntity<?> createPublication(@RequestBody PublicationDTO publicationDTO) {
        // Busca el usuario y el bot usando los IDs recibidos
        Optional<User> userOptional = userRepository.findById(publicationDTO.getIdUser());
        Optional<Bot> botOptional = botRepository.findById(publicationDTO.getIdBot());

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        if (botOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bot not found");
        }

        // Crea la nueva publicación
        User user = userOptional.get();
        Bot bot = botOptional.get();
        Publication publication = new Publication();
        publication.setUser(user);
        publication.setBot(bot);
        publication.setContent(publicationDTO.getContent());

        // Guarda la publicación en la base de datos
        publicationRepository.save(publication);

        // Convierte la entidad Publication a PublicationDTO
        PublicationDTO responseDTO = new PublicationDTO(publication);
        return ResponseEntity.ok(responseDTO);
    }



    @Operation(summary = "Update an existing publication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publication updated", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid publication ID supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Publication not found", content = @Content) })
    @PutMapping("{id}")
    public ResponseEntity<?> update(@RequestBody PublicationDTO publicationDTO, @PathVariable Integer id) {
        // Busca la publicación existente
        Optional<Publication> existingPublicationOptional = publicationRepository.findById(id);
        if (existingPublicationOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Publication not found");
        }

        Publication existingPublication = existingPublicationOptional.get();

        // Busca el usuario y el bot usando los IDs recibidos en el DTO
        Optional<User> userOptional = userRepository.findById(publicationDTO.getIdUser());
        Optional<Bot> botOptional = botRepository.findById(publicationDTO.getIdBot());

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        if (botOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bot not found");
        }

        // Asigna los valores a la publicación existente
        User user = userOptional.get();
        Bot bot = botOptional.get();
        existingPublication.setUser(user);
        existingPublication.setBot(bot);
        existingPublication.setContent(publicationDTO.getContent());  // Actualiza solo el contenido

        // Guarda la publicación actualizada
        publicationRepository.save(existingPublication);

        // Convierte la entidad Publication a PublicationDTO para la respuesta
        PublicationDTO responseDTO = new PublicationDTO(existingPublication);
        return ResponseEntity.ok(responseDTO);
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