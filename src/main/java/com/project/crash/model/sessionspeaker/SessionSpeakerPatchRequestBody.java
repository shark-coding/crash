package com.project.crash.model.sessionspeaker;

import jakarta.validation.constraints.NotEmpty;

public record SessionSpeakerPatchRequestBody(
        String company,
        String name,
        String description
) {
}
