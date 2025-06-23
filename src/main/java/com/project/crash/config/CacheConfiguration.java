package com.project.crash.config;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.crash.model.crashsession.CrashSession;
import com.project.crash.model.entity.CrashSessionEntity;
import com.project.crash.model.entity.UserEntity;
import org.springframework.beans.factory.annotation.Value; // ✅ 올바른 import
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;

@Configuration
public class CacheConfiguration {

    private static final ObjectMapper objectMapper =
            new ObjectMapper().registerModule(new JavaTimeModule());

    @Bean
    RedisConnectionFactory redisConnectionFactory(
            @Value("${redis.host}") String redisHost, @Value("${redis.port}") int redisPort) {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisHost);
        config.setPort(redisPort);

        return new LettuceConnectionFactory(config);
    }

    @Bean
    public RedisTemplate<String, UserEntity> userEntityRedisTemplate(
            RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, UserEntity> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        // 어떻게 문자열로 바꿔서 저장할 것인지
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<UserEntity>(objectMapper, UserEntity.class));
        return template;
    }

    @Bean
    public RedisTemplate<String, CrashSession> crashSessionRedisTemplate(
            RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, CrashSession> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<CrashSession>(objectMapper, CrashSession.class));
        return template;
    }

    @Bean
    public RedisTemplate<String, List<CrashSession>> crashSessionListRedisTemplate(
            RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, List<CrashSession>> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        template.setKeySerializer(new StringRedisSerializer());
        JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, CrashSession.class);
        template.setValueSerializer(new Jackson2JsonRedisSerializer<List<CrashSession>>(objectMapper, type));
        return template;
    }
}

