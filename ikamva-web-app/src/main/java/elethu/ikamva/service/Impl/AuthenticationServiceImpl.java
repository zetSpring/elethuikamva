package elethu.ikamva.service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import elethu.ikamva.commons.DateFormatter;
import elethu.ikamva.dto.AuthenticatedResponse;
import elethu.ikamva.dto.LoginRequest;
import elethu.ikamva.dto.RegistrationResponseData;
import elethu.ikamva.dto.UserRegistrationRequestData;
import elethu.ikamva.domain.Member;
import elethu.ikamva.domain.Token;
import elethu.ikamva.domain.User;
import elethu.ikamva.domain.enums.TokenType;
import elethu.ikamva.repositories.MemberRepository;
import elethu.ikamva.repositories.TokenRepository;
import elethu.ikamva.repositories.UserRepository;
import elethu.ikamva.service.AuthenticationService;
import elethu.ikamva.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static elethu.ikamva.utils.Validator.checkForNulls;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final MemberRepository memberRepository;
    private final AuthenticationManager authenticationManager;

    @Override
    public RegistrationResponseData registerUser(UserRegistrationRequestData requestData) {
        if (Objects.isNull(requestData)) {
            log.error("UserRegistrationRequestData is invalid");
            throw new IllegalArgumentException("UserRegistrationRequestData is invalid");
        }

        if (!checkForNulls(requestData)) {
            log.error("UserRegistrationRequestData is invalid");
            throw new IllegalArgumentException("UserRegistrationRequestData is invalid");
        }

        Member member = memberRepository
                .findMemberByInvestmentId(requestData.investmentId())
                .orElseThrow(() -> {
                    log.error("Member with investment id {} not found", requestData.investmentId());
                    return new IllegalArgumentException(
                            "Member with investment id " + requestData.investmentId() + " not found.");
                });

        User user = User.builder()
                .investmentId(requestData.investmentId())
                .password(passwordEncoder.encode(requestData.password()))
                .role(requestData.role())
                .createdDate(DateFormatter.returnLocalDateTime())
                .userMember(member)
                .build();

        userRepository.save(user);

        return RegistrationResponseData.builder()
                .success(true)
                .message("User successfully created.")
                .username(requestData.investmentId())
                .build();
    }

    @Override
    public AuthenticatedResponse authenticateUser(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        User user =
                userRepository.findByInvestmentId(loginRequest.getUsername()).orElseThrow();

        String token = jwtService.generateToken(user);
        String accessToken = jwtService.generateRefreshToken(user);

        this.revokeUserTokens(user);
        this.saveUserToken(user, token);

        return AuthenticatedResponse.builder()
                .accessToken(token)
                .refreshToken(accessToken)
                .build();
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;

        if (Objects.isNull(authHeader) || !authHeader.startsWith("Bearer ")) {
            log.error("Invalid refresh token");
            return;
        }

        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);

        if (Objects.nonNull(username)) {
            User user = userRepository.findByInvestmentId(username).orElseThrow();

            if (jwtService.isTokenValid(refreshToken, user)) {
                String accessToken = jwtService.generateToken(user);
                this.revokeUserTokens(user);
                this.saveUserToken(user, accessToken);
                AuthenticatedResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), accessToken);
            }
        }
    }

    private void saveUserToken(User user, String jwtToken) {
        if (ObjectUtils.anyNull(user, jwtToken)) {
            log.error("Invalid arguments  for saving user token");
            throw new IllegalArgumentException("Invalid arguments for saving user token");
        }

        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepository.save(token);
    }

    private void revokeUserTokens(User user) {
        if (Objects.isNull(user)) {
            log.info("Invalid arguments for revoking user tokens");
            return;
        }

        List<Token> validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if (validUserTokens.isEmpty()) {
            log.info("No valid tokens found for the user.");
            return;
        }

        validUserTokens.forEach(token -> {
            token.setRevoked(true);
            token.setExpired(true);
        });

        tokenRepository.saveAll(validUserTokens);
    }
}
