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
    
    public LocalDateTime crearTiquete(Viaje viaje, Cliente cliente, int cantidad) throws RuntimeException {
        if (viaje == null) throw new RuntimeException("Selecciona bien el viaje");
        if (viaje.getFechaSalida().isBefore(LocalDateTime.now())) throw new RuntimeException("Este viaje ya ocurrió");

        int puestosDesocupados = viaje.getBus().getPuestos() - viaje.getTiquetes().size();
        if (cantidad > puestosDesocupados) {
            throw new RuntimeException("Lo siento, este tiquete tiene solo " + puestosDesocupados + " puestos disponibles");
        }
        // Agrega la cantidad de tiquetes que se pidieron con un for y se les pone la misma
        // fecha, luego se retorna para usarla como "id" de los registrosPuntos
        LocalDateTime fechaVenta = LocalDateTime.now();
        for (int i = 0; i < cantidad; i++) {
            Tiquete tiquete = new Tiquete(viaje, cliente, fechaVenta);
            viaje.getTiquetes().add(tiquete);
            cliente.getTiquetes().add(tiquete);
        }
        return fechaVenta;
    }
    
    public IList<Tiquete> getTiquetes(Viaje viaje) {
        return viaje.getTiquetes();
    }
    
    public Tiquete obtenerTiquete(Viaje viaje, int idTiquete) {
        for (int i=0; i< viaje.getTiquetes().size();i++) {
            if (viaje.getTiquetes().get(i).getId() == idTiquete) {
                return viaje.getTiquetes().get(i);
            }
        }
        return null;
    }
    
}
