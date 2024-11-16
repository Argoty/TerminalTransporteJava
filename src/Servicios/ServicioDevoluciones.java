/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicios;

import Modelos.Devolucion;
import Modelos.MovimientoTransaccion;
import Modelos.Tiquete;
import Modelos.Usuarios.Cliente;
import Modelos.Viaje;
import Utils.IList;
import java.time.LocalDateTime;


/**
 *
 * @author PC
 */
public class ServicioDevoluciones {

    public ServicioDevoluciones() {
    }
    
    public void crearDevolucion(Viaje viaje, Cliente cliente, MovimientoTransaccion movimiento, int puntos) throws RuntimeException {
        Devolucion devolucion = new Devolucion(movimiento, puntos);
        
        viaje.getDevoluciones().add(devolucion);
        cliente.getDevoluciones().add(devolucion);
        
        viaje.getTiquetes().remove(movimiento.getTiquete());
        cliente.getTiquetes().remove(movimiento.getTiquete()); 
    }

    public IList<Devolucion> getDevolucionesVia(Viaje viaje) {
        return viaje.getDevoluciones();
    }
    public IList<Devolucion> getDevolucionesCli(Cliente cliente) {
        return cliente.getDevoluciones();
    }
}
