package com.wernhervonbraun.edundarocha.service;

import com.wernhervonbraun.edundarocha.dto.EquipamentoRequest;
import com.wernhervonbraun.edundarocha.entities.Equipamento;
import com.wernhervonbraun.edundarocha.exceptions.service.EquipamentoNaoEncontradoException;
import com.wernhervonbraun.edundarocha.repositories.EquipamentoRepository;
import com.wernhervonbraun.edundarocha.services.EquipamentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
@SpringBootTest
public class EquipamentoServiceTest {
    @InjectMocks
    private EquipamentoService equipamentoService;
    @Mock
    private EquipamentoRepository equipamentoRepository;
    private EquipamentoRequest equipamentoRequest;
    private Equipamento equipamento;
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
        equipamentos = Stream.of(equipamento,
                Equipamento.builder()
                        .id(2)
                        .comando("qualquer comando")
                        .densidade(12.03)
                        .host(HOST)
                        .porta(PORTA)
                        .nome(NOME)
                        .fabricante("outro")
                        .build()).collect(Collectors.toList());
    }

    @Test
    void retornarEquipamentosPredictWeaterComGetRainfallIntensity(){
        when(equipamentoRepository
                .findByFabricanteAndComando(anyString(), anyString()))
                .thenReturn(equipamentos
                        .stream()
                        .filter(e -> e.getFabricante().equals(FABRICANTE)
                                && e.getComando().equals(COMANDO) )
                        .collect(Collectors.toList()));
        List<Equipamento> response = equipamentoService.listarApenasEquipamentosPredictWeaterAndGetRainfallIntensity();
        assertNotNull(response);
        assertEquals(response.size(), 1);
        assertTrue(response.contains(equipamento));
    }

    @Test
    void cadastrarEquipamentoComSucesso(){
        when(equipamentoRepository.save(any())).thenReturn(equipamento);
        var response = equipamentoService.cadastrarEquipamento(equipamentoRequest);
        assertNotNull(response);
        assertEquals(response.getNome(), NOME);
        assertEquals(response.getFabricante(), FABRICANTE);
        assertEquals(response.getId(), ID);
    }

    @Test
    void apagarEquipamentoComSucesso(){
        when(equipamentoRepository.findById(anyInt())).thenReturn(Optional.of(equipamento));
        doNothing().when(equipamentoRepository).deleteById(anyInt());
        equipamentoService.deleteById(ID);
        verify(equipamentoRepository, times(1)).deleteById(anyInt());
    }

    @Test
    void apagarEquipamentoInexistente(){
        when(equipamentoRepository.findById(anyInt())).thenThrow(new EquipamentoNaoEncontradoException(ID));
        try{
            equipamentoService.deleteById(ID);
        }catch (Exception e){
            assertEquals(EquipamentoNaoEncontradoException.class, e.getClass());
            assertEquals("Não existe equipamento com esse id " + ID, e.getMessage());
        }
    }

}
