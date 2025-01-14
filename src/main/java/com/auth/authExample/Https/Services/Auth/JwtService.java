package com.auth.authExample.Https.Services.Auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    // Секретный ключ
    private static final String SECRET_KEY = "68c703879c95eba8d3b0bc2a79f71b03e5ceb59ca02b209391b91e4e5debff38bd1867b3392f87c97f666d4a3f8b6446108550f749dc18710afaf99fbb561a723ab5124919ccd8598ec4485c91f00a9c217121d6fd703cad9b90022a9ca79c744cdedf6f7a914f27f25e48079824e5482edeaa55cce1ab7d44e514be29a6552608e36e76c5a2293caecace0950ef6852bc13b07f6bf250a5063772c7cb8455a62605cb785ddccdeaf9aae89dbaa7028f847d672af9d920f4b22506ea2e1e2fbe48f130474d72086fce75a4a43e4700aee4e59cdf5eb6abed6e618e0b6864b0f8c4ce5d6f0fbd3f4d84cdccafefe72ea7731a4f6461777f0eb723ba50034fc35e";

    // Получить определенную информацию с токена.
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // Получить всю информацию с токена.
    private Claims getClaimsFromToken(String token) {
        return Jwts
                .parser() // парсим jwt
                .verifyWith(getSignKey()) // проверяем подписи
                .build() // создаем jwt
                .parseSignedClaims(token) //парсим подписанные утверждений (claims) в JWT (JSON Web Token).
                .getPayload(); // извлекаем данные
    }

    // Сгенерировать токен
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .claims(extraClaims) // данные которые нужно отправить с токеном
                .subject(userDetails.getUsername()) // установить id того, кто отправляет токен
                .issuedAt(new Date(System.currentTimeMillis())) // время создания токена
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) // время истечения времени токена
                .signWith(getSignKey()) // установить цифровую подпись (чтоб не докапывались)
                .compact(); // красиво все это собираем
    }

    // Получить цифровую подпись
    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsernameFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Проверяем токен
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return !isTokenExpired(token) &&
                username != null &&
                username.equals(userDetails.getUsername());
    }

    private boolean isTokenExpired(String token) {
        return getExpirationTimeOfToken(token)
                .before(new Date(System.currentTimeMillis()));
    }

    private Date getExpirationTimeOfToken(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
