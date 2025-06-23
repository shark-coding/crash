package com.project.crash.repository;

import com.project.crash.model.crashsession.CrashSession;
import com.project.crash.model.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class CrashSessionCacheRepository {
    @Autowired private RedisTemplate<String, CrashSession> crashSessionRedisTemplate;
    @Autowired private RedisTemplate<String, List<CrashSession>> crashSessionListRedisTemplate;


    public void setCrashSessionCache(CrashSession crashSession) {
        String redisKey = getRedisKey(crashSession.sessionId());
        crashSessionRedisTemplate.opsForValue().set(redisKey, crashSession);
    }

    public void setCrashSessionListCache(List<CrashSession> crashSession) {
        crashSessionListRedisTemplate.opsForValue().set("sessions", crashSession);
    }

    public Optional<CrashSession> getCrashSessionCache(Long sessionId) {
        String redisKey = getRedisKey(sessionId);
        CrashSession crashSession = crashSessionRedisTemplate.opsForValue().get(redisKey);
        return Optional.ofNullable(crashSession);
    }

    public List<CrashSession> getCrashSessionListCache() {
        List<CrashSession> crashSessions = crashSessionListRedisTemplate.opsForValue().get("sessions");
        if (ObjectUtils.isEmpty(crashSessions)) {
            return Collections.emptyList();
        }
        return crashSessions;
    }

    public String getRedisKey(Long sessionId) {
        return "sessionId:" + sessionId;
    }
}
