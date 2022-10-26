package com.example.newauthtest.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Status2FAModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long statusId;

    private Long userChatId;
    private String userAnswer2FA;
}
