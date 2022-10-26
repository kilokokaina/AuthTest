package com.example.newauthtest.service.impl;

import com.example.newauthtest.model.Role;
import com.example.newauthtest.model.UserModel;
import com.example.newauthtest.repo.UserRepository;
import com.example.newauthtest.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public record UserServiceImpl(UserRepository userRepository) implements UserService, UserDetailsService {
    @Override
    public boolean save(UserModel userModel) {
        UserModel userFromDB = userRepository.findByUsername(userModel.getUsername());

        if (userFromDB != null) return false;

        userModel.setRoleSet(Collections.singleton(Role.STUDENT));
        userRepository.save(userModel);

        return true;
    }

    public void update(UserModel userModel) {
        UserModel userFromDB = userRepository.findById(userModel.getUserId()).orElseThrow();
        BeanUtils.copyProperties(userModel, userFromDB, "user_id");

        userRepository.save(userFromDB);
    }

    @Override
    public Optional<UserModel> findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public UserModel findByUsername(String userName) {
        return userRepository.findByUsername(userName);
    }

    @Override
    public UserModel findByResetPasswordUUID(String stringUUID) {
        return userRepository.findByResetPasswordUUID(stringUUID);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserModel findByChatId(Long chatId) {
        return userRepository.findByTelegramBotChatId(chatId);
    }
}
