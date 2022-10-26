package com.example.newauthtest.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@Data
@Entity
public class UserModel implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private String username;
    private String password;

    private String loginIp;
    private LocalDateTime loginDate;

    private String resetPasswordUUID;

    private Long telegramBotChatId;
    private boolean status2FA;

    public UserModel() {}
    public UserModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roleSet;

    @Override
    public String toString() {
        return String.format("User %d = \n{ username: %s, \npassword: %s\n}",
                getUserId(), getUsername(), getPassword());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoleSet();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
