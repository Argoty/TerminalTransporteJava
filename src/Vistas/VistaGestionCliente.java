/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vistas;

import Controladores.ControladorCasetasPrincipal;
import Controladores.ControladorUsuario;
import Modelos.Usuarios.Cliente;

/**
 *
 * @author PC
 */
public class VistaGestionCliente extends javax.swing.JFrame {

    /**
     * Creates new form VistaGestionCliente
     */
    Cliente cliente;

    public VistaGestionCliente(Cliente cliente) {
        initComponents();
        setLocationRelativeTo(this);
        
        this.cliente = cliente;
        mostrarInfo();
    }
    
    // METODOS PRIVADOS DEL TAB DE INFO DE CLIENTE
    private void mostrarInfo() {
        nombreCliLabel.setText(cliente.getName());
        nroIdLabel.setText(cliente.getNroId() + "");
        emailLabel.setText(cliente.getEmail());
        telefonoLabel.setText(cliente.getTelefono());
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
        puntosAcumulados = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        devolucionesPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        reservaTiqPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        cantidad = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        cerrarSesionBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Nombre:");

        jLabel2.setText("Numero de Identificacion:");

        jLabel8.setText("Email:");

        jLabel9.setText("Telefono:");

        nombreCliLabel.setText("jLabel10");

        nroIdLabel.setText("jLabel11");

        emailLabel.setText("jLabel12");

        telefonoLabel.setText("jLabel13");

        javax.swing.GroupLayout informacionPanelLayout = new javax.swing.GroupLayout(informacionPanel);
        informacionPanel.setLayout(informacionPanelLayout);
        informacionPanelLayout.setHorizontalGroup(
            informacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(informacionPanelLayout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addGroup(informacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addGap(46, 46, 46)
                .addGroup(informacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(telefonoLabel)
                    .addComponent(emailLabel)
                    .addComponent(nroIdLabel)
                    .addComponent(nombreCliLabel))
                .addContainerGap(292, Short.MAX_VALUE))
        );
        informacionPanelLayout.setVerticalGroup(
            informacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(informacionPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(informacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(nombreCliLabel))
                .addGap(30, 30, 30)
                .addGroup(informacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(nroIdLabel))
                .addGap(33, 33, 33)
                .addGroup(informacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(emailLabel))
                .addGap(37, 37, 37)
                .addGroup(informacionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(telefonoLabel))
                .addContainerGap(157, Short.MAX_VALUE))
        );

        clienteTabbedPane.addTab("Mi información", informacionPanel);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Puntos Ganados", "Viaje"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout puntosAcumuladosLayout = new javax.swing.GroupLayout(puntosAcumulados);
        puntosAcumulados.setLayout(puntosAcumuladosLayout);
        puntosAcumuladosLayout.setHorizontalGroup(
            puntosAcumuladosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, puntosAcumuladosLayout.createSequentialGroup()
                .addContainerGap(160, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73))
        );
        puntosAcumuladosLayout.setVerticalGroup(
            puntosAcumuladosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, puntosAcumuladosLayout.createSequentialGroup()
                .addContainerGap(36, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        clienteTabbedPane.addTab("Puntos Acumulados", puntosAcumulados);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout devolucionesPanelLayout = new javax.swing.GroupLayout(devolucionesPanel);
        devolucionesPanel.setLayout(devolucionesPanelLayout);
        devolucionesPanelLayout.setHorizontalGroup(
            devolucionesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(devolucionesPanelLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(136, Short.MAX_VALUE))
        );
        devolucionesPanelLayout.setVerticalGroup(
            devolucionesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(devolucionesPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        clienteTabbedPane.addTab("Devoluciones", devolucionesPanel);

        jLabel3.setText("Viajes");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Destino", "Fecha", "Bus", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTable3);

        jLabel5.setText("LISTA DE VIAJES");

        jLabel6.setText("Ordenar por:");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Fecha", "Destino", "Ambos" }));

        jButton1.setText("Generar Reserva");

        jLabel7.setText("Cantidad");

        javax.swing.GroupLayout reservaTiqPanelLayout = new javax.swing.GroupLayout(reservaTiqPanel);
        reservaTiqPanel.setLayout(reservaTiqPanelLayout);
        reservaTiqPanelLayout.setHorizontalGroup(
            reservaTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reservaTiqPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(reservaTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(reservaTiqPanelLayout.createSequentialGroup()
                        .addGroup(reservaTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(reservaTiqPanelLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(36, 36, 36)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton1))
                        .addGap(16, 16, 16))
                    .addGroup(reservaTiqPanelLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(cantidad)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(16, 16, 16))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, reservaTiqPanelLayout.createSequentialGroup()
                .addContainerGap(290, Short.MAX_VALUE)
                .addGroup(reservaTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, reservaTiqPanelLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(134, 134, 134))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, reservaTiqPanelLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGroup(reservaTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(reservaTiqPanelLayout.createSequentialGroup()
                                .addGap(68, 68, 68)
                                .addComponent(jLabel4))
                            .addGroup(reservaTiqPanelLayout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(166, 166, 166))))
        );
        reservaTiqPanelLayout.setVerticalGroup(
            reservaTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reservaTiqPanelLayout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(reservaTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGroup(reservaTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(reservaTiqPanelLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(reservaTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(reservaTiqPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addComponent(jButton1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, reservaTiqPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        clienteTabbedPane.addTab("Reservar Tiquetes", reservaTiqPanel);

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(jTable4);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(110, 110, 110)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(118, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        clienteTabbedPane.addTab("Notificaciones", jPanel1);

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cerrarSesionBtn)
                .addGap(19, 19, 19))
            .addGroup(layout.createSequentialGroup()
                .addComponent(clienteTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 626, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(clienteTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cerrarSesionBtn)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cerrarSesionBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cerrarSesionBtnActionPerformed
        // TODO add your handling code here:
        new VistaAccesoUsuario().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_cerrarSesionBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField cantidad;
    private javax.swing.JButton cerrarSesionBtn;
    private javax.swing.JTabbedPane clienteTabbedPane;
    private javax.swing.JPanel devolucionesPanel;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JPanel informacionPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JLabel nombreCliLabel;
    private javax.swing.JLabel nroIdLabel;
    private javax.swing.JPanel puntosAcumulados;
    private javax.swing.JPanel reservaTiqPanel;
    private javax.swing.JLabel telefonoLabel;
    // End of variables declaration//GEN-END:variables
}
