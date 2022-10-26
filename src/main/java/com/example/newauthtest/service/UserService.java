package com.example.newauthtest.service;

import com.example.newauthtest.model.UserModel;

import java.util.Optional;

public interface UserService {
    boolean save(UserModel userModel);
    Optional<UserModel> findById(Long userId);
    UserModel findByUsername(String userName);
    UserModel findByResetPasswordUUID(String stringUUID);
    void update(UserModel userModel);
    UserModel findByChatId(Long chatId);
}
