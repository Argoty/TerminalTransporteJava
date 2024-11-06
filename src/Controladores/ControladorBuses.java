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
import Servicios.ServicioViajes;

/**
 *
 * @author PC
 */
public class ControladorBuses {

    private ServicioCaseta sc;
    private ServicioBuses sb;
    private ServicioViajes sv;
    private ServicioCasetasPrincipal scp;

    public ControladorBuses(int idAdmin) {
        this.scp = ServicioCasetasPrincipal.getInstance(); // Obtiene servicios de la matriz casetas
        this.sc = new ServicioCaseta(scp.getCasetaPorAdminID(idAdmin)); // Obtiene servicio de esta caseta
        this.sb = new ServicioBuses(sc.getCaseta().getEmpresa()); // Obtiene el servicio de buses exclusivo
        this.sv = new ServicioViajes(sc.getCaseta().getEmpresa());
    }

    public void agregarBus(Bus bus) throws RuntimeException {
        sb.agregarBus(bus, scp.getCasetas(), sc.getCaseta().getPlazasEstacionamiento());
        scp.saveDataCasetas();
    }

    public void editarBus(Bus bus) throws RuntimeException {
        if (sv.buscarBusPorViaje(bus.getPlaca()) == null) {
            sb.editarBus(bus);
            scp.saveDataCasetas();
        } else {
            throw new RuntimeException("El bus ya tiene viajes asignados, no se puede editar");
        }

    }

    public void eliminarBus(String placa) throws RuntimeException {
        if (sv.buscarBusPorViaje(placa) == null) {
            sb.eliminarBus(placa);
            scp.saveDataCasetas();
        } else {
            throw new RuntimeException("El bus ya tiene viajes asignados, no se puede eliminar");
        }

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
