package com.project.crash.model.sessionspeaker;

import jakarta.validation.constraints.NotEmpty;

public record SessionSpeakerPostRequestBody(
        @NotEmpty String company,
        @NotEmpty String name,
        @NotEmpty String description
) {
}
