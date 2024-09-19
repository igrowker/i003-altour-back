package com.igrowker.altour.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Date;

public class JWTAuthFilter extends OncePerRequestFilter {

    private final SecretKey secretKey;
    public JWTAuthFilter(SecretKey secretKey){
        this.secretKey = secretKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String token = extractJwtFromRequest(request);
            //System.out.println(token);
            if (token != null && validateToken(token)){
                String username = extractUsernameFromToken(token);

                if (username != null){
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, null);
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        catch (Exception e){
            SecurityContextHolder.clearContext();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            return;
        }
        filterChain.doFilter(request, response);
    }


    public String extractJwtFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token){
        try{
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            System.out.println(isTokenNotExpired(claimsJws.getBody().getExpiration()));
            if (isTokenSignatureValid(claimsJws) && isTokenNotExpired(claimsJws.getBody().getExpiration())){
                return true;
            }
        }catch (Exception e){
            System.out.println("ERROR: " + e.getMessage());
        }
        return false;
    }

    private boolean isTokenSignatureValid (Jws<Claims> claimsJws){
        try {
            claimsJws.getBody();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    private boolean isTokenNotExpired (Date expirationDate){
        return expirationDate != null && !expirationDate.before(new Date());
    }

    private String extractUsernameFromToken (String token){
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
            String username = claims.getSubject();
            return username;
        }catch (Exception e){
            return null;
        }
    }
}
