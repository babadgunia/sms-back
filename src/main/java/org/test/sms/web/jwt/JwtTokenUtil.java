package org.test.sms.web.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {

    private static final String CLAIM_KEY_USERNAME = "sub";

    private static final String CLAIM_KEY_AUDIENCE = "audience";

    private static final String CLAIM_KEY_CREATED = "created";

    private static final String AUDIENCE_WEB = "web";

    private Environment environment;

    private String secret;

    @Autowired
    public JwtTokenUtil(Environment environment) {
        this.environment = environment;
        this.secret = environment.getRequiredProperty("jwt.secret");
    }

    public String getUsernameFromToken(String token) {
        String username;
        try {
            username = getClaimsFromToken(token).getSubject();
        } catch (Exception e) {
            username = null;
        }

        return username;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }

        return claims;
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_AUDIENCE, AUDIENCE_WEB);
        claims.put(CLAIM_KEY_CREATED, new Date());
        claims.put("PERMISSIONS", userDetails.getAuthorities());

        return generateToken(claims);
    }

    private String generateToken(Map<String, Object> claims) {
        Date expirationDate = new Date(System.currentTimeMillis() + environment.getRequiredProperty("jwt.expiration", Long.class) * 1000);

        return Jwts.builder().setClaims(claims).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public Boolean isTokenExpired(String token) {
        Claims claims = getClaimsFromToken(token);
        Date expiration = claims.getExpiration();

        return expiration.before(new Date());
    }

    public String refreshToken(String token) {
        String refreshedToken;
        try {
            Claims claims = getClaimsFromToken(token);
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }

        return refreshedToken;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        Claims claims = getClaimsFromToken(token);
        Date created = new Date((Long) claims.get(CLAIM_KEY_CREATED));

        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
}