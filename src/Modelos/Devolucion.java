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

public class Devolucion {
    private Tiquete tiquete;
    private Cliente cliente;
    private Date fechaDevolucion;
    private double montoDevuelto; // Monto del dinero devuelto

    // Constructor
    public Devolucion(Tiquete tiquete, Cliente cliente, Date fechaDevolucion, double montoDevuelto) {
        this.tiquete = tiquete;
        this.cliente = cliente;
        this.fechaDevolucion = fechaDevolucion;
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

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
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

