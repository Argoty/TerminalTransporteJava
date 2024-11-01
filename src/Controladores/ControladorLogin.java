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
public class ControladorLogin {
    private ServicioUsuarios servicioUsuarios;

    public ControladorLogin() {
        servicioUsuarios = ServicioUsuarios.getInstance(); // Accede a la instancia única de ServicioUsuarios
    }

    public Usuario login(int id, String password) {
        return servicioUsuarios.loginUsuario(id, password);
    }
}
