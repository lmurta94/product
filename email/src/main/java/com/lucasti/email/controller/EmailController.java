package com.lucasti.email.controller;

import com.lucasti.email.dtos.kafka.EmailDTO;
import com.lucasti.email.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    EmailService emailService;

    @PostMapping
    ResponseEntity<String> enviarEmail(@RequestBody EmailDTO emailDTO){
//        emailService.sendEmail(emailDTO);
        return ResponseEntity.ok("Ok");
    }

    @GetMapping
    ResponseEntity<String> getEmail(){
//        emailService.sendEmail(emailDTO);
        return ResponseEntity.ok("Ok");
    }
}
