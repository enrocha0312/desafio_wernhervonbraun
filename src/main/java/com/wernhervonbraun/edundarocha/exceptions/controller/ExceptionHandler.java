package com.wernhervonbraun.edundarocha.exceptions.controller;

import com.wernhervonbraun.edundarocha.exceptions.service.EquipamentoNaoEncontradoException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.time.Instant;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(EquipamentoNaoEncontradoException.class)
    public ResponseEntity<StandardError> equipamentoNaoEncontrado(EquipamentoNaoEncontradoException e,
                                                          HttpServletRequest request){
        String error = "Não existe equipamento com o ID passado";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = StandardError.builder()
                .timestamp(Instant.now())
                .error(error)
                .message(e.getMessage())
                .status(status.value())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(status).body(err);
    }
}
