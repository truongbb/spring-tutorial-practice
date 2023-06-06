package com.github.truongbb.emailsending.model.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserSaveRequest {

    int id;

    @NotBlank
    @Size(max = 255)
    String name;

    @Size(max = 255)
    String address;

    @NotBlank
    @Email
    @Size(max = 255)
    String email;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate birthday;

}
