package org.example.studentexammanagement.model.request;

import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.example.studentexammanagement.constant.Gender;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentRequest {

    @NotBlank(message = "Name is required")
    @Length(max = 255, message = "Name is too long")
    String name;

    @NotBlank
    @Email
    @Length(max = 255)
    String email;

    @NotBlank
    @Length(max = 10)
    @Pattern(regexp = "0[0-9]{9}")
    String phone;

    @NotNull
    @Past
    LocalDate dob;

    @NotNull
    Gender gender;

}
