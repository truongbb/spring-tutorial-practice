package com.github.truongbb.jwtrefreshtoken.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUserRequest {

    @NotBlank
    @Length(max = 50, message = "username không được vượt quá 50 ký tự")
    String username;

}