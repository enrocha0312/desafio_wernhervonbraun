package com.wernhervonbraun.edundarocha.dto;

import com.wernhervonbraun.edundarocha.entities.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String token;
    private Usuario usuario;
}
