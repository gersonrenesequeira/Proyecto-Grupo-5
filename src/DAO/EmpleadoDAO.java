/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package DAO;

import modelo.Empleado;
import util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {

   public void crearEmpleado(Empleado empleado) throws SQLException {
    // Validar el rol antes de la inserción
    String rol = empleado.getRol();
    if (rol == null || (
        !rol.equals("empleado") && 
        !rol.equals("administrador") && 
        !rol.equals("servicio al cliente") && 
        !rol.equals("cajero") && 
        !rol.equals("mantenimiento")
    )) {
        throw new IllegalArgumentException(
            "El rol debe ser 'empleado', 'administrador', 'servicio al cliente', 'cajero' o 'mantenimiento'. Valor recibido: " + rol
        );
    }

    String sql = """
        INSERT INTO Empleado (
            nombre,
            apellido,
            cedula,
            telefono,
            direccion,
            correo,
            rol
        ) VALUES (?, ?, ?, ?, ?, ?, ?)
    """;

    try (Connection c = ConexionBD.getConnection();
         PreparedStatement stmt = c.prepareStatement(sql)) {
        stmt.setString(1, empleado.getNombre());
        stmt.setString(2, empleado.getApellido());
        stmt.setString(3, empleado.getCedula());
        stmt.setString(4, empleado.getTelefono());
        stmt.setString(5, empleado.getDireccion());
        stmt.setString(6, empleado.getCorreo());
        stmt.setString(7, empleado.getRol());
        stmt.executeUpdate();
    }
}

    public List<Empleado> leerTodosEmpleados() throws SQLException {
        String sql = "SELECT * FROM Empleado";
        List<Empleado> empleados = new ArrayList<>();

        try (Connection c = ConexionBD.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Empleado empleado = new Empleado();
                empleado.setIdEmpleado(rs.getInt("id_empleado"));
                empleado.setNombre(rs.getString("nombre"));
                empleado.setApellido(rs.getString("apellido"));
                empleado.setCedula(rs.getString("cedula"));
                empleado.setTelefono(rs.getString("telefono"));
                empleado.setDireccion(rs.getString("direccion"));
                empleado.setCorreo(rs.getString("correo"));
                empleado.setRol(rs.getString("rol"));
                empleados.add(empleado);
            }
        }
        return empleados;
    }

    public void actualizarEmpleado(Empleado empleado) throws SQLException {
        String sql = """
            UPDATE Empleado SET
                nombre = ?,
                apellido = ?,
                cedula = ?,
                telefono = ?,
                direccion = ?,
                correo = ?,
                rol = ?
            WHERE id_empleado = ?
        """;

        try (Connection c = ConexionBD.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setString(1, empleado.getNombre());
            stmt.setString(2, empleado.getApellido());
            stmt.setString(3, empleado.getCedula());
            stmt.setString(4, empleado.getTelefono());
            stmt.setString(5, empleado.getDireccion());
            stmt.setString(6, empleado.getCorreo());
            stmt.setString(7, empleado.getRol());
            stmt.setInt(8, empleado.getIdEmpleado());
            stmt.executeUpdate();
        }
    }

    public void eliminarEmpleado(int idEmpleado) throws SQLException {
        String sql = "DELETE FROM Empleado WHERE id_empleado = ?";

        try (Connection c = ConexionBD.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setInt(1, idEmpleado);
            stmt.executeUpdate();
        }
    }

    // Método para probar el DAO
public static void main(String[] args) {
    try {
        EmpleadoDAO dao = new EmpleadoDAO();

        Empleado empleado = new Empleado();
        empleado.setNombre("alvarado");
        empleado.setApellido("tomas");
        empleado.setCedula("91563745");
        empleado.setTelefono("8517-1764");
        empleado.setDireccion("RS. boaco 456");
        empleado.setCorreo("re.kar@gmail.com");
        empleado.setRol("cajero"); // Cambia "Recepcionista" por "empleado" o "administrador"

        dao.crearEmpleado(empleado);
        System.out.println("Empleado creado correctamente.");

    } catch (SQLException e) {
        System.err.println("Error al operar con empleado: " + e.getMessage());
    }
}
}