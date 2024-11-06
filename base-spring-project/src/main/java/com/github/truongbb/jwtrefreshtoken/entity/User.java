package com.github.truongbb.jwtrefreshtoken.entity;

import com.github.truongbb.jwtrefreshtoken.statics.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseEntity {

    String username;

    String password;

//    boolean activated;

    @Enumerated(EnumType.STRING)
    UserStatus status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    String avatar;

    LocalDateTime deletedDateTime;

    LocalDateTime activationMailSentAt;

    Integer activationMailSentCount;

    LocalDateTime forgotPasswordMailSentAt;

}
