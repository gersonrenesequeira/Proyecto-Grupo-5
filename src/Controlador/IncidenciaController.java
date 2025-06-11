/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package controlador;

import dao.IncidenciaDAO;
import modelo.Incidencia;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import util.ConexionBD;

public class IncidenciaController {
    private final IncidenciaDAO incidenciaDAO;
    private static final List<String> TIPOS_VALIDOS = Arrays.asList("retraso", "ausencia", "permiso", "otro");

    public IncidenciaController(Connection connection) {
        this.incidenciaDAO = new IncidenciaDAO(connection);
    }

    // Validar tipo_incidencia
    private void validarTipoIncidencia(String tipoIncidencia) throws IllegalArgumentException {
        if (tipoIncidencia == null || !TIPOS_VALIDOS.contains(tipoIncidencia.toLowerCase())) {
            throw new IllegalArgumentException("El tipo_incidencia debe ser uno de: " + TIPOS_VALIDOS);
        }
    }

    // Crear una nueva incidencia
    public void crearIncidencia(Incidencia incidencia) throws SQLException, IllegalArgumentException {
        if (incidencia == null) {
            throw new IllegalArgumentException("La incidencia no puede ser nula");
        }
        if (incidencia.getIdEmpleado() <= 0) {
            throw new IllegalArgumentException("El ID del empleado debe ser mayor que 0");
        }
        if (incidencia.getTipoIncidencia() == null || incidencia.getTipoIncidencia().trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo_incidencia no puede ser nulo o vacío");
        }
        if (incidencia.getFechaIncidencia() == null) {
            throw new IllegalArgumentException("La fecha_incidencia no puede ser nula");
        }

        // Validar tipo_incidencia
        validarTipoIncidencia(incidencia.getTipoIncidencia());

        // Verificar si el id_empleado existe (opcional, pero mejora la robustez)
        try {
            List<Incidencia> incidencias = incidenciaDAO.obtenerPorIdEmpleado(incidencia.getIdEmpleado());
        } catch (SQLException e) {
            if (e.getMessage().contains("no existe en la tabla Empleado")) {
                throw new IllegalArgumentException("El id_empleado " + incidencia.getIdEmpleado() + " no existe en la tabla Empleado");
            }
            throw e;
        }

        incidenciaDAO.insertar(incidencia);
    }

    // Actualizar una incidencia existente
    public void actualizarIncidencia(Incidencia incidencia) throws SQLException, IllegalArgumentException {
        if (incidencia == null) {
            throw new IllegalArgumentException("La incidencia no puede ser nula");
        }
        if (incidencia.getIdIncidencia() <= 0) {
            throw new IllegalArgumentException("El ID de la incidencia debe ser mayor que 0");
        }
        if (incidencia.getIdEmpleado() <= 0) {
            throw new IllegalArgumentException("El ID del empleado debe ser mayor que 0");
        }
        if (incidencia.getTipoIncidencia() == null || incidencia.getTipoIncidencia().trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo_incidencia no puede ser nulo o vacío");
        }
        if (incidencia.getFechaIncidencia() == null) {
            throw new IllegalArgumentException("La fecha_incidencia no puede ser nula");
        }

        // Validar tipo_incidencia
        validarTipoIncidencia(incidencia.getTipoIncidencia());

        // Verificar si la incidencia existe
        if (incidenciaDAO.obtenerPorId(incidencia.getIdIncidencia()) == null) {
            throw new IllegalArgumentException("No existe una incidencia con el ID proporcionado");
        }

        // Verificar si el id_empleado existe
        try {
            List<Incidencia> incidencias = incidenciaDAO.obtenerPorIdEmpleado(incidencia.getIdEmpleado());
        } catch (SQLException e) {
            if (e.getMessage().contains("no existe en la tabla Empleado")) {
                throw new IllegalArgumentException("El id_empleado " + incidencia.getIdEmpleado() + " no existe en la tabla Empleado");
            }
            throw e;
        }

        incidenciaDAO.actualizar(incidencia);
    }

    // Eliminar una incidencia por ID
    public void eliminarIncidencia(int idIncidencia) throws SQLException, IllegalArgumentException {
        if (idIncidencia <= 0) {
            throw new IllegalArgumentException("El ID de la incidencia debe ser mayor que 0");
        }
        if (incidenciaDAO.obtenerPorId(idIncidencia) == null) {
            throw new IllegalArgumentException("No existe una incidencia con el ID proporcionado");
        }
        incidenciaDAO.eliminar(idIncidencia);
    }

    // Obtener una incidencia por ID
    public Incidencia obtenerIncidenciaPorId(int idIncidencia) throws SQLException, IllegalArgumentException {
        if (idIncidencia <= 0) {
            throw new IllegalArgumentException("El ID de la incidencia debe ser mayor que 0");
        }
        Incidencia incidencia = incidenciaDAO.obtenerPorId(idIncidencia);
        if (incidencia == null) {
            throw new IllegalArgumentException("No existe una incidencia con el ID proporcionado");
        }
        return incidencia;
    }

