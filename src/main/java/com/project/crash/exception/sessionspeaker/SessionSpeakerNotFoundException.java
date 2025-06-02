package com.project.crash.exception.sessionspeaker;

import com.project.crash.exception.ClientErrorException;
import org.springframework.http.HttpStatus;

public class SessionSpeakerNotFoundException extends ClientErrorException {

    public SessionSpeakerNotFoundException() {
        super(HttpStatus.NOT_FOUND, "SessionSpeaker not found");
    }

    public SessionSpeakerNotFoundException(Long speakerId) {
        super(HttpStatus.NOT_FOUND, "SessionSpeaker with username " + speakerId + " not found");
    }
}
