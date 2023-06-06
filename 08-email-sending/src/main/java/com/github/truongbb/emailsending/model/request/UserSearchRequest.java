package com.github.truongbb.emailsending.model.request;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class UserSearchRequest {

    String name;
    String address;
    String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate birthday;

}
