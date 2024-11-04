/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicios;

import Modelos.Usuarios.AdminFlota;
import Modelos.Usuarios.AdminTerminal;
import Modelos.Usuarios.Cliente;
import Modelos.Usuarios.Usuario;

import DataPersistencia.DataUsuarios;

import Utils.IList;
import Utils.Lista;

/**
 *
 * @author PC
 */
public class ServicioUsuarios {

    private static ServicioUsuarios instancia; // Instancia única de ServicioUsuarios
    private IList<Usuario> usuarios;
    private DataUsuarios dataUsuarios;

    // Constructor privado para evitar la creación externa
    private ServicioUsuarios() {
        dataUsuarios = new DataUsuarios("usuariosData.bin");
        usuarios = dataUsuarios.loadUsuariosFromFile();
        // Agrego uno de terminal, de flota y de cliente por defecto
//        usuarios.add(new AdminTerminal("Leo", 1, "leo@gmail.com", "+57 3222838161", "123"));
//        usuarios.add(new AdminFlota("Javier", 2, "javier@gmail.com", "+57 318 9431874", "1"));
//        usuarios.add(new Cliente("Roa", 3, "roa@gmail.com", "+57 340 1852907", "1"));
//        saveDataUsuarios();
    }

    public void saveDataUsuarios() {
        dataUsuarios.saveUsuariosToFile(usuarios);
    }

    // Método para obtener la instancia única
    public static ServicioUsuarios getInstance() {
        if (instancia == null) {
            instancia = new ServicioUsuarios();
        }
        return instancia;
    }

    public IList<Cliente> getClientes() {
        IList<Cliente> clientes = new Lista<>();

        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i) instanceof Cliente cliente) {
                clientes.add(cliente);
            }
        }

        return clientes;
    }

    public void registrarUsuario(Usuario usuario) throws RuntimeException {
        if (!(usuario instanceof AdminFlota)) {
            validarUsuarioInfo(usuario);
        }
        usuarios.add(usuario);
        saveDataUsuarios();
    }

    public void actualizarUsuario(Usuario usuario) throws RuntimeException {
        if (usuario.getName().isBlank()
                || usuario.getEmail().isBlank() || usuario.getTelefono().isBlank()) {
            throw new RuntimeException("LOS CAMPOS NO PUEDEN SER VACIOS");
        }

        Usuario aux = buscarUsuarioPorId(usuario.getNroId());
        aux.setName(usuario.getName());
        aux.setEmail(usuario.getEmail());
        aux.setTelefono(usuario.getTelefono());
        
        saveDataUsuarios();
    }

    public Usuario buscarUsuarioPorId(int id) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getNroId() == id) {
                return usuarios.get(i);
            }
        }
        return null;
    }

    public Usuario loginUsuario(int id, String password) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getNroId() == id && usuarios.get(i).getPassword().equals(password)) {
                return usuarios.get(i);
            }
        }
        return null;
    }

    public void validarUsuarioInfo(Usuario usuario) throws RuntimeException {
        if (esCamposVacios(usuario)) {
            throw new RuntimeException("LOS CAMPOS NO PUEDEN SER VACIOS");
        }
        if (buscarUsuarioPorId(usuario.getNroId()) != null) {
            throw new RuntimeException("ESTE USUARIO YA EXISTE CON ESTE ID");
        }
    }

    private boolean esCamposVacios(Usuario usuario) {
        return usuario.getName().isBlank() || usuario.getPassword().isBlank()
                || usuario.getEmail().isBlank() || usuario.getTelefono().isBlank();
    }
}
