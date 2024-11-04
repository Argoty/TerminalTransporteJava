/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

/**
 *
 * @author PC
 */
import Modelos.Usuarios.Cliente;
import java.io.Serializable;
import java.time.LocalDateTime;

public class Notificacion implements Serializable{
    private Cliente cliente;
    private String mensaje;         
    private LocalDateTime fechaEnvio;        
        
    public Notificacion(Cliente cliente, String mensaje) {
        this.cliente = cliente;
        this.mensaje = mensaje;
        this.fechaEnvio = LocalDateTime.now(); // Fecha actual
    }

    // Getters y Setters
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

//    @Override
//    public String toString() {
//        return "Notificación para " + cliente.getUsername() + ": " + mensaje + " | Fecha: " + fechaEnvio;
//    }
}

