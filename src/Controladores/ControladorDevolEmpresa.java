/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Modelos.Devolucion;
import Modelos.EmpresaTransporte;
import Servicios.ServicioCasetasPrincipal;
import Servicios.ServicioDevoluciones;
import Servicios.ServicioViajes;
import Utils.IList;

/**
 *
 * @author PC
 */
public class ControladorDevolEmpresa {
    private EmpresaTransporte empresa;
    private ServicioViajes sv;
    private ServicioDevoluciones sd;

    public ControladorDevolEmpresa(int idAdmin) {
        this.empresa = ServicioCasetasPrincipal.getInstance().getCasetaPorAdminID(idAdmin)
                .getEmpresa();
        this.sv = new ServicioViajes(empresa);
        this.sd = new ServicioDevoluciones();
    }

    public IList<Devolucion> getDevoluciones() {
        return sd.getDevolucionesEmpr(empresa);
    }
}
