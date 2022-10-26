package com.example.newauthtest.service;

import com.example.newauthtest.model.Status2FAModel;

public interface StatusService {
    void save(Status2FAModel status2FAModel);
    Status2FAModel findByChatId(Long userChatId);
    void delete(Long statusId);
}
