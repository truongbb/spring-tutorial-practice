package com.github.truongbb.jwtrefreshtoken.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchUserDto {

    Long id;
    String username;
    String status;
    Long totalRecord;
}
