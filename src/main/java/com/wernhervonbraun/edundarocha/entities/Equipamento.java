package com.wernhervonbraun.edundarocha.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "equipamentos")
public class Equipamento{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String fabricante;
    private Double densidade;
    private String comando;
    private Integer porta;
    private String host;
}
