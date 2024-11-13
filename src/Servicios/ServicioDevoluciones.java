/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicios;

import Modelos.Devolucion;
import Modelos.Tiquete;
import Modelos.Usuarios.Cliente;
import Modelos.Viaje;
import Utils.IList;


/**
 *
 * @author PC
 */
public class ServicioDevoluciones {

    public ServicioDevoluciones() {
    }
    
    public void crearDevolucion(Viaje viaje, Tiquete tiquete, int resultadoPuntos) throws RuntimeException {
        Cliente cliente = tiquete.getCliente();
        Devolucion devolucion = new Devolucion(tiquete, -resultadoPuntos);
        
        viaje.getDevoluciones().add(devolucion);
        cliente.getDevoluciones().add(devolucion);
        
        viaje.getTiquetes().remove(tiquete);
        cliente.getTiquetes().remove(tiquete); 
    }

    public IList<Devolucion> getDevolucionesVia(Viaje viaje) {
        return viaje.getDevoluciones();
    }
    public IList<Devolucion> getDevolucionesCli(Cliente cliente) {
        return cliente.getDevoluciones();
    }
}
