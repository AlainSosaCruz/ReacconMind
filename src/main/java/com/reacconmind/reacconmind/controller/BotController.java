package com.reacconmind.reacconmind.controller;

import java.util.List;
import java.util.Optional;

import com.reacconmind.reacconmind.dto.BotDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.reacconmind.reacconmind.model.Bot;
import com.reacconmind.reacconmind.service.BotService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("bots")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
        RequestMethod.PUT })
        
@Tag(name = "Bot")
public class BotController {


    @Autowired
    private BotService botService;

    @Operation(summary = "Get all Bots", description = "Retrieve a list of all bots.")
    @ApiResponse(responseCode = "200", description = "List of bots retrieved successfully.", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BotDTO.class))) })
    @GetMapping
    public List<BotDTO> getAllBots() {
        List<Bot> bots = botService.getAllBots();
        return bots.stream().map(botService::convertEntityToDTO).toList();
    }

    @Operation(summary = "Get a Bot by ID", description = "Retrieve a bot by its ID.")
    @ApiResponse(responseCode = "200", description = "Bot retrieved successfully.", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = BotDTO.class)) })
    @ApiResponse(responseCode = "404", description = "Bot not found.")
    @GetMapping("/{idBot}")
    public ResponseEntity<BotDTO> getBotById(@PathVariable int idBot) {
        Optional<Bot> bot = botService.getBotById(idBot);
        return bot.map(b -> ResponseEntity.ok(botService.convertEntityToDTO(b)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a Bot", description = "Create a new bot.")
    @ApiResponse(responseCode = "200", description = "Bot created correctly.", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = BotDTO.class)) })
    @PostMapping
    public ResponseEntity<String> createBot(@RequestBody Bot bot) {
        System.out.println("BotDTO received: " + bot.getName() + ", " + bot.getTheme());
        botService.createBot(bot);
        return new ResponseEntity<>("Bot created successfully", HttpStatus.CREATED);
    }
    
    
    @Operation(summary = "Update a Bot", description = "Update an existing bot by ID.")
    @ApiResponse(responseCode = "200", description = "Bot updated successfully.", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = BotDTO.class)) })
    @ApiResponse(responseCode = "404", description = "Bot not found.")
    @PutMapping("/{idBot}")
    public ResponseEntity<BotDTO> updateBot(@PathVariable int idBot, @RequestBody BotDTO botDTO) {
        Bot updatedBot = botService.convertDTOToEntity(botDTO);
        Bot bot = botService.updateBot(idBot, updatedBot);
        if (bot != null) {
            return ResponseEntity.ok(botService.convertEntityToDTO(bot));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a Bot", description = "Delete a bot by ID.")
    @ApiResponse(responseCode = "204", description = "Bot deleted successfully.")
    @ApiResponse(responseCode = "404", description = "Bot not found.")
    @DeleteMapping("/{idBot}")
    public ResponseEntity<Void> deleteBot(@PathVariable int idBot) {
        botService.deleteBot(idBot);
        return ResponseEntity.noContent().build();
    }

}
