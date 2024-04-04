package com.wernhervonbraun.edundarocha.controllers;

import com.wernhervonbraun.edundarocha.dto.EquipamentoRequest;
import com.wernhervonbraun.edundarocha.dto.EquipamentoResponse;
import com.wernhervonbraun.edundarocha.dto.EquipamentoResponsePredictWeaterRainfall;
import com.wernhervonbraun.edundarocha.entities.Equipamento;
import com.wernhervonbraun.edundarocha.services.EquipamentoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/wernherapi")
public class EquipamentoController {
    @Autowired
    private EquipamentoService equipamentoService;
    @GetMapping("/equipamentospredictweaterparachuva")
    public ResponseEntity<List<EquipamentoResponsePredictWeaterRainfall>> encontrarTodosEquipamentosPredictWeaterComComandoRainfall(){
        return new ResponseEntity<>(equipamentoService
                .listarApenasEquipamentosPredictWeaterAndGetRainfallIntensity()
                .stream()
                .map(e -> new ModelMapper().map(e, EquipamentoResponsePredictWeaterRainfall.class))
                .collect(Collectors.toList()), HttpStatus.OK
        );
    }
    @PostMapping
    public ResponseEntity<EquipamentoResponse> cadastrarEquipamento(@RequestBody EquipamentoRequest equipamentoRequest){
        Equipamento equipamento = equipamentoService.cadastrarEquipamento(equipamentoRequest);
        EquipamentoResponse equipamentoResponse = new ModelMapper().map(equipamento, EquipamentoResponse.class);
        return new ResponseEntity<>(equipamentoResponse, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id){
        equipamentoService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
