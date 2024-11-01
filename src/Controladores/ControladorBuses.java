/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Modelos.Bus;
import Modelos.Caseta;
import Modelos.EmpresaTransporte;
import Utils.IList;

import Servicios.ServicioCasetasPrincipal;
import Servicios.ServicioBuses;

/**
 *
 * @author PC
 */
public class ControladorBuses {
    private ServicioBuses sb;
    private ServicioCasetasPrincipal scc;

    public ControladorBuses(int idAdmin) {
        this.scc = ServicioCasetasPrincipal.getInstance();
        EmpresaTransporte empr = scc.getCasetaPorAdminID(idAdmin)
                .getEmpresa();
        this.sb = new ServicioBuses(empr);
    }

    public void agregarBus(Bus bus, int nroPlazasCaseta) throws RuntimeException {
        sb.agregarBus(bus, scc.getCasetas(), nroPlazasCaseta);
    }

    public void editarBus(Bus bus) throws RuntimeException {
        sb.editarBus(bus);
    }

    public void eliminarBus(String placa) throws RuntimeException {
        sb.eliminarBus(placa);
    }

    public Bus buscarBusPorPlaca(String placa) {
        return sb.buscarBusPorPlaca(placa);
    }

    public IList<Bus> getBuses() {
        return sb.getBuses();
    }
}
