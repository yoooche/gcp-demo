package com.eight.demo.module.to;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailSendTO {
    private Long mailId;
    private String receiverRole;
    private String content;
}
