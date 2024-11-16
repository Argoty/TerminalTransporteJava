/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;
import Modelos.EmpresaTransporte;
import Modelos.Reserva;
import Modelos.Viaje;
import Servicios.ServicioCasetasPrincipal;
import Servicios.ServicioReservas;
import Servicios.ServicioUsuarios;
import Servicios.ServicioViajes;
import Utils.IList;
import Utils.Lista;

/**
 *
 * @author PC
 */
public class ControladorReservasEmpr {
    private ServicioCasetasPrincipal scp;
    private ServicioUsuarios su;
    private ServicioViajes sv;
    private ServicioReservas sr;
    public ControladorReservasEmpr(int idAdmin) {
        EmpresaTransporte empr = ServicioCasetasPrincipal.getInstance().getCasetaPorAdminID(idAdmin)
                .getEmpresa();
        this.sv = new ServicioViajes(empr);
        this.sr = new ServicioReservas();
    }
    public IList<Viaje> getViajesReservas() {
        IList<Viaje> viajesConReservas = new Lista<>();

        for (int i = 0; i < sv.getViajes().size(); i++) {
            Viaje viaje = sv.getViajes().get(i);
            if (viaje.getReservas() != null && !viaje.getReservas().isEmpty()) {
                viajesConReservas.add(viaje);
            }
        }

        return viajesConReservas;
    }
    public IList<Reserva> getReservas(int idViaje) {
        Viaje viaje = sv.buscarViajePorId(idViaje);
        return sr.getReservasViaje(viaje);
    }
}
