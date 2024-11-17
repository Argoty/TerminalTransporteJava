/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;
import Modelos.Usuarios.Cliente;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Javier Argoty
 */
public class Reserva implements Serializable{
    private static int cont = 1;
    private int id;
    private Cliente cliente;       
    private Viaje viaje; 
    private String metodoPago;
    private LocalDateTime fechaReserva;
    private boolean efectiva;

    public Reserva(Cliente cliente, Viaje viaje, String metodoPago) {
        this.id = cont++; 
        this.cliente = cliente;
        this.viaje = viaje;
        this.metodoPago = metodoPago;
        this.fechaReserva = LocalDateTime.now(); // Fecha actual
        this.efectiva = false;             
    }

    // Método para hacer efectiva la reserva
//    public void hacerEfectiva() {
//        if (Efectiva && viaje.getBus().getPuestos() >= cantidad) {
//            // Disminuir los puestos disponibles en el bus
//            viaje.getBus().setPuestos(viaje.getBus().getPuestos() - cantidad);
//            // Crear un tiquete para el cliente
//            Tiquete tiquete = new Tiquete(viaje, cliente);
//            // Agregar el tiquete al cliente (podría ser a una lista de tiquetes)
//            System.out.println("Reserva hecha efectiva. Tiquete emitido.");
//            this.Efectiva = false; // Marca la reserva como inEfectiva
//        } else {
//            System.out.println("No se puede hacer efectiva la reserva. El viaje no tiene suficientes puestos disponibles.");
//        }
//    }

    // Método para hacer efectiva
    public void hacerEfectiva() {
        this.efectiva = true;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }
    public Cliente getCliente() {
        return cliente;
    }

    public Viaje getViaje() {
        return viaje;
    }
    public String getMetodoPago() {
        return metodoPago;
    }
//    public int getCantidad() {
//        return cantidad;
//    }

    public LocalDateTime getFechaReserva() {
        return fechaReserva;
    }
    public String getFechaReservaStr() {
        return fechaReserva.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    public boolean isEfectiva() {
        return efectiva;
    }
    public String getEstado() {
        return efectiva ? "efectiva" : "no efectiva";
    }

    public void setEfectiva(boolean efectiva) {
        this.efectiva = efectiva;
    }
    public static void ajustarContadorPersistencia(int numeroDeReservas) {
        cont = numeroDeReservas;
    }
    public static int getContador() {
        return cont;
    }
}

