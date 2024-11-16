/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Modelos.MovimientoTransaccion;
import Modelos.Usuarios.Cliente;
import Servicios.ServicioCasetasPrincipal;
import Servicios.ServicioMovimientos;
import Utils.IList;

/**
 *
 * @author PC
 */
public class ControladorPuntos {
    private ServicioMovimientos src;
    private ServicioCasetasPrincipal scp;
    public ControladorPuntos(Cliente cliente) {
        this.src = new ServicioMovimientos(cliente);
        this.scp = ServicioCasetasPrincipal.getInstance();
    }
    public IList<MovimientoTransaccion> getRegistroPuntos(String criterio) {
        return src.getHistorialCompra(criterio);
    }
    public String getNombreEmpresaSegunViaje(int idViaje) {
        return scp.getCasetaPorViajeID(idViaje).getEmpresa().getNombre();
    }
    
}
