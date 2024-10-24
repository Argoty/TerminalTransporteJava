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
import java.util.Date;

public class Notificacion {
    private Cliente cliente;
    private String mensaje;         
    private Date fechaEnvio;        
        

    // Constructor
    public Notificacion(Cliente cliente, String mensaje) {
        this.cliente = cliente;
        this.mensaje = mensaje;
        this.fechaEnvio = new Date(); // Fecha actual
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

    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

//    @Override
//    public String toString() {
//        return "Notificación para " + cliente.getUsername() + ": " + mensaje + " | Fecha: " + fechaEnvio;
//    }
}

