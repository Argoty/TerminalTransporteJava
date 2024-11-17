/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import Modelos.Usuarios.AdminFlota;
import Utils.IList;
import Utils.Lista;
import java.io.Serializable;

/**
 *
 * @author Javier Argoty
 */
public class EmpresaTransporte implements Serializable {

    private int nit;
    private String nombre;
    private AdminFlota admin;

    private IList<Bus> buses;
    private IList<Viaje> viajes;
    private IList<Devolucion> devoluciones;

    public EmpresaTransporte(int nit, String nombre, AdminFlota admin) {
        this.nit = nit;
        this.nombre = nombre;
        this.admin = admin;

        this.buses = new Lista<>();
        this.viajes = new Lista<>();
        this.devoluciones = new Lista<>();
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

    public IList<Bus> getBuses() {
        return buses;
    }

    public IList<Viaje> getViajes() {
        return this.viajes;
    }
    public IList<Devolucion> getDevoluciones() {
        return this.devoluciones;
    }
}
