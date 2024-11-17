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
    private RegistroPuntos movimiento;
    private LocalDateTime fechaDevolucion;
    private int resultadoPuntos;

    // Constructor
    public Devolucion(RegistroPuntos movimiento, int resultadoPuntos) {
        this.movimiento = movimiento;
        this.fechaDevolucion = LocalDateTime.now();
        this.resultadoPuntos = resultadoPuntos;
    }

    // Getters y Setters
    public RegistroPuntos getMovimiento() {
        return movimiento;
    }

    public void setTiquete(RegistroPuntos movimiento) {
        this.movimiento = movimiento;
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
        return resultadoPuntos;
    }
   
}

