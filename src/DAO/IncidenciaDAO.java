/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package dao;

import modelo.Incidencia;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import util.ConexionBD;

public class IncidenciaDAO {
    private Connection connection;
    private static final List<String> TIPOS_VALIDOS = Arrays.asList("retraso", "ausencia", "permiso", "otro");

    public IncidenciaDAO(Connection connection) {
        this.connection = connection;
    }

    // Validar tipo_incidencia
    private void validarTipoIncidencia(String tipoIncidencia) throws SQLException {
        if (tipoIncidencia == null || !TIPOS_VALIDOS.contains(tipoIncidencia.toLowerCase())) {
            throw new SQLException("El tipo_incidencia debe ser uno de: " + TIPOS_VALIDOS);
        }
    }

    // Insertar una nueva incidencia
    public void insertar(Incidencia incidencia) throws SQLException {
        validarTipoIncidencia(incidencia.getTipoIncidencia());
        String sql = "INSERT INTO incidencias (id_empleado, tipo_incidencia, fecha_incidencia, descripcion) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, incidencia.getIdEmpleado());
            stmt.setString(2, incidencia.getTipoIncidencia());
            stmt.setDate(3, incidencia.getFechaIncidencia() != null ? 
                Date.valueOf(incidencia.getFechaIncidencia()) : null);
            stmt.setString(4, incidencia.getDescripcion());
            stmt.executeUpdate();

            // Obtener el ID generado
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    incidencia.setIdIncidencia(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == 1452) { // Error de MySQL para violación de clave foránea
                throw new SQLException("El id_empleado " + incidencia.getIdEmpleado() + " no existe en la tabla Empleado", e);
            } else if (e.getErrorCode() == 1048) { // Columna no puede ser nula
                throw new SQLException("Una columna obligatoria no puede ser nula: " + e.getMessage(), e);
            }
            throw new SQLException("Error al insertar incidencia: " + e.getMessage(), e);
        }
    }

    // Actualizar una incidencia existente
    public void actualizar(Incidencia incidencia) throws SQLException {
        validarTipoIncidencia(incidencia.getTipoIncidencia());
        String sql = "UPDATE incidencias SET id_empleado = ?, tipo_incidencia = ?, fecha_incidencia = ?, descripcion = ? WHERE id_incidencia = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, incidencia.getIdEmpleado());
            stmt.setString(2, incidencia.getTipoIncidencia());
            stmt.setDate(3, incidencia.getFechaIncidencia() != null ? 
                Date.valueOf(incidencia.getFechaIncidencia()) : null);
            stmt.setString(4, incidencia.getDescripcion());
            stmt.setInt(5, incidencia.getIdIncidencia());
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas == 0) {
                throw new SQLException("No se encontró incidencia con id_incidencia: " + incidencia.getIdIncidencia());
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == 1452) { // Error de MySQL para violación de clave foránea
                throw new SQLException("El id_empleado " + incidencia.getIdEmpleado() + " no existe en la tabla Empleado", e);
            }
            throw new SQLException("Error al actualizar incidencia: " + e.getMessage(), e);
        }
    }

    // Eliminar una incidencia por id_incidencia
    public void eliminar(int idIncidencia) throws SQLException {
        String sql = "DELETE FROM incidencias WHERE id_incidencia = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idIncidencia);
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas == 0) {
                throw new SQLException("No se encontró incidencia con id_incidencia: " + idIncidencia);
            }
        } catch (SQLException e) {
            throw new SQLException("Error al eliminar incidencia: " + e.getMessage(), e);
        }
    }

    // Obtener una incidencia por id_incidencia
    public Incidencia obtenerPorId(int idIncidencia) throws SQLException {
        String sql = "SELECT * FROM incidencias WHERE id_incidencia = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idIncidencia);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearIncidencia(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener incidencia por ID: " + e.getMessage(), e);
        }
    }

    // Obtener incidencias por id_empleado
    public List<Incidencia> obtenerPorIdEmpleado(int idEmpleado) throws SQLException {
        String sql = "SELECT * FROM incidencias WHERE id_empleado = ?";
        List<Incidencia> incidencias = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idEmpleado);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    incidencias.add(mapearIncidencia(rs));
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener incidencias por id_empleado: " + e.getMessage(), e);
        }
        return incidencias;
    }

    // Obtener todas las incidencias
    public List<Incidencia> obtenerTodas() throws SQLException {
        String sql = "SELECT * FROM incidencias";
        List<Incidencia> incidencias = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                incidencias.add(mapearIncidencia(rs));
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener todas las incidencias: " + e.getMessage(), e);
        }
        return incidencias;
    }

    // Método auxiliar para mapear un ResultSet a un objeto Incidencia
    private Incidencia mapearIncidencia(ResultSet rs) throws SQLException {
        Incidencia incidencia = new Incidencia();
        try {
            incidencia.setIdIncidencia(rs.getInt("id_incidencia"));
            incidencia.setIdEmpleado(rs.getInt("id_empleado"));
            incidencia.setTipoIncidencia(rs.getString("tipo_incidencia"));
            Date fechaIncidencia = rs.getDate("fecha_incidencia");
            incidencia.setFechaIncidencia(fechaIncidencia != null ? fechaIncidencia.toLocalDate() : null);
            incidencia.setDescripcion(rs.getString("descripcion"));
        } catch (SQLException e) {
            throw new SQLException("Error al mapear incidencia: " + e.getMessage(), e);
        }
        return incidencia;
    }

    // Método main para probar las operaciones del DAO
    public static void main(String[] args) {
        Connection conn = null;
        try {
            System.out.println("Intentando conectar a la base de datos...");
            conn = ConexionBD.getConnection();
            System.out.println("Conexión a la base de datos establecida correctamente.");

            IncidenciaDAO incidenciaDAO = new IncidenciaDAO(conn);
            System.out.println("Instancia de IncidenciaDAO creada correctamente.");

            // 1. Insertar una nueva incidencia
            System.out.println("\n=== Insertando una nueva incidencia ===");
            Incidencia nuevaIncidencia = new Incidencia();
            int idEmpleado = 6; // Cambia a un id_empleado existente en la tabla Empleado
            System.out.println("Preparando incidencia con id_empleado=" + idEmpleado);
            nuevaIncidencia.setIdEmpleado(idEmpleado);
            nuevaIncidencia.setTipoIncidencia("retraso");
            nuevaIncidencia.setFechaIncidencia(LocalDate.now());
            nuevaIncidencia.setDescripcion("El empleado llegó 30 minutos tarde sin justificación.");
            System.out.println("Valores establecidos: " + 
                "id_empleado=" + nuevaIncidencia.getIdEmpleado() + 
                ", tipo_incidencia=" + nuevaIncidencia.getTipoIncidencia() + 
                ", fecha_incidencia=" + nuevaIncidencia.getFechaIncidencia() + 
                ", descripcion=" + nuevaIncidencia.getDescripcion());
            try {
                incidenciaDAO.insertar(nuevaIncidencia);
                System.out.println("Incidencia insertada con ID: " + nuevaIncidencia.getIdIncidencia());
            } catch (SQLException e) {
                System.err.println("Error al insertar incidencia: " + e.getMessage());
                e.printStackTrace();
                return; // Salir si falla la inserción para evitar errores posteriores
            }

            // 2. Obtener la incidencia recién insertada por ID
            System.out.println("\n=== Obteniendo incidencia por ID ===");
            if (nuevaIncidencia.getIdIncidencia() > 0) {
                Incidencia incidenciaPorId = incidenciaDAO.obtenerPorId(nuevaIncidencia.getIdIncidencia());
                if (incidenciaPorId != null) {
                    System.out.println("Incidencia encontrada: ID=" + incidenciaPorId.getIdIncidencia() + 
                        ", Empleado=" + incidenciaPorId.getIdEmpleado() + 
                        ", Tipo=" + incidenciaPorId.getTipoIncidencia() + 
                        ", Fecha=" + incidenciaPorId.getFechaIncidencia() + 
                        ", Descripción=" + incidenciaPorId.getDescripcion());
                } else {
                    System.out.println("No se encontró incidencia con ID: " + nuevaIncidencia.getIdIncidencia());
                }
            }

            // 3. Obtener incidencias por id_empleado
            System.out.println("\n=== Obteniendo incidencias por id_empleado ===");
            List<Incidencia> incidenciasPorEmpleado = incidenciaDAO.obtenerPorIdEmpleado(idEmpleado);
            if (!incidenciasPorEmpleado.isEmpty()) {
                for (Incidencia inc : incidenciasPorEmpleado) {
                    System.out.println("Incidencia: ID=" + inc.getIdIncidencia() + 
                        ", Empleado=" + inc.getIdEmpleado() + 
                        ", Tipo=" + inc.getTipoIncidencia() + 
                        ", Fecha=" + inc.getFechaIncidencia() + 
                        ", Descripción=" + inc.getDescripcion());
                }
            } else {
                System.out.println("No se encontraron incidencias para id_empleado: " + idEmpleado);
            }

            // 4. Actualizar la incidencia
            System.out.println("\n=== Actualizando incidencia ===");
            if (nuevaIncidencia.getIdIncidencia() > 0) {
                nuevaIncidencia.setTipoIncidencia("ausencia");
                nuevaIncidencia.setFechaIncidencia(LocalDate.now());
                nuevaIncidencia.setDescripcion("El empleado no asistió al turno sin notificar.");
                try {
                    incidenciaDAO.actualizar(nuevaIncidencia);
                    System.out.println("Incidencia actualizada: ID=" + nuevaIncidencia.getIdIncidencia());
                    
                    // Verificar la actualización
                    Incidencia incidenciaActualizada = incidenciaDAO.obtenerPorId(nuevaIncidencia.getIdIncidencia());
                    System.out.println("Nuevo tipo: " + incidenciaActualizada.getTipoIncidencia() + 
                        ", Nueva descripción: " + incidenciaActualizada.getDescripcion());
                } catch (SQLException e) {
                    System.err.println("Error al actualizar incidencia: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            // 5. Obtener todas las incidencias
            System.out.println("\n=== Listando todas las incidencias ===");
            List<Incidencia> todasIncidencias = incidenciaDAO.obtenerTodas();
            if (!todasIncidencias.isEmpty()) {
                for (Incidencia inc : todasIncidencias) {
                    System.out.println("Incidencia: ID=" + inc.getIdIncidencia() + 
                        ", Empleado=" + inc.getIdEmpleado() + 
                        ", Tipo=" + inc.getTipoIncidencia() + 
                        ", Fecha=" + inc.getFechaIncidencia() + 
                        ", Descripción=" + inc.getDescripcion());
                }
            } else {
                System.out.println("No se encontraron incidencias.");
            }

            // 6. Eliminar la incidencia
            System.out.println("\n=== Eliminando incidencia ===");
            if (nuevaIncidencia.getIdIncidencia() > 0) {
                try {
                    incidenciaDAO.eliminar(nuevaIncidencia.getIdIncidencia());
                    System.out.println("Incidencia con ID " + nuevaIncidencia.getIdIncidencia() + " eliminada.");

                    // Verificar que se eliminó
                    Incidencia incidenciaEliminada = incidenciaDAO.obtenerPorId(nuevaIncidencia.getIdIncidencia());
                    if (incidenciaEliminada == null) {
                        System.out.println("Confirmado: No se encontró incidencia con ID: " + nuevaIncidencia.getIdIncidencia());
                    }
                } catch (SQLException e) {
                    System.err.println("Error al eliminar incidencia: " + e.getMessage());
                    e.printStackTrace();
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("Conexión cerrada correctamente.");
                } catch (SQLException e) {
                    System.err.println("Error al cerrar la conexión: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }
}