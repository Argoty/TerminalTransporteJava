/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicios;

import Modelos.EmpresaTransporte;
import Modelos.Viaje;
import Utils.IList;

/**
 *
 * @author PC
 */
public class ServicioViajes {
    EmpresaTransporte empresa;

    public ServicioViajes(EmpresaTransporte empresa) {
        this.empresa = empresa;
    }
    public IList<Viaje> getViajes() {
        return empresa.getViajes();
    }
    public void agregarViaje(String destino, String fSal, String hSal, String fLle, String hLle,
            String placaBus, int vlrUnit) throws RuntimeException{
        empresa.agregarViaje(destino, fSal, hSal, fLle, hLle, placaBus, vlrUnit);
    }
    public void eliminarViaje(int nroViaje) throws RuntimeException{
        empresa.eliminarViaje(nroViaje);
    }
}
