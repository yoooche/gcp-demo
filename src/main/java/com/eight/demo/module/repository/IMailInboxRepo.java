package com.eight.demo.module.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eight.demo.module.model.MailInbox;

public interface IMailInboxRepo extends JpaRepository<MailInbox, Long> {

}
