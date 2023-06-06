package com.github.truongbb.tinystudentmanagement05.model.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentSearchRequest {

    Long id;
    String name;
    String address;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate birthdayFrom;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate birthdayTo;

}
