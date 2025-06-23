package com.project.crash.service;

import com.project.crash.exception.crashsession.CrashSessionNotFoundException;
import com.project.crash.model.crashsession.CrashSession;
import com.project.crash.model.crashsession.CrashSessionPatchRequestBody;
import com.project.crash.model.crashsession.CrashSessionPostRequestBody;
import com.project.crash.model.entity.CrashSessionEntity;
import com.project.crash.model.entity.SessionSpeakerEntity;
import com.project.crash.model.sessionspeaker.SessionSpeaker;
import com.project.crash.repository.CrashSessionCacheRepository;
import com.project.crash.repository.CrashSessionEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@Service
public class CrashSessionService {
    @Autowired private CrashSessionEntityRepository crashSessionEntityRepository;
    @Autowired private SessionSpeakerService sessionSpeakerService;
    @Autowired private CrashSessionCacheRepository crashSessionCacheRepository;

    public List<CrashSession> getCrashSessions() {
        List<CrashSession> crashSessions = crashSessionCacheRepository.getCrashSessionListCache();
        if (!ObjectUtils.isEmpty(crashSessions)) {
            return crashSessions;
        } else {
            List<CrashSession> crashSessionList = crashSessionEntityRepository.findAll().stream().map(CrashSession::from).toList();
            crashSessionCacheRepository.setCrashSessionListCache(crashSessionList);
            return crashSessionList;
        }
    }

    public CrashSession getCrashSessionBySessionId(Long sessionId) {
        return crashSessionCacheRepository
                .getCrashSessionCache(sessionId)
                .orElseGet(() -> {
                    CrashSessionEntity crashSessionEntity = getCrashSessionEntityBySessionId(sessionId);
                    CrashSession crashSession = CrashSession.from(crashSessionEntity);
                    crashSessionCacheRepository.setCrashSessionCache(crashSession);
                    return crashSession;
                });
    }

    public CrashSession createCrashSession(CrashSessionPostRequestBody crashSessionPostRequestBody) {
        SessionSpeakerEntity sessionSpeakerEntity = sessionSpeakerService.getSessionSpeakerEntityBySpeakerId(
                crashSessionPostRequestBody.speakerId());
        CrashSessionEntity crashSessionEntity = CrashSessionEntity.of(
                crashSessionPostRequestBody.title(),
                crashSessionPostRequestBody.body(),
                crashSessionPostRequestBody.category(),
                crashSessionPostRequestBody.dateTime(),
                sessionSpeakerEntity);
        return CrashSession.from(
                crashSessionEntityRepository.save(crashSessionEntity));
    }

    public CrashSession updateCrashSession(Long sessionId, CrashSessionPatchRequestBody crashSessionPatchRequestBody) {
        CrashSessionEntity crashSessionEntity = getCrashSessionEntityBySessionId(sessionId);

        if(!ObjectUtils.isEmpty(crashSessionPatchRequestBody.title())) {
            crashSessionEntity.setTitle(
                    crashSessionPatchRequestBody.title()
            );
        }
        if(!ObjectUtils.isEmpty(crashSessionPatchRequestBody.body())) {
            crashSessionEntity.setBody(
                    crashSessionPatchRequestBody.body()
            );
        }
        if(!ObjectUtils.isEmpty(crashSessionPatchRequestBody.category())) {
            crashSessionEntity.setCategory(
                    crashSessionPatchRequestBody.category()
            );
        }
        if(!ObjectUtils.isEmpty(crashSessionPatchRequestBody.dateTime())) {
            crashSessionEntity.setDateTime(
                    crashSessionPatchRequestBody.dateTime()
            );
        }
        if(!ObjectUtils.isEmpty(crashSessionPatchRequestBody.speakerId())) {
            SessionSpeakerEntity sessionSpeakerEntity = sessionSpeakerService.getSessionSpeakerEntityBySpeakerId(
                    crashSessionPatchRequestBody.speakerId());

            crashSessionEntity.setSpeaker(sessionSpeakerEntity);
        }

        return CrashSession.from(
                crashSessionEntityRepository.save(crashSessionEntity));
    }

    public void deleteCrashSession(Long sessionId) {
        CrashSessionEntity crashSessionEntity = getCrashSessionEntityBySessionId(sessionId);
        crashSessionEntityRepository.delete(crashSessionEntity);
    }


    public CrashSessionEntity getCrashSessionEntityBySessionId(Long sessionId) {
        return crashSessionEntityRepository.findById(sessionId)
                .orElseThrow(
                        () -> new CrashSessionNotFoundException(sessionId));
    }
}