    // Obtener incidencias por id_empleado
    public List<Incidencia> obtenerIncidenciasPorEmpleado(int idEmpleado) throws SQLException, IllegalArgumentException {
        if (idEmpleado <= 0) {
            throw new IllegalArgumentException("El ID del empleado debe ser mayor que 0");
        }
        return incidenciaDAO.obtenerPorIdEmpleado(idEmpleado);
    }

    // Obtener todas las incidencias
    public List<Incidencia> obtenerTodasIncidencias() throws SQLException {
        return incidenciaDAO.obtenerTodas();
    }

    // Método main para probar las operaciones del controlador
    public static void main(String[] args) {
        try (Connection conn = ConexionBD.getConnection()) {
            IncidenciaController controller = new IncidenciaController(conn);
            System.out.println("Conexión a la base de datos establecida correctamente.");

            // 1. Crear una nueva incidencia
            System.out.println("\n=== Creando una nueva incidencia ===");
            Incidencia nuevaIncidencia = new Incidencia();
            int idEmpleado = 6; // Cambia a un id_empleado existente en la tabla Empleado
            nuevaIncidencia.setIdEmpleado(idEmpleado);
            nuevaIncidencia.setTipoIncidencia("retraso");
            nuevaIncidencia.setFechaIncidencia(LocalDate.now());
            nuevaIncidencia.setDescripcion("El empleado llegó 30 minutos tarde sin justificación.");
            try {
                controller.crearIncidencia(nuevaIncidencia);
                System.out.println("Incidencia creada con ID: " + nuevaIncidencia.getIdIncidencia());
            } catch (IllegalArgumentException | SQLException e) {
                System.err.println("Error al crear incidencia: " + e.getMessage());
            }

            // 2. Obtener la incidencia por ID
            System.out.println("\n=== Obteniendo incidencia por ID ===");
            try {
                if (nuevaIncidencia.getIdIncidencia() > 0) {
                    Incidencia incidenciaPorId = controller.obtenerIncidenciaPorId(nuevaIncidencia.getIdIncidencia());
                    System.out.println("Incidencia encontrada: ID=" + incidenciaPorId.getIdIncidencia() + 
                        ", Empleado=" + incidenciaPorId.getIdEmpleado() + 
                        ", Tipo=" + incidenciaPorId.getTipoIncidencia() + 
                        ", Fecha=" + incidenciaPorId.getFechaIncidencia() + 
                        ", Descripción=" + incidenciaPorId.getDescripcion());
                }
            } catch (IllegalArgumentException | SQLException e) {
                System.err.println("Error al obtener incidencia por ID: " + e.getMessage());
            }

            // 3. Obtener incidencias por id_empleado
            System.out.println("\n=== Obteniendo incidencias por id_empleado ===");
            try {
                List<Incidencia> incidenciasPorEmpleado = controller.obtenerIncidenciasPorEmpleado(idEmpleado);
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
            } catch (IllegalArgumentException | SQLException e) {
                System.err.println("Error al obtener incidencias por id_empleado: " + e.getMessage());
            }

            // 4. Actualizar la incidencia
            System.out.println("\n=== Actualizando incidencia ===");
            try {
                if (nuevaIncidencia.getIdIncidencia() > 0) {
                    nuevaIncidencia.setTipoIncidencia("ausencia");
                    nuevaIncidencia.setFechaIncidencia(LocalDate.now());
                    nuevaIncidencia.setDescripcion("El empleado no asistió al turno sin notificar.");
                    controller.actualizarIncidencia(nuevaIncidencia);
                    System.out.println("Incidencia actualizada: ID=" + nuevaIncidencia.getIdIncidencia());
                    
                    // Verificar la actualización
                    Incidencia incidenciaActualizada = controller.obtenerIncidenciaPorId(nuevaIncidencia.getIdIncidencia());
                    System.out.println("Nuevo tipo: " + incidenciaActualizada.getTipoIncidencia() + 
                        ", Nueva descripción: " + incidenciaActualizada.getDescripcion());
                }
            } catch (IllegalArgumentException | SQLException e) {
                System.err.println("Error al actualizar incidencia: " + e.getMessage());
            }

            // 5. Obtener todas las incidencias
            System.out.println("\n=== Listando todas las incidencias ===");
            try {
                List<Incidencia> todasIncidencias = controller.obtenerTodasIncidencias();
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
            } catch (SQLException e) {
                System.err.println("Error al listar incidencias: " + e.getMessage());
            }

            // 6. Eliminar la incidencia
            System.out.println("\n=== Eliminando incidencia ===");
            try {
                if (nuevaIncidencia.getIdIncidencia() > 0) {
                    controller.eliminarIncidencia(nuevaIncidencia.getIdIncidencia());
                    System.out.println("Incidencia con ID " + nuevaIncidencia.getIdIncidencia() + " eliminada.");

                    // Verificar que se eliminó
                    try {
                        controller.obtenerIncidenciaPorId(nuevaIncidencia.getIdIncidencia());
                        System.out.println("Error: La incidencia aún existe.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Confirmado: No se encontró incidencia con ID: " + nuevaIncidencia.getIdIncidencia());
                    }
                }
            } catch (IllegalArgumentException | SQLException e) {
                System.err.println("Error al eliminar incidencia: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }
}