package com.github.truongbb.tinystudentmanagement03.entity;

import com.github.truongbb.tinystudentmanagement03.statics.Region;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Student {

    int id;

    String name;

    String email;

    String address;

    String phone;

    LocalDate dob;

    float gpa;

    Region region;

}
