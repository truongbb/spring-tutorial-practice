package com.github.truongbb.emailsending.model.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {

    int id;
    String name;
    String address;
    String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate birthday;


}
