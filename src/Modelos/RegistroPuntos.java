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

public class RegistroPuntos implements Serializable {
    private int puntos;
    private Tiquete tiquete;
    private LocalDateTime fechaCreacion;

    public RegistroPuntos(int puntos, Tiquete tiquete, LocalDateTime fecha) {
        this.puntos = puntos;
        this.tiquete = tiquete;
        this.fechaCreacion = fecha == null ? LocalDateTime.now() : fecha; // Fecha de creación en el momento del registro
    }

    public int getPuntos() {
        return puntos;
    }
    public void aumentarPuntos(int puntos) {
        this.puntos += puntos;
    }

    public Tiquete getTiquete() {
        return tiquete;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    public String getFechaCreacionStr() {
        return this.fechaCreacion.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
}
       


