package dio.spring.security.jwt.security;

import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class JWTCreator {
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String ROLES_AUTHORITIES = "authorities";

    public static String create(String prefix, String key, JWTObject jwtObject) {
        SecretKey signingKey = generateSecureKey(key);

        String token = Jwts.builder()
                .setSubject(jwtObject.getSubject())
                .setIssuedAt(jwtObject.getIssuedAt())
                .setExpiration(jwtObject.getExpiration())
                .claim(ROLES_AUTHORITIES, checkRoles(jwtObject.getRoles()))
                .signWith(signingKey, SignatureAlgorithm.HS512)
                .compact();
        return prefix + " " + token;
    }

    public static JWTObject create(String token, String prefix, String key)
        throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException {
        JWTObject object = new JWTObject();
        token = token.replace(prefix, "").trim();

        SecretKey verificationKey = generateSecureKey(key);

        Claims claims = Jwts.parser()
                .verifyWith(verificationKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        object.setSubject(claims.getSubject());
        object.setExpiration(claims.getExpiration());
        object.setIssuedAt(claims.getIssuedAt());
        object.setRoles((List<String>) claims.get(ROLES_AUTHORITIES));
        return object;
    }

    private static SecretKey generateSecureKey(String key) {
        if (key.length() < 64) {
            StringBuilder paddedKey = new StringBuilder();
            while (paddedKey.length() < 64) {
                paddedKey.append(key);
            }
            key = paddedKey.substring(0, 64);
        }

        return new SecretKeySpec(
                key.getBytes(StandardCharsets.UTF_8),
                SignatureAlgorithm.HS512.getJcaName()
        );
    }


    private static List<String> checkRoles(List<String> roles) {
        return roles.stream().map(s -> "".concat(s.replaceAll("", ""))).collect(Collectors.toList());
    }
}
