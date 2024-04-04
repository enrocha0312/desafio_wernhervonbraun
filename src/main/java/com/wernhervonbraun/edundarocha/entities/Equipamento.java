package com.wernhervonbraun.edundarocha.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Equipamento{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String fabricante;
    private Double densidade;
    private String comando;
}
