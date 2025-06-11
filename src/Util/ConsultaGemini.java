package util;

import okhttp3.*;
import org.json.JSONObject;

public class ConsultaGemini {
    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent";
    private static final String API_KEY = "AIzaSyDpOgqNJkgiDE4dvlAygjQrg7pNlo5jn0E"; // Reemplaza con tu clave válida de Gemini

    public String generarConsultaSQL(String consultaNatural, String esquemaBD) throws Exception {
        OkHttpClient client = new OkHttpClient();
        String prompt = """
            Genera una consulta SQL válida para una base de datos MySQL con las siguientes tablas, vistas, funciones y relaciones:
            %s
            Instrucciones:
            - Usa solo las columnas listadas en cada tabla o vista.
            - Respeta el orden de los atributos según el esquema de la base de datos.
            - Asegúrate de que los JOINs sean correctos y utilicen las claves foráneas especificadas.
            - Si se solicita información de múltiples tablas, usa JOINs explícitos.
            - No generes subconsultas complejas ni funciones avanzadas a menos que sean explícitamente solicitadas.
            - Soporta funciones como ObtenerNombreCompleto, TotalHorasTrabajadasEmpleado, etc., si se mencionan.
            - Para procedimientos almacenados como HistorialAsistenciaEmpleado, devuélvelos con la sintaxis CALL.
            - Devuelve la consulta SQL en una sola línea, sin saltos de línea, comillas triples, ni formato adicional.
            Pregunta del usuario: "%s"
            """.formatted(esquemaBD, consultaNatural);

        JSONObject json = new JSONObject();
        json.put("contents", new JSONObject().put("parts", new JSONObject().put("text", prompt)));
        json.put("generationConfig", new JSONObject().put("response_mime_type", "text/plain"));

        RequestBody body = RequestBody.create(json.toString(), MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(API_URL + "?key=" + API_KEY)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new Exception("Error en la solicitud a Gemini: " + response.code());
            }
            String respuesta = response.body().string();
            return new JSONObject(respuesta).getJSONArray("candidates")
                    .getJSONObject(0)
                    .getJSONObject("content")
                    .getJSONArray("parts")
                    .getJSONObject(0)
                    .getString("text");
        }
    }

    public String obtenerEsquemaBD() {
        return """
            Tabla: Empleado (
                id_empleado INT PRIMARY KEY AUTO_INCREMENT,
                nombre VARCHAR(50) NOT NULL,
                apellido VARCHAR(50) NOT NULL,
                cedula VARCHAR(20) NOT NULL UNIQUE,
                correo VARCHAR(100),
                telefono VARCHAR(20),
                direccion VARCHAR(200),
                rol VARCHAR(20) NOT NULL CHECK (rol IN ('empleado', 'administrador', 'servicio al cliente', 'cajero', 'mantenimiento'))
            )
            Tabla: Administrador (
                id_administrador INT PRIMARY KEY AUTO_INCREMENT,
                id_empleado INT NOT NULL UNIQUE,
                login VARCHAR(50) NOT NULL UNIQUE,
                password VARCHAR(100) NOT NULL,
                ultima_actividad DATETIME,
                FOREIGN KEY (id_empleado) REFERENCES Empleado(id_empleado)
            )
            Tabla: Turnos (
                id_turno INT PRIMARY KEY AUTO_INCREMENT,
                id_empleado INT NOT NULL,
                fecha DATE NOT NULL,
                hora_inicio TIME NOT NULL,
                hora_fin TIME NOT NULL,
                tipo_turno VARCHAR(20) NOT NULL CHECK (tipo_turno IN ('mañana', 'tarde', 'noche', 'flexible')),
                FOREIGN KEY (id_empleado) REFERENCES Empleado(id_empleado)
            )
            Tabla: Registro_Asistencia (
                id_registro INT PRIMARY KEY AUTO_INCREMENT,
                id_empleado INT NOT NULL,
                nombre_empleado VARCHAR(50) NOT NULL,
                id_turno INT NOT NULL,
                fecha DATE NOT NULL,
                hora_entrada TIME,
                hora_salida TIME,
                horas_trabajadas FLOAT,
                FOREIGN KEY (id_empleado) REFERENCES Empleado(id_empleado),
                FOREIGN KEY (id_turno) REFERENCES Turnos(id_turno)
            )
            Tabla: Incidencias (
                id_incidencia INT PRIMARY KEY AUTO_INCREMENT,
                id_empleado INT NOT NULL,
                tipo_incidencia VARCHAR(50) NOT NULL CHECK (tipo_incidencia IN ('retraso', 'ausencia', 'permiso', 'otro')),
                descripcion TEXT,
                fecha_incidencia DATE NOT NULL,
                FOREIGN KEY (id_empleado) REFERENCES Empleado(id_empleado)
            )
            Vistas:
            - Vista_Turnos_Empleados: SELECT e.nombre, e.apellido, e.cedula, e.correo, e.telefono, e.direccion, t.fecha, t.tipo_turno, t.hora_inicio, t.hora_fin FROM Empleado e LEFT JOIN Turnos t ON e.id_empleado = t.id_empleado
            - Vista_Horas_Trabajadas_Mes: SELECT e.nombre, e.apellido, SUM(ra.horas_trabajadas) AS total_horas FROM Empleado e LEFT JOIN Registro_Asistencia ra ON e.id_empleado = ra.id_empleado WHERE ra.fecha BETWEEN '2025-04-01' AND '2025-04-30' GROUP BY e.id_empleado, e.nombre, e.apellido
            - Vista_Incidencias_Empleados: SELECT e.nombre, e.apellido, i.tipo_incidencia, i.fecha_incidencia, i.descripcion FROM Empleado e LEFT JOIN Incidencias i ON e.id_empleado = i.id_empleado
            - Vista_Actividad_Administradores: SELECT e.nombre, e.apellido, a.login, a.ultima_actividad FROM Empleado e INNER JOIN Administrador a ON e.id_empleado = a.id_empleado WHERE e.rol = 'administrador'
            - Vista_Resumen_Asistencia_Diaria: SELECT ra.nombre_empleado AS nombre, e.apellido, ra.fecha, ra.hora_entrada, ra.hora_salida, ra.horas_trabajadas, CASE WHEN ra.hora_entrada IS NOT NULL AND ra.hora_salida IS NOT NULL THEN 'Completo' WHEN ra.hora_entrada IS NULL AND ra.hora_salida IS NULL THEN 'Sin registro' ELSE 'Incompleto' END AS estado_asistencia FROM Empleado e LEFT JOIN Registro_Asistencia ra ON e.id_empleado = ra.id_empleado
            - Vista_Empleados_Con_Multiples_Incidencias: SELECT e.nombre, e.apellido, COUNT(i.id_incidencia) AS cantidad_incidencias FROM Empleado e JOIN Incidencias i ON e.id_empleado = i.id_empleado GROUP BY e.id_empleado HAVING COUNT(i.id_incidencia) > 1
            - Vista_Turnos_Por_Tipo: SELECT tipo_turno, COUNT(*) AS cantidad_turnos FROM Turnos GROUP BY tipo_turno
            - Vista_Asistencias_Pendientes: SELECT e.nombre, e.apellido, ra.fecha, ra.hora_entrada, ra.hora_salida, CASE WHEN ra.hora_entrada IS NULL THEN 'Falta hora entrada' WHEN ra.hora_salida IS NULL THEN 'Falta hora salida' ELSE 'Sin asistencia' END AS observacion FROM Empleado e LEFT JOIN Registro_Asistencia ra ON e.id_empleado = ra.id_empleado WHERE ra.hora_entrada IS NULL OR ra.hora_salida IS NULL
            - Vista_Dias_Trabajados: SELECT e.nombre, e.apellido, COUNT(DISTINCT ra.fecha) AS dias_trabajados FROM Empleado e JOIN Registro_Asistencia ra ON e.id_empleado = ra.id_empleado GROUP BY e.id_empleado
            Funciones:
            - ObtenerNombreCompleto(p_id_empleado INT): Retorna CONCAT(nombre, ' ', apellido)
            - CalcularHorasTrabajadas(p_hora_inicio TIME, p_hora_fin TIME): Retorna horas trabajadas en formato FLOAT
            - ContarIncidenciasEmpleado(p_id_empleado INT): Retorna número de incidencias
            - EsAdministrador(p_id_empleado INT): Retorna BOOLEAN si el empleado es administrador
            - TotalHorasTrabajadasEmpleado(p_id_empleado INT, p_fecha_inicio DATE, p_fecha_fin DATE): Retorna suma de horas trabajadas
            - ObtenerDetallesEmpleado(p_id_empleado INT): Retorna detalles completos del empleado
            - ObtenerTipoTurno(p_id_turno INT): Retorna tipo_turno de un turno
            - ContarEmpleadosPorRol(p_rol VARCHAR(20)): Retorna número de empleados por rol
            - PorcentajeAsistenciaMensual(p_id_empleado INT, p_mes DATE): Retorna porcentaje de asistencia
            - ObtenerLoginAdmin(p_id_empleado INT): Retorna login del administrador
            Procedimientos:
            - InsertarEmpleado(p_nombre VARCHAR(50), p_apellido VARCHAR(50), p_cedula VARCHAR(20), p_correo VARCHAR(100), p_telefono VARCHAR(20), p_direccion VARCHAR(200), p_rol VARCHAR(20))
            - RegistrarAsistencia(p_id_empleado INT, p_id_turno INT, p_fecha DATE, p_hora_entrada TIME, p_hora_salida TIME)
            - ActualizarEmpleado(p_id_empleado INT, p_nombre VARCHAR(50), p_apellido VARCHAR(50), p_correo VARCHAR(100), p_telefono VARCHAR(20), p_direccion VARCHAR(200), p_rol VARCHAR(20))
            - RegistrarIncidencia(p_id_empleado INT, p_tipo_incidencia VARCHAR(50), p_descripcion TEXT, p_fecha_incidencia DATE)
            - HistorialAsistenciaEmpleado(p_id_empleado INT)
            - EliminarEmpleado(p_id_empleado INT)
            - ConsultarIncidenciasPorTipo(p_tipo VARCHAR(50))
            - CambiarRolEmpleado(p_id_empleado INT, p_nuevo_rol VARCHAR(20))
            - ConsultarEmpleadosPorRol(p_rol VARCHAR(20))
            - ResumenAsistenciaPorFecha(p_fecha DATE)
            Relaciones:
            - Administrador.id_empleado -> Empleado.id_empleado
            - Turnos.id_empleado -> Empleado.id_empleado
            - Registro_Asistencia.id_empleado -> Empleado.id_empleado
            - Registro_Asistencia.id_turno -> Turnos.id_turno
            - Incidencias.id_empleado -> Empleado.id_empleado
            """;
    }

    public boolean esConsultaSegura(String sql) {
        String consulta = sql.toLowerCase().trim();
        return consulta.startsWith("select") || consulta.startsWith("call") &&
               !consulta.contains("drop") &&
               !consulta.contains("update") &&
               !consulta.contains("insert") &&
               !consulta.contains("delete");
    }
}