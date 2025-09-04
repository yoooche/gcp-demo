package com.eight.demo.module.service;

import com.eight.demo.module.to.MailInboxTO;
import com.eight.demo.module.to.MailSendTO;

public interface MailService {

    void sendMail(MailSendTO mailSendTO);

    void saveUserMailInbox(MailInboxTO mailInboxTO);
}
