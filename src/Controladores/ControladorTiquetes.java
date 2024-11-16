/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Modelos.EmpresaTransporte;
import Modelos.MovimientoTransaccion;
import Modelos.Tiquete;
import Modelos.Usuarios.Cliente;
import Modelos.Usuarios.Usuario;
import Modelos.Viaje;

import Servicios.ServicioCasetasPrincipal;
import Servicios.ServicioDevoluciones;
import Servicios.ServicioMovimientos;
import Servicios.ServicioTiquetes;
import Servicios.ServicioViajes;
import Servicios.ServicioUsuarios;

import Utils.IList;
import java.time.LocalDateTime;

/**
 *
 * @author PC
 */
public class ControladorTiquetes {
    private ServicioCasetasPrincipal scp;
    private ServicioTiquetes st;
    private ServicioViajes sv;
    private ServicioUsuarios su;
    private ServicioDevoluciones sd;

    public ControladorTiquetes(int idAdmin) {
        this.scp = ServicioCasetasPrincipal.getInstance();
        EmpresaTransporte empr = scp.getCasetaPorAdminID(idAdmin)
                .getEmpresa();
        this.sv = new ServicioViajes(empr);
        this.st = new ServicioTiquetes();
        this.sd = new ServicioDevoluciones();
        this.su = ServicioUsuarios.getInstance();
    }

    public void venderTiquete(int idViaje, int idCliente, int cantidad, int metodoPago) throws RuntimeException {
        Cliente cliente = buscarClientePorId(idCliente);
        Viaje viaje = buscarViajePorId(idViaje);

        IList<Tiquete> tiquetesVenta = st.crearTiquete(viaje, cliente, cantidad, metodoPago);
        
        // Agrega Puntos al usuario segun tiquetes creados
        ServicioMovimientos sm = new ServicioMovimientos(cliente);
        for (int i =0; i < tiquetesVenta.size(); i++) {
            sm.actualizarPuntos(tiquetesVenta.get(i));
        }
        
        // Guarda informacion en binarios
        scp.saveDataCasetas();
        su.saveDataUsuarios();
    }
    
    public void crearDevolucion(int idViaje, int idTiquete) throws RuntimeException{
        Viaje viaje = buscarViajePorId(idViaje);
        Tiquete tiqueteAEliminar = st.obtenerTiquete(viaje, idTiquete);
        if (tiqueteAEliminar.getViaje().getFechaSalida().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("El viaje ya ocurrió, no puedes hacer devolucion");
        }
        
        Cliente cliente = buscarClientePorId(tiqueteAEliminar.getCliente().getNroId());

        ServicioMovimientos sm = new ServicioMovimientos(cliente);
        MovimientoTransaccion movimiento = sm.getMovimientoPorIdTiquete(tiqueteAEliminar.getId());
        int puntosResultado = sm.actualizarPuntosDevolucion(movimiento);
        
        sd.crearDevolucion(viaje, cliente, movimiento, puntosResultado);
        // Guardo info
        scp.saveDataCasetas();
        su.saveDataUsuarios();
    }
    public IList<Tiquete> getTiquetes(int idViaje){
        Viaje viaje = buscarViajePorId(idViaje);
        return st.getTiquetes(viaje);
    }

    public IList<Viaje> getViajesFuturos() {
        return sv.getViajesFuturos();
    }
    public IList<Viaje> getViajes() {
        return sv.getViajes();
    }
    public Cliente buscarClientePorId(int idCliente) throws RuntimeException {
        Usuario usuario = su.buscarUsuarioPorId(idCliente);
        if (!(usuario instanceof Cliente)) {
            throw new RuntimeException("EL CLIENTE NO SE ENCUENTRA CON ESTE ID");
        }
        return (Cliente) usuario;
    }
    public Viaje buscarViajePorId(int idViaje) {
        return sv.buscarViajePorId(idViaje);
    }
   
}
