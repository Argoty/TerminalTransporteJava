/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicios;

import Modelos.Notificacion;
import Modelos.Reserva;
import Modelos.Tiquete;
import Modelos.Usuarios.Cliente;
import Modelos.Viaje;
import Utils.IList;
import java.time.LocalDateTime;

/**
 *
 * @author PC
 */
public class ServicioReservas {
    public ServicioReservas() {
        
    }
    public IList<Reserva> getReservasCli(Cliente cliente) {
        return cliente.getReservas();
    }
    public IList<Reserva> getReservasViaje(Viaje viaje) {
        return viaje.getReservas();
    }
    public void crearReserva(Cliente cliente, Viaje viaje, int cantidad) {
        if (viaje == null) throw new RuntimeException("Selecciona bien el viaje");
        if (viaje.getFechaSalida().isBefore(LocalDateTime.now())) throw new RuntimeException("Este viaje ya ocurrió");

        if (cantidad > viaje.getPuestosDesocupados()) {
            throw new RuntimeException("Lo siento, este viaje tiene solo " + viaje.getPuestosDesocupados() + " puestos disponibles");
        }
        
        // Agrega la cantidad de tiquetes que se pidieron con un for y se les pone la misma
        // fecha, luego se retorna para usarla como "id" de los registrosPuntos
        for (int i = 0; i < cantidad; i++) {
            Reserva reserva = new Reserva(cliente, viaje);
            viaje.getReservas().add(reserva);
            cliente.getReservas().add(reserva);
        }
    }
    public void cancelarReserva(Cliente cliente, Viaje viaje, Reserva reserva) {
        boolean reservaEliminadoViaje = eliminarReservaDeLista(viaje.getReservas(), reserva.getId());
        boolean reservaEliminadoCliente = eliminarReservaDeLista(cliente.getReservas(), reserva.getId());

        if (!reservaEliminadoCliente || !reservaEliminadoViaje) {
            throw new RuntimeException("No se pudo eliminar el tiquete de las listas correspondientes.");
        }
    }
    private boolean eliminarReservaDeLista(IList<Reserva> lista, int idReserva) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == idReserva) {
                lista.remove(i); // Usa el método remove de tu lista personalizada
                return true;
            }
        }
        return false; // No se encontró la Reserva
    }

    public void hacerEfectiva(Reserva reserva) throws RuntimeException{
        reserva.hacerEfectiva();
    }
    public Reserva getReservaPorIdViaje(int idReserva, Viaje viaje) {
        for (int i=0; i < getReservasViaje(viaje).size(); i++) {
            if (getReservasViaje(viaje).get(i).getId() == idReserva) {
                return getReservasViaje(viaje).get(i);
            }
        }
        return null;
    }
    public Reserva getReservaPorIdCliente(int idReserva, Cliente cliente) {
        for (int i=0; i < getReservasCli(cliente).size(); i++) {
            if (getReservasCli(cliente).get(i).getId() == idReserva) {
                return getReservasCli(cliente).get(i);
            }
        }
        return null;
    }
    
}
