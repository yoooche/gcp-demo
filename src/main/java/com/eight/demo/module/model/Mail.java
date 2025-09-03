package com.eight.demo.module.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "MAIL")
public class Mail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "RECEIVER_ROLE")
    private String receiverRole;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "STATUS")
    private Short status = 0;

    @Column(name = "VERSION")
    private Short version = 0;

    @Column(name = "CREATE_TIME")
    private LocalDateTime createTime = LocalDateTime.now();

    @Column(name = "UPDATE_TIME")
    private LocalDateTime updateTime = LocalDateTime.now();
}
