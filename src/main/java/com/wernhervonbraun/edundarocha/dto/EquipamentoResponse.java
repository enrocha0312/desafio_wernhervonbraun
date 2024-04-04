package com.wernhervonbraun.edundarocha.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EquipamentoResponse implements Serializable {
    private Integer id;
    private String nome;
    private String fabricante;
    private String comando;
}
