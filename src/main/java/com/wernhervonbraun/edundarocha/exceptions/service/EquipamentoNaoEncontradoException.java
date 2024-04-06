package com.wernhervonbraun.edundarocha.exceptions.service;

public class EquipamentoNaoEncontradoException extends RuntimeException {
    public EquipamentoNaoEncontradoException(Integer id){
        super("Não existe equipamento com esse id " + id);
    }
}
