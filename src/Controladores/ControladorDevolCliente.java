/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;
import Modelos.Devolucion;
import Modelos.Viaje;
import Servicios.ServicioDevoluciones;
import Utils.IList;
/**
 *
 * @author PC
 */
public class ControladorDevolCliente {
    private ServicioDevoluciones sd;
    public ControladorDevolCliente() {
        
    }
    public IList<Devolucion> getDevoluciones(Viaje viaje) {
        return sd.getDevoluciones(viaje);
    }
    
    
}
