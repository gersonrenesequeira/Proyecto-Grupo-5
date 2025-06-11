/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalDate;

public class Incidencia {
    private int idIncidencia;
    private int idEmpleado;
    private String tipoIncidencia;
    private LocalDate fechaIncidencia;
    private String descripcion;

    public Incidencia() {
    }

    public Incidencia(int idIncidencia, int idEmpleado, String tipoIncidencia, LocalDate fechaIncidencia, String descripcion) {
        this.idIncidencia = idIncidencia;
        this.idEmpleado = idEmpleado;
        this.tipoIncidencia = tipoIncidencia;
        this.fechaIncidencia = fechaIncidencia;
        this.descripcion = descripcion;
    }

    public int getIdIncidencia() {
        return idIncidencia;
    }

    public void setIdIncidencia(int idIncidencia) {
        this.idIncidencia = idIncidencia;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getTipoIncidencia() {
        return tipoIncidencia;
    }

    public void setTipoIncidencia(String tipoIncidencia) {
        this.tipoIncidencia = tipoIncidencia;
    }

    public LocalDate getFechaIncidencia() {
        return fechaIncidencia;
    }

    public void setFechaIncidencia(LocalDate fechaIncidencia) {
        this.fechaIncidencia = fechaIncidencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}