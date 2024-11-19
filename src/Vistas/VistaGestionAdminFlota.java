/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vistas;

import Controladores.ControladorBuses;
import Controladores.ControladorDevolEmpresa;
import Controladores.ControladorReservasEmpr;
import Controladores.ControladorTiquetes;
import Controladores.ControladorViajes;

import Modelos.Bus;
import Modelos.Devolucion;
import Modelos.Reserva;
import Modelos.Tiquete;
import Modelos.Usuarios.AdminFlota;
import Modelos.Usuarios.Cliente;
import Modelos.Viaje;
import Utils.IList;
import javax.swing.DefaultComboBoxModel;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author PC
 */
public class VistaGestionAdminFlota extends javax.swing.JFrame {
    ControladorBuses cb;
    ControladorViajes cv;
    ControladorTiquetes ct;
    ControladorDevolEmpresa cde;
    ControladorReservasEmpr cre;

    int idViajeSeleccionado = -1;
    int idTiqueteSeleccionado = -1;
    int idViajeReservaSeleccionado = -1;
    int idReservaSeleccionado = -1;

    public VistaGestionAdminFlota(AdminFlota admin) {
        initComponents();
        setLocationRelativeTo(this);

        int idAdmin = admin.getNroId();
        nombreAdminLabel.setText(admin.getName());
        // Creacion de controladores de cada tab
        this.cb = new ControladorBuses(idAdmin);
        this.cv = new ControladorViajes(idAdmin);
        this.ct = new ControladorTiquetes(idAdmin);
        this.cde = new ControladorDevolEmpresa(idAdmin);
        this.cre = new ControladorReservasEmpr(idAdmin);
        // Refresco de las tablas
        llenarTablaBuses();
        llenarTablaViajesSegunControlador("viajes");
        llenarTablaViajesSegunControlador("tiquetes");
        llenarTablaViajesSegunControlador("reservas");
        llenarTablaDevoluciones();

        alistarPlacasBusesCombobox();

        configurarSeleccionTablaViajes();
        configurarSeleccionTablaTiquetes();
        configurarSeleccionTablaViajesRese();
        configurarSeleccionTablaReservas();
    }

