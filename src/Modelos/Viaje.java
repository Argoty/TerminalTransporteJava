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
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Viaje implements Serializable{
    private static int cont = 1;
    private int id;
    private String origen = "Armenia";
    private String destino;
    private LocalDateTime fechaSalida;  
    private LocalDateTime fechaLlegada; 
    private LocalDateTime fechaCreacion; 
    private int vlrUnit;
    private Bus bus;
 
    private IList<Tiquete> tiquetes;
    private IList<Devolucion> devoluciones;
    // Constructor
    public Viaje(String destino, LocalDateTime fechaSalida, LocalDateTime fechaLlegada, int vlrUnit, Bus bus) {
        this.id = cont++; 
        this.destino = destino;
        this.fechaSalida = fechaSalida;
        this.fechaLlegada = fechaLlegada;
        this.fechaCreacion = LocalDateTime.now(); // Fecha y hora actuales para la creación del viaje
        this.vlrUnit = vlrUnit;
        this.bus = bus;
        
        this.tiquetes = new Lista<>();
        this.devoluciones = new Lista<>();
    }

    // Getters y Setters
    public int getId() {
        return id;
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
        return this.bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }
    // Método para ajustar contador en la persistencia
    public static void ajustarContadorPersistencia(int numeroDeViajes) {
        cont = numeroDeViajes + 1;
    }
    
    public IList<Tiquete> getTiquetes() {
        return this.tiquetes;
    }
    public IList<Devolucion> getDevoluciones(){
        return this.devoluciones;
    }
}


