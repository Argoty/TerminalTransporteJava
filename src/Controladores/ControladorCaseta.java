/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Modelos.Caseta;
import Modelos.EmpresaTransporte;

/**
 *
 * @author Javier Leoanardo Argoty Roa
 */

public class ControladorCaseta {

    Caseta caseta;

    public ControladorCaseta(Caseta caseta) {
        this.caseta = caseta;
    }
    
    public void asignarFlota(EmpresaTransporte empresa, int canonArrendamiento, int plazasEstacionamiento) {
        caseta.asignarFlota(empresa, canonArrendamiento, plazasEstacionamiento);
    }
    
//    public AdminFlota getAdminFlota() {
//        return caseta.getAdminFlota();
//    }

    public void liberarCaseta() {
        caseta.liberarCaseta();
    }

    public boolean isDisponible() {
        return caseta.isDisponible();
    }
    
    
    

}
