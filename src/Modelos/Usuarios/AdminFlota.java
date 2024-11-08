/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Usuarios;

import java.io.Serializable;

/**
 *
 * @author Javier Argoty
 */
public class AdminFlota extends Usuario implements Serializable{
    private int sueldo;

    public AdminFlota(String name,int nroId, String email, String tel,String password, int sueldo) {
        super(name,nroId, email, tel,password);
        this.sueldo = sueldo;
    }
    public int getSueldo() {
        return this.sueldo;
    }
    public void setSueldo(int sueldo) {
        this.sueldo = sueldo;
    }
}
