package com.eight.demo.module.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.eight.demo.module.service.MailService;
import com.eight.demo.module.to.MailInboxTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final MailService mailService;

    @KafkaListener(topics = Topic.MAIL_INBOX)
    public void processMailNotification(String content) {
        log.info("Receive topic [{}] and message=[{}]", Topic.MAIL_INBOX, content);
        try {
            var om = new ObjectMapper();
            var mail = om.readValue(content, MailInboxTO.class);
            mailService.saveUserMailInbox(mail);
        } catch (Exception e) {
            log.warn("Failed to process mail task", e);
        }
    }
}
