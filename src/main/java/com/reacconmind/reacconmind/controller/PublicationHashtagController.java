package com.reacconmind.reacconmind.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reacconmind.reacconmind.model.PublicationHashtag;
import com.reacconmind.reacconmind.service.PublicationHashtagService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("publicationHashtags")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE })
@Tag(name = "Publication Hashtags", description = "Provides methods for managing publication and hashtag relationships")
public class PublicationHashtagController {
    @Autowired
    private PublicationHashtagService publicationHashtagService;

    @Operation(summary = "Get all publication hashtag relationship with pagination", description = "Retrieve a paginated list of publication hashtag. Specify the page number and page size to get a subset.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successful retrieval of publication hashtags"),
                        @ApiResponse(responseCode = "400", description = "Invalid page number or page size provided"),
                        @ApiResponse(responseCode = "500", description = "Internal server error")
        })
        @GetMapping(value = "pagination", params = { "page", "pageSize" })
        public List<PublicationHashtag> getAllPaginated(
                        @Parameter(description = "The page number to retrieve. Default is 0 (first page).", required = false, example = "1") @RequestParam(value = "page", defaultValue = "0", required = false) int page,

                        @Parameter(description = "The number of hashtags per page. Default is 10.", required = false, example = "5") @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
                List<PublicationHashtag> publicationHashtags = publicationHashtagService.getAll(page, pageSize);
                return publicationHashtags;
        }

    @Operation(summary = "Save a new publication hashtag relationship")
    @ApiResponse(responseCode = "201", description = "Publication hashtag created successfully")
    @PostMapping
    public ResponseEntity<String> savePublicationHashtag(@RequestBody PublicationHashtag publicationHashtag) {
        publicationHashtagService.savePublicationHashtag(publicationHashtag);
        return ResponseEntity.status(HttpStatus.CREATED).body("PublicationHashtag relationship created successfully");
    }
}
