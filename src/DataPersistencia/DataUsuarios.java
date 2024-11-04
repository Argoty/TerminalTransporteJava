/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataPersistencia;

import Modelos.Usuarios.AdminFlota;
import Modelos.Usuarios.AdminTerminal;
import Modelos.Usuarios.Cliente;
import Modelos.Usuarios.Usuario;
import Utils.IList;
import Utils.Lista;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author PC
 */
public class DataUsuarios {

    private String filePath;

    public DataUsuarios(String filePath) {
        this.filePath = filePath;
    }

    public void saveUsuariosToFile(IList<Usuario> usuarios) {
        try (
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(usuarios);
            System.out.println("Usuarios guardados en: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public IList<Usuario> loadUsuariosFromFile() {
        File file = new File(filePath);

        if (!file.exists()) {
            // Esto es de test nomás
            IList<Usuario> usuarios = new Lista<>();
            usuarios.add(new AdminTerminal("Leo", 1, "leo@gmail.com", "+57 3222838161", "123"));
            usuarios.add(new AdminFlota("Javier", 2, "javier@gmail.com", "+57 318 9431874", "1"));
            usuarios.add(new Cliente("Roa", 3, "roa@gmail.com", "+57 340 1852907", "1"));
            
            saveUsuariosToFile(usuarios);
            return usuarios;
        }

        try (
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));) {
            return (IList<Usuario>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new Lista<>();

        }
    }
}
