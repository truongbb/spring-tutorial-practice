package com.github.truongbb.tinystudentmanagement01.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Student {

    int id;
    String name;
    String address;
    String phone;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate dob;
    float gpa;


}
