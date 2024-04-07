package com.wernhervonbraun.edundarocha.controllers;

import com.wernhervonbraun.edundarocha.dto.EquipamentoRequest;
import com.wernhervonbraun.edundarocha.dto.EquipamentoResponse;
import com.wernhervonbraun.edundarocha.dto.EquipamentoResponsePredictWeaterRainfall;
import com.wernhervonbraun.edundarocha.entities.Equipamento;
import com.wernhervonbraun.edundarocha.services.EquipamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/wernherapi/equipamentos")
public class EquipamentoController {
    @Autowired
    private EquipamentoService equipamentoService;
    @Operation(summary = "Retorna os equipamentos Predict Weater e de comando get_rainfall_intensity")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "retorna os equipamentos especificados no título",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = EquipamentoResponse.class))})
            }
    )
    @GetMapping("/equipamentospredictweaterparachuva")
    public ResponseEntity<List<EquipamentoResponsePredictWeaterRainfall>> encontrarTodosEquipamentosPredictWeaterComComandoRainfall(){
        return new ResponseEntity<>(equipamentoService
                .listarApenasEquipamentosPredictWeaterAndGetRainfallIntensity()
                .stream()
                .map(e -> new ModelMapper().map(e, EquipamentoResponsePredictWeaterRainfall.class))
                .collect(Collectors.toList()), HttpStatus.OK
        );
    }
    @Operation(summary = "Cadastra equipamento novo")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "retorna created e a entidade criada",
                            content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EquipamentoResponse.class))})
            }
    )
    @PostMapping
    public ResponseEntity<EquipamentoResponse> cadastrarEquipamento(@RequestBody EquipamentoRequest equipamentoRequest){
        Equipamento equipamento = equipamentoService.cadastrarEquipamento(equipamentoRequest);
        EquipamentoResponse equipamentoResponse = new ModelMapper().map(equipamento, EquipamentoResponse.class);
        return new ResponseEntity<>(equipamentoResponse, HttpStatus.CREATED);
    }
    @Operation(summary = "Deleta um equipamento por ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Se tiver sucesso, no content é retornado"),
                    @ApiResponse(responseCode = "404", description = "Se não exister a entidade com o id passado, traz 404 com mensagem de erro")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id){
        equipamentoService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
