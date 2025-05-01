package app.demo.security;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

public class Constants {
    public static final long JWT_EXPIRATION = 604800000L; // 7 days
    private static final String secret = "mysecretkeymysecretkeymysecretkeymysecretkeymysecretkeymysecretmysecretkey";
    public static final SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
}