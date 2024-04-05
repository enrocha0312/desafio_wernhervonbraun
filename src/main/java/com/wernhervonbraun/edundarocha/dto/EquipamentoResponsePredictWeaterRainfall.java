package com.wernhervonbraun.edundarocha.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EquipamentoResponsePredictWeaterRainfall implements Serializable {
    private Integer id;
    private String nome;
    private Double densidade;
    private String host;
    private Integer porta;
}
