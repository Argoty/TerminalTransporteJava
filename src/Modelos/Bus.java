/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author Javier Argoty
 */
public class Bus implements Serializable{
    private String placa;
    private String marca;
    private String tipo; // Por ejemplo, "Turismo", "Lujo", "Económico", "MiniBus"
    private int puestosDisponibles;
    private boolean disponible;
    private LocalDateTime fechaDisponible;

    public Bus(String placa, String marca, String tipo,int puestosDisponibles) {
        this.placa = placa;
        this.marca = marca;
        this.tipo = tipo;
        this.puestosDisponibles = puestosDisponibles;
        
        this.disponible = true; 
        this.fechaDisponible = LocalDateTime.now(); 
    }

    public String getPlaca() {
        return this.placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getPuestosDisponibles() {
        return puestosDisponibles;
    }

    public void setPuestosDisponibles(int puestosDisponibles) {
        this.puestosDisponibles = puestosDisponibles;
    }
    
    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isDisponible() {
        return LocalDateTime.now().isAfter(fechaDisponible);
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
    public void setFechaDisponible(LocalDateTime fechaDisponible) {
        this.fechaDisponible = fechaDisponible;
    }
}

