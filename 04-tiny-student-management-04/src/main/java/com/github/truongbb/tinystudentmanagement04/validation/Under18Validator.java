package com.github.truongbb.tinystudentmanagement03.validation;

import org.springframework.util.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;

public class Under18Validator implements ConstraintValidator<Under18, LocalDate> {

    @Override
    public void initialize(Under18 contactNumber) {
    }

    @Override
    public boolean isValid(LocalDate dob, ConstraintValidatorContext cxt) {
        if (ObjectUtils.isEmpty(dob)) {
            return false;
        }
        return Period.between(dob, LocalDate.now()).getYears() > 18;
    }

}