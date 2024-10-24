/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Usuarios;

/**
 *
 * @author Javier Argoty
 */
public abstract class Usuario {
    private String name;
    private int nroId;
    private String password;

    public Usuario(String name, int nroId,String password) {
        this.name = name;
        this.nroId = nroId;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public int getNroId() {
        return nroId;
    }

    public void setNroId(int nroId) {
        this.nroId = nroId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
