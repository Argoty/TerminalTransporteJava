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

public class RegistroCompra implements Serializable {
    private int puntos;
    private Viaje viaje;
//    private Cliente cliente;
    private int cantidadTiquetes;
    private LocalDateTime fechaCreacion;

    public RegistroCompra(int puntos, Viaje viaje, int cantidad, LocalDateTime fecha) {
        this.puntos = puntos;
        this.viaje = viaje;
        this.cantidadTiquetes = cantidad;
        this.fechaCreacion = fecha; 
    }

    public int getPuntos() {
        return puntos;
    }
    public void disminuirPuntos(int puntos) {
        this.puntos -= puntos;
    }
    
    public int getCantidadTiq() {
        return cantidadTiquetes;
    }
    public void setCantidadTiq(int cantidadTiq) {
        this.cantidadTiquetes = cantidadTiq;
    }

    public Viaje getViaje() {
        return viaje;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    public String getFechaCreacionStr() {
        return this.fechaCreacion.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
}
       


