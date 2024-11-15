/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

/**
 *
 * @author PC
 */
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Devolucion implements Serializable{
    private Tiquete tiquete;
    private LocalDateTime fechaDevolucion;
    private int resultadoPuntos;

    // Constructor
    public Devolucion(Tiquete tiquete, int resultadoPuntos) {
        this.tiquete = tiquete;
        this.fechaDevolucion = LocalDateTime.now();
        this.resultadoPuntos = resultadoPuntos;
    }

    // Getters y Setters
    public Tiquete getTiquete() {
        return tiquete;
    }

    public void setTiquete(Tiquete tiquete) {
        this.tiquete = tiquete;
    }

    public LocalDateTime getFechaDevolucion() {
        return fechaDevolucion;
    }
    
    public String getFechaDevolucionStr() {
        return fechaDevolucion.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    public void setFechaDevolucion(LocalDateTime fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }
    
    public int getResultadoPuntos() {
        return this.resultadoPuntos;
    }
    public void setResultadoPuntos(int resultadoPuntos) {
        this.resultadoPuntos = resultadoPuntos;
    }

    // Método SUJETO A CAMBIOS para hacer la devolución
//    public void realizarDevolucion() {
//        // Liberar el tiquete del viaje
//        //tiquete.getViaje().getBus().setPuestosDisponibles(tiquete.getViaje().getBus().getPuestosDisponibles() + tiquete.getCantidad());
//
//        // Descontar los puntos acumulados por el cliente
//        int puntosADescontar = (int) (montoDevuelto / 10000) * 3; // Regla de 3 puntos por cada $10,000
////        cliente.redimirPuntos(puntosADescontar);
//
//        System.out.println("Devolución realizada. Monto devuelto: " + montoDevuelto + ". Puntos descontados: " + puntosADescontar);
//    }
}

