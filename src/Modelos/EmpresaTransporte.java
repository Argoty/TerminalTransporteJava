/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import Modelos.Usuarios.AdminFlota;
import Utils.IList;
import Utils.Lista;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *
 * @author Javier Argoty
 */
public class EmpresaTransporte implements Serializable {

    private int nit;
    private String nombre;
    private AdminFlota admin;

    private IList<Bus> buses;
    private IList<Viaje> viajes;

    public EmpresaTransporte(int nit, String nombre, AdminFlota admin) {
        this.nit = nit;
        this.nombre = nombre;
        this.admin = admin;

        this.buses = new Lista<>();
        this.viajes = new Lista<>();
    }

    public int getNit() {
        return nit;
    }

    public void setNit(int nit) {
        this.nit = nit;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public AdminFlota getAdmin() {
        return admin;
    }

    // --------------------- METODOS PARA GESTIONAR BUSES -------------------------------
    public IList<Bus> getBuses() {
        return buses;
    }

    public void agregarBus(Bus bus, Caseta[][] casetas, int nroPlazasCaseta) throws RuntimeException {
        if (buses.size() >= nroPlazasCaseta) {
            throw new RuntimeException("La caseta de esta empresa ya tiene las plazas ocupadas");
        }
        if (esVaciosCamposBus(bus)) {
            throw new RuntimeException("COMPLETA LOS CAMPOS");
        }
        if (buscarBusGlobal(bus.getPlaca(), casetas)) {
            throw new RuntimeException("LA PLACA DE ESTE BUS YA EXISTE");
        }

        buses.add(bus);
    }

    private boolean buscarBusGlobal(String placa, Caseta[][] casetas) {
        for (Caseta[] caseta : casetas) {
            for (Caseta caseta1 : caseta) {
                if (caseta1.getEmpresa() == null) {
                    continue;
                }
                for (int k = 0; k < caseta1.getEmpresa().getBuses().size(); k++) {
                    if (caseta1.getEmpresa().getBuses().get(k).getPlaca().equals(placa)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void editarBus(Bus bus) throws RuntimeException {
        if (esVaciosCamposBus(bus)) {
            throw new RuntimeException("COMPLETA LOS CAMPOS");
        }
        Bus aux = buscarBusPorPlaca(bus.getPlaca());
        if (aux == null) {
            throw new RuntimeException("NO EXISTE ESTE BUS");
        }

        aux.setMarca(bus.getMarca());
        aux.setTipo(bus.getTipo());
        aux.setPuestosDisponibles(bus.getPuestosDisponibles());
    }

    public void eliminarBus(String placa) throws RuntimeException {
        if (placa.isBlank()) {
            throw new RuntimeException("ESCRIBE UNA PLACA");
        }
        for (int i = 0; i < buses.size(); i++) {
            if (buses.get(i).getPlaca().equals(placa)) {
                buses.remove(i);
                return;
            }
        }
        throw new RuntimeException("NO HAY UN BUS CON ESA PLACA");
    }

    private boolean esVaciosCamposBus(Bus bus) {
        return bus.getPlaca().isBlank() || bus.getMarca().isBlank() || bus.getTipo().isBlank();
    }

    public Bus buscarBusPorPlaca(String placa) {
        for (int i = 0; i < buses.size(); i++) {
            if (buses.get(i).getPlaca().equals(placa)) {
                return buses.get(i);
            }
        }
        return null;
    }

    // --------------------- METODOS PARA GESTIONAR VIAJES -------------------------------
    public IList<Viaje> getViajes() {
        return this.viajes;
    }

    public void agregarViaje(String destino, String fSal, String hSal, String fLle, String hLle,
            String placaBus, int vlrUnit) throws RuntimeException {
        if (placaBus == null || destino.isBlank() || placaBus.isBlank()) {
            throw new RuntimeException("LOS CAMPOS NO DEBEN SER VACIOS");
        }

        // Formato de fecha y hora
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        // Concatenar fecha y hora para salida y llegada
        String fechaHoraSalidaTexto = fSal + " " + hSal;
        String fechaHoraLlegadaTexto = fLle + " " + hLle;

        LocalDateTime fechaSalida;
        LocalDateTime fechaLlegada;

        try {
            // Parsear las fechas y horas de salida y llegada
            fechaSalida = LocalDateTime.parse(fechaHoraSalidaTexto, formatter);
            fechaLlegada = LocalDateTime.parse(fechaHoraLlegadaTexto, formatter);
        } catch (DateTimeParseException e) {
            throw new RuntimeException("FORMATO DE FECHA U HORA INCORRECTO. USE 'dd/mm/aaaa' para fecha y 'HH:mm' para hora.");
        }

        // Validación: La fecha de salida debe estar en el futuro
        if (!fechaSalida.isAfter(LocalDateTime.now())) {
            throw new RuntimeException("LA FECHA Y HORA DE SALIDA DEBEN SER FUTURAS.");
        }

        // Validación: La fecha de llegada debe ser posterior a la fecha de salida
        if (!fechaLlegada.isAfter(fechaSalida)) {
            throw new RuntimeException("LA FECHA Y HORA DE LLEGADA DEBEN SER POSTERIORES A LA FECHA Y HORA DE SALIDA.");
        }

        // Asumimos que tienes una lista de buses para buscar por placa
        Bus bus = buscarBusPorPlaca(placaBus);
        if (bus == null) {
            throw new RuntimeException("NO SE ENCONTRÓ EL BUS CON LA PLACA ESPECIFICADA.");
        }

        // Validación de disponibilidad y tiempo de inhabilitación de 1 día
        for (int i = 0; i < viajes.size(); i++) {
            Viaje viajeExistente = viajes.get(i);

            // Solo nos importa si el viaje es del mismo bus
            if (viajeExistente.getBus().getPlaca().equals(placaBus)) {
                LocalDateTime finExistente = viajeExistente.getFechaLlegada();

                // Verificar si el nuevo viaje comienza antes de 1 día después de la llegada del viaje existente
                LocalDateTime habilitadoDesde = finExistente.plusDays(1);
                if (fechaSalida.isBefore(habilitadoDesde)) {
                    throw new RuntimeException("EL BUS NO ESTÁ DISPONIBLE HASTA EL DÍA SIGUIENTE A LA LLEGADA DE SU ÚLTIMO VIAJE.");
                }
            }
        }

        // Si pasa todas las validaciones, se crea y agrega el nuevo viaje
        Viaje nuevoViaje = new Viaje(destino, fechaSalida, fechaLlegada, vlrUnit, bus);
        viajes.add(nuevoViaje);
    }

    public Bus buscarBusPorViaje(String placaBus) {
        for (int i = 0; i < viajes.size(); i++) {
            if (placaBus.equals(viajes.get(i).getBus().getPlaca())) {
                return viajes.get(i).getBus();
            }
        }
        return null;
    }
}
