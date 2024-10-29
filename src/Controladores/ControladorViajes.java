/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Modelos.EmpresaTransporte;

/**
 *
 * @author PC
 */
public class ControladorViajes {
    EmpresaTransporte empresa;

    public ControladorViajes(EmpresaTransporte empresa) {
        this.empresa = empresa;
    }
    public void agregarViaje(String destino, String fSal, String hSal, String fLle, String hLle,
            String placaBus, int vlrUnit) throws RuntimeException{
        empresa.agregarViaje(destino, fSal, hSal, fLle, hLle, placaBus, vlrUnit);
    }
}
