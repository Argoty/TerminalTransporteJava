/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;
import Modelos.EmpresaTransporte;
import Modelos.Reserva;
import Modelos.Tiquete;
import Modelos.Viaje;
import Servicios.ServicioCasetasPrincipal;
import Servicios.ServicioPuntos;
import Servicios.ServicioReservas;
import Servicios.ServicioTiquetes;
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
    private ServicioTiquetes st;
    public ControladorReservasEmpr(int idAdmin) {
        this.scp = ServicioCasetasPrincipal.getInstance();
        this.su = ServicioUsuarios.getInstance();
        EmpresaTransporte empr = scp.getCasetaPorAdminID(idAdmin)
                .getEmpresa();
        this.sv = new ServicioViajes(empr);
        this.sr = new ServicioReservas();
        this.st = new ServicioTiquetes();
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
    public void hacerEfectiva(int idReserva, int idViaje) {
        Reserva reserva = getReservaPorId(idReserva, idViaje);
        
        sr.hacerEfectiva(reserva);
        IList<Tiquete> tiquetesVenta = st.crearTiquete(reserva.getViaje(), reserva.getCliente(), 1, (reserva.getMetodoPago().equals("efectivo") ? 0 : 1));
        
        // Agrega Puntos al usuario segun tiquetes creados
        ServicioPuntos sm = new ServicioPuntos(reserva.getCliente());
        sm.actualizarPuntos(tiquetesVenta.get(0));
        
        // Guarda informacion en binarios
        scp.saveDataCasetas();
        su.saveDataUsuarios();
    }
    public Reserva getReservaPorId(int idReserva, int idViaje) {
        Viaje viaje = sv.buscarViajePorId(idViaje);
        return sr.getReservaPorId(idReserva, viaje);
    }
}
