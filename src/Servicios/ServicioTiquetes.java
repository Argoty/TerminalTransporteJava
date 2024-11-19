/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicios;

import Modelos.Tiquete;
import Modelos.Usuarios.Cliente;
import Modelos.Viaje;
import Utils.IList;
import Utils.IQueue;
import Utils.Lista;
import java.time.LocalDateTime;

/**
 *
 * @author PC
 */
public class ServicioTiquetes {

    public ServicioTiquetes() {
    }

    public IList<Tiquete> crearTiquete(Viaje viaje, Cliente cliente, int cantidad, int metodoPago) throws RuntimeException {
        if (viaje == null) {
            throw new RuntimeException("Selecciona bien el viaje");
        }
        if (viaje.getFechaSalida().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Este viaje ya ocurrió");
        }

        // Si esta lleno lo deja en cola
        if (viaje.getPuestosDesocupados() == 0) {
            for (int i = 0; i < cantidad; i++) {
                viaje.getColaEspera().enqueue(cliente);
            }
            return null;
        }

        if (cantidad > viaje.getPuestosDesocupados()) {
            throw new RuntimeException("Lo siento, este viaje tiene solo " + viaje.getPuestosDesocupados() + " puestos disponibles");
        }

        // Validaciones por si el metodo de pago es por puntos
        if (metodoPago == 1) {
            if (viaje.getVlrUnit() > 30000) {
                throw new RuntimeException("Solo se pueden redimir 90 puntos por un tiquete de máximo 30k");
            }
            if (cliente.getPuntosAcumulados() < 90 * cantidad) {
                throw new RuntimeException("El cliente no tiene suficientes puntos para canjear");
            }
        }

        LocalDateTime fechaVenta = LocalDateTime.now();
        IList<Tiquete> tiquetesVentas = new Lista<>();
        for (int i = 0; i < cantidad; i++) {
            Tiquete tiquete = new Tiquete(viaje, cliente, fechaVenta, metodoPago == 0 ? "efectivo" : "puntos");
            viaje.getTiquetes().add(tiquete);
            cliente.getTiquetes().add(tiquete);

            tiquetesVentas.add(tiquete);
        }
        return tiquetesVentas;
    }

    public IList<Tiquete> getTiquetes(Viaje viaje) {
        return viaje.getTiquetes();
    }

    public IQueue<Cliente> getColaEspera(Viaje viaje) {
        return viaje.getColaEspera();
    }

    public Tiquete obtenerTiquete(Viaje viaje, int idTiquete) {
        for (int i = 0; i < viaje.getTiquetes().size(); i++) {
            if (viaje.getTiquetes().get(i).getId() == idTiquete) {
                return viaje.getTiquetes().get(i);
            }
        }
        return null;
    }

}
