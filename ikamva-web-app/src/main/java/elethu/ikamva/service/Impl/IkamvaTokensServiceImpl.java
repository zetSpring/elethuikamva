package elethu.ikamva.service.Impl;

import elethu.ikamva.security.JwtUtil;
import elethu.ikamva.service.IkamvaTokensService;
import elethu.ikamva.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Service
@RequiredArgsConstructor
public class IkamvaTokensServiceImpl implements IkamvaTokensService {
    private final UserService userService;

    @Override
    public void getAccessToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var authorizationHeader = request.getHeader(AUTHORIZATION);

        if (Objects.isNull(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Authorization header is missing");
        }

        try {
            String refresh_token = JwtUtil.retrieveToken(authorizationHeader);
            var decodedJWT = JwtUtil.decodeJWT(refresh_token);
            var username = decodedJWT.getSubject();
            var user = userService.findUserByUsername(username);

            var jwtExpireDate = new Date(System.currentTimeMillis() + 10 * 60 * 1000);
            var access_token = JwtUtil.generateToken(null, user, request.getRequestURL().toString(), jwtExpireDate);

            log.info("JWT Expire Date: {}", jwtExpireDate);
            //log.info("JWT Refresh Expire Date: {}", jwtRefreshExpireDate);

            JwtUtil.setTokenResponse(response, access_token, refresh_token);
        } catch (Exception e) {
            JwtUtil.setAuthorizationError(response, e);
        }
    }
}
