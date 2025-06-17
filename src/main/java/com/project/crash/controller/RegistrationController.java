package com.project.crash.controller;

import com.project.crash.model.crashsession.CrashSession;
import com.project.crash.model.crashsession.CrashSessionPatchRequestBody;
import com.project.crash.model.crashsession.CrashSessionPostRequestBody;
import com.project.crash.model.entity.UserEntity;
import com.project.crash.model.registration.Registration;
import com.project.crash.model.registration.RegistrationPostRequestBody;
import com.project.crash.service.CrashSessionService;
import com.project.crash.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/registrations")
public class RegistrationController {

    @Autowired private RegistrationService registrationService;

    @GetMapping
    public ResponseEntity<List<Registration>> getRegistrations(Authentication authentication) {
        List<Registration> registrations = registrationService.getRegistrationsByCurrentUser((UserEntity) authentication.getPrincipal());
        return ResponseEntity.ok(registrations);
    }

    @GetMapping("/{registrationId}")
    public ResponseEntity<Registration> getRegistrationByRegistrationId(@PathVariable Long registrationId, Authentication authentication) {
        Registration registration = registrationService.getRegistrationByRegistrationIdByCurrentUser(registrationId, (UserEntity) authentication.getPrincipal());
        return ResponseEntity.ok(registration);
    }

    @PostMapping
    public ResponseEntity<Registration> createRegistration(
            @Valid @RequestBody RegistrationPostRequestBody registrationPostRequestBody,
            Authentication authentication
    ) {
        Registration sessionSpeaker = registrationService.createRegistrationByCurrentUser(registrationPostRequestBody, (UserEntity) authentication.getPrincipal());
        return ResponseEntity.ok(sessionSpeaker);
    }

    @DeleteMapping("/{registrationId}")
    public ResponseEntity<Void> deleteRegistration(@PathVariable Long registrationId, Authentication authentication) {
        registrationService.deleteRegistrationByRegistrationIdAndCurrentUser(registrationId, (UserEntity) authentication.getPrincipal());
        return ResponseEntity.noContent().build();
    }
}
