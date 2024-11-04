package org.example.jwtauthenticationspring3.exception;

public class UserExistedException extends Exception {
    public UserExistedException(String message) {
        super(message);
    }
}
