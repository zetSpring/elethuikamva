package elethu.ikamva.service;

import elethu.ikamva.dto.AuthenticatedResponse;
import elethu.ikamva.dto.LoginRequest;
import elethu.ikamva.dto.RegistrationResponseData;
import elethu.ikamva.dto.UserRegistrationRequestData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {
    RegistrationResponseData registerUser(UserRegistrationRequestData requestData);

    AuthenticatedResponse authenticateUser(LoginRequest loginRequest);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
