/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicios;

import Modelos.Tiquete;
import Modelos.Usuarios.Cliente;
import Modelos.Viaje;
import Utils.IList;
import java.time.LocalDateTime;

/**
 *
 * @author PC
 */
public class ServicioTiquetes {

    public ServicioTiquetes() {
    }
    
    public void crearTiquete(Viaje viaje, Cliente cliente, int cantidad) throws RuntimeException {
        if (viaje == null) throw new RuntimeException("Selecciona bien el viaje");
        if (viaje.getFechaSalida().isBefore(LocalDateTime.now())) throw new RuntimeException("Este viaje ya ocurrió");

        int puestosDesocupados = viaje.getBus().getPuestos() - viaje.getTiquetes().size();
        if (cantidad > puestosDesocupados) {
            throw new RuntimeException("Lo siento, este tiquete tiene solo " + puestosDesocupados + " puestos disponibles");
        }

        int totalInvertido = viaje.getVlrUnit() * cantidad;

        for (int i = 0; i < cantidad; i++) {
            Tiquete tiquete = new Tiquete(viaje, cliente);
            viaje.getTiquetes().add(tiquete);
            cliente.getTiquetes().add(tiquete);
        }

        cliente.agregarDineroInvertido(totalInvertido);

        // Actualizamos los puntos con el total invertido y el último tiquete creado
        cliente.actualizarPuntos(totalInvertido, cliente.getTiquetes().get(cliente.getTiquetes().size() - 1));
    }
    
    public IList<Tiquete> getTiquetes(Viaje viaje) {
        return viaje.getTiquetes();
    }
}
