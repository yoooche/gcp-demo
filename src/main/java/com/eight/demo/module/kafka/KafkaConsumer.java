package com.eight.demo.module.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.eight.demo.module.model.Mail;
import com.eight.demo.module.repository.IMailRepo;
import com.eight.demo.module.to.MailSendTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final IMailRepo mailRepo;

    @KafkaListener(topics = Topic.MAIL)
    public void processMailNotification(String content) {
        log.info("Receive topic [{}] and message=[{}]", Topic.MAIL, content);
        try {
            var om = new ObjectMapper();
            var mail = om.readValue(content, MailSendTO.class);
            var model = new Mail();
            model.setReceiverRole(mail.getReceiverRole());
            model.setContent(mail.getContent());
            mailRepo.saveAndFlush(model);
        } catch (Exception e) {
            log.warn("Failed to process mail task", e);
        }
    }
}
