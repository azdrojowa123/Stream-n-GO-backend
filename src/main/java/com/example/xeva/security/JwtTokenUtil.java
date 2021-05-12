package com.example.xeva.security;

import com.example.xeva.constants.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.*;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;

import javax.crypto.SecretKey;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;

    public JwtTokenUtil(){}

    public String getUsernameFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        String claimName = "username";
        return (String) claims.get(claimName);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SecurityConstants.JWT_KEY).parseClaimsJws(token).getBody();
    }

    public String generateToken(UserDetails userDetails){

        Map<String, Object> claims = new HashMap<>();
        claims.put("username", userDetails.getUsername()); // tuttaj do claims zostanie dodany email
        claims.put("authority", userDetails.getAuthorities());
        String jwt = Jwts.builder().setClaims(claims).
                setSubject("xeva app").setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date((new Date()).getTime() + (1000 * 60 * 60 * 3)))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.JWT_KEY).compact();

        return jwt;
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
