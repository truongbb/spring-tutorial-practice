package com.github.truongbb.jwtrefreshtoken.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;


@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "refresh_tokens")
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RefreshToken extends BaseEntity {

    @JoinColumn(name = "user_id")
    @ManyToOne(targetEntity = User.class)
    User user;

    @Column(name = "refresh_token")
    String refreshToken;

    //    @Type(type= "org.hibernate.type.NumericBooleanType")
    @Column(columnDefinition = "boolean default false")
    boolean invalidated;

}
