package com.reacconmind.reacconmind.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.reacconmind.reacconmind.dto.MessageDTO;
import com.reacconmind.reacconmind.service.MessageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Message")
@RequestMapping("ReacconMind/messages")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
        RequestMethod.PUT })
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Operation(summary = "Create a new message", description = "Create a new message.")
    @ApiResponse(responseCode = "201", description = "Message created successfully.")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @PostMapping
    public ResponseEntity<String> createMessage(@RequestBody MessageDTO messageDTO) {
        messageService.createMessage(messageDTO);
        return ResponseEntity.ok("Message created successfully");
    }
    
    
    @Operation(summary = "Update a message", description = "Update an existing message.")
    @ApiResponse(responseCode = "200", description = "Message updated successfully.")
    @ApiResponse(responseCode = "404", description = "Message not found")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateMessage(@PathVariable int id, @RequestBody MessageDTO messageDTO) {
        messageService.updateMessage(id, messageDTO);
        return ResponseEntity.ok("Message updated successfully");
    }

    @Operation(summary = "Delete a message", description = "Delete an existing message.")
    @ApiResponse(responseCode = "200", description = "Message deleted successfully.")
    @ApiResponse(responseCode = "404", description = "Message not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMessage(@PathVariable int id) {
        messageService.deleteMessage(id);
        return ResponseEntity.ok("Message deleted successfully");
    }

}
