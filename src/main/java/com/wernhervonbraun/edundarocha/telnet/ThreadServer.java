package com.wernhervonbraun.edundarocha.telnet;

import com.wernhervonbraun.edundarocha.entities.Equipamento;
import lombok.AllArgsConstructor;

import java.net.ServerSocket;
import java.net.Socket;
@AllArgsConstructor
public class ThreadServer implements Runnable{
    private Equipamento equipamento;
    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(equipamento.getPorta());
            System.out.println("Equipamento conectado como servidor. ID: " + equipamento.getId());
            Socket cliente = serverSocket.accept();
            System.out.println("Aplicação conectada");
            ThreadCliente threadCliente = new ThreadCliente(equipamento, cliente);
            threadCliente.start();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
