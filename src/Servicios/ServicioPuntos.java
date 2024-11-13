package Servicios;

import Modelos.RegistroCompra;
import Modelos.Viaje;
import Modelos.Usuarios.Cliente;
import Utils.IList;
import java.time.LocalDateTime;

public class ServicioPuntos {

    private Cliente cliente;

    public ServicioPuntos(Cliente cliente) {
        this.cliente = cliente;
    }

    public IList<RegistroCompra> getHistorialCompras() {
        return cliente.getHistorialPuntos();
    }

    public void actualizarPuntos(Viaje viaje, int cantidadTiquetes, LocalDateTime fecha) {
        int totalInvertido = viaje.getVlrUnit() * cantidadTiquetes;
        cliente.setDineroInvertido(cliente.getDineroInvertido() + totalInvertido);

        // Sumar al acumulado de dinero para los próximos puntos
        cliente.setDineroDespuesUltimoPunto(cliente.getDineroDespuesUltimoPunto() + totalInvertido);

        int puntosGanados = 0;

        // Calcular los puntos ganados y ajustar el dinero acumulado
        while (cliente.getDineroDespuesUltimoPunto() >= 10000) {
            cliente.setPuntosAcumulados(cliente.getPuntosAcumulados() + 3);
            cliente.setDineroDespuesUltimoPunto(cliente.getDineroDespuesUltimoPunto() - 10000);
            puntosGanados += 3;
        }

        // Registrar la compra si se ganaron puntos
        getHistorialCompras().add(new RegistroCompra(puntosGanados, viaje, cantidadTiquetes, fecha));
    }

    public int disminuirPuntos(Viaje viaje) {
        int dineroDevolucion = viaje.getVlrUnit();
        cliente.setDineroInvertido(cliente.getDineroInvertido() - dineroDevolucion);

        // Recalcular puntos basados en el dinero invertido y actualiza dinero restante
        int nuevoTotalDinero = cliente.getDineroInvertido();
        int puntosNuevos = (nuevoTotalDinero / 10000) * 3;
        int dineroRestanteParaProximoPunto = nuevoTotalDinero % 10000;

        int puntosPerdidos = cliente.getPuntosAcumulados() - puntosNuevos;

        // Actualizar puntos y dinero para el próximo punto
        cliente.setPuntosAcumulados(puntosNuevos);
        cliente.setDineroDespuesUltimoPunto(dineroRestanteParaProximoPunto);

        return puntosPerdidos; // Retorna la cantidad de puntos que perdió
    }

}