    // METODOS PRIVADOS DEL TAB BUSES
    private void limpiarCamposBus() {
        placaBusField.setText("");
        marcaBusField.setText("");
        tipoBusField.setText("");
        nroPuestosBusField.setText("");
    }
    private void llenarTablaBuses() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"Placa", "Marca", "Tipo", "Nro Puestos"});
        for (int i = 0; i < cb.getBuses().size(); i++) {
            Bus bus = cb.getBuses().get(i);

            model.addRow(new Object[]{
                bus.getPlaca(),
                bus.getMarca(),
                bus.getTipo(),
                bus.getPuestos()
            });
        }
        busesTabla.setModel(model);
        plazasDispLabel.setText(cb.getBuses().size() + "/" + cb.getPlazasEstacionamiento());
    }
    // METODOS PRIVADOS DEL TAB DE VIAJES
    private void alistarPlacasBusesCombobox() {
        DefaultComboBoxModel<String> comboBoxPlacasBuses = new DefaultComboBoxModel<>();
        for (int i = 0; i < cv.getBuses().size(); i++) {
            comboBoxPlacasBuses.addElement(cv.getBuses().get(i).getPlaca());
        }

        busesCoBox.setModel(comboBoxPlacasBuses);
    }
    private void llenarTablaViajesSegunControlador(String tipoControlador) {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"ID", "Destino", "Fecha Salida", "Fecha llegada", "Bus", "Vlr Unit"});
        
        IList<Viaje> viajes = tipoControlador.equals("viajes") ? cv.getViajes() : 
                tipoControlador.equals("tiquetes") ? ct.getViajes() : tipoControlador.equals("reservas") ?
                cre.getViajesReservas() : null;
        for (int i = 0; i < viajes.size(); i++) {
            model.addRow(new Object[]{
                viajes.get(i).getId(),
                viajes.get(i).getDestino(),
                viajes.get(i).getFechaSalidaStr(),
                viajes.get(i).getFechaLlegadaStr(),
                viajes.get(i).getBus().getPlaca(),
                viajes.get(i).getVlrUnit()
            });
        }
        JTable viajesTablaGeneral = tipoControlador.equals("viajes") ? this.viajesTabla : 
                tipoControlador.equals("tiquetes") ? this.viajesTablaTiq : tipoControlador.equals("reservas") ?
                this.viajesTablaRes : null;
        viajesTablaGeneral.setModel(model);
        // Ajustar el ancho de las columnas
        viajesTablaGeneral.getColumnModel().getColumn(0).setPreferredWidth(30);  // Ancho más pequeño para la columna N°
        viajesTablaGeneral.getColumnModel().getColumn(1).setPreferredWidth(150); // Ancho estándar para "Destino"
        viajesTablaGeneral.getColumnModel().getColumn(2).setPreferredWidth(165); // Ancho ligeramente mayor para "Fecha Salida"
        viajesTablaGeneral.getColumnModel().getColumn(3).setPreferredWidth(165); // Ancho ligeramente mayor para "Fecha Llegada"
        viajesTablaGeneral.getColumnModel().getColumn(4).setPreferredWidth(90); // Ancho estándar para "Bus"
        viajesTablaGeneral.getColumnModel().getColumn(5).setPreferredWidth(120);  // Ancho estándar para "Vlr Unit"
    }
    //------------------------- METODOS PRIVADOS DEL TAB DE TIQUETES -------------------------------------------------
    private void configurarSeleccionTablaViajes() {
        viajesTablaTiq.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                int filaSeleccionada = viajesTablaTiq.getSelectedRow();

                if (filaSeleccionada != -1) {
                    Object idViaje = viajesTablaTiq.getValueAt(filaSeleccionada, 0);
                    this.idViajeSeleccionado = Integer.parseInt(idViaje.toString());
                    actualizarTiquetes();
                    

                    this.idTiqueteSeleccionado = -1;
                }
            }
        });
    }
    private void actualizarTiquetes() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"ID", "Cliente", "Metodo Pago", "Fecha Compra"});
        for (int i = 0; i < ct.getTiquetes(idViajeSeleccionado).size(); i++) {
            Tiquete tiquete = ct.getTiquetes(idViajeSeleccionado).get(i);
            model.addRow(new Object[]{
                tiquete.getId(),
                tiquete.getCliente().getNroId() + ". " + tiquete.getCliente().getName(),
                tiquete.getMetodoPago(),
                tiquete.getFechaCompraStr()
            });
        }

        tablaTiquetes.setModel(model);

        Viaje viaje = ct.buscarViajePorId(idViajeSeleccionado);
        tituloViajeLabel.setText("Tiquetes del Viaje a " + viaje.getDestino() + " en el bus '" + viaje.getBus().getPlaca() + "' para el " + viaje.getFechaSalidaStr());
        puestosDisLabel.setText("Puestos Disponibles del viaje: " + viaje.getPuestosDesocupados() + "/" + viaje.getBus().getPuestos());
        verColaEperaBtn.setEnabled(!viaje.getColaEspera().isEmpty());
        
        venderTiquete.setText(viaje.getPuestosDesocupados() != 0 ? "Vender Tiquete" : "Enviar a cola de Espera" );
        
    }
    private void configurarSeleccionTablaTiquetes() {
        tablaTiquetes.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                int filaSeleccionada = tablaTiquetes.getSelectedRow();

                if (filaSeleccionada != -1) {
                    Object idTiquete = tablaTiquetes.getValueAt(filaSeleccionada, 0);
                    this.idTiqueteSeleccionado = Integer.parseInt(idTiquete.toString());
                }
            }
        });
    }
    //------------------------- METODOS PRIVADOS DEL TAB DE RESERVAS -------------------------
    private void configurarSeleccionTablaViajesRese() {
        viajesTablaRes.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                int filaSeleccionada = viajesTablaRes.getSelectedRow();

                if (filaSeleccionada != -1) {
                    Object idViaje = viajesTablaRes.getValueAt(filaSeleccionada, 0);
                    this.idViajeReservaSeleccionado = Integer.parseInt(idViaje.toString());
                    llenarTablaReservas();
                }
            }
        });
    }
    private void llenarTablaReservas() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"ID", "Cliente", "Fecha Reserva", "Estado"});
        for (int i = 0; i < cre.getReservas(idViajeReservaSeleccionado).size(); i++) {
            Reserva reserva = cre.getReservas(idViajeReservaSeleccionado).get(i);
            model.addRow(new Object[]{
                reserva.getId(),
                reserva.getCliente().getNroId() + ". " + reserva.getCliente().getName(),
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
    // ------------------------- METODOS PRIVADOS DEL TAB DE DEVOLUCIONES -------------------------
    private void llenarTablaDevoluciones() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"ID Tiquete", "Viaje", "Fecha Salida", "Cliente", "Fecha Devolucion", "Tipo", "Resultado Puntos"});
        for (int i = 0; i < cde.getDevoluciones().size(); i++) {
            Devolucion devolucion = cde.getDevoluciones().get(i);
            Tiquete tiquete = devolucion.getMovimiento().getTiquete();
            model.addRow(new Object[]{
                tiquete.getId(),
                tiquete.getViaje().getId() + ". " + tiquete.getViaje().getDestino(),
                tiquete.getViaje().getFechaSalidaStr(),
                tiquete.getCliente().getNroId() + ". " + tiquete.getCliente().getName(),
                devolucion.getFechaDevolucionStr(),
                tiquete.getMetodoPago(),
                devolucion.getResultadoPuntos()
            });
        }

        tablaDevoluciones.setModel(model);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPaneAdminFlota = new javax.swing.JTabbedPane();
        gestionBusesPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        plazasDispLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        placaBusField = new javax.swing.JTextField();
        nroPuestosBusField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        agregarBusBtn = new javax.swing.JButton();
        eliminarBusBtn = new javax.swing.JButton();
        editarBusBtn = new javax.swing.JButton();
        buscarBusBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        busesTabla = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        marcaBusField = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        tipoBusField = new javax.swing.JTextField();
        gestionViajesPanel = new javax.swing.JPanel();
        panelContainer = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        destinoField = new javax.swing.JTextField();
        horaSalField = new javax.swing.JTextField();
        crearViajeBtn = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        viajesTabla = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        fechaLlegField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        horaLlegField = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        busesCoBox = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        vlrUnitField = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        fechaSalField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        gestionVentaTiqPanel = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        cantidadTiquete = new javax.swing.JTextField();
        venderTiquete = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        viajesTablaTiq = new javax.swing.JTable();
        idClienteField = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        tituloViajeLabel = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaTiquetes = new javax.swing.JTable();
        buscarClienteBtn = new javax.swing.JButton();
        infoClienteLabel = new javax.swing.JLabel();
        puestosDisLabel = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        hacerDevolucionBtn = new javax.swing.JButton();
        metodoPagoCombo = new javax.swing.JComboBox<>();
        jLabel26 = new javax.swing.JLabel();
        verColaEperaBtn = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        viajesTablaRes = new javax.swing.JTable();
        tituloViajeLabel1 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tablaReservas = new javax.swing.JTable();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        hacerEfectivaBtn = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        metodoPagoReservaCombo = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablaDevoluciones = new javax.swing.JTable();
        jLabel25 = new javax.swing.JLabel();
        cerrarSesionBtn = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        nombreAdminLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel1.setText("Plazas Ocupadas:");

        plazasDispLabel.setText("0/0");

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel3.setText("Placa");

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel4.setText("Numero de ");

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel5.setText("Puestos");

        agregarBusBtn.setText("Agregar");
        agregarBusBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarBusBtnActionPerformed(evt);
            }
        });

        eliminarBusBtn.setText("Eliminar");
        eliminarBusBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarBusBtnActionPerformed(evt);
            }
        });

        editarBusBtn.setText("Editar");
        editarBusBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarBusBtnActionPerformed(evt);
            }
        });

        buscarBusBtn.setText("Buscar");
        buscarBusBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarBusBtnActionPerformed(evt);
            }
        });

        busesTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Placa", "Marca", "Tipo", "Numero Puestos"
            }
        ));
        jScrollPane1.setViewportView(busesTabla);

        jLabel15.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel15.setText("Marca");

        jLabel16.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel16.setText("Tipo");

        javax.swing.GroupLayout gestionBusesPanelLayout = new javax.swing.GroupLayout(gestionBusesPanel);
        gestionBusesPanel.setLayout(gestionBusesPanelLayout);
        gestionBusesPanelLayout.setHorizontalGroup(
            gestionBusesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gestionBusesPanelLayout.createSequentialGroup()
                .addGroup(gestionBusesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, gestionBusesPanelLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(gestionBusesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(gestionBusesPanelLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(48, 48, 48)
                                .addComponent(placaBusField, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(gestionBusesPanelLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(45, 45, 45)
                                .addComponent(plazasDispLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(gestionBusesPanelLayout.createSequentialGroup()
                                .addGroup(gestionBusesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(gestionBusesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(gestionBusesPanelLayout.createSequentialGroup()
                                            .addComponent(jLabel15)
                                            .addGap(32, 32, 32))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, gestionBusesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel4)))
                                    .addComponent(jLabel16))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(gestionBusesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(gestionBusesPanelLayout.createSequentialGroup()
                                        .addComponent(nroPuestosBusField, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, gestionBusesPanelLayout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(gestionBusesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(marcaBusField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(tipoBusField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, gestionBusesPanelLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(gestionBusesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(editarBusBtn)
                            .addComponent(agregarBusBtn))
                        .addGap(39, 39, 39)
                        .addGroup(gestionBusesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(eliminarBusBtn)
                            .addComponent(buscarBusBtn))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 111, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
        );
        gestionBusesPanelLayout.setVerticalGroup(
            gestionBusesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gestionBusesPanelLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(gestionBusesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(gestionBusesPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(58, Short.MAX_VALUE))
                    .addGroup(gestionBusesPanelLayout.createSequentialGroup()
                        .addGroup(gestionBusesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(placaBusField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39)
                        .addGroup(gestionBusesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(marcaBusField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(gestionBusesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(tipoBusField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addGroup(gestionBusesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(gestionBusesPanelLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5))
                            .addComponent(nroPuestosBusField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(gestionBusesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(plazasDispLabel))
                        .addGap(41, 41, 41)
                        .addGroup(gestionBusesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(agregarBusBtn)
                            .addComponent(eliminarBusBtn))
                        .addGap(18, 18, 18)
                        .addGroup(gestionBusesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(buscarBusBtn)
                            .addComponent(editarBusBtn))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jTabbedPaneAdminFlota.addTab("Gestion Buses", gestionBusesPanel);

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel8.setText("Destino");

        crearViajeBtn.setText("Crear Viaje");
        crearViajeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crearViajeBtnActionPerformed(evt);
            }
        });

        viajesTabla.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(viajesTabla);

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel6.setText("Hora Salida");

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel7.setText("Fecha llegada");

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel9.setText("Hora llegada");

        horaLlegField.setName(""); // NOI18N

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel10.setText("Bus");

        busesCoBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel11.setText("Valor Unitario");

        jLabel17.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel17.setText("Fecha Salida");

        jLabel2.setFont(new java.awt.Font("Ebrima", 0, 10)); // NOI18N
        jLabel2.setText("dd/mm/aaaa");

        jLabel18.setFont(new java.awt.Font("Ebrima", 0, 10)); // NOI18N
        jLabel18.setText("HH:mm");

        jLabel19.setFont(new java.awt.Font("Ebrima", 0, 10)); // NOI18N
        jLabel19.setText("dd/mm/aaaa");

        jLabel20.setFont(new java.awt.Font("Ebrima", 0, 10)); // NOI18N
        jLabel20.setText("HH:mm");

        javax.swing.GroupLayout panelContainerLayout = new javax.swing.GroupLayout(panelContainer);
        panelContainer.setLayout(panelContainerLayout);
        panelContainerLayout.setHorizontalGroup(
            panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(panelContainerLayout.createSequentialGroup()
                            .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6)
                                .addComponent(jLabel18)
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(fechaLlegField, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelContainerLayout.createSequentialGroup()
                            .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel11)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel20)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelContainerLayout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(horaLlegField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(busesCoBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(vlrUnitField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(panelContainerLayout.createSequentialGroup()
                                    .addGap(32, 32, 32)
                                    .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(horaSalField, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(destinoField, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                                            .addComponent(fechaSalField, javax.swing.GroupLayout.Alignment.TRAILING)))
                                    .addGap(0, 0, Short.MAX_VALUE)))))
                    .addGroup(panelContainerLayout.createSequentialGroup()
                        .addGap(87, 87, 87)
                        .addComponent(crearViajeBtn)))
                .addGap(53, 53, 53)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 498, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(9, Short.MAX_VALUE))
        );
        panelContainerLayout.setVerticalGroup(
            panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelContainerLayout.createSequentialGroup()
                        .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(destinoField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelContainerLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2))
                            .addGroup(panelContainerLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(fechaSalField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelContainerLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(horaSalField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                        .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fechaLlegField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelContainerLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel19)))
                        .addGap(26, 26, 26)
                        .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelContainerLayout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel20))
                            .addComponent(horaLlegField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(busesCoBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(vlrUnitField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addComponent(crearViajeBtn))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout gestionViajesPanelLayout = new javax.swing.GroupLayout(gestionViajesPanel);
        gestionViajesPanel.setLayout(gestionViajesPanelLayout);
        gestionViajesPanelLayout.setHorizontalGroup(
            gestionViajesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gestionViajesPanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(panelContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        gestionViajesPanelLayout.setVerticalGroup(
            gestionViajesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gestionViajesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPaneAdminFlota.addTab("Gestion Viajes", gestionViajesPanel);

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel12.setText("ID de Cliente");

        jLabel14.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel14.setText("Método Pago");

        venderTiquete.setText("Vender Tiquete");
        venderTiquete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                venderTiqueteActionPerformed(evt);
            }
        });

        viajesTablaTiq.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(viajesTablaTiq);

        jLabel13.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel13.setText("SELECCIONAR VIAJE");

        tituloViajeLabel.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        tituloViajeLabel.setText("Tiquetes del Viaje");

        tablaTiquetes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Cliente", "Metodo Pago", "Fecha Compra"
            }
        ));
        jScrollPane5.setViewportView(tablaTiquetes);

        buscarClienteBtn.setText("Buscar Cliente");
        buscarClienteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarClienteBtnActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel21.setText("Selecciona el tiquete para");

        jLabel22.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel22.setText("hacer su Devolución");

        hacerDevolucionBtn.setText("Hacer Devolución");
        hacerDevolucionBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hacerDevolucionBtnActionPerformed(evt);
            }
        });

        metodoPagoCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Efectivo", "Puntos" }));

        jLabel26.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel26.setText("Cantidad");

        verColaEperaBtn.setText("Ver cola de espera");
        verColaEperaBtn.setEnabled(false);
        verColaEperaBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verColaEperaBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout gestionVentaTiqPanelLayout = new javax.swing.GroupLayout(gestionVentaTiqPanel);
        gestionVentaTiqPanel.setLayout(gestionVentaTiqPanelLayout);
        gestionVentaTiqPanelLayout.setHorizontalGroup(
            gestionVentaTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gestionVentaTiqPanelLayout.createSequentialGroup()
                .addGroup(gestionVentaTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(gestionVentaTiqPanelLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(gestionVentaTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 568, Short.MAX_VALUE)
                            .addComponent(jScrollPane4)))
                    .addGroup(gestionVentaTiqPanelLayout.createSequentialGroup()
                        .addGap(249, 249, 249)
                        .addComponent(jLabel13))
                    .addGroup(gestionVentaTiqPanelLayout.createSequentialGroup()
                        .addGap(143, 143, 143)
                        .addComponent(tituloViajeLabel)))
                .addGroup(gestionVentaTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(gestionVentaTiqPanelLayout.createSequentialGroup()
                        .addGap(0, 30, Short.MAX_VALUE)
                        .addGroup(gestionVentaTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, gestionVentaTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, gestionVentaTiqPanelLayout.createSequentialGroup()
                                    .addComponent(buscarClienteBtn)
                                    .addGap(63, 63, 63))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, gestionVentaTiqPanelLayout.createSequentialGroup()
                                    .addComponent(infoClienteLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap())
                                .addGroup(gestionVentaTiqPanelLayout.createSequentialGroup()
                                    .addGroup(gestionVentaTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, gestionVentaTiqPanelLayout.createSequentialGroup()
                                            .addComponent(jLabel12)
                                            .addGap(18, 18, 18)
                                            .addComponent(idClienteField, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(21, 21, 21))
                                        .addGroup(gestionVentaTiqPanelLayout.createSequentialGroup()
                                            .addComponent(jLabel26)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(cantidadTiquete, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(gestionVentaTiqPanelLayout.createSequentialGroup()
                                            .addComponent(jLabel14)
                                            .addGap(18, 18, 18)
                                            .addComponent(metodoPagoCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGap(16, 16, 16)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, gestionVentaTiqPanelLayout.createSequentialGroup()
                                .addGroup(gestionVentaTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel22)
                                    .addGroup(gestionVentaTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(hacerDevolucionBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(69, 69, 69))))
                    .addGroup(gestionVentaTiqPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(gestionVentaTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(gestionVentaTiqPanelLayout.createSequentialGroup()
                                .addGap(0, 115, Short.MAX_VALUE)
                                .addComponent(verColaEperaBtn)
                                .addContainerGap())
                            .addGroup(gestionVentaTiqPanelLayout.createSequentialGroup()
                                .addComponent(puestosDisLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 17, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, gestionVentaTiqPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(venderTiquete, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37))))
        );
        gestionVentaTiqPanelLayout.setVerticalGroup(
            gestionVentaTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gestionVentaTiqPanelLayout.createSequentialGroup()
                .addGroup(gestionVentaTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(gestionVentaTiqPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(gestionVentaTiqPanelLayout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(gestionVentaTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(idClienteField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buscarClienteBtn)
                        .addGroup(gestionVentaTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(gestionVentaTiqPanelLayout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(infoClienteLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(gestionVentaTiqPanelLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(gestionVentaTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cantidadTiquete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel26))))
                        .addGap(18, 18, 18)
                        .addGroup(gestionVentaTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(metodoPagoCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(venderTiquete)))
                .addGroup(gestionVentaTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(gestionVentaTiqPanelLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(verColaEperaBtn))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, gestionVentaTiqPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tituloViajeLabel)))
                .addGroup(gestionVentaTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(gestionVentaTiqPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(gestionVentaTiqPanelLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(puestosDisLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(hacerDevolucionBtn)))
                .addGap(23, 23, 23))
        );

        jTabbedPaneAdminFlota.addTab("Venta Tiquetes", gestionVentaTiqPanel);

        jLabel27.setFont(new java.awt.Font("Ebrima", 1, 12)); // NOI18N
        jLabel27.setText("Seleccionar Viaje para ver sus reservas activas");

        viajesTablaRes.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane7.setViewportView(viajesTablaRes);

        tituloViajeLabel1.setText("Reservas del Viaje");

        tablaReservas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Cliente", "Metodo Pago", "Fecha Reserva", "Estado"
            }
        ));
        jScrollPane8.setViewportView(tablaReservas);

        jLabel28.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel28.setText("Selecciona la reserva");

        jLabel29.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel29.setText("HACER EFECTIVA UNA RESERVA");

        hacerEfectivaBtn.setText("Hacer efectiva");
        hacerEfectivaBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hacerEfectivaBtnActionPerformed(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel30.setText("que quiere hacer efectiva");

        jLabel31.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel31.setText("Metodo de Pago");

        metodoPagoReservaCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "efectivo", "puntos" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(tituloViajeLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jLabel27))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(309, 309, 309)
                        .addComponent(jLabel29))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 790, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 524, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel30)
                                            .addComponent(jLabel28))
                                        .addGap(58, 58, 58))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(96, 96, 96)
                                                .addComponent(hacerEfectivaBtn))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(36, 36, 36)
                                                .addComponent(jLabel31)
                                                .addGap(33, 33, 33)
                                                .addComponent(metodoPagoReservaCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(0, 0, Short.MAX_VALUE)))))))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tituloViajeLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel30)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel31)
                            .addComponent(metodoPagoReservaCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)
                        .addComponent(hacerEfectivaBtn)
                        .addGap(0, 31, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPaneAdminFlota.addTab("Reservas", jPanel1);

        jLabel23.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel23.setText("HISTORIAL DE DEVOLUCIONES");

        tablaDevoluciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Tiquete", "Viaje", "Fecha Salida", "Cliente", "Fecha Devolucion", "Tipo", "Resultado Puntos "
            }
        ));
        jScrollPane6.setViewportView(tablaDevoluciones);

        jLabel25.setText("(Si desea hacer devolucion, es en la pestaña de tiquetes)");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(140, 140, 140)
                        .addComponent(jLabel23)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel25))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 793, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPaneAdminFlota.addTab("Devoluciones", jPanel2);

        cerrarSesionBtn.setText("Cerrar sesión");
        cerrarSesionBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cerrarSesionBtnActionPerformed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Lucida Sans", 1, 12)); // NOI18N
        jLabel24.setText("Admin Flota:");

        nombreAdminLabel.setFont(new java.awt.Font("Lucida Sans", 0, 12)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPaneAdminFlota, javax.swing.GroupLayout.PREFERRED_SIZE, 837, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nombreAdminLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cerrarSesionBtn)
                .addGap(42, 42, 42))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPaneAdminFlota, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nombreAdminLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cerrarSesionBtn)
                        .addComponent(jLabel24)))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cerrarSesionBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cerrarSesionBtnActionPerformed
        new VistaAccesoUsuario().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_cerrarSesionBtnActionPerformed
    // ------------------- METODOS PERFORMED DEL TAB BUSES ----------------------
    private void agregarBusBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarBusBtnActionPerformed
        try {
            String placa = placaBusField.getText();
            String marca = marcaBusField.getText();
            String tipo = tipoBusField.getText();
            int nroPuestos = Integer.parseInt(nroPuestosBusField.getText());

            cb.agregarBus(new Bus(placa, marca, tipo, nroPuestos));
            llenarTablaBuses();
            limpiarCamposBus();
            alistarPlacasBusesCombobox();
            JOptionPane.showMessageDialog(this, "Bus agregado correctamente");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El numero de puestos debe ser un número válido", "Error de formato", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_agregarBusBtnActionPerformed
    private void eliminarBusBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarBusBtnActionPerformed
        try {
            String placa = placaBusField.getText();
            cb.eliminarBus(placa);
            llenarTablaBuses();
            alistarPlacasBusesCombobox();
            JOptionPane.showMessageDialog(this, "Bus eliminado correctamente");
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_eliminarBusBtnActionPerformed
    private void editarBusBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarBusBtnActionPerformed
        try {
            String placa = placaBusField.getText();
            String marca = marcaBusField.getText();
            String tipo = tipoBusField.getText();
            int nroPuestos = Integer.parseInt(nroPuestosBusField.getText());

            cb.editarBus(new Bus(placa, marca, tipo, nroPuestos));
            llenarTablaBuses();
            limpiarCamposBus();
            JOptionPane.showMessageDialog(this, "Bus editado correctamente");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El numero de puestos debe ser un número válido", "Error de formato", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_editarBusBtnActionPerformed
    private void buscarBusBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarBusBtnActionPerformed

        String placa = placaBusField.getText();
        Bus bus = cb.buscarBusPorPlaca(placa);

        if (bus == null) {
            JOptionPane.showMessageDialog(this, "No se encuentra bus con esta placa", "", JOptionPane.ERROR_MESSAGE);
        } else {
            marcaBusField.setText(bus.getMarca());
            tipoBusField.setText(bus.getTipo());
            nroPuestosBusField.setText(bus.getPuestos() + "");
        }
    }//GEN-LAST:event_buscarBusBtnActionPerformed
    // ------------------- METODOS PERFORMED DEL TAB VIAJES ----------------------
    private void crearViajeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crearViajeBtnActionPerformed
        try {
            String destino = destinoField.getText();
            String fechaSal = fechaSalField.getText();
            String horaSal = horaSalField.getText();
            String fechaLle = fechaLlegField.getText();
            String horaLle = horaLlegField.getText();
            String placaBus = (String) busesCoBox.getSelectedItem();
            int vlrUnit = Integer.parseInt(vlrUnitField.getText());

            cv.agregarViaje(destino, fechaSal, horaSal, fechaLle, horaLle, placaBus, vlrUnit);
            llenarTablaViajesSegunControlador("viajes");
            llenarTablaViajesSegunControlador("tiquetes");
            JOptionPane.showMessageDialog(this, "Viaje agregado correctamente");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El valor unitario debe ser un número valido", "Error de formato", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_crearViajeBtnActionPerformed
    // ------------------- METODOS PERFORMED DEL TAB DE VENTA DE TIQUETES ----------------------
    private void venderTiqueteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_venderTiqueteActionPerformed
        try {
            if (idViajeSeleccionado != -1) {
                int idCliente = Integer.parseInt(idClienteField.getText());
                int cantidad = Integer.parseInt(cantidadTiquete.getText());
                int metodoPago = Integer.parseInt(metodoPagoCombo.getSelectedIndex() + "");
                
                boolean esEncolado = ct.venderTiquete(idViajeSeleccionado, idCliente, cantidad, metodoPago);
                if (!esEncolado) {
                    actualizarTiquetes();
                    JOptionPane.showMessageDialog(this, "Tiquete vendido con éxito!", "Venta", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    verColaEperaBtn.setEnabled(true);
                    JOptionPane.showMessageDialog(this, "El cliente con ID '" + idCliente + "' acaba de pasar a cola de espera", "Cola de Espera", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor selecciona un viaje de la tabla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser un número valido", "Error de formato", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_venderTiqueteActionPerformed
    private void buscarClienteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarClienteBtnActionPerformed
        try {
            int idCliente = Integer.parseInt(idClienteField.getText());
            Cliente cliente = ct.buscarClientePorId(idCliente);
            infoClienteLabel.setText("ID: " + cliente.getNroId() + " | Nombre: " + cliente.getName() + " | Puntos: " + cliente.getPuntosAcumulados());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El id debe ser un número valido", "Error de formato", JOptionPane.ERROR_MESSAGE);
            infoClienteLabel.setText("");
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "", JOptionPane.ERROR_MESSAGE);
            infoClienteLabel.setText("");
        }
    }//GEN-LAST:event_buscarClienteBtnActionPerformed
    private void hacerDevolucionBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hacerDevolucionBtnActionPerformed
        try {
            if (idTiqueteSeleccionado != -1) {
                boolean desencolo = ct.crearDevolucion(idViajeSeleccionado, idTiqueteSeleccionado);
                actualizarTiquetes();
                llenarTablaDevoluciones();
                JOptionPane.showMessageDialog(this, "Tiquete devuelto con éxito!", "Devolución", JOptionPane.INFORMATION_MESSAGE);
                if (desencolo) {
                    llenarTablaViajesSegunControlador("reservas");
                    JOptionPane.showMessageDialog(this, "Se creo reserva al cliente que estaba en cola de espera!", "Cola de espera", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor selecciona un Tiquete de la tabla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_hacerDevolucionBtnActionPerformed
    // ------------------- METODOS PERFORMED DEL TAB DE RESERVAS ----------------------
    private void hacerEfectivaBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hacerEfectivaBtnActionPerformed
        try {
            if (idReservaSeleccionado != -1) {
                cre.hacerEfectiva(idReservaSeleccionado, idViajeReservaSeleccionado, metodoPagoReservaCombo.getSelectedIndex());
                llenarTablaReservas();
                // Resetea tablas de tiquetes
                viajesTablaTiq.clearSelection();
                DefaultTableModel model = (DefaultTableModel) tablaTiquetes.getModel();
                model.setRowCount(0);  // Elimina todas las filas de la tabla de tiquetes
                JOptionPane.showMessageDialog(this, "Reserva efectiva correctamente, ya tiene asegurado el tiquete!", "Reserva efectiva", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Por favor selecciona una Reserva de la tabla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        }catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_hacerEfectivaBtnActionPerformed

    private void verColaEperaBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verColaEperaBtnActionPerformed
        if (idViajeSeleccionado != -1) {
            JOptionPane.showMessageDialog(this,ct.getColaEspera(idViajeSeleccionado) , "Cola de Espera", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Por favor selecciona un viaje de la tabla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_verColaEperaBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton agregarBusBtn;
    private javax.swing.JButton buscarBusBtn;
    private javax.swing.JButton buscarClienteBtn;
    private javax.swing.JComboBox<String> busesCoBox;
    private javax.swing.JTable busesTabla;
    private javax.swing.JTextField cantidadTiquete;
    private javax.swing.JButton cerrarSesionBtn;
    private javax.swing.JButton crearViajeBtn;
    private javax.swing.JTextField destinoField;
    private javax.swing.JButton editarBusBtn;
    private javax.swing.JButton eliminarBusBtn;
    private javax.swing.JTextField fechaLlegField;
    private javax.swing.JTextField fechaSalField;
    private javax.swing.JPanel gestionBusesPanel;
    private javax.swing.JPanel gestionVentaTiqPanel;
    private javax.swing.JPanel gestionViajesPanel;
    private javax.swing.JButton hacerDevolucionBtn;
    private javax.swing.JButton hacerEfectivaBtn;
    private javax.swing.JTextField horaLlegField;
    private javax.swing.JTextField horaSalField;
    private javax.swing.JTextField idClienteField;
    private javax.swing.JLabel infoClienteLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTabbedPaneAdminFlota;
    private javax.swing.JTextField marcaBusField;
    private javax.swing.JComboBox<String> metodoPagoCombo;
    private javax.swing.JComboBox<String> metodoPagoReservaCombo;
    private javax.swing.JLabel nombreAdminLabel;
    private javax.swing.JTextField nroPuestosBusField;
    private javax.swing.JPanel panelContainer;
    private javax.swing.JTextField placaBusField;
    private javax.swing.JLabel plazasDispLabel;
    private javax.swing.JLabel puestosDisLabel;
    private javax.swing.JTable tablaDevoluciones;
    private javax.swing.JTable tablaReservas;
    private javax.swing.JTable tablaTiquetes;
    private javax.swing.JTextField tipoBusField;
    private javax.swing.JLabel tituloViajeLabel;
    private javax.swing.JLabel tituloViajeLabel1;
    private javax.swing.JButton venderTiquete;
    private javax.swing.JButton verColaEperaBtn;
    private javax.swing.JTable viajesTabla;
    private javax.swing.JTable viajesTablaRes;
    private javax.swing.JTable viajesTablaTiq;
    private javax.swing.JTextField vlrUnitField;
    // End of variables declaration//GEN-END:variables
}
