package com.example.newauthtest.repo;

import com.example.newauthtest.model.Status2FAModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status2FAModel, Long> {
    Status2FAModel findByUserChatId(Long userChatId);
}
