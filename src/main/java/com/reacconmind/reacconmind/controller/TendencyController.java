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
import com.reacconmind.reacconmind.dto.TendencyDTO;
import com.reacconmind.reacconmind.model.Hashtag;
import com.reacconmind.reacconmind.model.Tendency;
import com.reacconmind.reacconmind.service.TendencyService;

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
@Tag(name="Tendencies", description = "Provides methods for managing tendencies")
@RequestMapping("tendencies")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
                RequestMethod.DELETE })
@Configuration
@OpenAPIDefinition(info = @Info(title = "ReacconMind API", description = "API for trend management", version = "1.0"))
public class TendencyController {
        @Autowired
        private TendencyService tendencyService;

        @Autowired
        private ModelMapper modelMapper;

        @Operation(summary = "Get all Tendencies with pagination", description = "Retrieve a paginated list of tendencies. Specify the page number and page size to get a subset of tendencies.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successful retrieval of tendencies"),
                        @ApiResponse(responseCode = "400", description = "Invalid page number or page size provided"),
                        @ApiResponse(responseCode = "500", description = "Internal server error")
        })
        @GetMapping(value = "pagination", params = { "page", "pageSize" })
        public List<Tendency> getAllPaginated(
                        @Parameter(description = "The page number to retrieve. Default is 0 (first page).", required = false, example = "1") @RequestParam(value = "page", defaultValue = "0", required = false) int page,

                        @Parameter(description = "The number of hashtags per page. Default is 10.", required = false, example = "5") @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
                List<Tendency> hashtags = tendencyService.getAll(page, pageSize);
                return hashtags;
        }

        @Operation(summary = "Get a tendency by ID", description = "Get a specific trend by your ID.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Tendency found", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = Tendency.class)) }),
                        @ApiResponse(responseCode = "400", description = "Invalid tendency ID", content = @Content),
                        @ApiResponse(responseCode = "401", description = "Autentication failed", content = @Content(schema = @Schema(hidden = true))),
                        @ApiResponse(responseCode = "404", description = "Tendency not found", content = @Content) })
        @GetMapping("/{idTendency}")
        public ResponseEntity<?> getByIdUser(@PathVariable Integer idTendency) {
                Tendency tendency = tendencyService.getByIdTendency(idTendency);
                return new ResponseEntity<Tendency>(tendency, HttpStatus.OK);
        }

        @Operation(summary = "Add a new Tendency", description = "Add a new Tendency on the system.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Succefully tendency added", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = TendencyDTO.class)) }),
                        @ApiResponse(responseCode = "400", description = "Invalid data tendencies", content = @Content),
        })
        @PostMapping
        public ResponseEntity<TendencyDTO> addTendency(@RequestBody TendencyDTO tendencyDTO) {
                TendencyDTO savedTendency = convertToDTO(tendencyService.save(convertToEntity(tendencyDTO)));
                return new ResponseEntity<>(savedTendency, HttpStatus.CREATED);
        }

        private TendencyDTO convertToDTO(Tendency tendency) {
		return modelMapper.map(tendency, TendencyDTO.class);
	}

	public Tendency convertToEntity(TendencyDTO tendencyDTO) {
		return modelMapper.map(tendencyDTO, Tendency.class);
	}

}
