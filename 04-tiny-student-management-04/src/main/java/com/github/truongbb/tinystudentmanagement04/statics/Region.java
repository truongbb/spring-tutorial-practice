package com.github.truongbb.tinystudentmanagement03.statics;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC)
public enum Region {

    MIEN_BAC("Miền Bắc"),
    MIEN_TRUNG("Miền Trung"),
    MIEN_NAM("Miền Nam");

    String name;


}
