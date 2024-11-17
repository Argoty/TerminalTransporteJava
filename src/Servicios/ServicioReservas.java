/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicios;

import Modelos.Reserva;
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
    public void crearReserva(Cliente cliente, Viaje viaje, int cantidad, int metodoPago) {
        if (viaje == null) throw new RuntimeException("Selecciona bien el viaje");
        if (viaje.getFechaSalida().isBefore(LocalDateTime.now())) throw new RuntimeException("Este viaje ya ocurrió");

        int puestosDesocupados = viaje.getBus().getPuestos() - viaje.getTiquetes().size();
        if (cantidad > puestosDesocupados) {
            throw new RuntimeException("Lo siento, este viaje tiene solo " + puestosDesocupados + " puestos disponibles");
        }
        // Validaciones por si el metodo de pago es por puntos
        if (metodoPago == 1) {
            if (viaje.getVlrUnit() > 30000)throw new RuntimeException("Solo se pueden redimir 90 puntos por un tiquete de máximo 30k");
            if (cliente.getPuntosAcumulados() < 90 * cantidad) throw new RuntimeException("El cliente no tiene suficientes puntos para canjear");
        }
        // Agrega la cantidad de tiquetes que se pidieron con un for y se les pone la misma
        // fecha, luego se retorna para usarla como "id" de los registrosPuntos
        for (int i = 0; i < cantidad; i++) {
            Reserva reserva = new Reserva(cliente, viaje, metodoPago == 0 ? "efectivo" : "puntos");
            viaje.getReservas().add(reserva);
            cliente.getReservas().add(reserva);
        }
    }
    public void cancelarReserva(Cliente cliente, Viaje viaje, Reserva reserva) {
        cliente.getReservas().remove(reserva);
        viaje.getReservas().remove(reserva);
    }
    public void hacerEfectiva(Reserva reserva) {
        if (reserva.isEfectiva()) throw new RuntimeException("La Reserva ya está efectiva");
        reserva.hacerEfectiva();
        
    }
    public Reserva getReservaPorId(int idReserva, Viaje viaje) {
        for (int i=0; i < getReservasViaje(viaje).size(); i++) {
            if (getReservasViaje(viaje).get(i).getId() == idReserva) {
                return getReservasViaje(viaje).get(i);
            }
        }
        return null;
    }
    
}
