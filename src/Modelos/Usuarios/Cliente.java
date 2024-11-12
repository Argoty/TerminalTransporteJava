/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Usuarios;

import Modelos.Devolucion;
import Modelos.Notificacion;
import Modelos.RegistroPuntos;
import Modelos.Reserva;
import Modelos.Tiquete;
import Utils.IList;
import Utils.Lista;
import java.io.Serializable;

/**
 *
 * @author Javier Argoty
 */

public class Cliente extends Usuario implements Serializable {
    private int dineroInvertido;
    private int puntosAcumulados;
    private int dineroParaProximoPunto; // Dinero acumulado para el próximo múltiplo de 10,000
    
    private IList<Tiquete> tiquetes;
    private IList<Reserva> reservas;
    private IList<RegistroPuntos> historialPuntos;
    private IList<Devolucion> devoluciones;
    private IList<Notificacion> notificaciones;

    public Cliente(String name, int nroId, String email, String tel, String password) {
        super(name, nroId, email, tel, password);
        this.puntosAcumulados = 0;
        this.dineroInvertido = 0;
        this.dineroParaProximoPunto = 0;
        
        this.tiquetes = new Lista<>();
        this.reservas = new Lista<>();
        this.historialPuntos = new Lista<>();
        this.devoluciones = new Lista<>();
        this.notificaciones = new Lista<>();
    }

    public int getPuntosAcumulados() {
        return puntosAcumulados;
    }
    public void setPuntosAcumulados(int puntos) {
        this.puntosAcumulados = puntos;
    }

    public int getDineroInvertido() {
        return dineroInvertido;
    }
    public void setDineroInvertido(int dinero) {
        this.dineroInvertido = dinero;
    }
    public int getDineroProximoPunto() {
        return this.dineroParaProximoPunto;
    }
    public void setDineroProximoPunto(int dineroProximoPunto) {
        this.dineroParaProximoPunto = dineroProximoPunto;
    }

//    public void agregarDineroInvertido(int dineroInvertido) {
//        this.dineroInvertido += dineroInvertido;
//    }
    
    
    public IList<Tiquete> getTiquetes() {
        return this.tiquetes;
    }

    public IList<RegistroPuntos> getHistorialPuntos() {
        return historialPuntos;
    }
    public IList<Reserva> getReservas() {
        return reservas;
    }
    public IList<Devolucion> getDevoluciones() {
        return devoluciones;
    }
    public IList<Notificacion> getNotificaciones() {
        return notificaciones;
    }

//    public void agregarRegistroPuntos(int puntos, Tiquete tiquete) {
//        RegistroPuntos registro = new RegistroPuntos(puntos, tiquete);
//        historialPuntos.add(registro);
//    }
//
//    public void actualizarPuntos(int dineroInvertidoViaje, Tiquete tiquete) {
//        this.dineroParaProximoPunto += dineroInvertidoViaje;
//
//        while (this.dineroParaProximoPunto >= 10000) {
//            this.puntosAcumulados += 3;
//            this.dineroParaProximoPunto -= 10000;
//            agregarRegistroPuntos(3, tiquete);
//        }
//    }
    
//    public void redimirPuntos(int puntos) {
//        if (puntos <= this.puntosAcumulados) {
//            this.puntosAcumulados -= puntos;
//        } else {
//            System.out.println("No tienes suficientes puntos para redimir.");
//        }
//    }
}
