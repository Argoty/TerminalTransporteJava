/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicios;

import Modelos.Devolucion;
import Modelos.EmpresaTransporte;
import Modelos.RegistroPuntos;
import Modelos.Tiquete;
import Modelos.Usuarios.Cliente;
import Modelos.Viaje;
import Utils.IList;

/**
 *
 * @author PC
 */
public class ServicioDevoluciones {

    public ServicioDevoluciones() {
    }

    public void crearDevolucion(EmpresaTransporte empresa, Viaje viaje, Cliente cliente, RegistroPuntos registro, int puntos) throws RuntimeException {
        Devolucion devolucion = new Devolucion(registro, puntos);

        // Agregar la devolución a las listas
        empresa.getDevoluciones().add(devolucion);
        cliente.getDevoluciones().add(devolucion);

        // Eliminar el tiquete del viaje
        boolean tiqueteEliminadoViaje = eliminarTiqueteDeLista(viaje.getTiquetes(), registro.getTiquete().getId());
        boolean tiqueteEliminadoCliente = eliminarTiqueteDeLista(cliente.getTiquetes(), registro.getTiquete().getId());

        if (!tiqueteEliminadoViaje || !tiqueteEliminadoCliente) {
            throw new RuntimeException("No se pudo eliminar el tiquete de las listas correspondientes.");
        }
    }

    private boolean eliminarTiqueteDeLista(IList<Tiquete> lista, int idTiquete) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == idTiquete) {
                lista.remove(i); // Usa el método remove de tu lista personalizada
                return true;
            }
        }
        return false; // No se encontró el tiquete
    }

    public IList<Devolucion> getDevolucionesEmpr(EmpresaTransporte empresa) {
        return empresa.getDevoluciones();
    }

    public IList<Devolucion> getDevolucionesCli(Cliente cliente) {
        return cliente.getDevoluciones();
    }
}
