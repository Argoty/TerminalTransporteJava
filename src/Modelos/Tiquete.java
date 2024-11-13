/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import Modelos.Usuarios.Cliente;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Leonardo Argoty
 */
public class Tiquete implements Serializable{
    private static int cont = 1;
    private int id;
    private Viaje viaje;
    private Cliente cliente;
    private LocalDateTime fechaCompra;

    public Tiquete(Viaje viaje, Cliente cliente, LocalDateTime fecha) {
        this.id = cont++; 
        this.viaje = viaje;
        this.cliente = cliente;
        this.fechaCompra =fecha;
    }
    public int getId() {
        return id;
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

    public LocalDateTime getFechaCompra() {
        return fechaCompra;
    }
    public String getFechaCompraStr() {
        return this.fechaCompra.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    public void setFechaCompra(LocalDateTime fechaCompra) {
        this.fechaCompra = fechaCompra;
    }
    public static void ajustarContadorPersistencia(int numeroDeTiquetes) {
        cont = numeroDeTiquetes;
    }
    public static int getContador() {
        return cont;
    }
    
}

