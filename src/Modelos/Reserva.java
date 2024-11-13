/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;
import Modelos.Usuarios.Cliente;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author Javier Argoty
 */
public class Reserva implements Serializable{
    private Cliente cliente;       // Cliente que realiza la reserva
    private Viaje viaje;           // Viaje asociado a la reserva
    private int cantidad;          // Cantidad de tiquetes reservados
    private LocalDateTime fechaReserva;     // Fecha de la reserva
    private boolean activa;        // Estado de la reserva (activa o cancelada)

    public Reserva(Cliente cliente, Viaje viaje, int cantidad) {
        this.cliente = cliente;
        this.viaje = viaje;
        this.cantidad = cantidad;
        this.fechaReserva = LocalDateTime.now(); // Fecha actual
        this.activa = true;             // Por defecto, la reserva está activa
    }

    // Método para hacer efectiva la reserva
//    public void hacerEfectiva() {
//        if (activa && viaje.getBus().getPuestos() >= cantidad) {
//            // Disminuir los puestos disponibles en el bus
//            viaje.getBus().setPuestos(viaje.getBus().getPuestos() - cantidad);
//            // Crear un tiquete para el cliente
//            Tiquete tiquete = new Tiquete(viaje, cliente);
//            // Agregar el tiquete al cliente (podría ser a una lista de tiquetes)
//            System.out.println("Reserva hecha efectiva. Tiquete emitido.");
//            this.activa = false; // Marca la reserva como inactiva
//        } else {
//            System.out.println("No se puede hacer efectiva la reserva. El viaje no tiene suficientes puestos disponibles.");
//        }
//    }

    // Método para cancelar la reserva
    public void cancelar() {
        this.activa = false; // Marca la reserva como inactiva
        System.out.println("Reserva cancelada.");
    }

    // Getters y Setters
    public Cliente getCliente() {
        return cliente;
    }

    public Viaje getViaje() {
        return viaje;
    }

    public int getCantidad() {
        return cantidad;
    }

    public LocalDateTime getFechaReserva() {
        return fechaReserva;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }
}

