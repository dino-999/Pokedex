package hr.algebra.pokedex.service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtGeneratorServiceImpl implements JwtGeneratorService {
    private final SecretKey SECRET;

    public JwtGeneratorServiceImpl() {
        this.SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    @Override
    public String extractUsername(final String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public Date extractExpiration(final String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    @Override
    public String generateToken(final String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    @Override
    public Boolean validateToken(final String token, final UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private <T> T extractClaim(final String token, final Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(final String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(SECRET)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 864000000))
                .signWith(SECRET, SignatureAlgorithm.HS256)
                .compact();
    }


    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}