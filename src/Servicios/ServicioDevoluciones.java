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
    
    public void crearDevolucion(Viaje viaje, Cliente cliente, int idTiquete) throws RuntimeException {
        Tiquete tiqueteADevolver = null;
        for (int i=0; i< viaje.getTiquetes().size();i++) {
            if (viaje.getTiquetes().get(i).getId() == idTiquete) {
                tiqueteADevolver = viaje.getTiquetes().get(i);
                viaje.getTiquetes().remove(i);
            }
        }
        
        for (int i=0; i< cliente.getTiquetes().size();i++) {
            if (cliente.getTiquetes().get(i).getId() == idTiquete) {
                cliente.getTiquetes().remove(i);
            }
        }
        
        Devolucion devolucion = new Devolucion(tiqueteADevolver);
        viaje.getDevoluciones().add(devolucion);
        cliente.getDevoluciones().add(devolucion);
   
    }

    
    public IList<Devolucion> getDevoluciones(Viaje viaje) {
        return viaje.getDevoluciones();
    }
}
