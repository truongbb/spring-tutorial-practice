package com.github.truongbb.jwtrefreshtoken.exception.handler;

import com.github.truongbb.jwtrefreshtoken.exception.ExistedUserException;
import com.github.truongbb.jwtrefreshtoken.exception.InvalidRefreshTokenException;
import com.github.truongbb.jwtrefreshtoken.exception.ObjectNotFoundException;
import com.github.truongbb.jwtrefreshtoken.model.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            ExistedUserException.class,
            InvalidRefreshTokenException.class,
            ObjectNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleValidationExceptions(Exception ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(ex.getMessage())
                .build();

        if (ex instanceof ExistedUserException) {
            errorResponse.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } else if (ex instanceof InvalidRefreshTokenException || ex instanceof ObjectNotFoundException) {
            errorResponse.setCode(String.valueOf(HttpStatus.NOT_FOUND.value()));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        errorResponse.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

}
