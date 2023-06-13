//package com.github.truongbb.basicauthentication.entity;
//
//import lombok.AccessLevel;
//import lombok.Data;
//import lombok.experimental.FieldDefaults;
//
//import javax.persistence.*;
//
//@Data
//@Entity
//@Table(name = "user_role")
//@FieldDefaults(level = AccessLevel.PRIVATE)
//public class UserRole {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "user_role_id")
//    Long userRoleId;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    User user;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "role_id")
//    Role role;
//
//}
