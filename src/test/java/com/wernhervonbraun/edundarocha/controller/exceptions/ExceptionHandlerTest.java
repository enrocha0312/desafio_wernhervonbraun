package com.wernhervonbraun.edundarocha.controller.exceptions;

import com.wernhervonbraun.edundarocha.exceptions.controller.ExceptionHandler;
import com.wernhervonbraun.edundarocha.exceptions.controller.StandardError;
import com.wernhervonbraun.edundarocha.exceptions.service.EquipamentoNaoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ExceptionHandlerTest {
    @InjectMocks
    private ExceptionHandler exceptionHandler;
    public static Integer ID = 1;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void retornandoResponseEntityParaEquipamentoInexistente(){
        ResponseEntity<StandardError> response = exceptionHandler
                .equipamentoNaoEncontrado(new EquipamentoNaoEncontradoException(ID),
                        new MockHttpServletRequest());
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals("Não existe equipamento com o ID passado", response.getBody().getError());
        assertEquals(response.getBody().getStatus(), 404);
    }
}
