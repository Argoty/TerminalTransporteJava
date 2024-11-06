/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Modelos.Caseta;
import Modelos.EmpresaTransporte;
import Servicios.ServicioCaseta;
import Servicios.ServicioCasetasPrincipal;
import Servicios.ServicioUsuarios;

/**
 *
 * @author Javier Leoanardo Argoty Roa
 */
public class ControladorCaseta {

    private ServicioCaseta sc;
    private ServicioCasetasPrincipal scp;
    private ServicioUsuarios su;

    public ControladorCaseta(Caseta caseta) {
        this.sc = new ServicioCaseta(caseta);
        this.scp = ServicioCasetasPrincipal.getInstance();
        this.su = ServicioUsuarios.getInstance();
    }

    public void asignarFlota(EmpresaTransporte empresa, int canonArrendamiento,
            int plazasEstacionamiento) {
        if (this.isDisponible()) {
            // Valida primero info del usuario admin
            su.validarUsuarioInfo(empresa.getAdmin());
            // Asigno toda la flota
            sc.asignarFlota(empresa,
                    canonArrendamiento, plazasEstacionamiento, scp.getCasetas());
            su.registrarUsuario(empresa.getAdmin());
        } else {
            sc.asignarFlota(empresa,
                    canonArrendamiento, plazasEstacionamiento, scp.getCasetas());
            su.actualizarUsuario(empresa.getAdmin());
        }
        scp.saveDataCasetas();
    }

//    public AdminFlota getAdminFlota() {
//        return caseta.getAdminFlota();
//    }
    public Caseta getCaseta() {
        return sc.getCaseta();
    }

    public void liberarCaseta() {
        su.eliminarUsuario(getCaseta().getEmpresa().getAdmin().getNroId());
        sc.liberarCaseta();
        scp.saveDataCasetas();
    }
    public boolean isDisponible() {
        return sc.isDisponible();
    }
}
