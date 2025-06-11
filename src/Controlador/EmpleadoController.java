/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import DAO.EmpleadoDAO;
import modelo.Empleado;

import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.List;

public class EmpleadoController {
    private final EmpleadoDAO empleadoDAO;

    public EmpleadoController() {
        this.empleadoDAO = new EmpleadoDAO();
    }

    // Crear un nuevo empleado
    public void crearEmpleado(String nombre, String apellido, String cedula, String telefono,
                               String direccion, String correo, String rol) {
        try {
            Empleado empleado = new Empleado();
            empleado.setNombre(nombre);
            empleado.setApellido(apellido);
            empleado.setCedula(cedula);
            empleado.setTelefono(telefono);
            empleado.setDireccion(direccion);
            empleado.setCorreo(correo);
            empleado.setRol(rol);
            empleadoDAO.crearEmpleado(empleado);
            JOptionPane.showMessageDialog(null, "Empleado creado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al crear el empleado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Obtener todos los empleados
    public List<Empleado> obtenerTodosEmpleados() {
        try {
            return empleadoDAO.leerTodosEmpleados();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al leer los empleados: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    // Actualizar un empleado existente
    public void actualizarEmpleado(int idEmpleado, String nombre, String apellido, String cedula,
                                   String telefono, String direccion, String correo, String rol) {
        try {
            Empleado empleado = new Empleado();
            empleado.setIdEmpleado(idEmpleado);
            empleado.setNombre(nombre);
            empleado.setApellido(apellido);
            empleado.setCedula(cedula);
            empleado.setTelefono(telefono);
            empleado.setDireccion(direccion);
            empleado.setCorreo(correo);
            empleado.setRol(rol);
            empleadoDAO.actualizarEmpleado(empleado);
            JOptionPane.showMessageDialog(null, "Empleado actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el empleado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Eliminar un empleado
    public void eliminarEmpleado(int idEmpleado) {
        try {
            empleadoDAO.eliminarEmpleado(idEmpleado);
            JOptionPane.showMessageDialog(null, "Empleado eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el empleado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Main para pruebas
    public static void main(String[] args) {
        EmpleadoController controller = new EmpleadoController();

        // Crear un empleado
        controller.crearEmpleado("Mariano", "salgera", "456321123", "85071423", "Calle 666", "maagoxample.com", "Recepcionista");
    
       List<Empleado> empleados = controller.obtenerTodosEmpleados();
        if (empleados != null) {
            System.out.println("Lista de empleados:");
            for (Empleado e : empleados) {
                System.out.println("ID: " + e.getIdEmpleado()
                        + ", Nombre: " + e.getNombre() + " " + e.getApellido()
                        + ", Cédula: " + e.getCedula());
            }
        }

        // Actualizar un empleado (asumiendo ID 1)
        controller.actualizarEmpleado(1, "María", "López", "456789123", "7654321", "Av. Siempre Viva 123", "maria.lopez@example.com", "Supervisora");

        // Eliminar un empleado
        controller.eliminarEmpleado(1);
    
    
        // Leer empleados
     
    }
}
