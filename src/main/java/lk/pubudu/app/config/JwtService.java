package lk.pubudu.app.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    // This JwtService class is a service class which is responsible to manipulate JWT token.
    // Here username is the userEmail ID.

    // Secret key is used to verify the jwt signature, required minimum size of 256-bit.
    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970k";

    // This method is used to extract username from jwt token. (username is the user email id)
    public String extractUsername(String token) {
        // Here will extract the userEmail (subject) from the passed token.
        return extractClaim(token, Claims::getSubject);
    }

    // This method extract a single claim for the given jwt token.
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // This method is used to extract all claims from jwt token. (Claims are the statement about entities, typically they are the user details)
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // This method will return the sign in key based on secret key.
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // This method is used to generate token with user details only, without adding the extra claims.
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    // This method is used to generate token with user details and extra claims that we want to add to generate the token.
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))       // Set token expiration date to 1 day + 1000 milliseconds after created.
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)                         // set sign-in key and sign-in algorithm.
                .compact();
    }

    // This method is used to check validation of the token based on token username matching with spring security user, username and token expiration date.
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // This method is used to check whether token is expired or not.
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // This method is used to extract expiration date of the token.
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
