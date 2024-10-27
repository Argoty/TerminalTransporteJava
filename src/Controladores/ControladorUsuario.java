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
        usuarios.add(new AdminTerminal("Leo", 1,"leo@", "+57 322", "123"));
        usuarios.add(new AdminFlota("Javier", 2, "javier@", "+57","1"));
        
        usuarios.add(new Cliente("Roa", 3,"roa@","+57 340", "1"));
    }
    public void registrarUsuario(Usuario usuario) throws RuntimeException {
        if (!(usuario instanceof AdminFlota)) validarUsuarioInfo(usuario);
        
        usuarios.add(usuario);
    }
    public void actualizarUsuario(Usuario usuario) throws RuntimeException {
        
            if (usuario.getName().isBlank() || 
                usuario.getEmail().isBlank() || usuario.getTelefono().isBlank()) throw new RuntimeException("LOS CAMPOS NO PUEDEN SER VACIOS");
        
        Usuario aux = buscarUsuarioPorId(usuario.getNroId());
        //if (aux == null) throw new RuntimeException("ESTE USUARIO NO EXISTE");
        
        aux.setName(usuario.getName());
        aux.setEmail(usuario.getEmail());
        aux.setTelefono(usuario.getTelefono());
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
    
    public void validarUsuarioInfo(Usuario usuario) throws RuntimeException{
        if (esCamposVacios(usuario)) throw new RuntimeException("LOS CAMPOS NO PUEDEN SER VACIOS");
        if (buscarUsuarioPorId(usuario.getNroId()) != null) throw new RuntimeException("ESTE USUARIO YA EXISTE CON ESTE ID");
    }
    
    private boolean esCamposVacios(Usuario usuario) {
        return usuario.getName().isBlank() || usuario.getPassword().isBlank() || 
                usuario.getEmail().isBlank() || usuario.getTelefono().isBlank();
    }
}
