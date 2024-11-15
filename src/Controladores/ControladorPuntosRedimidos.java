/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Modelos.RegistroCompra;
import Modelos.Usuarios.Cliente;
import Servicios.ServicioCasetasPrincipal;
import Servicios.ServicioRegistrosCompras;
import Utils.IList;

/**
 *
 * @author PC
 */
public class ControladorPuntosRedimidos {
    private ServicioRegistrosCompras src;
    private ServicioCasetasPrincipal scp;
    public ControladorPuntosRedimidos(Cliente cliente) {
        this.src = new ServicioRegistrosCompras(cliente);
        this.scp = ServicioCasetasPrincipal.getInstance();
    }
    public IList<RegistroCompra> getRegistroPuntos() {
        return src.getHistorialCompra("puntos");
    }
    public String getNombreEmpresaSegunViaje(int idViaje) {
        return scp.getCasetaPorViajeID(idViaje).getEmpresa().getNombre();
    }
}
