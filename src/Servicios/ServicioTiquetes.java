/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicios;

import Modelos.EmpresaTransporte;
import Modelos.Usuarios.Cliente;
import Modelos.Viaje;

/**
 *
 * @author PC
 */
public class ServicioTiquetes {
    EmpresaTransporte empresa;

    public ServicioTiquetes(EmpresaTransporte empresa) {
        this.empresa = empresa;
    }
    
    public void crearTiquete(Viaje viaje, Cliente cliente, int cantidad) {
        
    }
}
