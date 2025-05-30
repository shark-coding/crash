package com.project.crash.exception.user;

import com.project.crash.exception.ClientErrorException;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends ClientErrorException {

    public UserAlreadyExistsException() {
        super(HttpStatus.CONFLICT, "User already exists");
    }

    public UserAlreadyExistsException(String message) {
        super(HttpStatus.CONFLICT, "User with username " + message + " already exists");
    }
}
