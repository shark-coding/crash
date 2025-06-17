package com.project.crash.controller;

import com.project.crash.model.crashsession.CrashSession;
import com.project.crash.model.crashsession.CrashSessionPatchRequestBody;
import com.project.crash.model.crashsession.CrashSessionPostRequestBody;
import com.project.crash.model.sessionspeaker.SessionSpeaker;
import com.project.crash.model.sessionspeaker.SessionSpeakerPatchRequestBody;
import com.project.crash.model.sessionspeaker.SessionSpeakerPostRequestBody;
import com.project.crash.service.CrashSessionService;
import com.project.crash.service.SessionSpeakerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/crash-sessions")
public class CrashSessionController {

    @Autowired
    private CrashSessionService crashSessionService;

    @GetMapping
    public ResponseEntity<List<CrashSession>> getCrashSessions() {
        List<CrashSession> crashSessions = crashSessionService.getCrashSessions();
        return ResponseEntity.ok(crashSessions);
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<CrashSession> getCrashSessionBySessionId(@PathVariable Long sessionId) {
        CrashSession crashSession = crashSessionService.getCrashSessionBySessionId(sessionId);
        return ResponseEntity.ok(crashSession);
    }

    @PostMapping
    public ResponseEntity<CrashSession> createCrashSession(
            @Valid @RequestBody CrashSessionPostRequestBody crashSessionPostRequestBody
    ) {
        CrashSession sessionSpeaker = crashSessionService.createCrashSession(crashSessionPostRequestBody);
        return ResponseEntity.ok(sessionSpeaker);
    }

    @PatchMapping("/{sessionId}")
    public ResponseEntity<CrashSession> updateCrashSession(
            @PathVariable Long sessionId,
            @RequestBody CrashSessionPatchRequestBody crashSessionPatchRequestBody) {
        CrashSession sessionSpeaker = crashSessionService.updateCrashSession(sessionId, crashSessionPatchRequestBody);
        return ResponseEntity.ok(sessionSpeaker);
    }

    @DeleteMapping("/{sessionId}")
    public ResponseEntity<Void> deleteCrashSession(@PathVariable Long sessionId) {
        crashSessionService.deleteCrashSession(sessionId);
        return ResponseEntity.noContent().build();
    }
}
