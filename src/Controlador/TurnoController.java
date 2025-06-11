/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Controlador;

import DAO.TurnoDAO;
import modelo.Turno;
import java.sql.SQLException;
import java.util.List;

public class TurnoController {
    private TurnoDAO turnoDAO;

    public TurnoController() {
        this.turnoDAO = new TurnoDAO();
    }

    private void validarCamposObligatorios(String fecha, String horaInicio, String horaFin, String tipoTurno) {
        if (fecha == null || fecha.trim().isEmpty()) {
            throw new IllegalArgumentException("La fecha es obligatoria.");
        }
        if (horaInicio == null || horaInicio.trim().isEmpty()) {
            throw new IllegalArgumentException("La hora de inicio es obligatoria.");
        }
        if (horaFin == null || horaFin.trim().isEmpty()) {
            throw new IllegalArgumentException("La hora de fin es obligatoria.");
        }
        if (tipoTurno == null || tipoTurno.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de turno es obligatorio.");
        }
        if (tipoTurno.length() > 20) {
            throw new IllegalArgumentException("El tipo de turno no puede exceder los 20 caracteres.");
        }
        if (!tipoTurno.equals("mañana") && !tipoTurno.equals("tarde") && !tipoTurno.equals("noche") && !tipoTurno.equals("flexible")) {
            throw new IllegalArgumentException("El tipo de turno debe ser 'mañana', 'tarde', 'noche' o 'flexible'.");
        }
    }

    public void crearTurno(int idEmpleado, String fecha, String horaInicio, String horaFin, String tipoTurno) {
        validarCamposObligatorios(fecha, horaInicio, horaFin, tipoTurno);
        try {
            turnoDAO.crearTurno(idEmpleado, fecha, horaInicio, horaFin, tipoTurno);
            System.out.println("Turno creado exitosamente para el empleado ID: " + idEmpleado);
        } catch (SQLException e) {
            System.err.println("Error de base de datos al crear el turno: " + e.getMessage());
            throw new RuntimeException("No se pudo crear el turno debido a un error de base de datos.", e);
        }
    }

    public List<Turno> obtenerTodosTurnos() {
        try {
            return turnoDAO.obtenerTodosTurnos();
        } catch (SQLException e) {
            System.err.println("Error de base de datos al obtener todos los turnos: " + e.getMessage());
            throw new RuntimeException("No se pudieron obtener los turnos debido a un error de base de datos.", e);
        }
    }

    public void actualizarTurno(int idTurno, String fecha, String horaInicio, String horaFin, String tipoTurno) {
        validarCamposObligatorios(fecha, horaInicio, horaFin, tipoTurno);
        try {
            turnoDAO.actualizarTurno(idTurno, fecha, horaInicio, horaFin, tipoTurno);
            System.out.println("Turno actualizado exitosamente con ID: " + idTurno);
        } catch (SQLException e) {
            System.err.println("Error de base de datos al actualizar el turno: " + e.getMessage());
            throw new RuntimeException("No se pudo actualizar el turno debido a un error de base de datos.", e);
        }
    }

    public void eliminarTurno(int idTurno) {
        try {
            turnoDAO.eliminarTurno(idTurno);
            System.out.println("Turno eliminado exitosamente con ID: " + idTurno);
        } catch (SQLException e) {
            System.err.println("Error de base de datos al eliminar el turno: " + e.getMessage());
            throw new RuntimeException("No se pudo eliminar el turno debido a un error de base de datos.", e);
        }
    }

    public List<Turno> leerTurnosPorEmpleado(int idEmpleado) {
        try {
            return turnoDAO.leerTurnosPorEmpleado(idEmpleado);
        } catch (SQLException e) {
            System.err.println("Error de base de datos al leer los turnos del empleado: " + e.getMessage());
            throw new RuntimeException("No se pudo leer los turnos del empleado debido a un error de base de datos.", e);
        }
    }
}