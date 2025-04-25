/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
import java.util.Date;

/**
 *
 * @author Dell Notebook
 */
public class Incidencia {
    private int id_incidencia;     // INT AUTO_INCREMENT PRIMARY KEY
    private int id_empleado;       // INT NOT NULL
    private String tipo_incidencia; // VARCHAR(50) NOT NULL
    private String descripcion;    // TEXT - Puede ser null
    private Date fecha_incidencia; // DATE NOT NULL

    public int getId_incidencia() {
        return id_incidencia;
    }

    public void setId_incidencia(int id_incidencia) {
        this.id_incidencia = id_incidencia;
    }

    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }

    public String getTipo_incidencia() {
        return tipo_incidencia;
    }

    public void setTipo_incidencia(String tipo_incidencia) {
        this.tipo_incidencia = tipo_incidencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha_incidencia() {
        return fecha_incidencia;
    }

    public void setFecha_incidencia(Date fecha_incidencia) {
        this.fecha_incidencia = fecha_incidencia;
    }

    public Incidencia() {
    }

    public Incidencia(int id_incidencia, int id_empleado, String tipo_incidencia, String descripcion, Date fecha_incidencia) {
        this.id_incidencia = id_incidencia;
        this.id_empleado = id_empleado;
        this.tipo_incidencia = tipo_incidencia;
        this.descripcion = descripcion;
        this.fecha_incidencia = fecha_incidencia;
    }
    
}
