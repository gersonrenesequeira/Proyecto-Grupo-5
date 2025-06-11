/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Vista;

import Controlador.TurnoController;
import DAO.EmpleadoDAO;
import modelo.Turno;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Usuario
 */
public class VistaTurnos extends javax.swing.JPanel implements EmpleadoChangeListener {
    private TurnoController turnoController;
    private DefaultTableModel modeloTabla;
    private List<Integer> empleadoIds; // Para almacenar los IDs de empleados
    private List<Integer> turnoIds; // Para almacenar los IDs de los turnos
    private int idTurnoSeleccionado = -1;

    /**
     * Creates new form VistaTurnos
     */
    public VistaTurnos() {
        initComponents();
        turnoController = new TurnoController();
        inicializarTabla();
        cargarEmpleadosEnCombo();
        cargarTiposTurnoEnCombo();
        cargarTurnos();
        textBuscarTurno.setName("textBuscarTurno");
    }

    private void inicializarTabla() {
        String[] columnas = {"ID Turno", "Nombre Empleado", "Fecha", "Hora inicio", "Hora fin", "Tipo turno"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }
        };
        tablaTurnos.setModel(modeloTabla);
        // Asegurar que todas las columnas sean visibles
        for (int i = 0; i < columnas.length; i++) {
            tablaTurnos.getColumnModel().getColumn(i).setMinWidth(50);
            if (i == 0) { // Ocultar solo la columna ID Turno
                tablaTurnos.getColumnModel().getColumn(i).setMinWidth(0);
                tablaTurnos.getColumnModel().getColumn(i).setMaxWidth(0);
                tablaTurnos.getColumnModel().getColumn(i).setWidth(0);
            }
        }
    }

    private void cargarEmpleadosEnCombo() {
        try {
            EmpleadoDAO empleadoDAO = new EmpleadoDAO();
            List<modelo.Empleado> empleados = empleadoDAO.leerTodosEmpleados();
            empleadoIds = new ArrayList<>();
            jComboEmpleadoT.removeAllItems();
            jComboEmpleadoT.addItem("Seleccione un empleado");
            empleadoIds.add(-1);
            for (modelo.Empleado empleado : empleados) {
                jComboEmpleadoT.addItem(empleado.getNombre());
                empleadoIds.add(empleado.getIdEmpleado());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar empleados: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarTiposTurnoEnCombo() {
        jComboTurnos.removeAllItems();
        jComboTurnos.addItem("Seleccione un tipo");
        jComboTurnos.addItem("mañana");
        jComboTurnos.addItem("tarde");
        jComboTurnos.addItem("noche");
        jComboTurnos.addItem("flexible");
    }

    private void cargarTurnos() {
        try {
            List<Turno> turnos = turnoController.obtenerTodosTurnos();
            modeloTabla.setRowCount(0); // Limpiar la tabla
            turnoIds = new ArrayList<>();
            if (turnos != null) {
                for (Turno turno : turnos) {
                    Object[] fila = {
                        turno.getIdTurno(),
                        turno.getNombreEmpleado() != null ? turno.getNombreEmpleado() : "Sin nombre",
                        turno.getFecha(),
                        turno.getHoraInicio(),
                        turno.getHoraFin(),
                        turno.getTipoTurno() != null ? turno.getTipoTurno() : "No especificado"
                    };
                    modeloTabla.addRow(fila);
                    turnoIds.add(turno.getIdTurno());
                }
            }
            // Verificar que la tabla tenga las columnas correctas
            if (tablaTurnos.getColumnCount() != 6) {
                System.out.println("Error: La tabla no tiene 6 columnas, tiene " + tablaTurnos.getColumnCount());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar turnos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        jComboEmpleadoT.setSelectedIndex(0);
        textFecha.setText("");
        textHorainicio.setText("");
        textHorafin.setText("");
        jComboTurnos.setSelectedIndex(0);
        textBuscarTurno.setText("");
        tablaTurnos.clearSelection();
        idTurnoSeleccionado = -1; // Resetear el ID seleccionado
    }

    private boolean validarCampos() {
        if (jComboEmpleadoT.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un empleado válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (textFecha.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "La fecha es obligatoria.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (textHorainicio.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "La hora de inicio es obligatoria.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (textHorafin.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "La hora de fin es obligatoria.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (jComboTurnos.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un tipo de turno válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    @Override
    public void onEmpleadoAdded(String nombreEmpleado, int idEmpleado) {
        // Agregar el nuevo empleado al JComboBox
        jComboEmpleadoT.addItem(nombreEmpleado);
        empleadoIds.add(idEmpleado);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaTurnos = new javax.swing.JTable();
        btnEditarT = new javax.swing.JButton();
        btnLimpiarT = new javax.swing.JButton();
        btnActualizarT = new javax.swing.JButton();
        btnEliminarT = new javax.swing.JButton();
        btnGuardarT = new javax.swing.JButton();
        btnBuscarT = new javax.swing.JButton();
        btnmostrarturnos = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        textBuscarTurno = new javax.swing.JTextField();
        textHorainicio = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        textFecha = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jComboEmpleadoT = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        textIDempleados = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jComboTurnos = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        textHorafin = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(204, 204, 255));

        tablaTurnos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID_empleados", "Fecha", "Hora inicio", "Hora fin", "Tipo turno"
            }
        ));
        jScrollPane1.setViewportView(tablaTurnos);

        btnEditarT.setText("Editar");
        btnEditarT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarTActionPerformed(evt);
            }
        });

        btnLimpiarT.setText("Limpiar");
        btnLimpiarT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarTActionPerformed(evt);
            }
        });

        btnActualizarT.setText("Actualizar");
        btnActualizarT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarTActionPerformed(evt);
            }
        });

        btnEliminarT.setText("Eliminar");
        btnEliminarT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarTActionPerformed(evt);
            }
        });

        btnGuardarT.setText("Guardar");
        btnGuardarT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarTActionPerformed(evt);
            }
        });

        btnBuscarT.setText("Buscar");
        btnBuscarT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarTActionPerformed(evt);
            }
        });

        btnmostrarturnos.setText("mostra turnos");
        btnmostrarturnos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmostrarturnosActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));

        jLabel5.setText("Buscar");

        jLabel3.setText("Hora inicio");

        jLabel2.setText("Fecha");

        jComboEmpleadoT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel7.setText("Empleados");

        jLabel1.setText("ID_empleados ");

        jComboTurnos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel6.setText("tipo_turno");

        textHorafin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textHorafinActionPerformed(evt);
            }
        });

        jLabel4.setText("Hora_fin");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(textHorainicio))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textBuscarTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(17, 17, 17))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(textFecha, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                            .addComponent(textIDempleados, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboEmpleadoT, javax.swing.GroupLayout.Alignment.LEADING, 0, 170, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(textHorafin, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6)
                                .addComponent(jComboTurnos, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(0, 14, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textIDempleados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboEmpleadoT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textHorafin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboTurnos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textHorainicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textBuscarTurno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1030, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(btnActualizarT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnGuardarT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnEliminarT)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnBuscarT))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnLimpiarT, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnEditarT))))
                            .addComponent(btnmostrarturnos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(402, 402, 402))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(75, 75, 75)
                                .addComponent(btnActualizarT))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnEditarT)
                                    .addComponent(btnLimpiarT)
                                    .addComponent(btnGuardarT))
                                .addGap(23, 23, 23)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnEliminarT)
                                    .addComponent(btnBuscarT))))
                        .addGap(18, 18, 18)
                        .addComponent(btnmostrarturnos)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void textHorafinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textHorafinActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textHorafinActionPerformed

    private void btnGuardarTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarTActionPerformed
        // TODO add your handling code here:
    if (!validarCampos()) {
            return;
        }
        try {
            int selectedIndex = jComboEmpleadoT.getSelectedIndex();
            int idEmpleado = empleadoIds.get(selectedIndex);
            String tipoTurno = jComboTurnos.getSelectedItem().toString();
            System.out.println("Guardando turno con tipo: " + tipoTurno); // Depuración
            turnoController.crearTurno(
                idEmpleado,
                textFecha.getText().trim(),
                textHorainicio.getText().trim(),
                textHorafin.getText().trim(),
                tipoTurno
            );
            cargarTurnos();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Turno guardado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar turno: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarTActionPerformed

    private void btnLimpiarTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarTActionPerformed
        // TODO add your handling code here:
          limpiarCampos();
    }//GEN-LAST:event_btnLimpiarTActionPerformed

    private void btnEditarTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarTActionPerformed
        // TODO add your handling code here:
       int filaSeleccionada = tablaTurnos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            idTurnoSeleccionado = turnoIds.get(filaSeleccionada);
            jComboEmpleadoT.setSelectedItem(modeloTabla.getValueAt(filaSeleccionada, 1).toString());
            textFecha.setText(modeloTabla.getValueAt(filaSeleccionada, 2).toString());
            textHorainicio.setText(modeloTabla.getValueAt(filaSeleccionada, 3).toString());
            textHorafin.setText(modeloTabla.getValueAt(filaSeleccionada, 4).toString());
            jComboTurnos.setSelectedItem(modeloTabla.getValueAt(filaSeleccionada, 5).toString());
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un turno de la tabla.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEditarTActionPerformed

    private void btnActualizarTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarTActionPerformed
        // TODO add your handling code here:
      if (idTurnoSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un turno para actualizar usando el botón Editar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!validarCampos()) {
            return;
        }
        try {
            turnoController.actualizarTurno(
                idTurnoSeleccionado,
                textFecha.getText().trim(),
                textHorainicio.getText().trim(),
                textHorafin.getText().trim(),
                jComboTurnos.getSelectedItem().toString()
            );
            cargarTurnos();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Turno actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar turno: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnActualizarTActionPerformed

    private void btnEliminarTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarTActionPerformed
        // TODO add your handling code here:
       int filaSeleccionada = tablaTurnos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int idTurno = turnoIds.get(filaSeleccionada);
            int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este turno?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    turnoController.eliminarTurno(idTurno);
                    cargarTurnos();
                    limpiarCampos();
                    JOptionPane.showMessageDialog(this, "Turno eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error al eliminar turno: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un turno de la tabla.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEliminarTActionPerformed

    private void btnBuscarTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarTActionPerformed
        // TODO add your handling code here:
              try {
            if (textBuscarTurno.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese un ID de empleado para buscar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int idBuscado = Integer.parseInt(textBuscarTurno.getText().trim());
            List<Turno> turnos = turnoController.leerTurnosPorEmpleado(idBuscado);
            modeloTabla.setRowCount(0);
            turnoIds = new ArrayList<>();
            if (!turnos.isEmpty()) {
                for (Turno turno : turnos) {
                    Object[] fila = {
                        turno.getIdTurno(),
                        turno.getNombreEmpleado() != null ? turno.getNombreEmpleado() : "Sin nombre",
                        turno.getFecha(),
                        turno.getHoraInicio(),
                        turno.getHoraFin(),
                        turno.getTipoTurno()
                    };
                    modeloTabla.addRow(fila);
                    turnoIds.add(turno.getIdTurno());
                }
            } else {
                JOptionPane.showMessageDialog(this, "No se encontraron turnos para el ID ingresado.", "Error", JOptionPane.ERROR_MESSAGE);
                cargarTurnos();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El ID debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al buscar turno: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
            

    }//GEN-LAST:event_btnBuscarTActionPerformed

    private void btnmostrarturnosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmostrarturnosActionPerformed
        // TODO add your handling code here:
          limpiarCampos();
        cargarTurnos(); // Recargar todos los turnos al limpiar
    }//GEN-LAST:event_btnmostrarturnosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizarT;
    private javax.swing.JButton btnBuscarT;
    private javax.swing.JButton btnEditarT;
    private javax.swing.JButton btnEliminarT;
    private javax.swing.JButton btnGuardarT;
    private javax.swing.JButton btnLimpiarT;
    private javax.swing.JButton btnmostrarturnos;
    private javax.swing.JComboBox<String> jComboEmpleadoT;
    private javax.swing.JComboBox<String> jComboTurnos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaTurnos;
    private javax.swing.JTextField textBuscarTurno;
    private javax.swing.JTextField textFecha;
    private javax.swing.JTextField textHorafin;
    private javax.swing.JTextField textHorainicio;
    private javax.swing.JTextField textIDempleados;
    // End of variables declaration//GEN-END:variables
}
