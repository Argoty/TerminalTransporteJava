/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import Modelos.Usuarios.Cliente;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author Leonardo Argoty
 */
public class Tiquete implements Serializable{
    private Viaje viaje;
    private Cliente cliente;
    private int cantidad;
    private LocalDateTime fechaCompra;

    public Tiquete(Viaje viaje, Cliente cliente, int cantidad) {
        this.viaje = viaje;
        this.cliente = cliente;
        this.cantidad = cantidad;
        this.fechaCompra = LocalDateTime.now();
    }

    public Viaje getViaje() {
        return viaje;
    }

    public void setViaje(Viaje viaje) {
        this.viaje = viaje;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDateTime getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDateTime fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    
}

