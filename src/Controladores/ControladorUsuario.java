/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Modelos.Usuarios.AdminFlota;
import Modelos.Usuarios.AdminTerminal;
import Modelos.Usuarios.Cliente;
import Modelos.Usuarios.Usuario;
import Utils.IList;
import Utils.Lista;

/**
 *
 * @author Javier Argoty
 */
public class ControladorUsuario {
    private IList<Usuario> usuarios;
    
    public ControladorUsuario() {
        usuarios = new Lista<>();
        // Agrego uno de terminal, de flota y de cliente por defecto
        usuarios.add(new AdminTerminal("Leo", 1, "123"));
        usuarios.add(new AdminFlota("Javier", 2, "1"));
        usuarios.add(new Cliente("Roa", 3, "1"));
    }
    public void registrarUsuario(Usuario usuario) throws RuntimeException {
        if (usuario.getName().isBlank() || usuario.getPassword().isBlank()) throw new RuntimeException("LOS CAMPOS NO PUEDEN SER VACIOS");
        if (buscarUsuarioPorId(usuario.getNroId()) != null) throw new RuntimeException("ESTE USUARIO YA EXISTE CON ESTE ID");
        
        usuarios.add(usuario);
    }
    
    public Usuario buscarUsuarioPorId(int id) {
        for (int i = 0; i < usuarios.size(); i++) {
            if(usuarios.get(i).getNroId() == id) {
                return usuarios.get(i);
            }
        }
        return null;
    }
    
    public Usuario loginUsuario(int id, String password) {
        for (int i = 0; i < usuarios.size(); i++) {
            if(usuarios.get(i).getNroId() == id && usuarios.get(i).getPassword().equals(password)) {
                return usuarios.get(i);
            }
        }
        return null;
    }
}
