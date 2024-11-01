/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Modelos.EmpresaTransporte;
import Modelos.Viaje;

import Servicios.ServicioViajes;
import Servicios.ServicioCasetasPrincipal;

import Utils.IList;

/**
 *
 * @author PC
 */
public class ControladorViajes {
    private ServicioViajes sv;
    
    public ControladorViajes(int idAdmin) {
        EmpresaTransporte empr = ServicioCasetasPrincipal.getInstance().getCasetaPorAdminID(idAdmin)
                .getEmpresa();
        this.sv = new ServicioViajes(empr);
    }
    public IList<Viaje> getViajes() {
        return sv.getViajes();
    }
    public void agregarViaje(String destino, String fSal, String hSal, String fLle, String hLle,
            String placaBus, int vlrUnit) throws RuntimeException{
        sv.agregarViaje(destino, fSal, hSal, fLle, hLle, placaBus, vlrUnit);
    }
    public void eliminarViaje(int nroViaje) throws RuntimeException{
        sv.eliminarViaje(nroViaje);
    }
}
