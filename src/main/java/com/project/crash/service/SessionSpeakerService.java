package com.project.crash.service;

import com.project.crash.exception.sessionspeaker.SessionSpeakerNotFoundException;
import com.project.crash.model.entity.CrashSessionEntity;
import com.project.crash.model.entity.SessionSpeakerEntity;
import com.project.crash.model.sessionspeaker.SessionSpeaker;
import com.project.crash.model.sessionspeaker.SessionSpeakerPatchRequestBody;
import com.project.crash.model.sessionspeaker.SessionSpeakerPostRequestBody;
import com.project.crash.repository.CrashSessionEntityRepository;
import com.project.crash.repository.SessionSpeakerEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.management.Descriptor;
import java.util.List;

@Service
public class SessionSpeakerService {
    @Autowired private SessionSpeakerEntityRepository sessionSpeakerEntityRepository;
    @Autowired private CrashSessionEntityRepository crashSessionEntityRepository;


    public List<SessionSpeaker> getSessionSpeakers() {
        List<SessionSpeakerEntity> sessionSpeakerEntities = sessionSpeakerEntityRepository.findAll();
        return sessionSpeakerEntities.stream().map(SessionSpeaker::from).toList();
    }

    public SessionSpeakerEntity getSessionSpeakerEntityBySpeakerId(Long speakerId) {
        return sessionSpeakerEntityRepository
                .findById(speakerId)
                .orElseThrow(() -> new SessionSpeakerNotFoundException(speakerId));
    }

    public SessionSpeaker getSessionSpeakerBySpeakerId(Long speakerId) {
        SessionSpeakerEntity sessionSpeakerEntity = getSessionSpeakerEntityBySpeakerId(speakerId);
        return SessionSpeaker.from(sessionSpeakerEntity);
    }


    public SessionSpeaker createSessionSpeaker(SessionSpeakerPostRequestBody sessionSpeakerPostRequestBody) {
        SessionSpeakerEntity sessionSpeakerEntity =
        SessionSpeakerEntity.of(
                sessionSpeakerPostRequestBody.company(),
                sessionSpeakerPostRequestBody.name(),
                sessionSpeakerPostRequestBody.description());

        return SessionSpeaker.from(sessionSpeakerEntityRepository.save(sessionSpeakerEntity));
    }

    public SessionSpeaker updateSessionSpeaker(Long speakerId, SessionSpeakerPatchRequestBody sessionSpeakerPatchRequestBody) {
        SessionSpeakerEntity sessionSpeakerEntity = getSessionSpeakerEntityBySpeakerId(speakerId);

        if (!ObjectUtils.isEmpty(sessionSpeakerPatchRequestBody.company())) {
            sessionSpeakerEntity.setCompany(sessionSpeakerPatchRequestBody.company());
        }

        if (!ObjectUtils.isEmpty(sessionSpeakerPatchRequestBody.name())) {
            sessionSpeakerEntity.setName(sessionSpeakerPatchRequestBody.name());
        }

        if (!ObjectUtils.isEmpty(sessionSpeakerPatchRequestBody.description())) {
            sessionSpeakerEntity.setDescription(sessionSpeakerPatchRequestBody.description());
        }

        return SessionSpeaker.from(sessionSpeakerEntityRepository.save(sessionSpeakerEntity));
    }

    public void deleteSessionSpeaker(Long speakerId) {
        SessionSpeakerEntity sessionSpeakerEntity = getSessionSpeakerEntityBySpeakerId(speakerId);
        if (!ObjectUtils.isEmpty(sessionSpeakerEntity)) {
            List<CrashSessionEntity> crashSessions = crashSessionEntityRepository.findBySpeaker_SpeakerId(speakerId);
            crashSessionEntityRepository.deleteAll(crashSessions);
            sessionSpeakerEntityRepository.delete(sessionSpeakerEntity);
        }

    }
}
