package com.reacconmind.reacconmind.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reacconmind.reacconmind.dto.AccountUserEmailAddDTO;
import com.reacconmind.reacconmind.dto.AccountUserEmailDTO;
import com.reacconmind.reacconmind.service.AccountUserEmailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/useremail")
public class AccountUserEmailController {

    @Autowired
    private AccountUserEmailService accountUserEmailService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<AccountUserEmailAddDTO> saveEmail(@RequestBody AccountUserEmailAddDTO accountUserEmail) {
        accountUserEmailService.saveEmail(accountUserEmail);
        return new ResponseEntity<>(accountUserEmail, HttpStatus.CREATED);
    }

    @GetMapping("/getByEmail/{email}")
    public ResponseEntity<AccountUserEmailDTO> getByEmail(@PathVariable String email) {
        AccountUserEmailDTO accountUserEmail = modelMapper.map(accountUserEmailService.findByEmail(email),
                AccountUserEmailDTO.class);

        return ResponseEntity.ok(accountUserEmail);
    }
}
