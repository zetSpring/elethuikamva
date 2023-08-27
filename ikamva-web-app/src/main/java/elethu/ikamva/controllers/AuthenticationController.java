package elethu.ikamva.controllers;

import elethu.ikamva.dao.AuthenticatedResponse;
import elethu.ikamva.dao.LoginRequest;
import elethu.ikamva.dao.RegistrationResponseData;
import elethu.ikamva.dao.UserRegistrationRequestData;
import elethu.ikamva.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponseData> registerUser(@RequestBody UserRegistrationRequestData requestData) {
        return ResponseEntity.ok(authenticationService.registerUser(requestData));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticatedResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authenticationService.authenticateUser(loginRequest));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authenticationService.refreshToken(request, response);
    }
}
