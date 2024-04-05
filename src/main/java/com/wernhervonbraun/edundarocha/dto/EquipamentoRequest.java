package com.wernhervonbraun.edundarocha.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquipamentoRequest implements Serializable {
    private String comando;
    private String fabricante;
    private String nome;
    private String host;
    private Integer porta;
}
