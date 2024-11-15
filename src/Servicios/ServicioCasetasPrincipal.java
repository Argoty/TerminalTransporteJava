/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicios;

import Modelos.Caseta;

import DataPersistencia.DataCasetas;

/**
 *
 * @author PC
 */
public class ServicioCasetasPrincipal {
    private static ServicioCasetasPrincipal instancia;
    private DataCasetas dataCasetas;
    Caseta[][] casetas;

    private ServicioCasetasPrincipal() {
        dataCasetas = new DataCasetas("casetasData.bin");
        this.casetas = dataCasetas.loadCasetasFromFile();
    }
    
    public void saveDataCasetas() {
        dataCasetas.saveCasetasToFile(casetas);
    }
    
    // Método para obtener la instancia única
    public static ServicioCasetasPrincipal getInstance() {
        if (instancia == null) {
            instancia = new ServicioCasetasPrincipal();
        }
        return instancia;
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
                if (caseta1.getEmpresa() == null) continue;
                if (caseta1.getEmpresa().getAdmin().getNroId() == idAdmin) {
                    return caseta1;
                }
            }
        }
        return null;
    }
    public Caseta getCasetaPorViajeID(int idViaje) {
        for (Caseta[] caseta : casetas) {
            for (Caseta caseta1 : caseta) {
                if (caseta1.getEmpresa() == null) continue;
                for (int i=0; i<caseta1.getEmpresa().getViajes().size();i++) {
                    if (caseta1.getEmpresa().getViajes().get(i).getId() == idViaje){
                        return caseta1;
                    }
                }
            }
        }
        return null;
    }
}
