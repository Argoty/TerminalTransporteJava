/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;
import Modelos.EmpresaTransporte;
import Modelos.Reserva;
import Modelos.Tiquete;
import Modelos.Usuarios.Cliente;
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
    public void hacerEfectiva(int idReserva, int idViaje, int metodoPago) {
        Reserva reservaViaje = getReservaPorIdViaje(idReserva, idViaje);
        // Validaciones por si el metodo de pago es por puntos
        if (metodoPago == 1) {
            if (reservaViaje.getViaje().getVlrUnit() > 30000)throw new RuntimeException("Solo se pueden redimir 90 puntos por un tiquete de máximo 30k");
            if (reservaViaje.getCliente().getPuntosAcumulados() < 90) throw new RuntimeException("El cliente no tiene suficientes puntos para canjear");
        }
        if (reservaViaje.isEfectiva()) throw new RuntimeException("La Reserva ya está efectiva");
        
        Reserva reservaCli = getReservaPorIdCli(idReserva, reservaViaje.getCliente().getNroId());
        sr.hacerEfectiva(reservaViaje);
        sr.hacerEfectiva(reservaCli);

        IList<Tiquete> tiquetesVenta = st.crearTiquete(reservaViaje.getViaje(), reservaCli.getCliente(), 1, metodoPago);
        
        // Agrega Puntos al usuario segun tiquetes creados
        ServicioPuntos sm = new ServicioPuntos(reservaCli.getCliente());
        sm.actualizarPuntos(tiquetesVenta.get(0));
        
        // Guarda informacion en binarios
        scp.saveDataCasetas();
        su.saveDataUsuarios();
    }
    public Reserva getReservaPorIdViaje(int idReserva, int idViaje) {
        Viaje viaje = sv.buscarViajePorId(idViaje);
        return sr.getReservaPorIdViaje(idReserva, viaje);
    }
    public Reserva getReservaPorIdCli(int idReserva, int idCliente) {
        Cliente cliente = (Cliente) su.buscarUsuarioPorId(idCliente);
        return sr.getReservaPorIdCliente(idReserva, cliente);
    }
}
