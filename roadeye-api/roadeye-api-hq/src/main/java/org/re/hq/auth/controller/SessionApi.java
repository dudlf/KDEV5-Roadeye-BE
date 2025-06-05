package org.re.hq.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.re.hq.auth.dto.SessionInfoResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/session")
@RequiredArgsConstructor
public class SessionApi {
    @GetMapping
    public SessionInfoResponse getSessionInfo(
        HttpServletRequest request
    ) {
        var session = request.getSession(false);
        return SessionInfoResponse.from(session);
    }
}
