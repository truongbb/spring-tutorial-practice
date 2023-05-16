package com.github.truongbb.tinystudentmanagement03.model;

import com.github.truongbb.tinystudentmanagement03.statics.Region;
import com.github.truongbb.tinystudentmanagement03.validation.Under18;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentCreateModel {

    @Size(max = 100, message = "Name cannot over 100 characters")
    @NotBlank(message = "Student name cannot be blank")
    String name;

    @NotBlank(message = "Student email cannot be blank")
    @Email(message = "Email is invalid")
    String email;

    @Size(max = 255, message = "Address cannot over 255 characters")
    @NotBlank(message = "Student address cannot be blank")
    String address;

    @NotBlank(message = "Student phone cannot be blank")
    @Pattern(regexp = "\\d{10}", message = "Phone must be 10 number characters")
    String phone;

    @NotNull(message = "Please enter birth date")
    @Past(message = "Birth date should be less than current date!!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Under18(message = "Student age must less than 18")
    LocalDate dob;

    @Min(value = 0, message = "GPA cannot be less than 0")
    @Max(value = 10, message = "GPA cannot be greater than 10")
//    @Range(min = 5, max = 10, message = "GPA must be a number between 0 and 10")
    float gpa;

    @NotNull(message = "Region cannot be null")
    Region region;

}
