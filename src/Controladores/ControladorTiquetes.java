/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Modelos.EmpresaTransporte;
import Modelos.Usuarios.Cliente;
import Modelos.Viaje;

import Servicios.ServicioCasetasPrincipal;
import Servicios.ServicioTiquetes;
import Servicios.ServicioViajes;
import Servicios.ServicioUsuarios;

import Utils.IList;

/**
 *
 * @author PC
 */
public class ControladorTiquetes {
    private ServicioTiquetes st;
    private ServicioViajes sv;
    private ServicioUsuarios su;
    
    public ControladorTiquetes(int idAdmin) {
        EmpresaTransporte empr = ServicioCasetasPrincipal.getInstance().getCasetaPorAdminID(idAdmin)
                .getEmpresa();
        this.sv = new ServicioViajes(empr);
        this.st = new ServicioTiquetes(empr);
        this.su = ServicioUsuarios.getInstance();
    }
    public IList<Viaje> getViajes() {
        return sv.getViajes();
    }
    
    public IList<Cliente> getClientes() {
        return su.getClientes();
    }
}
