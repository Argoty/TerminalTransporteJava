/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataPersistencia;

import Modelos.Caseta;
import Modelos.EmpresaTransporte;
import Modelos.Tiquete;
import Modelos.Usuarios.AdminFlota;
import Modelos.Viaje;

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
public class DataCasetas {

    private String filePath;

    public DataCasetas(String filePath) {
        this.filePath = filePath;
    }

    public void saveCasetasToFile(Caseta[][] casetas) {
        try (
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(casetas);
            System.out.println("Casetas guardadass en: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Caseta[][] loadCasetasFromFile() {
        File file = new File(filePath);

        if (!file.exists()) {
            Caseta[][] casetas = new Caseta[4][];
            configurarMatrizCasetas(5, casetas);
            initCasetas(casetas);
            // Esto es de prueba, despues se elimina
//            casetas[0][0].asignarFlota(new EmpresaTransporte(1, "Bolivariano",
//                    new AdminFlota("Javier", 2, "javier@gmail.com", "+57 318 9431874", "1", 2000000)), 900000, 5, casetas);

            saveCasetasToFile(casetas);
            return casetas;
        }

        try (
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));) {
            Caseta[][] casetas = (Caseta[][]) ois.readObject();

            // Persiste los ids que dependen del contador estatico de Viajes y Tiquetes
            int[] totalViajesYTiq = contarViajesTiqEnCasetas(casetas);
            Viaje.ajustarContadorPersistencia(totalViajesYTiq[0]);
            Tiquete.ajustarContadorPersistencia(totalViajesYTiq[1]);
            
            return casetas;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new Caseta[4][];

        }
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

    private void initCasetas(Caseta[][] casetas) {
        for (int i = 0; i < casetas.length; i++) {
            for (int j = 0; j < casetas[i].length; j++) {
                casetas[i][j] = new Caseta();
            }
        }
    }

    // Método para contar el total de viajes en todas las casetas
    private int[] contarViajesTiqEnCasetas(Caseta[][] casetas) {
        int[] nroViajesYTiq = new int[2];
        int contadorViajes = 0;
        int contadorTiquetes = 0;

        for (Caseta[] filaCaseta : casetas) {
            for (Caseta caseta : filaCaseta) {
                if (caseta.getEmpresa() != null) {

                    contadorViajes += caseta.getEmpresa().getViajes().size();

                    for (int i = 0; i < caseta.getEmpresa().getViajes().size(); i++) {
                        contadorTiquetes += caseta.getEmpresa().getViajes().get(i).getTiquetes().size();
                    }
                }
            }
        }
        
        nroViajesYTiq[0] = contadorViajes;
        nroViajesYTiq[1] = contadorTiquetes;
        
        return nroViajesYTiq;
    }

}
