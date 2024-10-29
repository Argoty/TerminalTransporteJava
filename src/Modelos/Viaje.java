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
import java.time.format.DateTimeFormatter;

public class Viaje {
    private String origen = "Armenia";
    private String destino;
    private LocalDateTime fechaSalida;  // Incluye fecha y hora de salida
    private LocalDateTime fechaLlegada; // Incluye fecha y hora de llegada
    private LocalDateTime fechaCreacion; // Fecha y hora de creación del viaje
    private int vlrUnit;
    private Bus bus;
    
    private IList<Tiquete> tiquetes;

    // Constructor
    public Viaje(String destino, LocalDateTime fechaSalida, LocalDateTime fechaLlegada, int vlrUnit, Bus bus) {
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
    public String getFechaSalidaStr() {
        return this.fechaSalida.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    public void setFechaSalida(LocalDateTime fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public LocalDateTime getFechaLlegada() {
        return fechaLlegada;
    }
    public String getFechaLlegadaStr() {
        return this.fechaLlegada.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    public void setFechaLlegada(LocalDateTime fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public int getVlrUnit() {
        return vlrUnit;
    }

    public void setVlrUnit(int vlrUnit) {
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


