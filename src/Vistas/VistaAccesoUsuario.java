/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vistas;

import Controladores.ControladorCasetasPrincipal;
import Controladores.ControladorUsuario;
import Modelos.Usuarios.AdminFlota;
import Modelos.Usuarios.AdminTerminal;
import Modelos.Usuarios.Cliente;
import Modelos.Usuarios.Usuario;
import javax.swing.JOptionPane;

/**
 *
 * @author PC
 */
public class VistaAccesoUsuario extends javax.swing.JFrame {

    /**
     * Creates new form VistaAccesoUsuario
     */
    ControladorUsuario cu;
    ControladorCasetasPrincipal ccp;

    public VistaAccesoUsuario(ControladorUsuario cu, ControladorCasetasPrincipal ccp) {
        initComponents();
        setLocationRelativeTo(this);
        
        this.cu = cu == null ? new ControladorUsuario() : cu;
        this.ccp = ccp == null ? new ControladorCasetasPrincipal() : ccp;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbedPaneAcceso = new javax.swing.JTabbedPane();
        VistaPanelLogin = new javax.swing.JPanel();
        nroIdField = new javax.swing.JTextField();
        passwordField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        loginBtn = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        nroIdCliField = new javax.swing.JTextField();
        passwordCliField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        registroCliBtn = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        nombreCliField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setText("Número de identificacion");

        jLabel3.setText("Contraseña");

        jLabel4.setText("LOGIN DE USUARIO");

        loginBtn.setText("LOGIN");
        loginBtn.setToolTipText("");
        loginBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout VistaPanelLoginLayout = new javax.swing.GroupLayout(VistaPanelLogin);
        VistaPanelLogin.setLayout(VistaPanelLoginLayout);
        VistaPanelLoginLayout.setHorizontalGroup(
            VistaPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(VistaPanelLoginLayout.createSequentialGroup()
                .addGroup(VistaPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(VistaPanelLoginLayout.createSequentialGroup()
                        .addGap(147, 147, 147)
                        .addComponent(jLabel4))
                    .addGroup(VistaPanelLoginLayout.createSequentialGroup()
                        .addGroup(VistaPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(VistaPanelLoginLayout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(VistaPanelLoginLayout.createSequentialGroup()
                                .addGap(62, 62, 62)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(VistaPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nroIdField, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(VistaPanelLoginLayout.createSequentialGroup()
                        .addGap(160, 160, 160)
                        .addComponent(loginBtn)))
                .addContainerGap(67, Short.MAX_VALUE))
        );
        VistaPanelLoginLayout.setVerticalGroup(
            VistaPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(VistaPanelLoginLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel4)
                .addGap(27, 27, 27)
                .addGroup(VistaPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(nroIdField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(VistaPanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addComponent(loginBtn)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        tabbedPaneAcceso.addTab("Login", VistaPanelLogin);

        jLabel5.setText("Número de identificacion");

        jLabel6.setText("Contraseña");

        registroCliBtn.setText("Registrar");
        registroCliBtn.setToolTipText("");
        registroCliBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registroCliBtnActionPerformed(evt);
            }
        });

        jLabel7.setText("Nombre de Cliente");

        nombreCliField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombreCliFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(nombreCliField, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(passwordCliField, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(nroIdCliField, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(44, 44, 44))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(159, 159, 159)
                .addComponent(registroCliBtn)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(nombreCliField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(nroIdCliField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(passwordCliField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(registroCliBtn)
                .addGap(39, 39, 39))
        );

        tabbedPaneAcceso.addTab("Registrar cliente", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabbedPaneAcceso)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedPaneAcceso)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loginBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginBtnActionPerformed
        try {
            int id = Integer.parseInt(nroIdField.getText());
            String password = passwordField.getText();

            Usuario usuario = cu.loginUsuario(id, password);
            if (usuario != null) {
                if (usuario instanceof AdminTerminal) {
                    new VistaCasetasPrincipal(ccp, cu).setVisible(true);
                    this.dispose();
                } else if (usuario instanceof AdminFlota) {
                    new VistaGestionAdminFlota(ccp, cu, (AdminFlota) usuario).setVisible(true);
                    this.dispose();
                } else if (usuario instanceof Cliente) {
                    new VistaGestionCliente(ccp, cu).setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(
                        VistaPanelLogin,
                        "CREDENCIALES INCORRECTAS",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "El ID debe ser un número válido.",
                    "Error de formato",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }//GEN-LAST:event_loginBtnActionPerformed

    private void registroCliBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registroCliBtnActionPerformed
        // TODO add your handling code here:
        try {
            String nombreCli = nombreCliField.getText();
            int idCli = Integer.parseInt(nroIdCliField.getText());
            String password = passwordCliField.getText();
            
            
            
        } catch(RuntimeException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//GEN-LAST:event_registroCliBtnActionPerformed

    private void nombreCliFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombreCliFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreCliFieldActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VistaAccesoUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaAccesoUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaAccesoUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaAccesoUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaAccesoUsuario(null,null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel VistaPanelLogin;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton loginBtn;
    private javax.swing.JTextField nombreCliField;
    private javax.swing.JTextField nroIdCliField;
    private javax.swing.JTextField nroIdField;
    private javax.swing.JTextField passwordCliField;
    private javax.swing.JTextField passwordField;
    private javax.swing.JButton registroCliBtn;
    private javax.swing.JTabbedPane tabbedPaneAcceso;
    // End of variables declaration//GEN-END:variables
}
