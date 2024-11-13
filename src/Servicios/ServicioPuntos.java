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
        // Calcular el total invertido en la compra actual
        int totalInvertido = viaje.getVlrUnit() * cantidadTiquetes;

        // Agregar dinero invertido
        cliente.setDineroInvertido(cliente.getDineroInvertido() + totalInvertido);

        // Sumar al acumulado de dinero para los próximos puntos
        cliente.setDineroProximoPunto(cliente.getDineroProximoPunto() + totalInvertido);

        int puntosGanados = 0;

        // Calcular los puntos ganados y ajustar el dinero acumulado
        while (cliente.getDineroProximoPunto() >= 10000) {
            cliente.setPuntosAcumulados(cliente.getPuntosAcumulados() + 3);
            cliente.setDineroProximoPunto(cliente.getDineroProximoPunto() - 10000);
            puntosGanados += 3;
        }

        // Registrar la compra si se ganaron puntos
        getHistorialCompras().add(new RegistroCompra(puntosGanados, viaje, cantidadTiquetes, fecha));
    }

    public void disminuirPuntos(Viaje viaje) {
        // Actualiza el dinero invertido, los puntos y el dinero que falta para proximo punto
        cliente.setDineroInvertido(cliente.getDineroInvertido() - viaje.getVlrUnit());
        cliente.setPuntosAcumulados(cliente.getPuntosAcumulados() - (viaje.getVlrUnit() / 10000 *3));
        cliente.setDineroProximoPunto(cliente.getDineroProximoPunto() - viaje.getVlrUnit());
    }

}
