package com.example.newauthtest.config;

import com.example.newauthtest.model.UserModel;
import com.example.newauthtest.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class AuthenticationListener {
    private final UserServiceImpl userService;

    @Autowired
    public AuthenticationListener(UserServiceImpl userService) {
        this.userService = userService;
    }

    @EventListener
    public void listenToSuccess(AuditApplicationEvent event) {
        AuditEvent auditEvent = event.getAuditEvent();

        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) auditEvent.getData();
        UserModel user = (UserModel) token.getPrincipal();

        log.info("Principal " + auditEvent.getPrincipal() + " - " + auditEvent.getType());

        WebAuthenticationDetails details = (WebAuthenticationDetails) auditEvent.getData().get("details");
        log.info("  Remote IP address: " + details.getRemoteAddress());
        log.info("  Session Id: " + details.getSessionId());

        LocalDateTime dateTime = LocalDateTime.now();

        user.setLoginIp(details.getRemoteAddress());
        user.setLoginDate(dateTime);

        userService.save(user);
    }
}
