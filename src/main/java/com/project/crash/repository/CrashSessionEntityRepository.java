package com.project.crash.repository;

import com.project.crash.model.entity.CrashSessionEntity;
import com.project.crash.model.entity.SessionSpeakerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrashSessionEntityRepository extends JpaRepository<CrashSessionEntity, Long> {}
