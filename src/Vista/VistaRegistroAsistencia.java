/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Vista;
import controlador.RegistroAsistenciaController;
import modelo.RegistroAsistencia;
import util.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import java.awt.FileDialog;
import java.util.Date;
/**
 *
 * @author Usuario
 */
public class VistaRegistroAsistencia extends javax.swing.JPanel {
    private RegistroAsistenciaController controller;
    private List<Integer> empleadoIds, turnoIds;
    /**
     * Creates new form VistaRegistroAsistencia
     */
    public VistaRegistroAsistencia() {
        initComponents();
        
        try {
            controller = new RegistroAsistenciaController();
            cargarEmpleados();
            cargarTurnos();
            consultarTodos();
            selectorfechaCompra.setDate(java.sql.Date.valueOf(LocalDate.now()));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al inicializar: " + e.getMessage());
        }
    }

    private void cargarEmpleados() {
        empleadoIds = new ArrayList<>();
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT id_empleado, nombre, apellido FROM Empleado");
             ResultSet rs = stmt.executeQuery()) {
            comboEmpleados.removeAllItems();
            while (rs.next()) {
                int id = rs.getInt("id_empleado");
                String nombre = rs.getString("nombre") + " " + rs.getString("apellido");
                comboEmpleados.addItem(nombre);
                empleadoIds.add(id);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar empleados: " + e.getMessage());
        }
    }
    
    private void cargarTurnos() {
        turnoIds = new ArrayList<>();
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT id_turno, tipo_turno, fecha FROM Turnos");
             ResultSet rs = stmt.executeQuery()) {
            comboTurno.removeAllItems();
            while (rs.next()) {
                int id = rs.getInt("id_turno");
                String descripcion = rs.getString("tipo_turno") + " (" + rs.getDate("fecha") + ")";
                comboTurno.addItem(descripcion);
                turnoIds.add(id);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar turnos: " + e.getMessage());
        }
    }

    private void consultarTodos() {
        try {
            List<RegistroAsistencia> asistencias = controller.obtenerTodasLasAsistencias();
            mostrarResultados(asistencias);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al consultar: " + ex.getMessage());
        }
    }

