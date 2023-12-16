package com.miguelonxo.LigaMiguelonxo.security;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;



import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;



@Component
public class JwtTokenUtil {

    private static final long EXPIRATION_TIME = 864_000_000; // 10 days

    private static final String SECRET_KEY = "unaClaveSecretaMuyLargaQueNadiePuedeAdivinar";

    /* public String generateAccessToken(User user){
        List<String> roleNames = user.getRoles().stream()
            .map(UserRol::getRol)
            .map(Rol::getNombre)
            .collect(Collectors.toList());
        System.out.println("roleNames: " + roleNames);
        return Jwts.builder()
            .setSubject("%s,%s".formatted(user.getId(), user.getEmail()))
            .setIssuer("CodeJava")
            .claim("roles", roleNames)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
            .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
            .compact();
    } */

    /* public boolean validateAccessToken(String token){
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            LOGGER.error("Token has expired", ex.getMessage());
        }catch (IllegalArgumentException ex){
            LOGGER.error("Token is null, empty or only whitespace", ex.getMessage());
        }catch (MalformedJwtException ex){
            LOGGER.error("Token is invalid", ex.getMessage());
        }catch (UnsupportedJwtException ex) {
           LOGGER.error("JWT is not supported", ex);
        }catch (SignatureException ex) {
            LOGGER.error("Signature validation failed");
        }
        return false;
    }
    
    public String getSubject(String token){
        return parseClaims(token).getSubject();
    }

    public Claims parseClaims(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY)
            .parseClaimsJws(token).getBody();
    } */

    // Generar token JWT firmado con clave privada
    public String generateToken(UserDetails userDetails){
        //recuperar los roles del usuario
        List<String> roles = userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());


        return Jwts.builder()
            //.setClaims(claims)
            .claim("roles", roles)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
            .compact();
    }

    // Verificar token JWT con clave pública
    public boolean validateToken(String token) {


        //token recibido
        System.out.println("Token recibido: " + token);

        try {
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            System.out.println("Error al validar el token: " + e.getMessage());
            return false;
        }
    }

    public String getSecretKey() {
        return SECRET_KEY;
    }
    
}
