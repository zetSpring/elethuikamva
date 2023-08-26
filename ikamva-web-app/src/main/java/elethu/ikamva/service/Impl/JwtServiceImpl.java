package elethu.ikamva.service.Impl;

import elethu.ikamva.repositories.TokenRepository;
import elethu.ikamva.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    @Value("${application.security.jwt.secret}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long tokenExpirationTime;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshTokenExpirationTime;

    private final TokenRepository tokenRepository;

    @Override
    public String extractUsername(String token) {
        return this.extractClaim(token, Claims::getSubject);
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return this.generateToken(new HashMap<>(), userDetails);
    }

    @Override
    public String generateToken(Map<String, Object> extractClaims, UserDetails userDetails) {
        return this.createToken(extractClaims, userDetails, tokenExpirationTime);
    }

    @Override
    public String generateRefreshToken(UserDetails userDetails) {
        return this.createToken(new HashMap<>(), userDetails, refreshTokenExpirationTime);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = this.extractUsername(token);
        return username.equals(userDetails.getUsername()) && !this.isTokenExpired(token);
    }

    @Override
    public boolean isValidDbStatus(String jwtToken) {
        return tokenRepository
                .findByToken(jwtToken)
                .map(token -> !token.isExpired() && !token.isRevoked())
                .orElse(false);
    }

    private boolean isTokenExpired(String token) {
        return this.extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return this.extractClaim(token, Claims::getExpiration);
    }

    private String createToken(Map<String, Object> claims, UserDetails userDetails, long expirationTime) {
        log.info("Creating token for user: {} expirationTime: {}", userDetails.getUsername(), expirationTime);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
