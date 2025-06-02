package com.project.crash.model.sessionspeaker;

import com.project.crash.model.entity.SessionSpeakerEntity;

public record SessionSpeaker(Long speakerId, String speakerName, String speakerDescription, String profile) {

    public static SessionSpeaker from(SessionSpeakerEntity sessionSpeakerEntity) {
        return new SessionSpeaker(
                sessionSpeakerEntity.getSpeakerId(),
                sessionSpeakerEntity.getName(),
                sessionSpeakerEntity.getDescription(),
                sessionSpeakerEntity.getProfile());
    }
}
