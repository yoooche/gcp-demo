package com.eight.demo.module.service.impl;

import java.util.ArrayList;
import org.springframework.stereotype.Service;
import com.eight.demo.module.common.error.BaseException;
import com.eight.demo.module.constant.StatusCode;
import com.eight.demo.module.kafka.KafkaSender;
import com.eight.demo.module.kafka.Topic;
import com.eight.demo.module.model.Mail;
import com.eight.demo.module.model.MailInbox;
import com.eight.demo.module.repository.IMailInboxRepo;
import com.eight.demo.module.repository.IMailRepo;
import com.eight.demo.module.repository.IRoleRepo;
import com.eight.demo.module.repository.IUserRoleRepo;
import com.eight.demo.module.service.MailService;
import com.eight.demo.module.to.MailInboxTO;
import com.eight.demo.module.to.MailSendTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final IMailRepo mailRepo;
    private final IMailInboxRepo mailInboxRepo;
    private final IUserRoleRepo userRoleRepo;
    private final IRoleRepo roleRepo;
    private final KafkaSender kafkaSender;

    @Override
    @Transactional
    public void sendMail(MailSendTO mailSendTO) {
        var mail = new Mail();
        var role = roleRepo.findByRoleType(mailSendTO.getReceiverRole())
                .orElseThrow(() -> new BaseException(StatusCode.REQ_PARAM_ERR, "Role type does not exist"));

        mail.setContent(mailSendTO.getContent());
        mail.setReceiverRoleId(role.getRoleId());
        mailRepo.saveAndFlush(mail);

        var mailInboxTO = MailInboxTO.builder()
                .mailId(mail.getMailId())
                .roleId(mail.getReceiverRoleId())
                .build();

        kafkaSender.send(Topic.MAIL_INBOX, mailInboxTO);
    }

    @Override
    @Transactional
    public void saveUserMailInbox(MailInboxTO to) {
        if (to.getMailId() == null || to.getRoleId() == null) {
            log.info("Mail id can not be null");
            return;
        }

        var userIds = userRoleRepo.findUserIdByRoleId(to.getRoleId());
        var mailInboxes = new ArrayList<MailInbox>();
        userIds.forEach(userId -> {
            var mailInbox = new MailInbox();
            mailInbox.setMailId(to.getMailId());
            mailInbox.setUserId(userId);
            mailInboxes.add(mailInbox);
        });

        log.info("Total {} mails inbox", mailInboxes.size());
        mailInboxRepo.saveAll(mailInboxes);
    }

}
