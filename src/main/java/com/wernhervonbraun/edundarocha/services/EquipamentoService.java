package com.wernhervonbraun.edundarocha.services;

import com.wernhervonbraun.edundarocha.dto.EquipamentoRequest;
import com.wernhervonbraun.edundarocha.entities.Equipamento;
import com.wernhervonbraun.edundarocha.repositories.EquipamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipamentoService {
    @Autowired
    private EquipamentoRepository equipamentoRepository;
    public List<Equipamento> listarApenasEquipamentosPredictWeaterAndGetRainfallIntensity(){
        return  equipamentoRepository.findByFabricanteAndComando("PredictWeater", "get_rainfall_intensity");
    }
    public Equipamento cadastrarEquipamento(EquipamentoRequest equipamentoRequest){
        Equipamento equipamento = Equipamento.builder()
                .id(null)
                .densidade(null)
                .fabricante(equipamentoRequest.getFabricante())
                .comando(equipamentoRequest.getComando())
                .nome(equipamentoRequest.getNome())
                .host(equipamentoRequest.getHost())
                .porta(equipamentoRequest.getPorta())
                .build();
        return equipamentoRepository.save(equipamento);
    }

    public void deleteById(Integer id){
        equipamentoRepository.deleteById(id);
    }


}
