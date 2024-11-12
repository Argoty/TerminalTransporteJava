/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Modelos.RegistroPuntos;
import Modelos.Usuarios.Cliente;
import Servicios.ServicioCasetasPrincipal;
import Servicios.ServicioPuntos;
import Utils.IList;

/**
 *
 * @author PC
 */
public class ControladorPuntos {
    private ServicioPuntos sp;
    private ServicioCasetasPrincipal scp;
    public ControladorPuntos(Cliente cliente) {
        this.sp = new ServicioPuntos(cliente);
        this.scp = ServicioCasetasPrincipal.getInstance();
    }
    public IList<RegistroPuntos> getRegistroPuntos() {
        return sp.getPuntosJuntos();
    }
    public String getNombreEmpresaSegunViaje(int idViaje) {
        return scp.getCasetaPorViajeID(idViaje).getEmpresa().getNombre();
    }
    
}
