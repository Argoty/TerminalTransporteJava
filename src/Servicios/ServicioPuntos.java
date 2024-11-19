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

            cliente.setDineroInvertido(cliente.getDineroInvertido() + tiquete.getViaje().getVlrUnit());
            cliente.getHistorialCompras().add(new RegistroPuntos(puntosGanados, tiquete));
        } else { // Pago con puntos
            cliente.setPuntosAcumulados(cliente.getPuntosAcumulados() - 90);
            cliente.getHistorialCompras().add(new RegistroPuntos(-90, tiquete));
        }
    }

    // AUN NADA
    public int puntosResultado(int precioTiquete) {
        cliente.setDineroRestante(cliente.getDineroRestante() + precioTiquete);
//        while() {
//            
//        }
        return 0;
    }

    public int actualizarPuntosDevolucion(RegistroPuntos registro) {
        Tiquete tiquete = registro.getTiquete();

        if (tiquete.getMetodoPago().equals("efectivo")) {
            int precioTiquete = tiquete.getViaje().getVlrUnit();

//            while(cliente.getDineroRestante() >= 10000) {
//                
//            }
            int puntosADevolver = registro.getPuntos(); // Puntos obtenidos por esta compra
            cliente.setDineroInvertido(cliente.getDineroInvertido() - precioTiquete);

            cliente.setPuntosAcumulados(cliente.getPuntosAcumulados() - puntosADevolver);

            cliente.setDineroRestante(cliente.getDineroRestante() - precioTiquete % 10000);

            return -puntosADevolver;
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
