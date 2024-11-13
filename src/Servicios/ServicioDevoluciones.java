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
    
    public void crearDevolucion(Viaje viaje, Tiquete tiquete) throws RuntimeException {
        viaje.getTiquetes().remove(tiquete);
        
        Cliente cliente = tiquete.getCliente();
        cliente.getTiquetes().remove(tiquete);
        
        Devolucion devolucion = new Devolucion(tiquete);
        viaje.getDevoluciones().add(devolucion);
        cliente.getDevoluciones().add(devolucion);
    }

    
    public IList<Devolucion> getDevoluciones(Viaje viaje) {
        return viaje.getDevoluciones();
    }
}
