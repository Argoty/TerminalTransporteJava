/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Modelos.Caseta;
import Servicios.ServicioCasetasPrincipal;

/**
 *
 * @author PC
 */
public class ControladorCasetasPrincipal {
    private ServicioCasetasPrincipal servicioCasetasPrincipal;

    public ControladorCasetasPrincipal() {
        servicioCasetasPrincipal = ServicioCasetasPrincipal.getInstance();
    }

    public Caseta getCaseta(int fila, int columna) {
        return servicioCasetasPrincipal.getCaseta(fila, columna);
    }

//    public Caseta[][] getCasetas() {
//        return casetas;
//    }
}
