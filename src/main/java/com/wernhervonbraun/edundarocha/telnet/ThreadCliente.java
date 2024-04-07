package com.wernhervonbraun.edundarocha.telnet;

import com.wernhervonbraun.edundarocha.entities.Equipamento;
import lombok.AllArgsConstructor;

import java.io.PrintWriter;
import java.net.Socket;
@AllArgsConstructor
public class ThreadCliente extends Thread{
    private Equipamento equipamento;
    private Socket socket=null;
    public void run()
    {
        try{
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("Socket cliente conectado ao equipamento de ID: " + equipamento.getId()); //enviar mensagem ao cliente
            out.close();
            socket.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
