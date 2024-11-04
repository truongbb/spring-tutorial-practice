package org.example.jwtauthenticationspring3.exception.handler;

import org.example.jwtauthenticationspring3.exception.ObjectNotFoundException;
import org.example.jwtauthenticationspring3.exception.UnprocessableEntityException;
import org.example.jwtauthenticationspring3.exception.UserExistedException;
import org.example.jwtauthenticationspring3.model.response.ErrorResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<ErrorResponseModel> handleValidationExceptions(ObjectNotFoundException ex) {
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserExistedException.class)
    public ResponseEntity<ErrorResponseModel> handleUserExistedException(UserExistedException ex) {
        ErrorResponseModel errorResponseModel = ErrorResponseModel.builder()
                .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseModel);
    }

}
