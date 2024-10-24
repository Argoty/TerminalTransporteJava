/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

/**
 *
 * @author Javier Argoty
 */
public class Caseta {
    private EmpresaTransporte empresa;
    private boolean disponible;
    private int canonArrendamiento;
    private int plazasEstacionamiento;

    public Caseta() {
        this.empresa = null;
        this.disponible = true; // Por defecto, la caseta está disponible
    }


    public void asignarFlota(EmpresaTransporte empresa, int canonArrendamiento, int plazasEstacionamiento) {
        this.empresa = empresa;
        this.canonArrendamiento = canonArrendamiento;
        this.plazasEstacionamiento = plazasEstacionamiento;
        this.disponible = false; // Al asignar una empresa, la caseta ya no está disponible
    }

    public void liberarCaseta() {
        this.empresa = null;
        this.disponible = true; // Al liberar la caseta, vuelve a estar disponible
    }

    public boolean isDisponible() {
        return disponible;
    }
    
    public int getCanonArrendamiento() {
        return canonArrendamiento;
    }
    public int getPlazasEstacionamiento() {
        return plazasEstacionamiento;
    }

}

