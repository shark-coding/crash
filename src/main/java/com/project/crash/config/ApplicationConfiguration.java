package com.project.crash.config;

import com.project.crash.model.crashsession.CrashSessionCategory;
import com.project.crash.model.crashsession.CrashSessionPostRequestBody;
import com.project.crash.model.sessionspeaker.SessionSpeaker;
import com.project.crash.model.sessionspeaker.SessionSpeakerPostRequestBody;
import com.project.crash.model.user.UserSignUpRequestBody;
import com.project.crash.service.CrashSessionService;
import com.project.crash.service.SessionSpeakerService;
import com.project.crash.service.UserService;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Configuration
public class ApplicationConfiguration {

    private static final Faker faker = new Faker();

    @Autowired
    private UserService userService;

    @Autowired
    private SessionSpeakerService sessionSpeakerService;

    @Autowired
    private CrashSessionService crashSessionService;


    @Bean
    public ApplicationRunner applivationRunner() {
        return new ApplicationRunner() {
            @Override
            public void run(ApplicationArguments args) throws Exception {
                // user 및 sessionSpeaker 생성
                createTestUsers();
                createTestSessionSpeakers(10);
            }

        };
    }

    private void createTestUsers() {
        userService.signUp(new UserSignUpRequestBody("victoria", "1234", "Sanga", "victoria@crash.com"));
        userService.signUp(new UserSignUpRequestBody("rose", "1234", "Rose", "rose@crash.com"));
        userService.signUp(new UserSignUpRequestBody("jayce", "1234", "Dev Jayce", "jayce@crash.com"));
        userService.signUp(new UserSignUpRequestBody("pop", "1234", "pop", "pop@crash.com"));
//        userService.signUp(new UserSignUpRequestBody(faker.name().name(), "1234", faker.name().fullName(), faker.internet().emailAddress()));
    }

    private void createTestSessionSpeakers(int numberOfSpeakers) {
        List<SessionSpeaker> sessionSpeakers = IntStream.range(0, numberOfSpeakers)
                .mapToObj(i -> createTestSessionSpeaker()).toList();

        sessionSpeakers.forEach(
                sessionSpeaker -> {
                    int numberOfSessions = new Random().nextInt(4) + 1;
                    IntStream.range(0, numberOfSessions).forEach(i -> createTestCrashSession(sessionSpeaker));
                }
        );
    }

    private SessionSpeaker createTestSessionSpeaker() {
        String name = faker.name().fullName();
        String company = faker.company().name();
        String description = faker.shakespeare().romeoAndJulietQuote(); // 예로 넣음

        return sessionSpeakerService.createSessionSpeaker(
                new SessionSpeakerPostRequestBody(company, name, description));
    }

    private void createTestCrashSession(SessionSpeaker sessionSpeaker) {
        String title = faker.book().title();
        String body = faker.shakespeare().asYouLikeItQuote()
                + faker.shakespeare().hamletQuote()
                + faker.shakespeare().kingRichardIIIQuote()
                + faker.shakespeare().romeoAndJulietQuote();

        crashSessionService.createCrashSession(
                new CrashSessionPostRequestBody(
                        title,
                        body,
                        getRandomCategory(),
                        ZonedDateTime.now().plusDays(new Random().nextInt(2) + 1),
                        sessionSpeaker.speakerId()
                ));
    }

    private CrashSessionCategory getRandomCategory() {
        CrashSessionCategory[] categories = CrashSessionCategory.values();
        int randomIndex = new Random().nextInt(categories.length);
        return categories[randomIndex];
    }
}
