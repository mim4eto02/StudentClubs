package com.softuni.StudentClubs.handler;

import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
public class ShutdownHandler {

    private final SessionRegistry sessionRegistry;

    public ShutdownHandler(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }

    @PreDestroy
    public void onShutdown() {
        this.invalidateAllSessions();
    }

    private void invalidateAllSessions() {

        this.sessionRegistry
                .getAllPrincipals()
                .forEach(principal ->
                        this.sessionRegistry
                                .getAllSessions(principal, false)
                                .forEach(SessionInformation::expireNow)
                );
    }
}