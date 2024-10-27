/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import Modelos.Usuarios.AdminFlota;
import Utils.IList;
import Utils.Lista;

/**
 *
 * @author Javier Argoty
 */
public class EmpresaTransporte {

    private int nit;
    private String nombre;
    private AdminFlota admin;

    private IList<Bus> buses;
    private IList<Viaje> viajes;

    public EmpresaTransporte(int nit, String nombre, AdminFlota admin) {
        this.nit = nit;
        this.nombre = nombre;
        this.admin = admin;

        this.buses = new Lista<>();
        this.viajes = new Lista<>();
    }

    public int getNit() {
        return nit;
    }

    public void setNit(int nit) {
        this.nit = nit;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public AdminFlota getAdmin() {
        return admin;
    }

    // --------------------- METODOS PARA GESTIONAR BUSES -------------------------------
    public IList<Bus> getBuses() {
        return buses;
    }

    public void agregarBus(Bus bus, Caseta[][] casetas,int nroPlazasCaseta) throws RuntimeException {
        if (buses.size() >= nroPlazasCaseta) throw new RuntimeException("La caseta de esta empresa ya tiene las plazas ocupadas");
        if (esVaciosCamposBus(bus)) {
            throw new RuntimeException("COMPLETA LOS CAMPOS");
        }
        if (buscarBusGlobal(bus.getPlaca(), casetas)) {
            throw new RuntimeException("LA PLACA DE ESTE BUS YA EXISTE");
        }

        buses.add(bus);
    }

    private boolean buscarBusGlobal(String placa, Caseta[][] casetas) {
        for (Caseta[] caseta : casetas) {
            for (Caseta caseta1 : caseta) {
                if (caseta1.getEmpresa() == null) continue;
                for (int k = 0; k < caseta1.getEmpresa().getBuses().size(); k++) {
                    if (caseta1.getEmpresa().getBuses().get(k).getPlaca().equals(placa)) {
                        return true;
                    }
                }
            }
        }
        return false;
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
        aux.setPuestosDisponibles(bus.getPuestosDisponibles());
    }

    public void eliminarBus(String placa) throws RuntimeException {
        if (placa.isBlank()) {
            throw new RuntimeException("ESCRIBE UNA PLACA");
        }
        for (int i = 0; i < buses.size(); i++) {
            if (buses.get(i).getPlaca().equals(placa)) {
                buses.remove(i);
                return;
            }
        }
        throw new RuntimeException("NO HAY UN BUS CON ESA PLACA");
    }

    private boolean esVaciosCamposBus(Bus bus) {
        return bus.getPlaca().isBlank() || bus.getMarca().isBlank() || bus.getTipo().isBlank();
    }

    public Bus buscarBusPorPlaca(String placa) {
        for (int i = 0; i < buses.size(); i++) {
            if (buses.get(i).getPlaca().equals(placa)) {
                return buses.get(i);
            }
        }
        return null;
    }

}
