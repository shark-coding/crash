package com.project.crash.model.registration;

import com.project.crash.model.crashsession.CrashSession;
import com.project.crash.model.entity.RegistrationEntity;
import com.project.crash.model.user.User;

public record Registration(Long registrationId, User user, CrashSession session) {
    public static Registration from(RegistrationEntity registrationEntity) {
        return new Registration(
                registrationEntity.getRegistrationId(),
                User.from(registrationEntity.getUser()),
                CrashSession.from(registrationEntity.getSession()));
    }
}
