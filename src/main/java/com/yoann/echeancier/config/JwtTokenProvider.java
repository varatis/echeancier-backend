package com.yoann.echeancier.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.SignatureException;
import java.util.Date;

@Component
public class JwtTokenProvider {
    // Clé de test sécurisée
    private final String jwtSecret = "votre_clé_secrète_très_longue_et_sécurisée_pour_la_signature_jwt_hs512_2024_08_17_plus_longue";

    // Convertit la clé en Base64
    private final Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(Encoders.BASE64.encode(jwtSecret.getBytes())));

    private final long jwtExpirationInMs = 604800000L; // 7 jours

    // Génère le token JWT
    public String generateToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // Récupère l'email de l'utilisateur à partir du token
    public String getUserEmailFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    // Valide le token JWT
    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            // Token JWT malformé
        } catch (ExpiredJwtException ex) {
            // Token JWT expiré
        } catch (UnsupportedJwtException ex) {
            // Token JWT non supporté
        } catch (IllegalArgumentException ex) {
            // Chaîne de revendications JWT vide
        }
        return false;
    }
}
