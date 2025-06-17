package com.project.crash.service;

import com.project.crash.exception.registration.RegistrationAlreadyExistsException;
import com.project.crash.exception.registration.RegistrationNotFoundException;
import com.project.crash.model.crashsession.CrashSession;
import com.project.crash.model.crashsession.CrashSessionRegistrationStatus;
import com.project.crash.model.entity.CrashSessionEntity;
import com.project.crash.model.entity.RegistrationEntity;
import com.project.crash.model.entity.UserEntity;
import com.project.crash.model.registration.Registration;
import com.project.crash.model.registration.RegistrationPostRequestBody;
import com.project.crash.repository.RegistrationEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegistrationService {
    @Autowired private RegistrationEntityRepository registrationEntityRepository;
    @Autowired private CrashSessionService crashSessionService;


    public List<Registration> getRegistrationsByCurrentUser(UserEntity currentUser) {
        List<RegistrationEntity> registrationEntities = registrationEntityRepository.findByUser(currentUser);
        return registrationEntities.stream().map(Registration::from).toList();
    }

    public Registration getRegistrationByRegistrationIdByCurrentUser(Long registrationId, UserEntity currentUser) {
        RegistrationEntity registrationEntity = getRegistrationEntityByRegistrationIdAndUserEntity(registrationId, currentUser);
        return Registration.from(registrationEntity);
    }

    public RegistrationEntity getRegistrationEntityByRegistrationIdAndUserEntity(Long registrationId, UserEntity userEntity) {
        return registrationEntityRepository.findByRegistrationIdAndUser(registrationId, userEntity)
                .orElseThrow(
                        () -> new RegistrationNotFoundException(registrationId, userEntity)
                );
    }

    public Registration createRegistrationByCurrentUser(RegistrationPostRequestBody registrationPostRequestBody, UserEntity currentUser) {
        CrashSessionEntity crashSessionEntity = crashSessionService.getCrashSessionEntityBySessionId(registrationPostRequestBody.sessionId());
        registrationEntityRepository.findByUserAndSession(currentUser, crashSessionEntity)
                .ifPresent(
                        registrationEntity -> {
                            throw new RegistrationAlreadyExistsException(
                                    registrationEntity.getRegistrationId(), currentUser
                            );
                        });

        RegistrationEntity registrationEntity = RegistrationEntity.of(currentUser, crashSessionEntity);
        return Registration.from(registrationEntityRepository.save(registrationEntity));
    }

    public void deleteRegistrationByRegistrationIdAndCurrentUser(Long registrationId, UserEntity currentUser) {
        RegistrationEntity registrationEntity = getRegistrationEntityByRegistrationIdAndUserEntity(registrationId, currentUser);
        registrationEntityRepository.delete(registrationEntity);
    }

    public CrashSessionRegistrationStatus getCrashSessionRegistrationStatusBySessionIdAndCurrentUser(Long sessionId, UserEntity currentUser) {
        CrashSessionEntity crashSessionEntity = crashSessionService.getCrashSessionEntityBySessionId(sessionId);
        Optional<RegistrationEntity> registrationEntity = registrationEntityRepository.findByUserAndSession(currentUser, crashSessionEntity);
        return new CrashSessionRegistrationStatus(
                sessionId,
                registrationEntity.isPresent(),
                registrationEntity.map(RegistrationEntity::getRegistrationId).orElse(null));
    }
}
