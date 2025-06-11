/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

public class Turno {
    private int idTurno;
    private int idEmpleado;
    private String fecha;
    private String horaInicio;
    private String horaFin;
    private String tipoTurno;
    private String nombreEmpleado;

    public Turno() {
    }

    public Turno(int idEmpleado, String fecha, String horaInicio, String horaFin, String tipoTurno) {
        this.idEmpleado = idEmpleado;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.tipoTurno = tipoTurno;
    }

    public int getIdTurno() {
        return idTurno;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public String getTipoTurno() {
        return tipoTurno;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setIdTurno(int idTurno) {
        this.idTurno = idTurno;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public void setTipoTurno(String tipoTurno) {
        this.tipoTurno = tipoTurno;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }
}