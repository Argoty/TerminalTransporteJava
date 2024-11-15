package Servicios;

import Modelos.RegistroCompra;
import Modelos.Viaje;
import Modelos.Usuarios.Cliente;
import Utils.IList;
import Utils.Lista;
import java.time.LocalDateTime;

public class ServicioRegistrosCompras {

    private Cliente cliente;

    public ServicioRegistrosCompras(Cliente cliente) {
        this.cliente = cliente;
    }

    public IList<RegistroCompra> getHistorialCompra(String tipo) {
        IList<RegistroCompra> historialCompra = new Lista<>();
        for (int i = 0; i < cliente.getHistorialCompras().size(); i++) {
            if (cliente.getHistorialCompras().get(i).getMetodoPago().equals(tipo)) {
                historialCompra.add(cliente.getHistorialCompras().get(i));
            }
        }
        return historialCompra;
    }

    public void actualizarPuntos(Viaje viaje, int cantidadTiquetes, LocalDateTime fecha, int metodoPago) {
        if (metodoPago == 0) {  // Pago en efectivo
            int totalInvertido = viaje.getVlrUnit() * cantidadTiquetes;

            // Actualizamos dineroInvertido solo para pagos en efectivo
            cliente.setDineroInvertido(cliente.getDineroInvertido() + totalInvertido);

            // Calculamos los puntos basados en el dinero invertido total
            int dineroTotal = cliente.getDineroInvertido();
            int puntosActuales = cliente.getPuntosAcumulados();

            // Recalculamos los puntos en función del dineroTotal (dineroInvertido)
            int nuevosPuntos = (dineroTotal / 10000) * 3;

            // Determinamos la cantidad de puntos a agregar en función de los puntos acumulados actuales
            int puntosGanados = nuevosPuntos - puntosActuales;

            // Sumamos los puntos ganados a los puntos acumulados actuales
            cliente.setPuntosAcumulados(puntosActuales + puntosGanados);

            // Registramos la compra en efectivo
            cliente.getHistorialCompras().add(new RegistroCompra(puntosGanados, viaje, cantidadTiquetes, fecha, "efectivo"));
        } else {  // Pago con puntos
            int puntosUsados = 90 * cantidadTiquetes;
            cliente.setPuntosAcumulados(cliente.getPuntosAcumulados() - puntosUsados);

            // Registramos la compra realizada con puntos
            cliente.getHistorialCompras().add(new RegistroCompra(puntosUsados, viaje, cantidadTiquetes, fecha, "puntos"));
        }
    }

    public int actualizarPuntosDevolucion(Viaje viaje, String metodoPago) {
        if (metodoPago.equals("efectivo")) {
            // Calcular los puntos ganados exclusivamente por el viaje devuelto
            int puntosGanadosPorViaje = (viaje.getVlrUnit() / 10000) * 3;

            // Restar el valor del viaje del dinero invertido
            cliente.setDineroInvertido(cliente.getDineroInvertido() - viaje.getVlrUnit());

            // Calcular los puntos acumulados basados en el nuevo total de dinero invertido
            int puntosActualizadosSegunDinero = (cliente.getDineroInvertido() / 10000) * 3;

            // Calcular la diferencia de puntos que deben ser restados del total acumulado
            int puntosAEliminar = cliente.getPuntosAcumulados() - puntosActualizadosSegunDinero;

            // Actualizar los puntos acumulados con el nuevo cálculo
            cliente.setPuntosAcumulados(puntosActualizadosSegunDinero);

            // Retornar los puntos eliminados en negativo
            return -puntosAEliminar;
        } else {
            // Caso para devoluciones pagadas con puntos
            cliente.setPuntosAcumulados(cliente.getPuntosAcumulados() + 90);
            return 90;
        }
    }

}
