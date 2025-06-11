/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nb fs://nb fs/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import modelo.Administrador;
import util.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gena
 */
public class AdministradorDAO {

    public void crearAdministrador(Administrador administrador) throws SQLException {
        String sql = """
            INSERT INTO Administrador (
                id_empleado, 
                login, 
                password, 
                ultima_actividad
            ) VALUES (?, ?, ?, ?)""";

        try (Connection c = ConexionBD.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setInt(1, administrador.getIdEmpleado());
            stmt.setString(2, administrador.getLogin());
            stmt.setString(3, administrador.getPassword());
            stmt.setObject(4, administrador.getUltimaActividad());
            stmt.executeUpdate();
        }
    }

    public Administrador validarAdministrador(String login, String password) throws SQLException {
        String sql = "SELECT * FROM Administrador WHERE login = ? AND password = ?";
        try (Connection c = ConexionBD.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setString(1, login);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Administrador(
                        rs.getInt("id_administrador"),
                        rs.getInt("id_empleado"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getObject("ultima_actividad", LocalDateTime.class)
                    );
                }
            }
        }
        return null; // Si no se encuentra coincidencia
    }

    public List<Administrador> leerTodosAdministradores() throws SQLException {
        String sql = "SELECT * FROM Administrador";
        List<Administrador> administradores = new ArrayList<>();

        try (Connection c = ConexionBD.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Administrador administrador = new Administrador();
                administrador.setIdAdministrador(rs.getInt("id_administrador"));
                administrador.setIdEmpleado(rs.getInt("id_empleado"));
                administrador.setLogin(rs.getString("login"));
                administrador.setPassword(rs.getString("password"));
                administrador.setUltimaActividad(rs.getObject("ultima_actividad", LocalDateTime.class));
                administradores.add(administrador);
            }
        }
        return administradores;
    }

    public void actualizarAdministrador(Administrador administrador) throws SQLException {
        String sql = "UPDATE Administrador SET id_empleado = ?, login = ?, password = ?, ultima_actividad = ? WHERE id_administrador = ?";

        try (Connection c = ConexionBD.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setInt(1, administrador.getIdEmpleado());
            stmt.setString(2, administrador.getLogin());
            stmt.setString(3, administrador.getPassword());
            stmt.setObject(4, administrador.getUltimaActividad());
            stmt.setInt(5, administrador.getIdAdministrador());
            stmt.executeUpdate();
        }
    }

    public void eliminarAdministrador(int idAdministrador) throws SQLException {
        String sql = "DELETE FROM Administrador WHERE id_administrador = ?";

        try (Connection c = ConexionBD.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setInt(1, idAdministrador);
            stmt.executeUpdate();
        }
    }

    public static void main(String[] args) {
        try {
            AdministradorDAO dao = new AdministradorDAO();
            Administrador administrador = new Administrador();
            administrador.setIdAdministrador(1); // ID existente
            administrador.setIdEmpleado(100);
            administrador.setLogin("nuevo_admin");
            administrador.setPassword("nueva_password");
            administrador.setUltimaActividad(LocalDateTime.now());
            dao.actualizarAdministrador(administrador);
            System.out.println("Administrador actualizado.");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}