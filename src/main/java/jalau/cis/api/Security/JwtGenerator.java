package jalau.cis.api.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtGenerator {
  public String generateToken(Authentication authentication) {
    String username = authentication.getName();
    Date emitDate = new Date(System.currentTimeMillis());
    Date expiraeDate = new Date(emitDate.getTime() + ConstantsToke.JWT_EXPIRATION_TIME);
    return generateToken(username,emitDate,expiraeDate);
  }


  private String generateToken(String username, Date emit, Date expirate) {
    try {
      return Jwts.builder()
        .header()
        .type("JWT")
        .and()
        .subject(username)
        .expiration(expirate)
        .issuedAt(emit)
        .signWith(getSecretKey(), Jwts.SIG.HS256)
        .compact();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return e.getMessage();
    }
  }

  private SecretKey getSecretKey() {
    String encodedKey = ConstantsToke.JWT_SECRET_KEY;
    return Keys.hmacShaKeyFor(encodedKey.getBytes());
  }

  public String getUsernameFromToken(String token) {
    Claims payload =
      Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token).getPayload();
    return payload.getSubject();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token).getPayload();
      return true;
    } catch (Exception e) {
      throw new AuthenticationCredentialsNotFoundException("Jwt token expired");
    }
  }
}
