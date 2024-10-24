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
import java.util.Date;

public class Viaje {
    private String origen = "Armenia";
    private String destino;
    private Date fechaSalida;
    private Date fechaLlegada;
    private double precio;
    private Bus bus;
    
    private IList<Tiquete> tiquetes;

    public Viaje(String destino, Date fechaSalida, Date fechaLlegada, double precio, Bus bus) {
        this.destino = destino;
        this.fechaSalida = fechaSalida;
        this.fechaLlegada = fechaLlegada;
        this.precio = precio;
        this.bus = bus;
        
        this.tiquetes = new Lista<>();
    }

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

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public Date getFechaLlegada() {
        return fechaLlegada;
    }

    public void setFechaLlegada(Date fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
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

