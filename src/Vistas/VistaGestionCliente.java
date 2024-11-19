/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vistas;

import Controladores.ControladorPuntos;
import Controladores.ControladorDevolCliente;
import Controladores.ControladorInfoCliente;
import Controladores.ControladorNotificaciones;
import Controladores.ControladorReservar;
import Modelos.RegistroPuntos;
import Modelos.Devolucion;
import Modelos.Reserva;
import Modelos.Tiquete;
import Modelos.Usuarios.Cliente;
import Modelos.Viaje;
import Utils.IList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author PC
 */
public class VistaGestionCliente extends javax.swing.JFrame {

    /**
     * Creates new form VistaGestionCliente
     */
    Cliente cliente;
    ControladorInfoCliente cic;
    ControladorPuntos cp;
    ControladorDevolCliente cdc;
    ControladorReservar cr;
    ControladorNotificaciones cn;

    int idViajeSeleccionado = -1;
    int idReservaSeleccionado = -1;

    public VistaGestionCliente(Cliente cliente) {
        initComponents();
        setLocationRelativeTo(this);

        this.cliente = cliente;
        cic = new ControladorInfoCliente(cliente);
        cp = new ControladorPuntos(cliente);
        cdc = new ControladorDevolCliente(cliente);
        cr = new ControladorReservar(cliente);
        cn = new ControladorNotificaciones(cliente);

        mostrarInfo();
        llenarTablaPuntos("efectivo");
        llenarTablaPuntos("puntos");
        llenarTablaDevoluciones();
        llenarTablaViajesRes(cr.getViajesAll());
        llenarTablaReservas();
        llenarTablaNotificaciones();

        configurarSeleccionTablaViajes();
        configurarSeleccionTablaReservas();
    }

    // METODOS PRIVADOS DEL TAB DE INFO DE CLIENTE
    private void mostrarInfo() {
        nombreCliLabel.setText(cic.getName());
        nroIdLabel.setText(cic.getNroId() + "");
        emailLabel.setText(cic.getEmail());
        telefonoLabel.setText(cic.getTelefono());
        puntosAcumuladosLabel.setText(cic.getPuntosAcumulados() + "");
        dineroInvertidoLabel.setText("$" + cic.getDineroInvertido());
    }

    // MÉTODOS PRIVADOS DEL TAB DE PUNTOS
    private void llenarTablaPuntos(String criterio) {
        DefaultTableModel model = new DefaultTableModel();

        model.setColumnIdentifiers(new Object[]{"Puntos", "Id Tiquete", "Viaje", "Bus", "Empresa", "Vlr Unit", "Fecha"});
        for (int i = 0; i < cp.getRegistroPuntos(criterio).size(); i++) {
            RegistroPuntos regPunto = cp.getRegistroPuntos(criterio).get(i);
            Tiquete tiquete = regPunto.getTiquete();
            int vlrUnit = regPunto.getTiquete().getViaje().getVlrUnit();
            model.addRow(new Object[]{
                regPunto.getPuntos(),
                tiquete.getId(),
                tiquete.getViaje().getDestino() + " el " + tiquete.getViaje().getFechaSalidaStr(),
                tiquete.getViaje().getBus().getPlaca(),
                cp.getNombreEmpresaSegunViaje(tiquete.getViaje().getId()),
                vlrUnit,
                tiquete.getFechaCompraStr(),});
        }
        if (criterio.equals("efectivo")) {
            puntosGanadosTable.setModel(model);
        } else {
            tablaPuntosRedimidos.setModel(model);
        }
    }

