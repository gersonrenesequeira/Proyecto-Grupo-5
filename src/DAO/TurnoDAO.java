/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package DAO;

import modelo.Turno;
import util.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TurnoDAO {
    private Connection conexion;

    public TurnoDAO() {
        try {
            this.conexion = ConexionBD.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error al establecer la conexión: " + e.getMessage());
        }
    }

    public void crearTurno(int idEmpleado, String fecha, String horaInicio, String horaFin, String tipoTurno) throws SQLException {
        String sql = "INSERT INTO turnos (id_empleado, fecha, hora_inicio, hora_fin, tipo_turno) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idEmpleado);
            stmt.setString(2, fecha);
            stmt.setString(3, horaInicio);
            stmt.setString(4, horaFin);
            stmt.setString(5, tipoTurno);
            stmt.executeUpdate();
        }
    }

    public List<Turno> obtenerTodosTurnos() throws SQLException {
        List<Turno> turnos = new ArrayList<>();
        String sql = "SELECT t.*, e.nombre FROM turnos t LEFT JOIN Empleado e ON t.id_empleado = e.id_empleado";
        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Turno turno = new Turno();
                turno.setIdTurno(rs.getInt("id_turno"));
                turno.setIdEmpleado(rs.getInt("id_empleado"));
                turno.setFecha(rs.getString("fecha"));
                turno.setHoraInicio(rs.getString("hora_inicio"));
                turno.setHoraFin(rs.getString("hora_fin"));
                turno.setTipoTurno(rs.getString("tipo_turno"));
                turno.setNombreEmpleado(rs.getString("nombre"));
                turnos.add(turno);
            }
        }
        return turnos;
    }

    public void actualizarTurno(int idTurno, String fecha, String horaInicio, String horaFin, String tipoTurno) throws SQLException {
        String sql = "UPDATE turnos SET fecha = ?, hora_inicio = ?, hora_fin = ?, tipo_turno = ? WHERE id_turno = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, fecha);
            stmt.setString(2, horaInicio);
            stmt.setString(3, horaFin);
            stmt.setString(4, tipoTurno);
            stmt.setInt(5, idTurno);
            stmt.executeUpdate();
        }
    }

    public void eliminarTurno(int idTurno) throws SQLException {
        String sql = "DELETE FROM turnos WHERE id_turno = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idTurno);
            stmt.executeUpdate();
        }
    }

    public List<Turno> leerTurnosPorEmpleado(int idEmpleado) throws SQLException {
        List<Turno> turnos = new ArrayList<>();
        String sql = "SELECT t.*, e.nombre FROM turnos t LEFT JOIN Empleado e ON t.id_empleado = e.id_empleado WHERE t.id_empleado = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idEmpleado);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Turno turno = new Turno();
                    turno.setIdTurno(rs.getInt("id_turno"));
                    turno.setIdEmpleado(rs.getInt("id_empleado"));
                    turno.setFecha(rs.getString("fecha"));
                    turno.setHoraInicio(rs.getString("hora_inicio"));
                    turno.setHoraFin(rs.getString("hora_fin"));
                    turno.setTipoTurno(rs.getString("tipo_turno"));
                    turno.setNombreEmpleado(rs.getString("nombre"));
                    turnos.add(turno);
                }
            }
        }
        return turnos;
    }
}