package com.wernhervonbraun.edundarocha.dto;

import com.wernhervonbraun.edundarocha.entities.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginDTO implements Serializable{
    private String token;
    private Usuario usuario;
}

