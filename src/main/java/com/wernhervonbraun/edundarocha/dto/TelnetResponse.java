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
public class TelnetResponse implements Serializable {
    private Integer id;
    private Double densidade;
    private String host;
    private Integer porta;
    private String respostaComando;
}
