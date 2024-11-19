/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Modelos.EmpresaTransporte;
import Modelos.Notificacion;
import Modelos.RegistroPuntos;
import Modelos.Reserva;
import Modelos.Tiquete;
import Modelos.Usuarios.Cliente;
import Modelos.Usuarios.Usuario;
import Modelos.Viaje;

import Servicios.ServicioCasetasPrincipal;
import Servicios.ServicioDevoluciones;
import Servicios.ServicioPuntos;
import Servicios.ServicioTiquetes;
import Servicios.ServicioViajes;
import Servicios.ServicioUsuarios;

import Utils.IList;
import Utils.IQueue;
import java.time.LocalDateTime;

/**
 *
 * @author PC
 */
public class ControladorTiquetes {

    private EmpresaTransporte empresa;
    private ServicioCasetasPrincipal scp;
    private ServicioTiquetes st;
    private ServicioViajes sv;
    private ServicioUsuarios su;
    private ServicioDevoluciones sd;

    public ControladorTiquetes(int idAdmin) {
        this.scp = ServicioCasetasPrincipal.getInstance();
        this.empresa = scp.getCasetaPorAdminID(idAdmin)
                .getEmpresa();
        this.sv = new ServicioViajes(empresa);
        this.st = new ServicioTiquetes();
        this.sd = new ServicioDevoluciones();
        this.su = ServicioUsuarios.getInstance();
    }
    public void guardarBinarios() {
        scp.saveDataCasetas();
        su.saveDataUsuarios();
    }

    public boolean venderTiquete(int idViaje, int idCliente, int cantidad, int metodoPago) throws RuntimeException {
        Cliente cliente = buscarClientePorId(idCliente);
        Viaje viaje = buscarViajePorId(idViaje);

        IList<Tiquete> tiquetesVenta = st.crearTiquete(viaje, cliente, cantidad, metodoPago);

        // Agrega Puntos al usuario segun tiquetes creados si no se hizo venta si no es null
        // si es null es porque mandó un cliente a la cola de espera
        if (tiquetesVenta != null) {
            ServicioPuntos sp = new ServicioPuntos(cliente);
            for (int i = 0; i < tiquetesVenta.size(); i++) {
                sp.actualizarPuntos(tiquetesVenta.get(i));
            }
            guardarBinarios();
            return false;
        } else {
            guardarBinarios();
            return true;
        }
    }

    public boolean crearDevolucion(int idViaje, int idTiquete) throws RuntimeException {
        Viaje viaje = buscarViajePorId(idViaje);
        
        Tiquete tiqueteAEliminar = st.obtenerTiquete(viaje, idTiquete);
        if (tiqueteAEliminar.getViaje().getFechaSalida().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("El viaje ya ocurrió, no puedes hacer devolucion");
        }

        Cliente cliente = buscarClientePorId(tiqueteAEliminar.getCliente().getNroId());

        ServicioPuntos sp = new ServicioPuntos(cliente);
        RegistroPuntos registroPuntos = sp.getRegistroPorIdTiquete(tiqueteAEliminar.getId());
        int puntosResultado = sp.actualizarPuntosDevolucion(registroPuntos);

        sd.crearDevolucion(this.empresa, viaje, cliente, registroPuntos, puntosResultado);
        
        boolean desencolo = false;
        // VERIFICA SI HAY PARA DESENCOLAR Y CREA RESERVA SI ES ASI
        if (!viaje.getColaEspera().isEmpty() && viaje.getPuestosDesocupados() == 1) {
            Cliente clienteDesencolado = buscarClientePorId(viaje.getColaEspera().dequeue().getNroId());

            // Crear una nueva reserva para el cliente desencolado
            Reserva nuevaReserva = new Reserva(clienteDesencolado, viaje);
            viaje.getReservas().add(nuevaReserva);
            clienteDesencolado.getReservas().add(nuevaReserva);
            
            clienteDesencolado.getNotificaciones().add(new Notificacion(clienteDesencolado, "Posible tiquete para viaje a " + viaje.getDestino() + " el " + viaje.getFechaSalidaStr() + " en el bus " + viaje.getBus().getPlaca() ));
            desencolo = true;
        }
        // Guardo info
        scp.saveDataCasetas();
        su.saveDataUsuarios();
        return desencolo;
    }

    public IList<Tiquete> getTiquetes(int idViaje) {
        Viaje viaje = buscarViajePorId(idViaje);
        return st.getTiquetes(viaje);
    }

    // METODO PARA MOSTRAR COLA DE ESPERA DE ESE VIAJE
    public String getColaEspera(int idViaje) {
        Viaje viaje = buscarViajePorId(idViaje);
        IQueue<Cliente> colaAux = st.getColaEspera(viaje).clone();
        String resultadoCola = "";
        int cont = 1;
        while (!colaAux.isEmpty()) {
            Cliente cliente = colaAux.dequeue();
            resultadoCola += cont + ". " + cliente.getName() + "/ ID: " + cliente.getNroId() + "\n";
            cont++;
        }
        return resultadoCola;
    }

    public IList<Viaje> getViajesFuturos() {
        return sv.getViajesFuturos();
    }

    public IList<Viaje> getViajes() {
        return sv.getViajes();
    }

    public Cliente buscarClientePorId(int idCliente) throws RuntimeException {
        Usuario usuario = su.buscarUsuarioPorId(idCliente);
        if (!(usuario instanceof Cliente)) {
            throw new RuntimeException("EL CLIENTE NO SE ENCUENTRA CON ESTE ID");
        }
        return (Cliente) usuario;
    }

    public Viaje buscarViajePorId(int idViaje) {
        return sv.buscarViajePorId(idViaje);
    }

}
