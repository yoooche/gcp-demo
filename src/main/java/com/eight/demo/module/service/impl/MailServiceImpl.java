package com.eight.demo.module.service.impl;

import org.springframework.stereotype.Service;

import com.eight.demo.module.kafka.KafkaSender;
import com.eight.demo.module.kafka.Topic;
import com.eight.demo.module.service.MailService;
import com.eight.demo.module.to.MailSendTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final KafkaSender kafkaSender;

    @Override
    public void sendMail(MailSendTO mailSendTO) {
        kafkaSender.send(Topic.MAIL, mailSendTO);
    }

}
