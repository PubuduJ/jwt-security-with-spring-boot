package lk.pubudu.app.config;

import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    // This JwtService class is a service class which is responsible to manipulate JWT token.

    // Secret key which is used to verify the jwt signature.
    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970k";

    // This method is used to extract username from jwt token. (username is the user email id)
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
}
