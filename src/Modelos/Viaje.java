/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

/**
 *
 * @author Javier Argoty
 */
import Utils.IList;
import Utils.Lista;
import java.time.LocalDateTime;

public class Viaje {
    private String origen = "Armenia";
    private String destino;
    private LocalDateTime fechaSalida;  // Incluye fecha y hora de salida
    private LocalDateTime fechaLlegada; // Incluye fecha y hora de llegada
    private LocalDateTime fechaCreacion; // Fecha y hora de creación del viaje
    private double vlrUnit;
    private Bus bus;
    
    private IList<Tiquete> tiquetes;

    // Constructor
    public Viaje(String destino, LocalDateTime fechaSalida, LocalDateTime fechaLlegada, double vlrUnit, Bus bus) {
        this.destino = destino;
        this.fechaSalida = fechaSalida;
        this.fechaLlegada = fechaLlegada;
        this.fechaCreacion = LocalDateTime.now(); // Fecha y hora actuales para la creación del viaje
        this.vlrUnit = vlrUnit;
        this.bus = bus;
        
        this.tiquetes = new Lista<>();
    }

    // Getters y Setters
    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public LocalDateTime getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDateTime fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public LocalDateTime getFechaLlegada() {
        return fechaLlegada;
    }

    public void setFechaLlegada(LocalDateTime fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public double getVlrUnit() {
        return vlrUnit;
    }

    public void setVlrUnit(double vlrUnit) {
        this.vlrUnit = vlrUnit;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }
    
    public IList<Tiquete> getTiquetes() {
        return this.tiquetes;
    }
}


