package org.example.studentexammanagement.model.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PROTECTED)
public class CommonSearchRequest {

    int pageIndex = 0;
    int pageSize = 10;

}
