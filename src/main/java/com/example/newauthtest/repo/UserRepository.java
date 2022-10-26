package com.example.newauthtest.repo;

import com.example.newauthtest.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    UserModel findByUsername(String userName);
    UserModel findByResetPasswordUUID(String stringUUID);
    UserModel findByTelegramBotChatId(Long chatId);
}
