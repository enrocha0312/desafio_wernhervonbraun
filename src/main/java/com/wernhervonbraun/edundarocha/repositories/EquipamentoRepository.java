package com.wernhervonbraun.edundarocha.repositories;

import com.wernhervonbraun.edundarocha.entities.Equipamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EquipamentoRepository extends JpaRepository<Equipamento, Integer> {
    @Query(value="SELECT * FROM equipamentos e where e.fabricante = :fabricante and e.comando = :comando", nativeQuery = true)
    List<Equipamento> findByFabricanteAndComando(@Param("fabricante") String fabricante, @Param("comando") String comando);
}
