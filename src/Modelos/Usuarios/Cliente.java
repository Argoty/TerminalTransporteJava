/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Usuarios;

import Modelos.Devolucion;
import Modelos.Notificacion;
import Modelos.Tiquete;
import Utils.IList;

/**
 *
 * @author Javier Argoty
 */
public class Cliente extends Usuario {
    private int puntosAcumulados;
    // Sujeto a cambios
    //private IList<Notificacion> notificaciones;
    //private IList<Notificacion> tiquetes;
    


    public Cliente(String name,int nroId, String password) {
        super(name, nroId,password);
        this.puntosAcumulados = 0;
    }



    public int getPuntosAcumulados() {
        return puntosAcumulados;
    }

    public void agregarPuntos(int puntos) {
        this.puntosAcumulados += puntos;
    }

    public void redimirPuntos(int puntos) {
        if (puntos <= this.puntosAcumulados) {
            this.puntosAcumulados -= puntos;
        } else {
            System.out.println("No tienes suficientes puntos para redimir.");
        }
    }
}
