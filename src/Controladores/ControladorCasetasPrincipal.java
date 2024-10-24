/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Modelos.Caseta;
import Modelos.EmpresaTransporte;
import Modelos.Usuarios.AdminFlota;

/**
 *
 * @author PC
 */
public class ControladorCasetasPrincipal {
    Caseta[][] casetas;

    public ControladorCasetasPrincipal() {
        this.casetas = new Caseta[4][];
        configurarMatrizCasetas(5, this.casetas);
        
        initCasetas();
    }
    
    private void configurarMatrizCasetas(int columnas, Caseta[][] casetas) {
        int filas = casetas.length;
        for (int i = 0; i < filas; i++) {
            if (i == 0) {
                // La primera fila tiene todas sus columnas
                 casetas[i] = new Caseta[columnas];
            } else {
                // El resto de las filas tienen la mitad de las columnas
                casetas[i] = new Caseta[columnas /2];
            }
        }
    }
    
    private void initCasetas(){
        for (int i = 0; i < casetas.length; i++) {
            for (int j = 0; j < casetas[i].length; j++) {
                casetas[i][j] = new Caseta();
                
                // Asigno un admin flota por defecto con su empresa
                if (i == 0 && j ==0) {
                    casetas[i][j].asignarFlota(new EmpresaTransporte(1, "Bolivariano", new AdminFlota("Javier", 2, "1")), 900000, 5);
                }
            }
        }
    }

    public Caseta getCaseta(int fila,int columna){
        return casetas[fila][columna];
    }
    
    public Caseta[][] getCasetas(){
        return casetas;
    }
}
