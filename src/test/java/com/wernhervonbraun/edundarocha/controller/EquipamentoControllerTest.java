package com.wernhervonbraun.edundarocha.controller;

import com.wernhervonbraun.edundarocha.controllers.EquipamentoController;
import com.wernhervonbraun.edundarocha.dto.EquipamentoRequest;
import com.wernhervonbraun.edundarocha.dto.EquipamentoResponse;
import com.wernhervonbraun.edundarocha.dto.EquipamentoResponsePredictWeaterRainfall;
import com.wernhervonbraun.edundarocha.entities.Equipamento;
import com.wernhervonbraun.edundarocha.services.EquipamentoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class EquipamentoControllerTest {
    @InjectMocks
    private EquipamentoController equipamentoController;
    @Mock
    private EquipamentoService equipamentoService;
    @Mock
    private ModelMapper modelMapper;
    private EquipamentoRequest equipamentoRequest;
    private Equipamento equipamento;
    private EquipamentoResponsePredictWeaterRainfall equipamentoResponsePredictWeaterRainfall;
    private EquipamentoResponse equipamentoResponse;
    private List<Equipamento> equipamentos;
    private static Integer ID = 1;
    private static String NOME = "Nome";
    private static String FABRICANTE = "PredictWeater";
    private static String COMANDO = "get_rainfall_intensity";
    private static String HOST = "localhost";
    private static Integer PORTA = 8088;
    private static Double DENSIDADE = 15.6;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        equipamento = Equipamento.builder()
                .id(ID)
                .comando(COMANDO)
                .densidade(DENSIDADE)
                .host(HOST)
                .porta(PORTA)
                .nome(NOME)
                .fabricante(FABRICANTE)
                .build();
        equipamentoRequest = EquipamentoRequest.builder()
                .nome(NOME)
                .porta(PORTA)
                .host(HOST)
                .fabricante(FABRICANTE)
                .comando(COMANDO)
                .build();
        equipamentoResponse = EquipamentoResponse.builder()
                .nome(NOME)
                .id(ID)
                .fabricante(FABRICANTE)
                .comando(COMANDO)
                .host(HOST)
                .porta(PORTA)
                .build();
        equipamentoResponsePredictWeaterRainfall = EquipamentoResponsePredictWeaterRainfall.builder()
                .nome(NOME)
                .densidade(DENSIDADE)
                .host(HOST)
                .porta(PORTA)
                .id(ID)
                .build();
        equipamentos = Stream.of(equipamento)
                       .collect(Collectors.toList());
    }
    @Test
    void retornaListaDeEquipsPredictWeaterAndGetRainfall(){
        when(equipamentoService.listarApenasEquipamentosPredictWeaterAndGetRainfallIntensity())
                .thenReturn(equipamentos);
        when(modelMapper.map(any(), any())).thenReturn(equipamentoResponsePredictWeaterRainfall);
        var response = equipamentoController
                .encontrarTodosEquipamentosPredictWeaterComComandoRainfall();
        assertNotNull(response.getBody());
        assertEquals(response.getBody().size(), 1);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertTrue(response.getBody().contains(equipamentoResponsePredictWeaterRainfall));
    }
    @Test
    void cadastroDeEquipamento(){
        when(equipamentoService.cadastrarEquipamento(any())).thenReturn(equipamento);
        when(modelMapper.map(any(), any())).thenReturn(equipamentoResponse);
        var response = equipamentoController.cadastrarEquipamento(equipamentoRequest);
        assertNotNull(response.getBody());
        assertEquals(response.getBody().getClass(), EquipamentoResponse.class);
        assertEquals(response.getBody().getId(), ID);
        assertEquals(response.getBody().getNome(), equipamentoRequest.getNome());
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }
    @Test
    void apagarEquipamentoComSucesso(){
        doNothing().when(equipamentoService).deleteById(anyInt());
        var response = equipamentoController.deleteById(ID);
        assertNotNull(response);
        assertEquals(response.getClass(), ResponseEntity.class);
        verify(equipamentoService, times(1)).deleteById(anyInt());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
