package com.project.crash.repository;

import com.project.crash.model.entity.CrashSessionEntity;
import com.project.crash.model.entity.RegistrationEntity;
import com.project.crash.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegistrationEntityRepository extends JpaRepository<RegistrationEntity, Long> {
    List<RegistrationEntity> findByUser(UserEntity user);

    Optional<RegistrationEntity> findByRegistrationIdAndUser(Long registrationId, UserEntity user);

    // 중복 생성 방지, 생성 전 사전 조회
    Optional<RegistrationEntity> findByUserAndSession(UserEntity user, CrashSessionEntity session);
}
