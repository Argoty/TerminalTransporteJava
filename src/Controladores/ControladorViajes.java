/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Modelos.Bus;
import Modelos.EmpresaTransporte;
import Modelos.Viaje;

import Servicios.ServicioViajes;
import Servicios.ServicioBuses;

import Servicios.ServicioCasetasPrincipal;

import Utils.IList;

/**
 *
 * @author PC
 */
public class ControladorViajes {
    private ServicioViajes sv;
    private ServicioBuses sb;
    private ServicioCasetasPrincipal scp;
    
    public ControladorViajes(int idAdmin) {
        this.scp = ServicioCasetasPrincipal.getInstance();
        EmpresaTransporte empr = scp.getCasetaPorAdminID(idAdmin)
                .getEmpresa();
        this.sv = new ServicioViajes(empr);
        this.sb = new ServicioBuses(empr);
    }
    public IList<Viaje> getViajes() {
        return sv.getViajes();
    }
    public void agregarViaje(String destino, String fSal, String hSal, String fLle, String hLle,
            String placaBus, int vlrUnit) throws RuntimeException{
        sv.agregarViaje(destino, fSal, hSal, fLle, hLle, placaBus, vlrUnit);
        scp.saveDataCasetas();
    }
    public IList<Bus> getBuses() {
        return sb.getBuses();
    }
}
