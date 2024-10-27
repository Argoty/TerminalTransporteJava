/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Modelos.Bus;
import Modelos.Caseta;
import Modelos.EmpresaTransporte;
import Utils.IList;

/**
 *
 * @author PC
 */
public class ControladorBuses {
    EmpresaTransporte empresa;

    public ControladorBuses(EmpresaTransporte empresa) {
        this.empresa = empresa;
    }
    public void agregarBus(Bus bus, Caseta[][] casetas, int nroPlazasCaseta) throws RuntimeException{
        empresa.agregarBus(bus, casetas, nroPlazasCaseta);
    }
    public void editarBus(Bus bus) throws RuntimeException{
        empresa.editarBus(bus);
    }
    public void eliminarBus(String placa) throws RuntimeException{
        empresa.eliminarBus(placa);
    }
    public Bus buscarBusPorPlaca(String placa) {
        return empresa.buscarBusPorPlaca(placa);
    }
    public IList<Bus> getBuses() {
        return empresa.getBuses();
    }
}