    private void mostrarResultados(List<RegistroAsistencia> asistencias) {
        DefaultTableModel model = (DefaultTableModel) tablaAsistencia.getModel();
        model.setRowCount(0); // Limpiar tabla
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT id_empleado, nombre, apellido FROM Empleado WHERE id_empleado = ?")) {
            for (RegistroAsistencia r : asistencias) {
                stmt.setInt(1, r.getIdEmpleado());
                try (ResultSet rs = stmt.executeQuery()) {
                    String nombre = r.getIdEmpleado() + "";
                    if (rs.next()) {
                        nombre = rs.getString("nombre") + " " + rs.getString("apellido");
                    }
                    model.addRow(new Object[]{
                        r.getIdRegistro(),
                        nombre,
                        r.getIdTurno(),
                        r.getFecha(),
                        r.getHoraEntrada(),
                        r.getHoraSalida(),
                        String.format("%.2f", r.getHorasTrabajadas())
                    });
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al obtener nombres de empleados: " + e.getMessage());
        }
    }
    private void limpiarCampos() {
        // Solo limpiar horaEntrada y horaSalida, dejando intactos JComboBox y selector de fecha
        textHoraEntrada.setText("");
        textHoraSalida.setText("");  // Asume que horaSalida es un JTextField
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        comboEmpleados = new javax.swing.JComboBox<>();
        comboTurno = new javax.swing.JComboBox<>();
        selectorfechaCompra = new com.toedter.calendar.JDateChooser();
        textHoraEntrada = new javax.swing.JTextField();
        textHoraSalida = new javax.swing.JTextField();
        btnRegistrar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnElimiar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        textBuscar = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaAsistencia = new javax.swing.JTable();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jLabel1.setText("Empleado");

        jLabel2.setText("Turno");

        jLabel3.setText("Fecha");

        jLabel4.setText("Hora Entrada");

        jLabel5.setText("Hora Salida");

        comboEmpleados.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboEmpleadosActionPerformed(evt);
            }
        });

        comboTurno.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboTurno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboTurnoActionPerformed(evt);
            }
        });

        textHoraEntrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textHoraEntradaActionPerformed(evt);
            }
        });

        textHoraSalida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textHoraSalidaActionPerformed(evt);
            }
        });

        btnRegistrar.setText("Registrar");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accionBotonRegistrar(evt);
            }
        });

        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accionBotonActualizar(evt);
            }
        });

        btnElimiar.setText("Eliminar");
        btnElimiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accionBotonEliminar(evt);
            }
        });

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });
        btnLimpiar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textBuscarKeyTyped(evt);
            }
        });

        jButton1.setText("Generar Reporte");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accionBotonGenerarReporte(evt);
            }
        });

        textBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textBuscarActionPerformed(evt);
            }
        });

        jLabel6.setText("Buscar");

        tablaAsistencia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Empleado", "Turno", "Fecha", "Entrada", "Salida", "Horas"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaAsistencia);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(comboEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(selectorfechaCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(textHoraEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(46, 46, 46)
                                .addComponent(jLabel2)
                                .addGap(68, 68, 68)
                                .addComponent(jLabel3)
                                .addGap(77, 77, 77)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(textHoraSalida, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 653, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(textBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnElimiar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnRegistrar, javax.swing.GroupLayout.Alignment.TRAILING))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnActualizar)
                                            .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(0, 31, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(comboEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(comboTurno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(selectorfechaCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(textHoraEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(textHoraSalida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegistrar)
                    .addComponent(btnActualizar))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnElimiar)
                            .addComponent(btnLimpiar)
                            .addComponent(textBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel6)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void textHoraSalidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textHoraSalidaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textHoraSalidaActionPerformed

    private void comboEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboEmpleadosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboEmpleadosActionPerformed

    private void comboTurnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTurnoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboTurnoActionPerformed

    private void textHoraEntradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textHoraEntradaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textHoraEntradaActionPerformed

    private void accionBotonRegistrar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accionBotonRegistrar
        // TODO add your handling code here:
        try {
            // Validar campos
            int indiceEmpleado = comboEmpleados.getSelectedIndex();
            if (indiceEmpleado < 0) {
                JOptionPane.showMessageDialog(this, "Seleccione un empleado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int indiceTurno = comboTurno.getSelectedIndex();
            if (indiceTurno < 0) {
                JOptionPane.showMessageDialog(this, "Seleccione un turno.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            java.util.Date fecha = selectorfechaCompra.getDate();
            if (fecha == null) {
                JOptionPane.showMessageDialog(this, "Seleccione una fecha.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String horaEntradaStr = textHoraEntrada.getText().trim();
            String horaSalidaStr = textHoraSalida.getText().trim();
            if (horaEntradaStr.isEmpty() || horaSalidaStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese las horas de entrada y salida.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear registro
            RegistroAsistencia registro = new RegistroAsistencia();
            registro.setIdEmpleado(empleadoIds.get(indiceEmpleado));
            registro.setIdTurno(turnoIds.get(indiceTurno));
            registro.setFecha(fecha.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
            registro.setHoraEntrada(LocalTime.parse(horaEntradaStr));
            registro.setHoraSalida(LocalTime.parse(horaSalidaStr));
            long minutos = java.time.Duration.between(registro.getHoraEntrada(), registro.getHoraSalida()).toMinutes();
            registro.setHorasTrabajadas((float) minutos / 60);

            // Guardar en la base de datos
            controller.registrarAsistencia(registro);

            // Mostrar éxito y actualizar interfaz
            JOptionPane.showMessageDialog(this, "Registro creado con ID: " + registro.getIdRegistro(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
            consultarTodos();
            limpiarCampos();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al registrar en la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Error en los datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_accionBotonRegistrar

    private void accionBotonActualizar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accionBotonActualizar
        // TODO add your handling code here:
          try {
            // Obtener el índice de la fila seleccionada
            int filaSeleccionada = tablaAsistencia.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un registro para actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener el ID del registro seleccionado
            Integer idRegistro = (Integer) tablaAsistencia.getValueAt(filaSeleccionada, 0); // Columna "ID"
            if (idRegistro == null) {
                JOptionPane.showMessageDialog(this, "ID de registro no válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validar campos del formulario
            int indiceEmpleado = comboEmpleados.getSelectedIndex();
            if (indiceEmpleado < 0) {
                JOptionPane.showMessageDialog(this, "Seleccione un empleado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int indiceTurno = comboTurno.getSelectedIndex();
            if (indiceTurno < 0) {
                JOptionPane.showMessageDialog(this, "Seleccione un turno.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            java.util.Date fecha = selectorfechaCompra.getDate();
            if (fecha == null) {
                JOptionPane.showMessageDialog(this, "Seleccione una fecha.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String horaEntradaStr = textHoraEntrada.getText().trim();
            String horaSalidaStr = textHoraSalida.getText().trim();
            if (horaEntradaStr.isEmpty() || horaSalidaStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese las horas de entrada y salida.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear objeto RegistroAsistencia con los nuevos datos
            RegistroAsistencia registro = new RegistroAsistencia();
            registro.setIdRegistro(idRegistro);
            registro.setIdEmpleado(empleadoIds.get(indiceEmpleado));
            registro.setIdTurno(turnoIds.get(indiceTurno));
            registro.setFecha(fecha.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
            registro.setHoraEntrada(LocalTime.parse(horaEntradaStr));
            registro.setHoraSalida(LocalTime.parse(horaSalidaStr));
            long minutos = java.time.Duration.between(registro.getHoraEntrada(), registro.getHoraSalida()).toMinutes();
            registro.setHorasTrabajadas((float) minutos / 60);

            // Actualizar el registro en la base de datos
            controller.actualizarAsistencia(registro);

            // Mostrar éxito y actualizar interfaz
            JOptionPane.showMessageDialog(this, "Registro actualizado con ID: " + idRegistro, "Éxito", JOptionPane.INFORMATION_MESSAGE);
            consultarTodos();
            limpiarCampos();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar el registro: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Error en los datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error inesperado al actualizar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_accionBotonActualizar

    private void accionBotonEliminar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accionBotonEliminar
        // TODO add your handling code here:
          int filaSeleccionada = tablaAsistencia.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int idRegistro = Integer.parseInt(tablaAsistencia.getValueAt(filaSeleccionada, 0).toString()); // Columna ID
            int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar el registro con ID " + idRegistro + "?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    System.out.println("Intentando eliminar registro con ID: " + idRegistro);
                    controller.eliminarAsistencia(idRegistro);
                    System.out.println("Eliminación solicitada para ID: " + idRegistro);
                    consultarTodos();
                    limpiarCampos();
                    JOptionPane.showMessageDialog(this, "Registro eliminado con ID: " + idRegistro, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error al eliminar el registro: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un registro de la tabla.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_accionBotonEliminar

    private void textBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textBuscarActionPerformed
        // TODO add your handling code here:
       
    }//GEN-LAST:event_textBuscarActionPerformed

    private void textBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textBuscarKeyTyped
        // TODO add your handling code here:
      
    }//GEN-LAST:event_textBuscarKeyTyped

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // TODO add your handling code here:
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void accionBotonGenerarReporte(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accionBotonGenerarReporte
        // TODO add your handling code here:
        try {
            FileDialog dialogoArchivo = new FileDialog((java.awt.Frame) null, "Guardar Reporte PDF", FileDialog.SAVE);
            dialogoArchivo.setFile("ReporteAsistencias.pdf");
            dialogoArchivo.setVisible(true);

            String ruta = dialogoArchivo.getDirectory();
            String nombreArchivo = dialogoArchivo.getFile();

            if (ruta == null || nombreArchivo == null) {
                JOptionPane.showMessageDialog(this, "Operación cancelada.", "Información", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            String rutaCompleta = ruta + nombreArchivo;

            PdfWriter escritor = new PdfWriter(rutaCompleta);
            PdfDocument pdf = new PdfDocument(escritor);
            Document documento = new Document(pdf);

            documento.add(new Paragraph("Reporte de Asistencias")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(20)
                    .setBold());

            documento.add(new Paragraph("Fecha: " + new Date().toString())
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(12));

            Table tabla = new Table(UnitValue.createPercentArray(new float[]{1, 2, 1, 2, 2, 2})); // Proporciones para 6 columnas
            tabla.setWidth(UnitValue.createPercentValue(100));
            tabla.addHeaderCell("ID Registro");
            tabla.addHeaderCell("Empleado");
            tabla.addHeaderCell("ID Turno");
            tabla.addHeaderCell("Fecha");
            tabla.addHeaderCell("Hora Entrada");
            tabla.addHeaderCell("Hora Salida");

            List<RegistroAsistencia> listaAsistencias = controller.obtenerTodasLasAsistencias();
            if (listaAsistencias != null) {
                try (Connection conn = util.ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement("SELECT nombre, apellido FROM Empleado WHERE id_empleado = ?")) {
                    for (RegistroAsistencia asistencia : listaAsistencias) {
                        stmt.setInt(1, asistencia.getIdEmpleado());
                        try (ResultSet rs = stmt.executeQuery()) {
                            String nombreEmpleado = "Empleado ID: " + asistencia.getIdEmpleado();
                            if (rs.next()) {
                                nombreEmpleado = rs.getString("nombre") + " " + rs.getString("apellido");
                            }
                            tabla.addCell(String.valueOf(asistencia.getIdRegistro()));
                            tabla.addCell(nombreEmpleado);
                            tabla.addCell(String.valueOf(asistencia.getIdTurno()));
                            tabla.addCell(asistencia.getFecha() != null ? asistencia.getFecha().toString() : "");
                            tabla.addCell(asistencia.getHoraEntrada() != null ? asistencia.getHoraEntrada().toString() : "");
                            tabla.addCell(asistencia.getHoraSalida() != null ? asistencia.getHoraSalida().toString() : "");
                        }
                    }
                }
            }

            documento.add(tabla);

            documento.add(new Paragraph("Notas: Reporte generado automáticamente desde el sistema.")
                    .setFontSize(10)
                    .setMarginTop(20));

            documento.close();
            JOptionPane.showMessageDialog(
                    this,
                    "Reporte PDF generado con éxito en: " + rutaCompleta,
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    "Error al generar el PDF: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    "Error inesperado al generar el PDF: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_accionBotonGenerarReporte


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnElimiar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JComboBox<String> comboEmpleados;
    private javax.swing.JComboBox<String> comboTurno;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser selectorfechaCompra;
    private javax.swing.JTable tablaAsistencia;
    private javax.swing.JTextField textBuscar;
    private javax.swing.JTextField textHoraEntrada;
    private javax.swing.JTextField textHoraSalida;
    // End of variables declaration//GEN-END:variables
}
