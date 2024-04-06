package com.wernhervonbraun.edundarocha.repositories;

import com.wernhervonbraun.edundarocha.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {
    Optional<Usuario> findByEmail(String email);
}
