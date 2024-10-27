/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Usuarios;

import Modelos.Devolucion;
import Modelos.Notificacion;
import Modelos.Reserva;
import Modelos.Tiquete;
import Utils.IList;
import Utils.Lista;

/**
 *
 * @author Javier Argoty
 */
public class Cliente extends Usuario {
    private int puntosAcumulados;
    // Sujeto a cambios
    private IList<Notificacion> notificaciones;
    private IList<Tiquete> tiquetes;
    private IList<Reserva> reservas;


    public Cliente(String name,int nroId, String email, String tel,String password) {
        super(name,nroId, email, tel,password);
        this.puntosAcumulados = 0;
        
        notificaciones = new Lista<>();
        tiquetes = new Lista<>();
        reservas = new Lista<>();
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
