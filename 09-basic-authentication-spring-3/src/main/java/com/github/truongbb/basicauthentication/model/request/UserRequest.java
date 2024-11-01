package com.github.truongbb.basicauthentication.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {

    @NotBlank
    @Size(max = 100)
    String username;

    @NotBlank
    String password;

}
