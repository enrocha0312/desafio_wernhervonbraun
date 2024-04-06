package com.wernhervonbraun.edundarocha.security.services;

import com.wernhervonbraun.edundarocha.entities.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class JWTTokenService {

    private static final String SECRET = "chavewernhervonbraun";
    private final int EXPIRATION = 7200000;//2 horas
    public String generateToken(Authentication authentication){
        Usuario usuario = (Usuario) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(usuario.getId().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public Optional<Integer> obtainIdFromUsuario(String token){
        try{
            Claims claims = extractClaimsFromToken(token).getBody();
            return Optional.ofNullable(Integer.parseInt(claims.getSubject()));
        }catch (Exception e){
            return Optional.empty();
        }
    }
    //metodo que extrai do token as permissoes do usuario
    private Jws<Claims> extractClaimsFromToken (String token){
        return Jwts
                .parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token);
    }
}
