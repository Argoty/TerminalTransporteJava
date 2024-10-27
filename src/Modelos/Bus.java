/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

/**
 *
 * @author Javier Argoty
 */
public class Bus {
    private String placa;
    private String marca;
    private String tipo; // Por ejemplo, "Turismo", "Lujo", "Económico", "MiniBus"
    private int puestosDisponibles;
    private boolean disponible;

    public Bus(String placa, String marca, String tipo,int puestosDisponibles) {
        this.placa = placa;
        this.marca = marca;
        this.tipo = tipo;
        this.puestosDisponibles = puestosDisponibles;
        this.disponible = true; // por defecto está disponible al ser creado
    }

    public String getPlaca() {
        return placa;
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
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}

