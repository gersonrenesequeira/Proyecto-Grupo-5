/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.RegistroAsistenciaDAO;
import modelo.RegistroAsistencia;
import util.ConexionBD;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;

public class RegistroAsistenciaController {
    private RegistroAsistenciaDAO registroAsistenciaDAO;

    public RegistroAsistenciaController() throws SQLException {
        Connection conn = ConexionBD.getConnection();
        this.registroAsistenciaDAO = new RegistroAsistenciaDAO(conn);
    }
     public void registrarAsistencia(RegistroAsistencia registro) throws SQLException {
        try {
            registroAsistenciaDAO.insertar(registro);
            JOptionPane.showMessageDialog(null, "Registro de asistencia creado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al crear el registro de asistencia: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            throw e; // Propagamos la excepción para que el llamador pueda manejarla si es necesario
        }
    }

    public void actualizarAsistencia(RegistroAsistencia registro) throws SQLException {
        try {
            registroAsistenciaDAO.actualizar(registro);
            JOptionPane.showMessageDialog(null, "Registro de asistencia actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el registro de asistencia: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            throw e; // Propagamos la excepción
        }
    }

    public void eliminarAsistencia(int idRegistro) throws SQLException {
        try {
            registroAsistenciaDAO.eliminar(idRegistro);
            JOptionPane.showMessageDialog(null, "Registro de asistencia eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el registro de asistencia: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            throw e; // Propagamos la excepción
        }
    }

    public RegistroAsistencia obtenerAsistenciaPorId(int idRegistro) throws SQLException {
        try {
            return registroAsistenciaDAO.obtenerPorId(idRegistro);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener registro por ID: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            throw e; // Propagamos la excepción
        }
    }

    public List<RegistroAsistencia> obtenerAsistenciasPorEmpleado(int idEmpleado) throws SQLException {
        try {
            return registroAsistenciaDAO.obtenerPorIdEmpleado(idEmpleado);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener asistencias por empleado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            throw e; // Propagamos la excepción
        }
    }

    public List<RegistroAsistencia> obtenerTodasLasAsistencias() throws SQLException {
        try {
            return registroAsistenciaDAO.obtenerTodos();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener todas las asistencias: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            throw e; // Propagamos la excepción
        }
    }

    public List<RegistroAsistencia> obtenerAsistenciasPorCriterio(String criterio) throws SQLException {
        try {
            return registroAsistenciaDAO.obtenerAsistenciasPorCriterio(criterio);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar asistencias: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            throw e; // Propagamos la excepción
        }
    }
}
