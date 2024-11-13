/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Modelos.EmpresaTransporte;
import Modelos.Tiquete;
import Modelos.Usuarios.Cliente;
import Modelos.Usuarios.Usuario;
import Modelos.Viaje;

import Servicios.ServicioCasetasPrincipal;
import Servicios.ServicioDevoluciones;
import Servicios.ServicioPuntos;
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

    public void venderTiquete(int idViaje, int idCliente, int cantidad) throws RuntimeException {
        Viaje viaje = buscarViajePorId(idViaje);
        Cliente cliente = buscarClientePorId(idCliente);
        LocalDateTime fechaCompra = st.crearTiquete(viaje, cliente, cantidad);
        
        // Agrega Puntos al usuario
        ServicioPuntos sp = new ServicioPuntos(cliente);
        sp.actualizarPuntos(viaje, cantidad, fechaCompra);
        
        // Guarda informacion en binarios
        scp.saveDataCasetas();
        su.saveDataUsuarios();
    }
    
    public void crearDevolucion(int idViaje, int idCliente, int idTiquete) {
        Viaje viaje = buscarViajePorId(idViaje);
        Tiquete tiqueteAEliminar = st.obtenerTiquete(viaje, idTiquete);
        
        ServicioPuntos sp = new ServicioPuntos(tiqueteAEliminar.getCliente());
        int puntosPerdidos = sp.disminuirPuntos(tiqueteAEliminar.getViaje());
        
        sd.crearDevolucion(viaje, tiqueteAEliminar, puntosPerdidos);
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
