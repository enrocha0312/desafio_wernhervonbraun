package com.wernhervonbraun.edundarocha.controllers;

import com.wernhervonbraun.edundarocha.services.EquipamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EquipamentoController {
    @Autowired
    private EquipamentoService equipamentoService;
}
