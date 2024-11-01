/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Modelos.Usuarios.Usuario;
import Servicios.ServicioUsuarios;

/**
 *
 * @author PC
 */
public class ControladorRegistro {
    private ServicioUsuarios servicioUsuarios;

    public ControladorRegistro() {
        servicioUsuarios = ServicioUsuarios.getInstance(); // Accede a la instancia única de ServicioUsuarios
    }

    public void registrarUsuario(Usuario usuario) {
        servicioUsuarios.registrarUsuario(usuario);
    }
}
