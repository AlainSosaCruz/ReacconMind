package com.reacconmind.reacconmind.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reacconmind.reacconmind.dto.HashtagDTO;
import com.reacconmind.reacconmind.model.Hashtag;
import com.reacconmind.reacconmind.service.HashtagService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Hashtag", description = "Provides methods for managing hashtags")
@RequestMapping("hashtags")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
                RequestMethod.PUT })
@Configuration
@OpenAPIDefinition(info = @Info(title = "ReacconMind API", description = "ReacconMind Api to Hashtag Management", version = "1.0"))
public class HashtagController {
        @Autowired
        private HashtagService hashtagService;

        @Autowired
        private ModelMapper modelMapper;

        @Operation(summary = "Get all Hashtags with pagination", description = "Retrieve a paginated list of hashtags. Specify the page number and page size to get a subset of hashtags.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successful retrieval of hashtags"),
                        @ApiResponse(responseCode = "400", description = "Invalid page number or page size provided"),
                        @ApiResponse(responseCode = "500", description = "Internal server error")
        })
        @GetMapping(value = "pagination", params = { "page", "pageSize" })
        public List<Hashtag> getAllPaginated(
                        @Parameter(description = "The page number to retrieve. Default is 0 (first page).", required = false, example = "1") @RequestParam(value = "page", defaultValue = "0", required = false) int page,

                        @Parameter(description = "The number of hashtags per page. Default is 10.", required = false, example = "5") @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
                List<Hashtag> hashtags = hashtagService.getAll(page, pageSize);
                return hashtags;
        }

        @Operation(summary = "Get a Hashtag by ID", description = "Get a specific Hashtag with the ID.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Hashtag found", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = Hashtag.class)) }),
                        @ApiResponse(responseCode = "400", description = "Invalid Hashtag ID", content = @Content),
                        @ApiResponse(responseCode = "401", description = "Authentication failure", content = @Content(schema = @Schema(hidden = true))),
                        @ApiResponse(responseCode = "404", description = "Hashtag not found", content = @Content) })
        @GetMapping("/{idHashtag}")
        public ResponseEntity<?> getByIdHashtag(@PathVariable Integer idHashtag) {
                Hashtag hashtag = hashtagService.getByIdHashtag(idHashtag);
                return new ResponseEntity<Hashtag>(hashtag, HttpStatus.OK);
        }

        @Operation(summary = "Add a new Hashtag", description = "Add a new Hashtag to the system")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Hashtag added successfully", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = HashtagDTO.class)),
                        }),
                        @ApiResponse(responseCode = "400", description = "Invalid hashtag data", content = @Content),
        })
        @PostMapping
        public ResponseEntity<HashtagDTO> createHashtag(@RequestBody HashtagDTO hashtagDTO) {
                HashtagDTO savedHashtag = convertToDTO(hashtagService.save(convertToEntity(hashtagDTO)));
                return new ResponseEntity<>(savedHashtag, HttpStatus.CREATED);
        }

        private HashtagDTO convertToDTO(Hashtag hashtag) {
                return modelMapper.map(hashtag, HashtagDTO.class);
        }

        public Hashtag convertToEntity(HashtagDTO hashtagDTO) {
                return modelMapper.map(hashtagDTO, Hashtag.class);
        }
}
