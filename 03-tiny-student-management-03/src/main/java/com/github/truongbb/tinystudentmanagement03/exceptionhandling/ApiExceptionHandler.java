package com.github.truongbb.tinystudentmanagement03.exceptionhandling;

import com.github.truongbb.tinystudentmanagement03.exceptionhandling.exception.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ApiExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
//    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ExceptionHandler(ObjectNotFoundException.class)
    public String handleValidationExceptions(ObjectNotFoundException ex, Model model) {
        model.addAttribute("errorMess", ex.getMessage());
        return "/404";
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
//        return errors;
    }

}
