/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import java.io.Serializable;


/**
 *
 * @author PC
 */
public class MovimientoTransaccion implements Serializable {
    private int puntos;
    private Tiquete tiquete;

    public MovimientoTransaccion(int puntos, Tiquete tiquete) {
        this.puntos = puntos;
        this.tiquete = tiquete;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public Tiquete getTiquete() {
        return tiquete;
    }

    public void setTiquete(Tiquete tiquete) {
        this.tiquete = tiquete;
    }
}
       


