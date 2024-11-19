/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vistas;

import Controladores.ControladorCasetasPrincipal;
import Modelos.Caseta;

//import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
//import javax.swing.JOptionPane;

/**
 *
 * @author Javier Argoty
 */
public class VistaCasetasPrincipal extends javax.swing.JFrame implements ActionListener {

    JButton[][] botones;
    ControladorCasetasPrincipal ccp;
    
    public VistaCasetasPrincipal() {
        initComponents();
        setLocationRelativeTo(this);
        
        this.ccp = new ControladorCasetasPrincipal();

        this.botones = new JButton[4][];
        configurarMatrizBotones(botones, 5);

        dibujarBotones();
    }

    private void configurarMatrizBotones(JButton[][] botones, int columnas) {
        int filas = botones.length;
        for (int i = 0; i < filas; i++) {
            if (i == 0) {
                // La primera fila tiene todas sus columnas
                botones[i] = new JButton[columnas];
            } else {
                // El resto de las filas tienen la mitad de las columnas
                botones[i] = new JButton[columnas / 2];
            }
        }
    }

    private void dibujarBotones() {
        int separado = 20; 
        int ancho = 80;    
        int alto = 60;     

        for (int i = 0; i < botones.length; i++) {
            for (int j = 0; j < botones[i].length; j++) {
                botones[i][j] = new JButton();
                // setBounds(posX, posY, ancho, alto)
                

                int validacionSeparacion = ((i == 1) && (j >= 1) ? j + 3
                        : (i == 3) && (j >= 0) ? j + 3
                                : j);
                botones[i][j].setBounds(
                        ancho * validacionSeparacion + separado,
                        alto * i + separado,
                        ancho, alto);         

                //botones[i][j].setText(String.valueOf(texto)); 
                botones[i][j].addActionListener(this);        
                panelBtnCasetas.add(botones[i][j]);              
            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < botones.length; i++) {
            for (int j = 0; j < botones[i].length; j++) {
                if (e.getSource().equals(botones[i][j])) {
                    int fila = i;
                    int columna = j;
                    Caseta caseta = ccp.getCaseta(fila, columna);
                    
                    new VistaCaseta(caseta).setVisible(true);
                    this.dispose();
                }
            }
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBtnCasetas = new javax.swing.JPanel();
        cerrarSesionBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelBtnCasetas.setBorder(javax.swing.BorderFactory.createTitledBorder("Casetas"));

        cerrarSesionBtn.setText("Cerrar Sesión");
        cerrarSesionBtn.setToolTipText("");
        cerrarSesionBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cerrarSesionBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBtnCasetasLayout = new javax.swing.GroupLayout(panelBtnCasetas);
        panelBtnCasetas.setLayout(panelBtnCasetasLayout);
        panelBtnCasetasLayout.setHorizontalGroup(
            panelBtnCasetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBtnCasetasLayout.createSequentialGroup()
                .addContainerGap(336, Short.MAX_VALUE)
                .addComponent(cerrarSesionBtn)
                .addGap(22, 22, 22))
        );
        panelBtnCasetasLayout.setVerticalGroup(
            panelBtnCasetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBtnCasetasLayout.createSequentialGroup()
                .addContainerGap(248, Short.MAX_VALUE)
                .addComponent(cerrarSesionBtn)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelBtnCasetas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelBtnCasetas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelBtnCasetas.getAccessibleContext().setAccessibleDescription("Lockers");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cerrarSesionBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cerrarSesionBtnActionPerformed
        new VistaAccesoUsuario().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_cerrarSesionBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cerrarSesionBtn;
    private javax.swing.JPanel panelBtnCasetas;
    // End of variables declaration//GEN-END:variables

}
