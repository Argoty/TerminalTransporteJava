/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Modelos.Notificacion;
import Modelos.Usuarios.Cliente;
import Utils.IList;

/**
 *
 * @author PC
 */
public class ControladorNotificaciones {
    private Cliente cliente;
 
    public ControladorNotificaciones(Cliente cliente) {
        this.cliente = cliente;
    }
    public IList<Notificacion> getNotificaciones() {
        return cliente.getNotificaciones();
    }
}
