/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Modelos.Caseta;
import Modelos.Reserva;
import Servicios.ServicioCasetasPrincipal;
import Servicios.ServicioUsuarios;

import Modelos.Usuarios.Cliente;
import Modelos.Viaje;
import Servicios.ServicioReservas;
import Utils.IList;
import Utils.Lista;

/**
 *
 * @author PC
 */
public class ControladorReservar {

    private ServicioCasetasPrincipal scp;
    private ServicioUsuarios su;
    private ServicioReservas sr;
    private Cliente cliente;

    public ControladorReservar(Cliente cliente) {
        this.scp = ServicioCasetasPrincipal.getInstance();
        this.su = ServicioUsuarios.getInstance();
        this.sr = new ServicioReservas();
        this.cliente = cliente;
    }

    public IList<Viaje> getViajesAll() {
        IList<Viaje> viajesAll = new Lista<>();
        for (Caseta[] caseta : scp.getCasetas()) {
            for (Caseta caseta1 : caseta) {
                if (caseta1.getEmpresa() == null) {
                    continue;
                }
                for (int i = 0; i < caseta1.getEmpresa().getViajes().size(); i++) {
                    Viaje viaje = caseta1.getEmpresa().getViajes().get(i);
                    viajesAll.add(viaje);
                }
            }
        }
        return viajesAll;
    }

    public IList<Viaje> filtrarViajes(String criterio, String destinoFiltro, String fechaFiltro) throws RuntimeException {
        IList<Viaje> viajesFiltrados = new Lista<>();

        for (int i = 0; i < getViajesAll().size(); i++) {
            Viaje viaje = getViajesAll().get(i);
            switch (criterio) {
                case "Fecha" -> {
                    if (viaje.getFechaSalidaStr().contains(fechaFiltro)) {
                        viajesFiltrados.add(viaje);
                    }
                }

                case "Destino" -> {
                    if (viaje.getDestino().equalsIgnoreCase(destinoFiltro)) {
                        viajesFiltrados.add(viaje);
                    }
                }

                case "Ambos" -> {
                    if (viaje.getFechaSalidaStr().contains(fechaFiltro) && viaje.getDestino().equalsIgnoreCase(destinoFiltro)) {
                        viajesFiltrados.add(viaje);
                    }
                }
            }
        }
        if (viajesFiltrados.isEmpty()) {
            throw new RuntimeException("No hay ningun " + (criterio.equals("Ambos") ? " Destino y Fecha" : criterio) + " que coincida.");
        }
        return viajesFiltrados;
    }

    public void realizarReserva(int idViajeSeleccionado, int cantidad, int metodoPago) {
        Viaje viaje = buscarViajePorIdAll(idViajeSeleccionado);
        sr.crearReserva(cliente, viaje, cantidad, metodoPago);
        
        // Guarda informacion en binarios
        scp.saveDataCasetas();
        su.saveDataUsuarios();
    }
    public void cancelarReserva(int idReserva) {
        Reserva reserva = buscarReservaPorId(idReserva);
        Viaje viaje = buscarViajePorIdAll(reserva.getViaje().getId());
        sr.cancelarReserva(cliente, viaje, reserva);
        
        // Guarda informacion en binarios
        scp.saveDataCasetas();
        su.saveDataUsuarios();
    }
    
    public Reserva buscarReservaPorId(int idReserva){
        for (int i=0; i < getReservas().size(); i++) {
            Reserva reserva = getReservas().get(i);
            if (reserva.getId() == idReserva) {
                return reserva;
            }
        }
        return null;
    }

    public Viaje buscarViajePorIdAll(int idViaje) {
        IList<Viaje> viajesAll = getViajesAll();
        for (int i = 0; i < viajesAll.size(); i++) {
            if (idViaje == viajesAll.get(i).getId()) {
                return viajesAll.get(i);
            }
        }
        return null;
    }

    public IList<Reserva> getReservas() {
        return sr.getReservasCli(cliente);
    }

    public String getNombreEmpresaSegunViaje(int idViaje) {
        return scp.getCasetaPorViajeID(idViaje).getEmpresa().getNombre();
    }
}
