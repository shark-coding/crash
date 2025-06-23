package com.project.crash.model.slack;

import java.util.List;

public record SlackNotificationMessage(
        List<SlackNotificationBlock> blocks
) {
}

