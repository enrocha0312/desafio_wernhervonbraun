package com.wernhervonbraun.edundarocha.services;

import com.wernhervonbraun.edundarocha.repositories.EquipamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EquipamentoService {
    @Autowired
    private EquipamentoRepository equipamentoRepository;
}
