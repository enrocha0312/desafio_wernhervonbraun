package com.wernhervonbraun.edundarocha.controllers;

import com.wernhervonbraun.edundarocha.dto.EquipamentoRequest;
import com.wernhervonbraun.edundarocha.dto.EquipamentoResponse;
import com.wernhervonbraun.edundarocha.dto.EquipamentoResponsePredictWeaterRainfall;
import com.wernhervonbraun.edundarocha.dto.TelnetResponse;
import com.wernhervonbraun.edundarocha.entities.Equipamento;
import com.wernhervonbraun.edundarocha.services.EquipamentoService;
import com.wernhervonbraun.edundarocha.telnet.ThreadCliente;
import com.wernhervonbraun.edundarocha.telnet.ThreadServer;
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

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
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

    @Operation(summary = "Retorna os equipamentos Predict Weater e de comando get_rainfall_intensity com comando enviado e conexão telnet")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "retorna os equipamentos com medições e comando após conexão telnet",
                            content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TelnetResponse.class))}),
                    @ApiResponse(responseCode = "400", description = "passou comando diferente do get rainfall_intensity",
                            content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TelnetResponse.class))}),
                    @ApiResponse(responseCode = "400", description = "Insucesso de conexão Telnet",
                            content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TelnetResponse.class))}),
            }
    )

    @GetMapping("/telnetconnection")
    public ResponseEntity<List<TelnetResponse>> conectarTelnet(@RequestParam String comando) throws InterruptedException {
            List<Equipamento> equipamentos = equipamentoService.listarApenasEquipamentosPredictWeaterAndGetRainfallIntensity();
            List<TelnetResponse> telnetResponses = new ArrayList<>();
            ExecutorService executorService = Executors.newFixedThreadPool(equipamentos.size());
            if(comando.equals("get_rainfall_intensity")) {
                for (Equipamento e : equipamentos) {
                    try {
                        ThreadServer threadServer = new ThreadServer(e);
                        executorService.execute(threadServer);
                        Socket socket = new Socket(e.getHost(), e.getPorta());
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                        out.println("Endpoint funcionando");
                        out.close();
                        socket.close();
                        StringBuilder response = new StringBuilder("Comando realizado com sucesso: " + comando);
                        telnetResponses.add(TelnetResponse.builder()
                                .host(e.getHost())
                                .porta(e.getPorta())
                                .respostaComando(response.toString())
                                .densidade(e.getDensidade())
                                .id(e.getId())
                                .build());
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                        StringBuilder response = new StringBuilder("Insucesso para o comando: " + comando);
                        telnetResponses.add(TelnetResponse.builder()
                                .host(e.getHost())
                                .porta(e.getPorta())
                                .respostaComando(response.toString())
                                .densidade(e.getDensidade())
                                .id(e.getId())
                                .build());
                    }
                }
                executorService.shutdown();
                executorService.awaitTermination(10000, TimeUnit.SECONDS);
                if(telnetResponses.stream().
                        anyMatch(equip -> equip.getRespostaComando().equals("Insucesso para o comando: " + comando))){
                    return new ResponseEntity<>(telnetResponses, HttpStatus.BAD_REQUEST);
                }
                return new ResponseEntity<>(telnetResponses, HttpStatus.OK);
            }
            String response = "Comando diferente do esperado";
            equipamentos.forEach(e -> {
                telnetResponses.add(TelnetResponse.builder()
                        .host(e.getHost())
                        .porta(e.getPorta())
                        .respostaComando(response.toString())
                        .densidade(e.getDensidade())
                        .id(e.getId())
                        .build());
            });
            return new ResponseEntity<>(telnetResponses, HttpStatus.BAD_REQUEST);
    }
}
