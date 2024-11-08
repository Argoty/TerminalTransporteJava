/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vistas;

import Controladores.ControladorBuses;
import Controladores.ControladorTiquetes;
import Controladores.ControladorViajes;

import Modelos.Bus;
import Modelos.Tiquete;
import Modelos.Usuarios.AdminFlota;
import Modelos.Usuarios.Cliente;
import Modelos.Viaje;
import javax.swing.DefaultComboBoxModel;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author PC
 */
public class VistaGestionAdminFlota extends javax.swing.JFrame {

    ControladorBuses cb;
    ControladorViajes cv;
    ControladorTiquetes ct;
    int idViajeSeleccionado = -1;

    public VistaGestionAdminFlota(AdminFlota admin) {
        initComponents();
        setLocationRelativeTo(this);

        int idAdmin = admin.getNroId();
        // Creacion de controladores de cada tab
        this.cb = new ControladorBuses(idAdmin);
        this.cv = new ControladorViajes(idAdmin);
        this.ct = new ControladorTiquetes(idAdmin);
        // Refresco de las tablas
        llenarTablaBuses();
        llenarTablaViajes();
        llenarTablaViajesTiq();

        alistarPlacasBusesCombobox();
        configurarSeleccionTablaViajes();
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

    private void llenarTablaViajes() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"ID", "Destino", "Fecha Salida", "Fecha llegada", "Bus", "Vlr Unit"});
        for (int i = 0; i < cv.getViajes().size(); i++) {
            model.addRow(new Object[]{
                cv.getViajes().get(i).getId(),
                cv.getViajes().get(i).getDestino(),
                cv.getViajes().get(i).getFechaSalidaStr(),
                cv.getViajes().get(i).getFechaLlegadaStr(),
                cv.getViajes().get(i).getBus().getPlaca(),
                cv.getViajes().get(i).getVlrUnit()
            });
        }
        viajesTabla.setModel(model);
        // Ajustar el ancho de las columnas
        viajesTabla.getColumnModel().getColumn(0).setPreferredWidth(30);  // Ancho más pequeño para la columna N°
        viajesTabla.getColumnModel().getColumn(1).setPreferredWidth(150); // Ancho estándar para "Destino"
        viajesTabla.getColumnModel().getColumn(2).setPreferredWidth(165); // Ancho ligeramente mayor para "Fecha Salida"
        viajesTabla.getColumnModel().getColumn(3).setPreferredWidth(165); // Ancho ligeramente mayor para "Fecha Llegada"
        viajesTabla.getColumnModel().getColumn(4).setPreferredWidth(90); // Ancho estándar para "Bus"
        viajesTabla.getColumnModel().getColumn(5).setPreferredWidth(120);  // Ancho estándar para "Vlr Unit"
    }

    // METODOS PRIVADOS DEL TAB DE TIQUETES
    private void llenarTablaViajesTiq() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"ID", "Destino", "Fecha Salida", "Fecha llegada", "Bus", "Vlr Unit"});
        for (int i = 0; i < ct.getViajes().size(); i++) {
            Viaje viaje = ct.getViajes().get(i);
            model.addRow(new Object[]{
                viaje.getId(),
                viaje.getDestino(),
                viaje.getFechaSalidaStr(),
                viaje.getFechaLlegadaStr(),
                viaje.getBus().getPlaca(),
                viaje.getVlrUnit(),});
        }

        viajesTablaTiq.setModel(model);
        // Ajustar el ancho de las columnas
        viajesTablaTiq.getColumnModel().getColumn(0).setPreferredWidth(30);  // Ancho más pequeño para la columna N°
        viajesTablaTiq.getColumnModel().getColumn(1).setPreferredWidth(150); // Ancho estándar para "Destino"
        viajesTablaTiq.getColumnModel().getColumn(2).setPreferredWidth(165); // Ancho ligeramente mayor para "Fecha Salida"
        viajesTablaTiq.getColumnModel().getColumn(3).setPreferredWidth(165); // Ancho ligeramente mayor para "Fecha Llegada"
        viajesTablaTiq.getColumnModel().getColumn(4).setPreferredWidth(90); // Ancho estándar para "Bus"
        viajesTablaTiq.getColumnModel().getColumn(5).setPreferredWidth(120);  // Ancho estándar para "Vlr Unit"
    }

    private void configurarSeleccionTablaViajes() {
        viajesTablaTiq.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                int filaSeleccionada = viajesTablaTiq.getSelectedRow();

                if (filaSeleccionada != -1) {
                    Object idViaje = viajesTablaTiq.getValueAt(filaSeleccionada, 0);
                    this.idViajeSeleccionado = Integer.parseInt(idViaje.toString());
                    actualizarTiquetes();
                }
            }
        });
    }

    private void actualizarTiquetes() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"ID", "Cliente", "Fecha Compra"});
        for (int i = 0; i < ct.getTiquetes(idViajeSeleccionado).size(); i++) {
            Tiquete tiquete = ct.getTiquetes(idViajeSeleccionado).get(i);
            model.addRow(new Object[]{
                tiquete.getId(),
                tiquete.getCliente().getNroId() + ". " + tiquete.getCliente().getName(),
                tiquete.getFechaCompraStr()
            });
        }

        tablaTiquetes.setModel(model);

        Viaje viaje = ct.buscarViajePorId(idViajeSeleccionado);
        tituloViajeLabel.setText("Tiquetes del Viaje a " + viaje.getDestino() + " en el bus '" + viaje.getBus().getPlaca() + "' para el " + viaje.getFechaSalidaStr() );
        puestosBusLabel.setText("Puestos Totales del bus: " + viaje.getBus().getPuestos());
        puestosDisLabel.setText("Puestos Disponibles del bus: " + (viaje.getBus().getPuestos() - viaje.getTiquetes().size()));
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
        puestosBusLabel = new javax.swing.JLabel();
        cerrarSesionBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Plazas Ocupadas:");

        plazasDispLabel.setText("0/0");

        jLabel3.setText("Placa");

        jLabel4.setText("Numero de ");

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

        jLabel15.setText("Marca");

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
                                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(gestionBusesPanelLayout.createSequentialGroup()
                                            .addComponent(jLabel15)
                                            .addGap(32, 32, 32)))
                                    .addComponent(jLabel5)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 112, Short.MAX_VALUE)
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
                        .addContainerGap(64, Short.MAX_VALUE))
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

        jLabel6.setText("Hora Salida");

        jLabel7.setText("Fecha llegada");

        jLabel9.setText("Hora llegada");

        horaLlegField.setName(""); // NOI18N

        jLabel10.setText("Bus");

        busesCoBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel11.setText("Valor Unitario");

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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
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
                .addContainerGap(19, Short.MAX_VALUE))
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

        jLabel12.setText("ID de Cliente");

        jLabel14.setText("Cantidad");

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

        jLabel13.setText("SELECCIONAR VIAJE");

        tituloViajeLabel.setText("Tiquetes del Viaje");

        tablaTiquetes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Cliente", "Fecha Compra"
            }
        ));
        jScrollPane5.setViewportView(tablaTiquetes);

        buscarClienteBtn.setText("Buscar Cliente");
        buscarClienteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarClienteBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout gestionVentaTiqPanelLayout = new javax.swing.GroupLayout(gestionVentaTiqPanel);
        gestionVentaTiqPanel.setLayout(gestionVentaTiqPanelLayout);
        gestionVentaTiqPanelLayout.setHorizontalGroup(
            gestionVentaTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gestionVentaTiqPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(gestionVentaTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 568, Short.MAX_VALUE)
                    .addComponent(jScrollPane4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(gestionVentaTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, gestionVentaTiqPanelLayout.createSequentialGroup()
                        .addGroup(gestionVentaTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(gestionVentaTiqPanelLayout.createSequentialGroup()
                                .addGroup(gestionVentaTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel14))
                                .addGap(18, 18, 18)
                                .addGroup(gestionVentaTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(idClienteField, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cantidadTiquete, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(gestionVentaTiqPanelLayout.createSequentialGroup()
                                .addGroup(gestionVentaTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(buscarClienteBtn)
                                    .addComponent(venderTiquete))
                                .addGap(55, 55, 55))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, gestionVentaTiqPanelLayout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(infoClienteLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(12, 12, 12))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, gestionVentaTiqPanelLayout.createSequentialGroup()
                        .addGroup(gestionVentaTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(puestosBusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(puestosDisLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33))))
            .addGroup(gestionVentaTiqPanelLayout.createSequentialGroup()
                .addGroup(gestionVentaTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(gestionVentaTiqPanelLayout.createSequentialGroup()
                        .addGap(249, 249, 249)
                        .addComponent(jLabel13))
                    .addGroup(gestionVentaTiqPanelLayout.createSequentialGroup()
                        .addGap(151, 151, 151)
                        .addComponent(tituloViajeLabel)))
                .addGap(0, 0, Short.MAX_VALUE))
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
                        .addGap(3, 3, 3)
                        .addComponent(infoClienteLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(gestionVentaTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel14)
                            .addComponent(cantidadTiquete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addComponent(venderTiquete)))
                .addGroup(gestionVentaTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(gestionVentaTiqPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tituloViajeLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(gestionVentaTiqPanelLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(puestosBusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(puestosDisLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jTabbedPaneAdminFlota.addTab("Venta Tiquetes", gestionVentaTiqPanel);

        cerrarSesionBtn.setText("Cerrar sesión");
        cerrarSesionBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cerrarSesionBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cerrarSesionBtn)
                .addGap(72, 72, 72))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPaneAdminFlota, javax.swing.GroupLayout.PREFERRED_SIZE, 837, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPaneAdminFlota, javax.swing.GroupLayout.PREFERRED_SIZE, 472, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cerrarSesionBtn)
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
            llenarTablaViajes();
            llenarTablaViajesTiq();
            JOptionPane.showMessageDialog(this, "Viaje agregado correctamente");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El valor unitario debe ser un número valido", "Error de formato", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_crearViajeBtnActionPerformed

    private void venderTiqueteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_venderTiqueteActionPerformed
        try {
            if (idViajeSeleccionado != -1) {
                int idCliente = Integer.parseInt(idClienteField.getText());
                int cantidad = Integer.parseInt(cantidadTiquete.getText());

                ct.venderTiquete(idViajeSeleccionado, idCliente, cantidad);
                actualizarTiquetes();
                JOptionPane.showMessageDialog(this, "Tiquete vendido con éxito!", "Venta", JOptionPane.INFORMATION_MESSAGE);
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
            infoClienteLabel.setText("ID: " + cliente.getNroId() + " | Nombre: " + cliente.getName());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El id debe ser un número valido", "Error de formato", JOptionPane.ERROR_MESSAGE);
            infoClienteLabel.setText("");
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "", JOptionPane.ERROR_MESSAGE);
            infoClienteLabel.setText("");
        }
    }//GEN-LAST:event_buscarClienteBtnActionPerformed

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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPaneAdminFlota;
    private javax.swing.JTextField marcaBusField;
    private javax.swing.JTextField nroPuestosBusField;
    private javax.swing.JPanel panelContainer;
    private javax.swing.JTextField placaBusField;
    private javax.swing.JLabel plazasDispLabel;
    private javax.swing.JLabel puestosBusLabel;
    private javax.swing.JLabel puestosDisLabel;
    private javax.swing.JTable tablaTiquetes;
    private javax.swing.JTextField tipoBusField;
    private javax.swing.JLabel tituloViajeLabel;
    private javax.swing.JButton venderTiquete;
    private javax.swing.JTable viajesTabla;
    private javax.swing.JTable viajesTablaTiq;
    private javax.swing.JTextField vlrUnitField;
    // End of variables declaration//GEN-END:variables
}
