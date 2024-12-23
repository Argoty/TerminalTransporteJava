/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;
import Modelos.Devolucion;
import Modelos.Usuarios.Cliente;
import Servicios.ServicioCasetasPrincipal;
import Servicios.ServicioDevoluciones;
import Utils.IList;
/**
 *
 * @author PC
 */
public class ControladorDevolCliente {
    private ServicioDevoluciones sd;
    private Cliente cliente;
    private ServicioCasetasPrincipal scp;
    public ControladorDevolCliente(Cliente cliente) {
        this.cliente = cliente;
        this.scp = ServicioCasetasPrincipal.getInstance();
        this.sd = new ServicioDevoluciones();
    }
    public IList<Devolucion> getDevoluciones() {
        return sd.getDevolucionesCli(cliente);
    }
    public String getNombreEmpresaSegunViaje(int idViaje) {
        return scp.getCasetaPorViajeID(idViaje).getEmpresa().getNombre();
    }
    
    
}
