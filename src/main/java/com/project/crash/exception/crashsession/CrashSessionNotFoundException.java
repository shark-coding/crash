package com.project.crash.exception.crashsession;

import com.project.crash.exception.ClientErrorException;
import org.springframework.http.HttpStatus;

public class CrashSessionNotFoundException extends ClientErrorException {

    public CrashSessionNotFoundException() {
        super(HttpStatus.NOT_FOUND, "CrashSession not found");
    }

    public CrashSessionNotFoundException(Long sessionId) {
        super(HttpStatus.NOT_FOUND, "CrashSession with username " + sessionId + " not found");
    }
}
