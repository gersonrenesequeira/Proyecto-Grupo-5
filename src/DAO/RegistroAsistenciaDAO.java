/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package dao;

import modelo.RegistroAsistencia;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import util.ConexionBD;

public class RegistroAsistenciaDAO {
    private Connection connection;

    public RegistroAsistenciaDAO(Connection connection) {
        this.connection = connection;
    }

      public void insertar(RegistroAsistencia registro) throws SQLException {
        String sql = "INSERT INTO registro_asistencia (id_empleado, id_turno, fecha, hora_entrada, hora_salida, horas_trabajadas) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection c = ConexionBD.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, registro.getIdEmpleado());
            stmt.setInt(2, registro.getIdTurno());
            stmt.setDate(3, registro.getFecha() != null ? Date.valueOf(registro.getFecha()) : null);
            stmt.setTime(4, registro.getHoraEntrada() != null ? Time.valueOf(registro.getHoraEntrada()) : null);
            stmt.setTime(5, registro.getHoraSalida() != null ? Time.valueOf(registro.getHoraSalida()) : null);
            stmt.setFloat(6, registro.getHorasTrabajadas() != null ? registro.getHorasTrabajadas() : 0.0f);
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    registro.setIdRegistro(rs.getInt(1));
                }
            }
        }
    }

    public void actualizar(RegistroAsistencia registro) throws SQLException {
        String sql = "UPDATE registro_asistencia SET id_empleado = ?, id_turno = ?, fecha = ?, hora_entrada = ?, hora_salida = ?, horas_trabajadas = ? WHERE id_registro = ?";
        try (Connection c = ConexionBD.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setInt(1, registro.getIdEmpleado());
            stmt.setInt(2, registro.getIdTurno());
            stmt.setDate(3, registro.getFecha() != null ? Date.valueOf(registro.getFecha()) : null);
            stmt.setTime(4, registro.getHoraEntrada() != null ? Time.valueOf(registro.getHoraEntrada()) : null);
            stmt.setTime(5, registro.getHoraSalida() != null ? Time.valueOf(registro.getHoraSalida()) : null);
            stmt.setFloat(6, registro.getHorasTrabajadas() != null ? registro.getHorasTrabajadas() : 0.0f);
            stmt.setInt(7, registro.getIdRegistro());
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas == 0) {
                throw new SQLException("No se encontró registro con id_registro: " + registro.getIdRegistro());
            }
        }
    }

    public void eliminar(int idRegistro) throws SQLException {
        String sql = "DELETE FROM registro_asistencia WHERE id_registro = ?";
        try (Connection c = ConexionBD.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setInt(1, idRegistro);
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas == 0) {
                throw new SQLException("No se encontró registro con id_registro: " + idRegistro);
            }
        }
    }

    public RegistroAsistencia obtenerPorId(int idRegistro) throws SQLException {
        String sql = "SELECT * FROM registro_asistencia WHERE id_registro = ?";
        try (Connection c = ConexionBD.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return mapearRegistroAsistencia(rs);
            }
            return null;
        }
    }

    public List<RegistroAsistencia> obtenerPorIdEmpleado(int idEmpleado) throws SQLException {
        String sql = "SELECT * FROM registro_asistencia WHERE id_empleado = ?";
        List<RegistroAsistencia> registros = new ArrayList<>();
        try (Connection c = ConexionBD.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                registros.add(mapearRegistroAsistencia(rs));
            }
        }
        return registros;
    }

    public List<RegistroAsistencia> obtenerTodos() throws SQLException {
        String sql = "SELECT * FROM registro_asistencia";
        List<RegistroAsistencia> registros = new ArrayList<>();
        try (Connection c = ConexionBD.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                registros.add(mapearRegistroAsistencia(rs));
            }
        }
        return registros;
    }

    public List<RegistroAsistencia> obtenerAsistenciasPorCriterio(String criterio) throws SQLException {
        String sql = "SELECT * FROM registro_asistencia WHERE id_registro = ? OR id_empleado IN (SELECT id_empleado FROM Empleado WHERE nombre LIKE ? OR apellido LIKE ?)";
        List<RegistroAsistencia> registros = new ArrayList<>();
        try (Connection c = ConexionBD.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql)) {
            try {
                stmt.setInt(1, Integer.parseInt(criterio));
                stmt.setString(2, "%" + criterio + "%");
                stmt.setString(3, "%" + criterio + "%");
            } catch (NumberFormatException e) {
                sql = "SELECT * FROM registro_asistencia WHERE id_empleado IN (SELECT id_empleado FROM Empleado WHERE nombre LIKE ? OR apellido LIKE ?)";
                try (PreparedStatement stmt2 = c.prepareStatement(sql)) {
                    stmt2.setString(1, "%" + criterio + "%");
                    stmt2.setString(2, "%" + criterio + "%");
                    try (ResultSet rs = stmt2.executeQuery()) {
                        while (rs.next()) {
                            registros.add(mapearRegistroAsistencia(rs));
                        }
                    }
                    return registros;
                }
            }
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    registros.add(mapearRegistroAsistencia(rs));
                }
            }
        }
        return registros;
    }

    private RegistroAsistencia mapearRegistroAsistencia(ResultSet rs) throws SQLException {
        RegistroAsistencia registro = new RegistroAsistencia();
        registro.setIdRegistro(rs.getInt("id_registro"));
        registro.setIdEmpleado(rs.getInt("id_empleado"));
        registro.setIdTurno(rs.getInt("id_turno"));
        Date fecha = rs.getDate("fecha");
        registro.setFecha(fecha != null ? fecha.toLocalDate() : null);
        Time horaEntrada = rs.getTime("hora_entrada");
        registro.setHoraEntrada(horaEntrada != null ? horaEntrada.toLocalTime() : null);
        Time horaSalida = rs.getTime("hora_salida");
        registro.setHoraSalida(horaSalida != null ? horaSalida.toLocalTime() : null);
        registro.setHorasTrabajadas(rs.getFloat("horas_trabajadas"));
        return registro;
    }
}