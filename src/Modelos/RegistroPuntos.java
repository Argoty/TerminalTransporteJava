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

public class RegistroPuntos implements Serializable {
    private int puntos;
    private Tiquete tiquete;
    private LocalDateTime fechaCreacion;

    public RegistroPuntos(int puntos, Tiquete tiquete) {
        this.puntos = puntos;
        this.tiquete = tiquete;
        this.fechaCreacion = LocalDateTime.now(); // Fecha de creación en el momento del registro
    }

    public int getPuntos() {
        return puntos;
    }

    public Tiquete getTiquete() {
        return tiquete;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
}
       


