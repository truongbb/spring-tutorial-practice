package org.example.studentexammanagement.model.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@EqualsAndHashCode(callSuper = true)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchStudentRequest extends CommonSearchRequest {

    String name;
    Float gpaStart;
    Float gpaEnd;
    String email;
    String phone;

}
