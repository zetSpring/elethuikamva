package elethu.ikamva.service;

import elethu.ikamva.dao.AuthenticatedResponse;
import elethu.ikamva.dao.LoginRequest;
import elethu.ikamva.dao.RegistrationResponseData;
import elethu.ikamva.dao.UserRegistrationRequestData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {
    RegistrationResponseData registerUser(UserRegistrationRequestData requestData);

    AuthenticatedResponse authenticateUser(LoginRequest loginRequest);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
