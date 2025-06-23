package com.project.crash.service;

import com.project.crash.model.registration.Registration;
import com.project.crash.model.slack.SlackNotificationBlock;
import com.project.crash.model.slack.SlackNotificationMessage;
import com.project.crash.model.slack.SlackNotificationText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

import static java.awt.SystemColor.text;

@Service
public class SlackService {
    private static final Logger logger = LoggerFactory.getLogger(SlackService.class);

    private static final RestClient restClient = RestClient.create();

    @Value("${slack.webhook}")
    private String slackWebhook;

    public void sendSlackNotification(Registration registration) {
        String linkText = getRegistrationDetailsPageLinkText(registration);

        SlackNotificationMessage slackNotificationMessage = new SlackNotificationMessage(
                List.of(
                        new SlackNotificationBlock(
                                "section",
                                new SlackNotificationText(
                                        "mrkdwn",linkText
                                )
                        )
                )
        );

        String response = restClient
                .post()
                .uri(slackWebhook)
                .body(slackNotificationMessage)
                .retrieve()
                .body(String.class);
        logger.info(response);
    }

    private String getRegistrationDetailsPageLinkText(Registration registration) {
        String baseLink = "https://dev-jayce.github.io/crash/registration.html?registration=";
        Long registrationId = registration.registrationId();
        String username = registration.user().username();
        Long sessionId = registration.session().sessionId();
        String link = baseLink + registrationId + "," + username + "," + sessionId;
        return  ":collision: *CRASH* <" + link + "|Registraion Details>";
    }
}
