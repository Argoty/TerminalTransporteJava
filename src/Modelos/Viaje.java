/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

/**
 *
 * @author Javier Argoty
 */
import Modelos.Usuarios.Cliente;
import Utils.IList;
import Utils.Lista;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Viaje implements Serializable{
    private String origen = "Armenia";
    private int id;
    private String destino;
    private LocalDateTime fechaSalida;  
    private LocalDateTime fechaLlegada; 
    private LocalDateTime fechaCreacion; 
    private int vlrUnit;
    private Bus bus;
    
    private IList<Tiquete> tiquetes;
    private static int cont = 1;

    // Constructor
    public Viaje(String destino, LocalDateTime fechaSalida, LocalDateTime fechaLlegada, int vlrUnit, Bus bus) {
        this.destino = destino;
        this.fechaSalida = fechaSalida;
        this.fechaLlegada = fechaLlegada;
        this.fechaCreacion = LocalDateTime.now(); // Fecha y hora actuales para la creación del viaje
        this.vlrUnit = vlrUnit;
        this.bus = bus;
        
        this.tiquetes = new Lista<>();
        
        this.id = cont;
        cont++;
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
    // METODOS PARA MANEJAR LOGICA DE TIQUETES SEGUN EL VIAJE
    public IList<Tiquete> getTiquetes() {
        return this.tiquetes;
    }
    public void crearTiquete(Cliente cliente, int cantidad) {
        tiquetes.add(new Tiquete(this, cliente));
    }
}


