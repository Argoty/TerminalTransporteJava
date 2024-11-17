/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicios;

import Modelos.Devolucion;
import Modelos.EmpresaTransporte;
import Modelos.RegistroPuntos;
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
    
    public void crearDevolucion(EmpresaTransporte empresa, Viaje viaje, Cliente cliente, RegistroPuntos registro, int puntos) throws RuntimeException {
        Devolucion devolucion = new Devolucion(registro, puntos);
        
        empresa.getDevoluciones().add(devolucion);
        cliente.getDevoluciones().add(devolucion);
        
        viaje.getTiquetes().remove(registro.getTiquete());
        cliente.getTiquetes().remove(registro.getTiquete()); 
    }

    public IList<Devolucion> getDevolucionesEmpr(EmpresaTransporte empresa) {
        return empresa.getDevoluciones();
    }
    public IList<Devolucion> getDevolucionesCli(Cliente cliente) {
        return cliente.getDevoluciones();
    }
}
