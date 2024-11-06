/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Modelos.Bus;
import Modelos.EmpresaTransporte;
import Utils.IList;

import Servicios.ServicioCaseta;
import Servicios.ServicioCasetasPrincipal;
import Servicios.ServicioBuses;

/**
 *
 * @author PC
 */
public class ControladorBuses {
    private ServicioCaseta sc;
    private ServicioBuses sb;
    private ServicioCasetasPrincipal scp;

    public ControladorBuses(int idAdmin) {
        this.scp = ServicioCasetasPrincipal.getInstance(); // Obtiene servicios de la matriz casetas
        this.sc = new ServicioCaseta(scp.getCasetaPorAdminID(idAdmin)); // Obtiene servicio de esta caseta
        this.sb = new ServicioBuses(sc.getCaseta().getEmpresa()); // Obtiene el servicio de buses exclusivo
    }

    public void agregarBus(Bus bus) throws RuntimeException {
        sb.agregarBus(bus, scp.getCasetas(), sc.getCaseta().getPlazasEstacionamiento());
        scp.saveDataCasetas();
    }

    public void editarBus(Bus bus) throws RuntimeException {
        sb.editarBus(bus);
        scp.saveDataCasetas();
    }

    public void eliminarBus(String placa) throws RuntimeException {
        sb.eliminarBus(placa);
        scp.saveDataCasetas();
    }

    public Bus buscarBusPorPlaca(String placa) {
        return sb.buscarBusPorPlaca(placa);
    }

    public IList<Bus> getBuses() {
        return sb.getBuses();
    }
    public int getPlazasEstacionamiento() {
        return sc.getCaseta().getPlazasEstacionamiento();
    }
}
