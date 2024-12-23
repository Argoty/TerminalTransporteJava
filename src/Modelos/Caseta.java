/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import java.io.Serializable;

/**
 *
 * @author Javier Argoty
 */
public class Caseta implements Serializable{
    private static int contadorCasetas = 1;
    
    private int nroCaseta;
    private EmpresaTransporte empresa;
    private boolean disponible;
    private int canonArrendamiento;
    private int plazasEstacionamiento;

    public Caseta() {
        this.nroCaseta = contadorCasetas++;
        this.empresa = null;
        this.disponible = true; // Por defecto, la caseta está disponible
    }


    public void asignarFlota(EmpresaTransporte empresa, int canonArrendamiento, 
            int plazasEstacionamiento, Caseta[][] casetas) throws RuntimeException {
        if (empresa.getNombre().isBlank()) throw new RuntimeException("COMPLETA TODOS LOS CAMPOS");
        if (!validarNitEmpresa(casetas, empresa.getNit())) throw new RuntimeException("El nit de esta empresa ya existe");
        
        this.empresa = empresa;
        this.canonArrendamiento = canonArrendamiento;
        this.plazasEstacionamiento = plazasEstacionamiento;
        this.disponible = false; // Al asignar una empresa, la caseta ya no está disponible
    }
    // valida a privado despues
    public boolean validarNitEmpresa(Caseta[][] casetas, int nit) {
        for (Caseta[] caseta : casetas) {
            for (Caseta caseta1 : caseta) {
                if (caseta1.getEmpresa() != null && caseta1.getNroCaseta() != this.getNroCaseta()
                        && caseta1.getEmpresa().getNit() == nit) {
                    return false;
                }
            }
        }
        return true;
    }
    

    public void liberarCaseta() {
        this.empresa = null;
        this.canonArrendamiento = 0;
        this.plazasEstacionamiento = 0;
        this.disponible = true; // Al liberar la caseta, vuelve a estar disponible
    }

    public boolean isDisponible() {
        return disponible;
    }
    public int getNroCaseta() {
        return nroCaseta;
    }
    
    public int getCanonArrendamiento() {
        return canonArrendamiento;
    }
    public int getPlazasEstacionamiento() {
        return plazasEstacionamiento;
    }
    
    public EmpresaTransporte getEmpresa() {
        return empresa;
    }

}

