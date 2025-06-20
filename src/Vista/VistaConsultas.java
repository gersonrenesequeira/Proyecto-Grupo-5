/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import Controlador.ConsultaDinamicaControlador;
/**
 *
 * @author Usuario
 */
public class VistaConsultas extends javax.swing.JPanel {
private ConsultaDinamicaControlador Controlador;
    /**
     * Creates new form VistaConsultasDinamicas
     */
  public VistaConsultas() {
        initComponents();
        this.Controlador = new ConsultaDinamicaControlador();
        
        TablaResultadosconsultas.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][]{},
            new String[]{}
        ));
    }
     

    public JTextField getTextConsultagru() {
        return textConsultagru;
    }
    
     public JTable getTablaResultadosconsultas() {
        return TablaResultadosconsultas;
    }

    public JButton getBtnConsultargru() {
        return btnConsultargru;
    }

    public JButton getBtnLimpiargru() {
        return btnLimpiargru;
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        textConsultagru = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaResultadosconsultas = new javax.swing.JTable();
        btnConsultargru = new javax.swing.JButton();
        btnLimpiargru = new javax.swing.JButton();

        jLabel1.setText("Consultas");

        TablaResultadosconsultas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(TablaResultadosconsultas);

        btnConsultargru.setText("Consultar");
        btnConsultargru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultargruActionPerformed(evt);
            }
        });

        btnLimpiargru.setText("Limpiar");
        btnLimpiargru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiargruActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 527, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(textConsultagru, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(46, 46, 46)
                                .addComponent(btnConsultargru)
                                .addGap(18, 18, 18)
                                .addComponent(btnLimpiargru)))))
                .addContainerGap(56, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textConsultagru, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConsultargru)
                    .addComponent(btnLimpiargru))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(11, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnConsultargruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultargruActionPerformed
        // TODO add your handling code here:
              String consultaNatural = textConsultagru.getText().trim();
        if (!consultaNatural.isEmpty()) {
           List<Object[]> resultados = Controlador.ejecutarConsultaNatural(consultaNatural);
String[] columnNames = Controlador.obtenerNombresColumnas(consultaNatural);

            if (resultados != null && columnNames.length > 0) {
                DefaultTableModel model = new DefaultTableModel(columnNames, 0);
                for (Object[] fila : resultados) {
                    model.addRow(fila);
                }
                TablaResultadosconsultas.setModel(model);
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "No se encontraron resultados.", "Información", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Por favor, ingrese una consulta.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }                             

 
        
    }//GEN-LAST:event_btnConsultargruActionPerformed

    private void btnLimpiargruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiargruActionPerformed
        // TODO add your handling code here:
                String consultaNatural = textConsultagru.getText().trim();
        if (!consultaNatural.isEmpty()) {
           List<Object[]> resultados = Controlador.ejecutarConsultaNatural(consultaNatural);
String[] columnNames = Controlador.obtenerNombresColumnas(consultaNatural);

            if (resultados != null && columnNames.length > 0) {
                DefaultTableModel model = new DefaultTableModel(columnNames, 0);
                for (Object[] fila : resultados) {
                    model.addRow(fila);
                }
                TablaResultadosconsultas.setModel(model);
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "No se encontraron resultados.", "Información", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Por favor, ingrese una consulta.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnLimpiargruActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TablaResultadosconsultas;
    private javax.swing.JButton btnConsultargru;
    private javax.swing.JButton btnLimpiargru;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField textConsultagru;
    // End of variables declaration//GEN-END:variables
}
