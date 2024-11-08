/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import java.io.Serializable;

/**
 *
 * @author Javier Argoty
 */
public class Bus implements Serializable{
    private String placa;
    private String marca;
    private String tipo; // Por ejemplo, "Turismo", "Lujo", "Económico", "MiniBus"
    private int puestos;

    public Bus(String placa, String marca, String tipo,int puestos) {
        this.placa = placa;
        this.marca = marca;
        this.tipo = tipo;
        this.puestos = puestos;
    }

    public String getPlaca() {
        return this.placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getPuestos() {
        return puestos;
    }

    public void setPuestos(int puestos) {
        this.puestos = puestos;
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
}

