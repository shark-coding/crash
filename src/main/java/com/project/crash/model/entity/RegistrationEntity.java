package com.project.crash.model.entity;

import jakarta.persistence.*;

import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Table(name = "registration",
indexes = {
        @Index(
                name = "registration_userid_sessionid_idx",
                columnList = "userid, sessionid",
                unique = true)
})
public class RegistrationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long registrationId;

    @ManyToOne
    @JoinColumn(name = "userid")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "sessionid")
    private CrashSessionEntity session;

    @Column
    private ZonedDateTime createDateTime;

    public Long getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(Long registrationId) {
        this.registrationId = registrationId;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public CrashSessionEntity getSession() {
        return session;
    }

    public void setSession(CrashSessionEntity session) {
        this.session = session;
    }

    public ZonedDateTime getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(ZonedDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistrationEntity that = (RegistrationEntity) o;
        return Objects.equals(registrationId, that.registrationId) && Objects.equals(user, that.user) && Objects.equals(session, that.session) && Objects.equals(createDateTime, that.createDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registrationId, user, session, createDateTime);
    }

    public static RegistrationEntity of (UserEntity user, CrashSessionEntity sessionEntity) {
        RegistrationEntity registrationEntity = new RegistrationEntity();
        registrationEntity.setUser(user);
        registrationEntity.setSession(sessionEntity);
        return registrationEntity;
    }

    @PrePersist
    private void prePersist() {
        this.createDateTime = ZonedDateTime.now();
    }
}
