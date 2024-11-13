/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;
import Modelos.Devolucion;
import Modelos.Usuarios.Cliente;
import Servicios.ServicioDevoluciones;
import Utils.IList;
/**
 *
 * @author PC
 */
public class ControladorDevolCliente {
    private ServicioDevoluciones sd;
    private Cliente cliente;
    public ControladorDevolCliente(Cliente cliente) {
        this.cliente = cliente;
        this.sd = new ServicioDevoluciones();
    }
    public IList<Devolucion> getDevoluciones() {
        return sd.getDevolucionesCli(cliente);
    }
    
    
}
