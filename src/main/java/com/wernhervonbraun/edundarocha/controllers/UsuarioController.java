package com.wernhervonbraun.edundarocha.controllers;

import com.wernhervonbraun.edundarocha.dto.EquipamentoResponse;
import com.wernhervonbraun.edundarocha.dto.LoginDTO;
import com.wernhervonbraun.edundarocha.dto.LoginRequest;
import com.wernhervonbraun.edundarocha.dto.LoginResponse;
import com.wernhervonbraun.edundarocha.entities.Usuario;
import com.wernhervonbraun.edundarocha.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("v1/wernherapi/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> findAll(){
        List<Usuario> usuarios = usuarioService.findAll();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Usuario>> findById(@PathVariable("id") Integer id){
        return new ResponseEntity<>(usuarioService.findById(id), HttpStatus.OK);
    }
    @Operation(summary = "Criar seu usuário")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "retorna usuário criado",
                            content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Usuario.class))})
            }
    )
    @PostMapping
    public ResponseEntity<Usuario> create(@Valid @RequestBody Usuario usuario){
        Usuario user = usuarioService.adicionar(usuario);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    @Operation(summary = "Autenticar")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "retorna seu token",
                            content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = LoginResponse.class))})
            }
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login (@RequestBody LoginRequest request){
        LoginDTO loginDTO = usuarioService.doLogin(request.getEmail(), request.getSenha());
        LoginResponse loginResponse = new ModelMapper().map(loginDTO, LoginResponse.class);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }
}