    // MÉTODOS PRIVADOS DEL TAB DE DEVOLUCIONES
    private void llenarTablaDevoluciones() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"ID Tiquete", "Viaje", "Bus", "Empresa", "Vlr Unit", "Fecha", "Tipo", "Resultado Puntos"});
        for (int i = 0; i < cdc.getDevoluciones().size(); i++) {
            Devolucion devolucion = cdc.getDevoluciones().get(i);
            Tiquete tiquete = devolucion.getMovimiento().getTiquete();
            model.addRow(new Object[]{
                tiquete.getId(),
                tiquete.getViaje().getDestino() + " el " + tiquete.getViaje().getFechaSalidaStr(),
                tiquete.getViaje().getBus().getPlaca(),
                cdc.getNombreEmpresaSegunViaje(tiquete.getViaje().getId()),
                tiquete.getViaje().getVlrUnit(),
                devolucion.getFechaDevolucionStr(),
                tiquete.getMetodoPago(),
                devolucion.getResultadoPuntos()
            });
        }
        tablaDevoluciones.setModel(model);
    }

    // MÉTODOS PRIVADOS DEL TAB DE RESERVAS
    private void llenarTablaViajesRes(IList<Viaje> viajes) {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"ID", "Destino", "Empresa", "Fecha Salida", "Bus", "Vlr Unit", "Puestos Disponibles"});
        for (int i = 0; i < viajes.size(); i++) {
            Viaje viaje = viajes.get(i);
            model.addRow(new Object[]{
                viaje.getId(),
                viaje.getDestino(),
                cr.getNombreEmpresaSegunViaje(viaje.getId()),
                viaje.getFechaSalidaStr(),
                viaje.getBus().getPlaca(),
                viaje.getVlrUnit(),
                viaje.getPuestosDesocupados() + "/" + viaje.getBus().getPuestos()
            });
        }

        viajesTablaRese.setModel(model);
        // Ajustar el ancho de las columnas
        viajesTablaRese.getColumnModel().getColumn(0).setPreferredWidth(30);  // ID
        viajesTablaRese.getColumnModel().getColumn(1).setPreferredWidth(100); // Destino (más pequeño)
        viajesTablaRese.getColumnModel().getColumn(2).setPreferredWidth(120); // Empresa (reducido ligeramente)
        viajesTablaRese.getColumnModel().getColumn(3).setPreferredWidth(165); // Fecha Salida
        viajesTablaRese.getColumnModel().getColumn(4).setPreferredWidth(90);  // Bus
        viajesTablaRese.getColumnModel().getColumn(5).setPreferredWidth(120); // Vlr Unit
        viajesTablaRese.getColumnModel().getColumn(6).setPreferredWidth(150); // Puestos Disponibles
    }

    private void configurarSeleccionTablaViajes() {
        viajesTablaRese.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                int filaSeleccionada = viajesTablaRese.getSelectedRow();

                if (filaSeleccionada != -1) {
                    Object idViaje = viajesTablaRese.getValueAt(filaSeleccionada, 0);
                    this.idViajeSeleccionado = Integer.parseInt(idViaje.toString());
                }
            }
        });
    }

    private void llenarTablaReservas() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"ID", "Viaje", "Fecha", "Bus", "Vlr Unit", "Fecha Reserva", "Estado"});
        for (int i = 0; i < cr.getReservas().size(); i++) {
            Reserva reserva = cr.getReservas().get(i);
            model.addRow(new Object[]{
                reserva.getId(),
                reserva.getViaje().getId() + ". " + reserva.getViaje().getDestino(),
                reserva.getViaje().getFechaSalidaStr(),
                reserva.getViaje().getBus().getPlaca(),
                reserva.getViaje().getVlrUnit(),
                reserva.getFechaReservaStr(),
                reserva.getEstado()
            });
        }

        tablaReservas.setModel(model);
    }

    private void configurarSeleccionTablaReservas() {
        tablaReservas.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                int filaSeleccionada = tablaReservas.getSelectedRow();

                if (filaSeleccionada != -1) {
                    Object idReserva = tablaReservas.getValueAt(filaSeleccionada, 0);
                    this.idReservaSeleccionado = Integer.parseInt(idReserva.toString());
                }
            }
        });
    }
    // MÉTODOS PRIVADOS DEL TAB DE DEVOLUCIONES
    private void llenarTablaNotificaciones() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"Mensaje", "Fecha Envío"});
        for (int i = 0; i < cn.getNotificaciones().size(); i++) {
            model.addRow(new Object[]{
                cn.getNotificaciones().get(i).getMensaje(),
                cn.getNotificaciones().get(i).getFechaEnvioStr(),
            });
        }
        tablaNotificaciones.setModel(model);
        tablaNotificaciones.getColumnModel().getColumn(0).setPreferredWidth(300);
        tablaNotificaciones.getColumnModel().getColumn(1).setPreferredWidth(100);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        clienteTabbedPane = new javax.swing.JTabbedPane();
        informacionPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        nombreCliLabel = new javax.swing.JLabel();
        nroIdLabel = new javax.swing.JLabel();
        emailLabel = new javax.swing.JLabel();
        telefonoLabel = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        puntosAcumuladosLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        dineroInvertidoLabel = new javax.swing.JLabel();
        puntosAcumulados = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        puntosGanadosTable = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaPuntosRedimidos = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        devolucionesPanel = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablaDevoluciones = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        reservarTiqPanel = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        viajesTablaRese = new javax.swing.JTable();
        reservarBtn = new javax.swing.JButton();
        filtroOpcionesCombo = new javax.swing.JComboBox<>();
        filtroDestinoField = new javax.swing.JTextField();
        tituloViajeLabel = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tablaReservas = new javax.swing.JTable();
        filtrarBtn = new javax.swing.JButton();
        filtroFechaField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cantidadReserField = new javax.swing.JTextField();
        cancelarReservaBtn = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        notificacionesPanel = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablaNotificaciones = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        cerrarSesionBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel1.setText("Nombre:");

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel2.setText("Numero de Identificacion:");

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel8.setText("Email:");

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel9.setText("Telefono:");

        nombreCliLabel.setText("jLabel10");

        nroIdLabel.setText("jLabel11");

        emailLabel.setText("jLabel12");

        telefonoLabel.setText("jLabel13");

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel10.setText("Puntos Acumulados");

        puntosAcumuladosLabel.setText("jLabel13");

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel3.setText("Dinero Invertido");

        dineroInvertidoLabel.setText("jLabel17");

        javax.swing.GroupLayout informacionPanelLayout = new javax.swing.GroupLayout(informacionPanel);
        informacionPanel.setLayout(informacionPanelLayout);
        informacionPanelLayout.setHorizontalGroup(
            informacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(informacionPanelLayout.createSequentialGroup()
                .addGap(146, 146, 146)
                .addGroup(informacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(informacionPanelLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(telefonoLabel))
                    .addGroup(informacionPanelLayout.createSequentialGroup()
                        .addGroup(informacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel8)
                            .addComponent(jLabel10)
                            .addComponent(jLabel3))
                        .addGap(161, 161, 161)
                        .addGroup(informacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dineroInvertidoLabel)
                            .addComponent(puntosAcumuladosLabel)
                            .addComponent(nroIdLabel)
                            .addComponent(nombreCliLabel)
                            .addComponent(emailLabel))))
                .addContainerGap(252, Short.MAX_VALUE))
        );
        informacionPanelLayout.setVerticalGroup(
            informacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(informacionPanelLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(informacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(nombreCliLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                .addGroup(informacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(nroIdLabel))
                .addGap(50, 50, 50)
                .addGroup(informacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(emailLabel))
                .addGap(62, 62, 62)
                .addGroup(informacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(telefonoLabel))
                .addGap(70, 70, 70)
                .addGroup(informacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(puntosAcumuladosLabel))
                .addGap(68, 68, 68)
                .addGroup(informacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(dineroInvertidoLabel))
                .addGap(82, 82, 82))
        );

        clienteTabbedPane.addTab("Mi información", informacionPanel);

        puntosGanadosTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Puntos", "Viaje", "Fecha Salida", "Bus", "Empresa", "Fecha Creacion"
            }
        ));
        jScrollPane5.setViewportView(puntosGanadosTable);

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel11.setText("Historial Puntos Ganados");

        tablaPuntosRedimidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Tiquete", "Cliente", "Fecha Devolucion", "Monto Devuelto"
            }
        ));
        jScrollPane3.setViewportView(tablaPuntosRedimidos);

        jLabel16.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel16.setText("Historial Puntos Redimidos");

        javax.swing.GroupLayout puntosAcumuladosLayout = new javax.swing.GroupLayout(puntosAcumulados);
        puntosAcumulados.setLayout(puntosAcumuladosLayout);
        puntosAcumuladosLayout.setHorizontalGroup(
            puntosAcumuladosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, puntosAcumuladosLayout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(puntosAcumuladosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 714, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addGap(20, 20, 20))
            .addGroup(puntosAcumuladosLayout.createSequentialGroup()
                .addGroup(puntosAcumuladosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(puntosAcumuladosLayout.createSequentialGroup()
                        .addGap(285, 285, 285)
                        .addComponent(jLabel11))
                    .addGroup(puntosAcumuladosLayout.createSequentialGroup()
                        .addGap(292, 292, 292)
                        .addComponent(jLabel16)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        puntosAcumuladosLayout.setVerticalGroup(
            puntosAcumuladosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, puntosAcumuladosLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        clienteTabbedPane.addTab("Puntos Ganados", puntosAcumulados);

        tablaDevoluciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Tiquete", "Cliente", "Fecha Devolucion", "Monto Devuelto"
            }
        ));
        jScrollPane6.setViewportView(tablaDevoluciones);

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel4.setText("Historial Devoluciones");

        javax.swing.GroupLayout devolucionesPanelLayout = new javax.swing.GroupLayout(devolucionesPanel);
        devolucionesPanel.setLayout(devolucionesPanelLayout);
        devolucionesPanelLayout.setHorizontalGroup(
            devolucionesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(devolucionesPanelLayout.createSequentialGroup()
                .addGroup(devolucionesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(devolucionesPanelLayout.createSequentialGroup()
                        .addGap(295, 295, 295)
                        .addComponent(jLabel4))
                    .addGroup(devolucionesPanelLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 708, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        devolucionesPanelLayout.setVerticalGroup(
            devolucionesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(devolucionesPanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        clienteTabbedPane.addTab("Devoluciones", devolucionesPanel);

        jLabel13.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel13.setText("Selecciona Viaje");

        viajesTablaRese.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Destino", "Fecha Salida", "Fecha llegada", "Bus", "Vlr Unit"
            }
        ));
        viajesTablaRese.setSelectionBackground(new java.awt.Color(0, 0, 0));
        jScrollPane4.setViewportView(viajesTablaRese);

        reservarBtn.setBackground(new java.awt.Color(255, 102, 51));
        reservarBtn.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        reservarBtn.setText("Reservar Viaje");
        reservarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reservarBtnActionPerformed(evt);
            }
        });

        filtroOpcionesCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Destino", "Fecha", "Ambos" }));

        filtroDestinoField.setToolTipText("Fecha");

        tituloViajeLabel.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        tituloViajeLabel.setText("Registro de todas sus reservas");

        tablaReservas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Viaje", "Bus", "Vlr Unit", "Fecha", "Metodo Pago", "Fecha Reserva", "Estado"
            }
        ));
        tablaReservas.setSelectionBackground(new java.awt.Color(0, 0, 0));
        jScrollPane8.setViewportView(tablaReservas);

        filtrarBtn.setBackground(new java.awt.Color(0, 204, 102));
        filtrarBtn.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        filtrarBtn.setText("Filtrar");
        filtrarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filtrarBtnActionPerformed(evt);
            }
        });

        filtroFechaField.setToolTipText("Destino");

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        jLabel5.setText("Destino");

        jLabel6.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        jLabel6.setText("Fecha Salida (dd/MM/aaaa)");

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel7.setText("Cantidad");

        cancelarReservaBtn.setBackground(new java.awt.Color(255, 153, 102));
        cancelarReservaBtn.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        cancelarReservaBtn.setText("Cancelar Reserva");
        cancelarReservaBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarReservaBtnActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel15.setText("Selecciona Reserva");

        jLabel17.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel17.setText("en la tabla ");

        jLabel18.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel18.setText("en la tabla");

        javax.swing.GroupLayout reservarTiqPanelLayout = new javax.swing.GroupLayout(reservarTiqPanel);
        reservarTiqPanel.setLayout(reservarTiqPanelLayout);
        reservarTiqPanelLayout.setHorizontalGroup(
            reservarTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reservarTiqPanelLayout.createSequentialGroup()
                .addGroup(reservarTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(reservarTiqPanelLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(reservarTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(reservarTiqPanelLayout.createSequentialGroup()
                                .addGroup(reservarTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(reservarTiqPanelLayout.createSequentialGroup()
                                        .addComponent(filtroOpcionesCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(42, 42, 42)
                                        .addGroup(reservarTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(filtroDestinoField, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel5))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(reservarTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(reservarTiqPanelLayout.createSequentialGroup()
                                                .addComponent(jLabel6)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 91, Short.MAX_VALUE))
                                            .addGroup(reservarTiqPanelLayout.createSequentialGroup()
                                                .addComponent(filtroFechaField, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(filtrarBtn))))
                                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 564, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(reservarTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(reservarTiqPanelLayout.createSequentialGroup()
                                        .addGap(37, 37, 37)
                                        .addGroup(reservarTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(reservarBtn)
                                            .addComponent(jLabel15)
                                            .addComponent(cancelarReservaBtn)))
                                    .addGroup(reservarTiqPanelLayout.createSequentialGroup()
                                        .addGap(56, 56, 56)
                                        .addComponent(jLabel18))))
                            .addGroup(reservarTiqPanelLayout.createSequentialGroup()
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 547, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(reservarTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(reservarTiqPanelLayout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cantidadReserField, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(reservarTiqPanelLayout.createSequentialGroup()
                                        .addGap(51, 51, 51)
                                        .addComponent(jLabel13))
                                    .addGroup(reservarTiqPanelLayout.createSequentialGroup()
                                        .addGap(66, 66, 66)
                                        .addComponent(jLabel17))))))
                    .addGroup(reservarTiqPanelLayout.createSequentialGroup()
                        .addGap(179, 179, 179)
                        .addComponent(tituloViajeLabel)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        reservarTiqPanelLayout.setVerticalGroup(
            reservarTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reservarTiqPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(reservarTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(reservarTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(filtroDestinoField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filtroFechaField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filtroOpcionesCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filtrarBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(reservarTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(reservarTiqPanelLayout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(reservarTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(cantidadReserField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(reservarBtn)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(reservarTiqPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(reservarTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(reservarTiqPanelLayout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel18)
                        .addGap(18, 18, 18)
                        .addComponent(cancelarReservaBtn))
                    .addGroup(reservarTiqPanelLayout.createSequentialGroup()
                        .addComponent(tituloViajeLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        clienteTabbedPane.addTab("Reservar", reservarTiqPanel);

        tablaNotificaciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Mensaje", "Fecha envío"
            }
        ));
        jScrollPane7.setViewportView(tablaNotificaciones);

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel12.setText("HISTORIAL DE NOTIFICACIONES DE POSIBLES TIQUETES");

        javax.swing.GroupLayout notificacionesPanelLayout = new javax.swing.GroupLayout(notificacionesPanel);
        notificacionesPanel.setLayout(notificacionesPanelLayout);
        notificacionesPanelLayout.setHorizontalGroup(
            notificacionesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(notificacionesPanelLayout.createSequentialGroup()
                .addGroup(notificacionesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(notificacionesPanelLayout.createSequentialGroup()
                        .addGap(210, 210, 210)
                        .addComponent(jLabel12))
                    .addGroup(notificacionesPanelLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 682, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        notificacionesPanelLayout.setVerticalGroup(
            notificacionesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(notificacionesPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        clienteTabbedPane.addTab("Notificaciones", notificacionesPanel);

        cerrarSesionBtn.setBackground(new java.awt.Color(0, 0, 0));
        cerrarSesionBtn.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        cerrarSesionBtn.setForeground(new java.awt.Color(255, 255, 255));
        cerrarSesionBtn.setText("Cerrar Sesión");
        cerrarSesionBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cerrarSesionBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(clienteTabbedPane)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(cerrarSesionBtn)
                .addGap(52, 52, 52))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(clienteTabbedPane)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cerrarSesionBtn)
                .addGap(12, 12, 12))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cerrarSesionBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cerrarSesionBtnActionPerformed
        // TODO add your handling code here:
        new VistaAccesoUsuario().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_cerrarSesionBtnActionPerformed

    private void cancelarReservaBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarReservaBtnActionPerformed
        try {
            if (idReservaSeleccionado != -1) {
                boolean desencoloMismo = cr.cancelarReserva(idReservaSeleccionado);
                llenarTablaViajesRes(cr.getViajesAll());
                llenarTablaReservas();
                if (desencoloMismo) {
                    llenarTablaNotificaciones();
                }
                JOptionPane.showMessageDialog(this, "Reserva cancelada correctamente", "Cancelación Reserva", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Por favor selecciona una reserva de la tabla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_cancelarReservaBtnActionPerformed

    private void filtrarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filtrarBtnActionPerformed
        try {
            llenarTablaViajesRes(cr.filtrarViajes(filtroOpcionesCombo.getSelectedItem() + "", filtroDestinoField.getText(), filtroFechaField.getText()));
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "", JOptionPane.ERROR_MESSAGE);
            llenarTablaViajesRes(cr.getViajesAll());
        }
    }//GEN-LAST:event_filtrarBtnActionPerformed

    private void reservarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reservarBtnActionPerformed
        try {
            if (idViajeSeleccionado != -1) {
                int cantidad = Integer.parseInt(cantidadReserField.getText());

                cr.realizarReserva(idViajeSeleccionado, cantidad);
                llenarTablaReservas();
                llenarTablaViajesRes(cr.getViajesAll());
                JOptionPane.showMessageDialog(this, "Reserva hecha con éxito!", "Reserva", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Por favor selecciona un viaje de la tabla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser un número valido", "Error de formato", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_reservarBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelarReservaBtn;
    private javax.swing.JTextField cantidadReserField;
    private javax.swing.JButton cerrarSesionBtn;
    private javax.swing.JTabbedPane clienteTabbedPane;
    private javax.swing.JPanel devolucionesPanel;
    private javax.swing.JLabel dineroInvertidoLabel;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JButton filtrarBtn;
    private javax.swing.JTextField filtroDestinoField;
    private javax.swing.JTextField filtroFechaField;
    private javax.swing.JComboBox<String> filtroOpcionesCombo;
    private javax.swing.JPanel informacionPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JLabel nombreCliLabel;
    private javax.swing.JPanel notificacionesPanel;
    private javax.swing.JLabel nroIdLabel;
    private javax.swing.JPanel puntosAcumulados;
    private javax.swing.JLabel puntosAcumuladosLabel;
    private javax.swing.JTable puntosGanadosTable;
    private javax.swing.JButton reservarBtn;
    private javax.swing.JPanel reservarTiqPanel;
    private javax.swing.JTable tablaDevoluciones;
    private javax.swing.JTable tablaNotificaciones;
    private javax.swing.JTable tablaPuntosRedimidos;
    private javax.swing.JTable tablaReservas;
    private javax.swing.JLabel telefonoLabel;
    private javax.swing.JLabel tituloViajeLabel;
    private javax.swing.JTable viajesTablaRese;
    // End of variables declaration//GEN-END:variables
}
