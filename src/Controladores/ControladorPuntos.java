/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Modelos.RegistroPuntos;
import Modelos.Usuarios.Cliente;
import Utils.IList;

/**
 *
 * @author PC
 */
public class ControladorPuntos {
    private Cliente cliente;
    public ControladorPuntos(Cliente cliente) {
        this.cliente = cliente;
    }
    public IList<RegistroPuntos> getRegistroPuntos() {
        return this.cliente.getHistorialPuntos();
    }
    
}
