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
    private int salario;

    public AdminFlota(String name,int nroId, String email, String tel,String password) {
        super(name,nroId, email, tel,password);

    }


    // Métodos adicionales para gestionar buses y viajes
}
