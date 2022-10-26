package com.example.newauthtest.service.impl;

import com.example.newauthtest.model.Status2FAModel;
import com.example.newauthtest.repo.StatusRepository;
import com.example.newauthtest.service.StatusService;
import org.springframework.stereotype.Service;

@Service
public record StatusServiceImpl(StatusRepository statusRepository) implements StatusService {
    @Override
    public void save(Status2FAModel status2FAModel) {
        statusRepository.save(status2FAModel);
    }

    @Override
    public Status2FAModel findByChatId(Long userChatId) {
        return statusRepository.findByUserChatId(userChatId);
    }

    @Override
    public void delete(Long statusId) {
        statusRepository.deleteById(statusId);
    }
}
