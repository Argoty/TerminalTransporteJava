package Servicios;

import Modelos.RegistroPuntos;
import Modelos.Tiquete;
import Modelos.Usuarios.Cliente;
import Utils.IList;
import Utils.Lista;

public class ServicioPuntos {

    private Cliente cliente;

    public ServicioPuntos(Cliente cliente) {
        this.cliente = cliente;
    }

    public IList<RegistroPuntos> getHistorialCompra(String tipo) {
        IList<RegistroPuntos> historialCompra = new Lista<>();
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

            cliente.setDineroInvertido(cliente.getDineroInvertido() + precioTiquete);
            cliente.getHistorialCompras().add(new RegistroPuntos(puntosGanados, tiquete));
        } else { // Pago con puntos
            cliente.setPuntosAcumulados(cliente.getPuntosAcumulados() - 90);
            cliente.getHistorialCompras().add(new RegistroPuntos(-90, tiquete));
        }
    }

    public int actualizarPuntosDevolucion(RegistroPuntos registro) {
        Tiquete tiquete = registro.getTiquete();

        if (tiquete.getMetodoPago().equals("efectivo")) {
            int precioTiquete = tiquete.getViaje().getVlrUnit();

            int puntosDevueltos = 0;
            int contDinero = precioTiquete;

            // Cuento los puntos exclusivos de esa compra
            while (contDinero >= 10000) {
                contDinero -= 10000;
                puntosDevueltos += 3;
            }
            contDinero = cliente.getDineroRestante() - contDinero;
            
            if (contDinero < 0) {
                puntosDevueltos += 3;
                contDinero += 10000;
            }
            
            int puntosTotales = cliente.getPuntosAcumulados() - puntosDevueltos;
            if (puntosTotales < 0) throw new RuntimeException("No se puede hacer devolucion de este tiquete porque los puntos que se ganaron en este ya fueron gastados");
            cliente.setPuntosAcumulados(puntosTotales);
            cliente.setDineroInvertido(cliente.getDineroInvertido() - precioTiquete);
            cliente.setDineroRestante(contDinero);

            return -puntosDevueltos;
        } else {
            // Devolución de una compra realizada con puntos
            cliente.setPuntosAcumulados(cliente.getPuntosAcumulados() + 90);
            return 90;
        }
    }

    public RegistroPuntos getRegistroPorIdTiquete(int idTiquete) {
        for (int i = 0; i < cliente.getHistorialCompras().size(); i++) {
            if (cliente.getHistorialCompras().get(i).getTiquete().getId() == idTiquete) {
                return cliente.getHistorialCompras().get(i);
            }
        }
        return null;
    }
}
