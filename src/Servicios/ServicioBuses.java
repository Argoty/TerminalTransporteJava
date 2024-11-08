/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicios;

import Modelos.Bus;
import Modelos.Caseta;
import Modelos.EmpresaTransporte;
import Utils.IList;

/**
 *
 * @author PC
 */
public class ServicioBuses {
    EmpresaTransporte empresa;

    public ServicioBuses(EmpresaTransporte empresa) {
        this.empresa = empresa;
    }
    public void agregarBus(Bus bus, Caseta[][] casetas, int nroPlazasCaseta) throws RuntimeException {
        if (getBuses().size() >= nroPlazasCaseta) {
            throw new RuntimeException("La caseta de esta empresa ya tiene las plazas ocupadas");
        }
        if (esVaciosCamposBus(bus)) {
            throw new RuntimeException("COMPLETA LOS CAMPOS");
        }
        if (buscarBusGlobal(bus.getPlaca(), casetas)) {
            throw new RuntimeException("LA PLACA DE ESTE BUS YA EXISTE");
        }

        getBuses().add(bus);
    }
    public void editarBus(Bus bus) throws RuntimeException {
        if (esVaciosCamposBus(bus)) {
            throw new RuntimeException("COMPLETA LOS CAMPOS");
        }
        Bus aux = buscarBusPorPlaca(bus.getPlaca());
        if (aux == null) {
            throw new RuntimeException("NO EXISTE ESTE BUS");
        }

        aux.setMarca(bus.getMarca());
        aux.setTipo(bus.getTipo());
        aux.setPuestos(bus.getPuestos());
    }
    public void eliminarBus(String placa) throws RuntimeException {
        if (placa.isBlank()) {
            throw new RuntimeException("ESCRIBE UNA PLACA");
        }
        for (int i = 0; i < getBuses().size(); i++) {
            if (getBuses().get(i).getPlaca().equals(placa)) {
                getBuses().remove(i);
                return;
            }
        }
        throw new RuntimeException("NO HAY UN BUS CON ESA PLACA");
    }
    public Bus buscarBusPorPlaca(String placa) {
        for (int i = 0; i < getBuses().size(); i++) {
            if (getBuses().get(i).getPlaca().equals(placa)) {
                return getBuses().get(i);
            }
        }
        return null;
    }
    public IList<Bus> getBuses() {
        return this.empresa.getBuses();
    }
    // METODOS PRIVADOS QUE AYUDAN A LOGICA
    private boolean buscarBusGlobal(String placa, Caseta[][] casetas) {
        for (Caseta[] caseta : casetas) {
            for (Caseta caseta1 : caseta) {
                if (caseta1.getEmpresa() == null) {
                    continue;
                }
                for (int k = 0; k < caseta1.getEmpresa().getBuses().size(); k++) {
                    if (caseta1.getEmpresa().getBuses().get(k).getPlaca().equals(placa)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private boolean esVaciosCamposBus(Bus bus) {
        return bus.getPlaca().isBlank() || bus.getMarca().isBlank() || bus.getTipo().isBlank();
    }
}
