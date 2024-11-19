/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servicios;

import Modelos.Bus;
import Modelos.EmpresaTransporte;
import Modelos.Viaje;
import Utils.IList;
import Utils.Lista;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *
 * @author PC
 */
public class ServicioViajes {

    EmpresaTransporte empresa;

    public ServicioViajes(EmpresaTransporte empresa) {
        this.empresa = empresa;
    }

    public IList<Viaje> getViajes() {
        return this.empresa.getViajes();
    }

    public IList<Viaje> getViajesFuturos() {
        IList<Viaje> viajesFuturos = new Lista<>();

        for (int i = 0; i < getViajes().size(); i++) {
            Viaje viaje = getViajes().get(i);
            // Solo agregar viajes cuya fecha de salida sea futura
            if (viaje.getFechaSalida().isAfter(LocalDateTime.now())) {
                viajesFuturos.add(viaje);
            }
        }

        return viajesFuturos;
    }

    public void agregarViaje(String destino, String fSal, String hSal, String fLle, String hLle,
            Bus bus, int vlrUnit) throws RuntimeException {
        if (bus == null || destino.isBlank()) {
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

        // Validación de disponibilidad y tiempo de inhabilitación de 1 día
        LocalDateTime ahora = LocalDateTime.now();
        for (int i = 0; i < getViajes().size(); i++) {
            Viaje viajeExistente = getViajes().get(i);
            if (viajeExistente.getBus().getPlaca().equals(bus.getPlaca())) {
                LocalDateTime inicioExistente = viajeExistente.getFechaSalida();
                LocalDateTime finExistente = viajeExistente.getFechaLlegada();
                LocalDateTime habilitadoDesde = finExistente.plusDays(1);


                // Verificar si el bus está actualmente en un viaje
                if (!ahora.isBefore(inicioExistente) && !ahora.isAfter(habilitadoDesde)) {
                    throw new RuntimeException("NO PUEDE SER PROGRAMADO HASTA QUE PASE 1 DIA DE SU LLEGADA.");
                }

                // Valida si el nuevo viaje comienza antes de 1 día después de la llegada del viaje existente
                if (fechaSalida.isBefore(habilitadoDesde)) {
                    throw new RuntimeException("EL BUS NO ESTÁ DISPONIBLE HASTA EL DÍA SIGUIENTE A LA LLEGADA DE SU ÚLTIMO VIAJE ("
                            + habilitadoDesde.format(formatter) + ").");
                }
            }
        }

        Viaje nuevoViaje = new Viaje(destino, fechaSalida, fechaLlegada, vlrUnit, bus);
        getViajes().add(nuevoViaje);
    }

    public Bus buscarBusPorViaje(String placaBus) {
        for (int i = 0; i < getViajes().size(); i++) {
            if (placaBus.equals(getViajes().get(i).getBus().getPlaca())) {
                return getViajes().get(i).getBus();
            }
        }
        return null;
    }

    public Viaje buscarViajePorId(int idViaje) {
        for (int i = 0; i < getViajes().size(); i++) {
            if (getViajes().get(i).getId() == idViaje) {
                return getViajes().get(i);
            }
        }
        return null;
    }
;
}
