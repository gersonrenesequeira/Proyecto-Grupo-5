/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalDate;
import java.time.LocalTime;

public class RegistroAsistencia {
    private int idRegistro;
    private int idEmpleado;
    private int idTurno;
    private LocalDate fecha;
    private LocalTime horaEntrada;
    private LocalTime horaSalida;
    private Float horasTrabajadas;

    // Constructor vacío
    public RegistroAsistencia() {
    }

    // Constructor con todos los campos
    public RegistroAsistencia(int idRegistro, int idEmpleado, int idTurno, LocalDate fecha, LocalTime horaEntrada, LocalTime horaSalida, Float horasTrabajadas) {
        this.idRegistro = idRegistro;
        this.idEmpleado = idEmpleado;
        this.idTurno = idTurno;
        this.fecha = fecha;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.horasTrabajadas = horasTrabajadas;
    }

    // Getters y Setters
    public int getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(int idTurno) {
        this.idTurno = idTurno;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(LocalTime horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public LocalTime getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(LocalTime horaSalida) {
        this.horaSalida = horaSalida;
    }

    public Float getHorasTrabajadas() {
        return horasTrabajadas;
    }

    public void setHorasTrabajadas(Float horasTrabajadas) {
        this.horasTrabajadas = horasTrabajadas;
    }

    @Override
    public String toString() {
        return "RegistroAsistencia{" +
                "idRegistro=" + idRegistro +
                ", idEmpleado=" + idEmpleado +
                ", idTurno=" + idTurno +
                ", fecha=" + fecha +
                ", horaEntrada=" + horaEntrada +
                ", horaSalida=" + horaSalida +
                ", horasTrabajadas=" + horasTrabajadas +
                '}';
    }
}