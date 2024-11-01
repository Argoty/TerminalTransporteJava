/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicios;

import Modelos.Caseta;
import Modelos.EmpresaTransporte;
import Modelos.Usuarios.AdminFlota;

/**
 *
 * @author PC
 */
public class ServicioCasetasPrincipal {
    private static ServicioCasetasPrincipal instancia;
    Caseta[][] casetas;

    private ServicioCasetasPrincipal() {
        this.casetas = new Caseta[4][];
        configurarMatrizCasetas(5, this.casetas);

        initCasetas();
    }
    // Método para obtener la instancia única
    public static ServicioCasetasPrincipal getInstance() {
        if (instancia == null) {
            instancia = new ServicioCasetasPrincipal();
        }
        return instancia;
    }

    private void configurarMatrizCasetas(int columnas, Caseta[][] casetas) {
        int filas = casetas.length;
        for (int i = 0; i < filas; i++) {
            if (i == 0) {
                // La primera fila tiene todas sus columnas
                casetas[i] = new Caseta[columnas];
            } else {
                // El resto de las filas tienen la mitad de las columnas
                casetas[i] = new Caseta[columnas / 2];
            }
        }
    }

    private void initCasetas() {
        for (int i = 0; i < casetas.length; i++) {
            for (int j = 0; j < casetas[i].length; j++) {
                casetas[i][j] = new Caseta();
            }
        }
        // Asigno por defecto una empresa con su admin para testear mejor
        casetas[0][0].asignarFlota(new EmpresaTransporte(1, "Bolivariano",
                new AdminFlota("Javier", 2, "javier@", "+57", "1")), 900000, 5, this.casetas);
    }

    public Caseta getCaseta(int fila, int columna) {
        return casetas[fila][columna];
    }

    public Caseta[][] getCasetas() {
        return casetas;
    }
    
    public Caseta getCasetaPorAdminID(int idAdmin) {
        for (Caseta[] caseta : casetas) {
            for (Caseta caseta1 : caseta) {
                if (caseta1.getEmpresa().getAdmin().getNroId() == idAdmin) {
                    return caseta1;
                }
            }
        }
        return null;
    }
}
