package Servicios;

import Modelos.MovimientoTransaccion;
import Modelos.Tiquete;
import Modelos.Usuarios.Cliente;
import Utils.IList;
import Utils.Lista;

public class ServicioMovimientos {

    private Cliente cliente;

    public ServicioMovimientos(Cliente cliente) {
        this.cliente = cliente;
    }

    public IList<MovimientoTransaccion> getHistorialCompra(String tipo) {
        IList<MovimientoTransaccion> historialCompra = new Lista<>();
        for (int i = 0; i < cliente.getHistorialCompras().size(); i++) {
            if (cliente.getHistorialCompras().get(i).getTiquete().getMetodoPago().equals(tipo)) {
                historialCompra.add(cliente.getHistorialCompras().get(i));
            }
        }
        return historialCompra;
    }

    public void actualizarPuntos(Tiquete tiquete) {
        if (tiquete.getMetodoPago().equals("efectivo")) { // Pago en efectivo
            int precioTiquete = tiquete.getViaje().getVlrUnit();

            cliente.setDineroRestante(cliente.getDineroRestante() + precioTiquete);

            int puntosGanados = 0;

            while (cliente.getDineroRestante() >= 10000) {
                cliente.setPuntosAcumulados(cliente.getPuntosAcumulados() + 3);
                cliente.setDineroRestante(cliente.getDineroRestante() - 10000);
                puntosGanados += 3;
            }

            cliente.setDineroInvertido(cliente.getDineroInvertido() + tiquete.getViaje().getVlrUnit());
            cliente.getHistorialCompras().add(new MovimientoTransaccion(puntosGanados, tiquete));
        } else { // Pago con puntos
            cliente.setPuntosAcumulados(cliente.getPuntosAcumulados() - 90);
            cliente.getHistorialCompras().add(new MovimientoTransaccion(-90, tiquete));
        }
    }

    public int actualizarPuntosDevolucion(MovimientoTransaccion movimiento) {
        Tiquete tiquete = movimiento.getTiquete();

        if (tiquete.getMetodoPago().equals("efectivo")) {
            // Restar el valor del tiquete devuelto del dinero invertido
            int precioTiquete = tiquete.getViaje().getVlrUnit();
            cliente.setDineroInvertido(cliente.getDineroInvertido() - precioTiquete);

            int puntosActualizados = (cliente.getDineroInvertido() / 10000) * 3;
            int puntosPerdidos = cliente.getPuntosAcumulados() - puntosActualizados;

            cliente.setPuntosAcumulados(puntosActualizados);
            cliente.setDineroRestante(cliente.getDineroInvertido() % 10000);

            // Retornar los puntos perdidos para registro en devoluciones
            return -puntosPerdidos;
        } else {
            // Devolución de una compra realizada con puntos
            cliente.setPuntosAcumulados(cliente.getPuntosAcumulados() + 90);
            return 90;
        }
    }

    public MovimientoTransaccion getMovimientoPorIdTiquete(int idTiquete) {
        for (int i = 0; i < cliente.getHistorialCompras().size(); i++) {
            if (cliente.getHistorialCompras().get(i).getTiquete().getId() == idTiquete) {
                return cliente.getHistorialCompras().get(i);
            }
        }
        return null;
    }
}
