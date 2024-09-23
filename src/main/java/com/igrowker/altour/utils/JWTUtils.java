package com.igrowker.altour.utils;

import com.igrowker.altour.exceptions.ForbiddenException;
import com.igrowker.altour.persistence.entity.CustomUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JWTUtils {
    @Autowired
    private Environment environment;

    private static final long EXPIRATION_TIME = 86400000;//24hs

    private java.security.Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String getSecretKey() {
        return environment.getProperty("secret.key");
    }

    public String generateToken(UserDetails user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getKey())
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        try{
            return claimsTFunction.apply(Jwts.parser().verifyWith((SecretKey) getKey()).build().parseSignedClaims(token).getPayload());
        } catch (Exception e){
            // TODO VERIFICAR COMO MANEJAR EXEPCION, PORQUE NO ESTA FUNCIONANDO COMO ENTIENDO QUE DEBERIA.. CREERIA QUE CUANDO SALTA LA EXC EN UN FILTRO NO TENDRA EL MISMO TRATAMIENTO? AVERIGUAR!
            throw new ForbiddenException("INVALID JWT: "+e.getMessage());
        }
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }
}
