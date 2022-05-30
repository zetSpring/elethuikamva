package elethu.ikamva.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import elethu.ikamva.domain.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class JwtUtil {
    public static UsernamePasswordAuthenticationToken validateToken(String token) {
        var decodedJWT = decodeJWT(token);
        var username = decodedJWT.getSubject();
        String[] roles = decodedJWT.getClaim("roles").asArray(String.class);

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));

        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }

    public static String generateToken(User user, elethu.ikamva.domain.User ikamvaUser, String requestUrl, Date expireDate) {
        if (Objects.isNull(user) && Objects.isNull(ikamvaUser)) {
            throw new RuntimeException("Ikamva and Details users cannot be both null.");
        }

        String username;
        List<String> roles;
        if (Objects.nonNull(user)) {
            username = user.getUsername();
            roles = user.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
        } else {
            username = ikamvaUser.getUsername();
            roles = ikamvaUser.getRoles().stream()
                    .map(Role::getRoleDescription)
                    .collect(Collectors.toList());
        }

        return JWT.create()
                .withSubject(username)
                .withExpiresAt(expireDate)
                .withIssuer(requestUrl)
                .withClaim("roles", roles)
                .sign(returnAlgorithm());
    }

    public static DecodedJWT decodeJWT(String token) {
        var algorithm = returnAlgorithm();
        var verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

    public static void setTokenResponse(HttpServletResponse response, String access_token, String refresh_token) throws IOException {
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);

        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

    public static void setAuthorizationError(HttpServletResponse response, Exception e) throws IOException {
        response.setHeader("error", e.getMessage());
        response.setStatus(FORBIDDEN.value());

        Map<String, String> error = new HashMap<>();
        error.put("error_message", e.getMessage());
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }

    private static Algorithm returnAlgorithm() {
        return Algorithm.HMAC256("secret".getBytes());
    }

    public static String retrieveToken(String authorizationHeader) {
        return authorizationHeader.substring("Bearer ".length());
    }
}
