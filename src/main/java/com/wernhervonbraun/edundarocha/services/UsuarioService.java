package com.wernhervonbraun.edundarocha.services;

import com.wernhervonbraun.edundarocha.dto.LoginDTO;
import com.wernhervonbraun.edundarocha.entities.Usuario;
import com.wernhervonbraun.edundarocha.repositories.UsuarioRepository;
import com.wernhervonbraun.edundarocha.security.services.JWTTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private static final String HEADER_PREFIX = "Bearer ";
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTTokenService jwtTokenService;
    @Autowired
    private AuthenticationManager authenticationManager;

    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(Integer id){
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> findByEmail(String email){
        return usuarioRepository.findByEmail(email);
    }
    public Usuario adicionar(Usuario usuario){
        usuario.setId(null);
        if(findByEmail(usuario.getEmail()).isPresent()){
            throw new InputMismatchException("Ja existe usuario cadastro com esse email");
        }
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }
    public LoginDTO doLogin(String email, String senha){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(email,senha);
        try{
            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = HEADER_PREFIX + jwtTokenService.generateToken(authentication);
            Usuario usuario = usuarioRepository.findByEmail(email).get();
            return new LoginDTO(token, usuario);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
