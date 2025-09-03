package com.eight.demo.module.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eight.demo.module.model.Mail;

public interface IMailRepo extends JpaRepository<Mail, Long> {

}
