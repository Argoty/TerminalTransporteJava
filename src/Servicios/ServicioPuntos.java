/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicios;

import Modelos.RegistroPuntos;
import Modelos.Tiquete;
import Modelos.Usuarios.Cliente;
import Utils.IList;
import Utils.Lista;

/**
 *
 * @author PC
 */
public class ServicioPuntos {

    private Cliente cliente;

    public ServicioPuntos(Cliente cliente) {
        this.cliente = cliente;
    }

    public IList<RegistroPuntos> getHistorialPuntos() {
        return this.cliente.getHistorialPuntos();
    }

    public IList<RegistroPuntos> getPuntosJuntos() {
        IList<RegistroPuntos> puntosAgrupados = new Lista<>();

        for (int i = 0; i < getHistorialPuntos().size(); i++) {
            RegistroPuntos registroActual = getHistorialPuntos().get(i);
            boolean encontrado = false;

            for (int j = 0; j < puntosAgrupados.size(); j++) {
                RegistroPuntos registroAgrupado = puntosAgrupados.get(j);

                if (registroAgrupado.getTiquete().getViaje().getId() == registroActual.getTiquete().getViaje().getId()
                        && registroAgrupado.getFechaCreacion().equals(registroActual.getFechaCreacion())) {

                    registroAgrupado.aumentarPuntos(registroActual.getPuntos());
                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) {
                puntosAgrupados.add(new RegistroPuntos(registroActual.getPuntos(), registroActual.getTiquete(), registroActual.getFechaCreacion()));
            }
        }

        return puntosAgrupados;
    }

    public void agregarDineroInvertido(int dinero) {
        cliente.setDineroInvertido(cliente.getDineroInvertido() + dinero);
    }

    public void agregarRegistroPuntos(RegistroPuntos registro) {
        getHistorialPuntos().add(registro);
    }

    public void actualizarPuntos(int dineroInvertidoViaje, Tiquete tiquete) {
        cliente.setDineroProximoPunto(cliente.getDineroProximoPunto() + dineroInvertidoViaje);

        // Creo el registro para guardar los de esta venta con la misma fecha
        RegistroPuntos registro = new RegistroPuntos(3, tiquete, null);
        while (cliente.getDineroProximoPunto() >= 10000) {
            cliente.setPuntosAcumulados(cliente.getPuntosAcumulados() + 3);
            cliente.setDineroProximoPunto(cliente.getDineroProximoPunto() - 10000);
            agregarRegistroPuntos(registro);
        }
    }
    
    public void eliminarRegistroPunto(int idTiquete) {
        Tiquete tiquete = null;
        int cantPuntosEliminados = 0;
        
        // Elimino el registro de puntos mientras obtengo el tiquete que se elimino y el total de puntos a eliminar
        for (int i=0; i < getHistorialPuntos().size(); i++) {
            if (getHistorialPuntos().get(i).getTiquete().getId() == idTiquete) {
                if (tiquete == null) tiquete = getHistorialPuntos().get(i).getTiquete();
                getHistorialPuntos().remove(i);
                cantPuntosEliminados += 3;
            }
        }
        
        // Devuelvo el dinero invertido y los puntos ganados
        cliente.setDineroInvertido(cliente.getDineroInvertido() - tiquete.getViaje().getVlrUnit());
        cliente.setPuntosAcumulados(cliente.getPuntosAcumulados() - cantPuntosEliminados);
    }

}
