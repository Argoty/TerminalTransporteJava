/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Modelos.Usuarios.Cliente;

/**
 *
 * @author PC
 */
public class ControladorInfoCliente {
    private Cliente cliente;
 
    public ControladorInfoCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public String getName() {
        return cliente.getName();
    }
    public int getNroId() {
        return cliente.getNroId();
    }
    public String getEmail() {
        return cliente.getEmail();
    }
    public String getTelefono() {
        return cliente.getTelefono();
    }
    public int getPuntosAcumulados() {
        return cliente.getPuntosAcumulados();
    }
    public int getDineroInvertido() {
        return cliente.getDineroInvertido();
    }
}
