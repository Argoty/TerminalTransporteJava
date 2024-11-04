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

public class Devolucion implements Serializable{
    private Tiquete tiquete;
    private Cliente cliente;
    private LocalDateTime fechaDevolucion;
    private double montoDevuelto; // Monto del dinero devuelto

    // Constructor
    public Devolucion(Tiquete tiquete, Cliente cliente, double montoDevuelto) {
        this.tiquete = tiquete;
        this.cliente = cliente;
        this.fechaDevolucion = LocalDateTime.now();
        this.montoDevuelto = montoDevuelto;
    }

    // Getters y Setters
    public Tiquete getTiquete() {
        return tiquete;
    }

    public void setTiquete(Tiquete tiquete) {
        this.tiquete = tiquete;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDateTime getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDateTime fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public double getMontoDevuelto() {
        return montoDevuelto;
    }

    public void setMontoDevuelto(double montoDevuelto) {
        this.montoDevuelto = montoDevuelto;
    }

    // Método SUJETO A CAMBIOS para hacer la devolución
    public void realizarDevolucion() {
        // Liberar el tiquete del viaje
        //tiquete.getViaje().getBus().setPuestosDisponibles(tiquete.getViaje().getBus().getPuestosDisponibles() + tiquete.getCantidad());

        // Descontar los puntos acumulados por el cliente
        int puntosADescontar = (int) (montoDevuelto / 10000) * 3; // Regla de 3 puntos por cada $10,000
        cliente.redimirPuntos(puntosADescontar);

        System.out.println("Devolución realizada. Monto devuelto: " + montoDevuelto + ". Puntos descontados: " + puntosADescontar);
    }
}

