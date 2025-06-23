package com.project.crash.model.slack;

public record SlackNotificationBlock(
        String type,
        SlackNotificationText text
) {
}
