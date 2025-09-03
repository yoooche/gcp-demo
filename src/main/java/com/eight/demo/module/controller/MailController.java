package com.eight.demo.module.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eight.demo.module.service.MailService;
import com.eight.demo.module.to.MailSendTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping(value = "/mail")
@RestController
public class MailController {

    private final MailService mailService;

    @PostMapping
    public ResponseEntity<Void> sendMail(@RequestBody MailSendTO mailSendTO) {
        mailService.sendMail(mailSendTO);
        return ResponseEntity.ok().build();
    }
}
