/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Modelos.Caseta;
import Modelos.Notificacion;
import Modelos.Reserva;
import Servicios.ServicioCasetasPrincipal;
import Servicios.ServicioUsuarios;

import Modelos.Usuarios.Cliente;
import Modelos.Usuarios.Usuario;
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

    public void realizarReserva(int idViajeSeleccionado, int cantidad) {
        Viaje viaje = buscarViajePorIdAll(idViajeSeleccionado);
        sr.crearReserva(cliente, viaje, cantidad);
        
        // Guarda informacion en binarios
        scp.saveDataCasetas();
        su.saveDataUsuarios();
    }
    public boolean cancelarReserva(int idReserva) throws RuntimeException{
        Reserva reserva = buscarReservaPorId(idReserva);
        if (reserva.isEfectiva()) {
            throw new RuntimeException("Esta reserva ya está efectiva, no se puede cancelar");
        }
        Viaje viaje = buscarViajePorIdAll(reserva.getViaje().getId());
        sr.cancelarReserva(cliente, viaje, reserva);
        
        boolean esMismoClienteCola = false;
        // Si estaba lleno saca de la cola al cliente que estaba esperando y le crea su reserva
        if (!viaje.getColaEspera().isEmpty() && viaje.getPuestosDesocupados() == 1) {
            Cliente clienteDesencolado = buscarClientePorId(viaje.getColaEspera().dequeue().getNroId());

            // Crear una nueva reserva para el cliente desencolado
            Reserva nuevaReserva = new Reserva(clienteDesencolado, viaje);
            viaje.getReservas().add(nuevaReserva);
            clienteDesencolado.getReservas().add(nuevaReserva);
            clienteDesencolado.getNotificaciones().add(new Notificacion(clienteDesencolado, "Posible tiquete para viaje a " + viaje.getDestino() + " el " + viaje.getFechaSalidaStr() + " en el bus " + viaje.getBus().getPlaca() ));
            esMismoClienteCola = cliente.equals(clienteDesencolado);
        }
        
        // Guarda informacion en binarios
        scp.saveDataCasetas();
        su.saveDataUsuarios();

        return esMismoClienteCola;
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
    public Cliente buscarClientePorId(int idCliente) throws RuntimeException {
        Usuario usuario = su.buscarUsuarioPorId(idCliente);
        if (!(usuario instanceof Cliente)) {
            throw new RuntimeException("EL CLIENTE NO SE ENCUENTRA CON ESTE ID");
        }
        return (Cliente) usuario;
    }
}
