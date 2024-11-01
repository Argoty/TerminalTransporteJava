/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicios;

import Modelos.Caseta;
import Modelos.EmpresaTransporte;

/**
 *
 * @author PC
 */
public class ServicioCaseta {
    Caseta caseta;
    

    public ServicioCaseta(Caseta caseta) {
        this.caseta = caseta;
    }
    
    public void asignarFlota(EmpresaTransporte empresa, int canonArrendamiento, 
            int plazasEstacionamiento, Caseta[][] casetas) {
        caseta.asignarFlota(empresa, canonArrendamiento, plazasEstacionamiento, casetas);
    }
    
//    public AdminFlota getAdminFlota() {
//        return caseta.getAdminFlota();
//    }
    public Caseta getCaseta() {
        return caseta;
    }

    public void liberarCaseta() {
        caseta.liberarCaseta();
    }

    public boolean isDisponible() {
        return caseta.isDisponible();
    }
}
