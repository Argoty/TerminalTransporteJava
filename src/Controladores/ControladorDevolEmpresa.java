/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Modelos.Devolucion;
import Modelos.EmpresaTransporte;
import Modelos.Viaje;
import Servicios.ServicioCasetasPrincipal;
import Servicios.ServicioDevoluciones;
import Servicios.ServicioViajes;
import Utils.IList;
import Utils.Lista;

/**
 *
 * @author PC
 */
public class ControladorDevolEmpresa {
    private ServicioViajes sv;
    private ServicioDevoluciones sd;

    public ControladorDevolEmpresa(int idAdmin) {
        EmpresaTransporte empr = ServicioCasetasPrincipal.getInstance().getCasetaPorAdminID(idAdmin)
                .getEmpresa();
        this.sv = new ServicioViajes(empr);
        this.sd = new ServicioDevoluciones();
    }

    public IList<Viaje> getViajesDevol() {
        IList<Viaje> viajesConDevoluciones = new Lista<>();

        for (int i = 0; i < sv.getViajes().size(); i++) {
            Viaje viaje = sv.getViajes().get(i);
            if (viaje.getDevoluciones() != null && !viaje.getDevoluciones().isEmpty()) {
                viajesConDevoluciones.add(viaje);
            }
        }

        return viajesConDevoluciones;
    }

    public IList<Devolucion> getDevoluciones(int idViaje) {
        Viaje viaje = sv.buscarViajePorId(idViaje);
        return sd.getDevolucionesVia(viaje);
    }
}
