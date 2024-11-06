package com.github.truongbb.jwtrefreshtoken.model.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
public class BaseSearchRequest {

    Integer pageIndex = 0;
    Integer pageSize = 10;

}
