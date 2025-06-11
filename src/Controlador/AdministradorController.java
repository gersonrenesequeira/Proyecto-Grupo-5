/*
 * Click nb fs://nb fs/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nb fs://nb fs/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import DAO.AdministradorDAO;
import modelo.Administrador;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Gena
 */
public class AdministradorController {
    private final AdministradorDAO administradorDAO;

    public AdministradorController() {
        this.administradorDAO = new AdministradorDAO();
    }

    public void crearAdministrador(int idEmpleado, String login, String password) {
        try {
            Administrador admin = new Administrador();
            admin.setIdEmpleado(idEmpleado);
            admin.setLogin(login);
            admin.setPassword(password);
            admin.setUltimaActividad(LocalDateTime.now());
            administradorDAO.crearAdministrador(admin);
            JOptionPane.showMessageDialog(null, "Administrador creado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al crear el administrador: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Administrador validarCredenciales(String login, String password) {
        try {
            Administrador admin = administradorDAO.validarAdministrador(login, password);
            if (admin != null) {
                System.out.println("Inicio de sesión exitoso.");
                return admin;
            } else {
                JOptionPane.showMessageDialog(null, "Login o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al validar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public List<Administrador> obtenerTodosAdministradores() {
        try {
            return administradorDAO.leerTodosAdministradores();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al leer los administradores: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public void actualizarAdministrador(int idAdministrador, int idEmpleado, String login, String password, LocalDateTime ultimaActividad) {
        try {
            Administrador admin = new Administrador();
            admin.setIdAdministrador(idAdministrador);
            admin.setIdEmpleado(idEmpleado);
            admin.setLogin(login);
            admin.setPassword(password);
            admin.setUltimaActividad(ultimaActividad);
            administradorDAO.actualizarAdministrador(admin);
            JOptionPane.showMessageDialog(null, "Administrador actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el administrador: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void eliminarAdministrador(int idAdministrador) {
        try {
            administradorDAO.eliminarAdministrador(idAdministrador);
            JOptionPane.showMessageDialog(null, "Administrador eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el administrador: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        AdministradorController controlador = new AdministradorController();

        // Crear un administrador
        controlador.crearAdministrador(100, "ana_admin", "ana2025");

        // Leer todos los administradores
        List<Administrador> administradores = controlador.obtenerTodosAdministradores();
        if (administradores != null) {
            System.out.println("Lista de administradores:");
            for (Administrador a : administradores) {
                System.out.println("ID: " + a.getIdAdministrador()
                        + ", Empleado ID: " + a.getIdEmpleado()
                        + ", Login: " + a.getLogin());
            }
        }

        // Actualizar un administrador (suponiendo que ID 1 existe)
        controlador.actualizarAdministrador(1, 100, "ana_maria_admin", "nueva2025", LocalDateTime.now());

        // Eliminar un administrador
        controlador.eliminarAdministrador(1);
    }
}