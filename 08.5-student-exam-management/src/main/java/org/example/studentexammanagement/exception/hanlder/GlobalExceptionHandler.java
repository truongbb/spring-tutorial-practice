package org.example.studentexammanagement.exception.hanlder;

import org.example.studentexammanagement.exception.ObjectNotFoundException;
import org.example.studentexammanagement.exception.UnprocessableEntityException;
import org.example.studentexammanagement.model.ErrorResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<?> handleValidationExceptions(ObjectNotFoundException ex) {
        ErrorResponseModel errorResponseModel = ErrorResponseModel.builder()
                .code(String.valueOf(HttpStatus.NOT_FOUND.value()))
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseModel);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<ErrorResponseModel> handleUnprocessableEntityException(UnprocessableEntityException ex) {
        ErrorResponseModel errorResponseModel = ErrorResponseModel.builder()
                .code(String.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponseModel);
    }

}
