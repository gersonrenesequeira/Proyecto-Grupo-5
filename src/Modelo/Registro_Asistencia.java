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
public class Registro_Asistencia {
    private int id_registro;      // INT AUTO_INCREMENT PRIMARY KEY
    private int id_empleado;      // INT NOT NULL
    private Integer id_turno;     // INT - Puede ser null
    private Date fecha;           // DATE NOT NULL
    private Date hora_entrada;    // TIME - Puede ser null
    private Date hora_salida;     // TIME - Puede ser null
    private Float horas_trabajadas; // FLOAT - Puede ser null

    public int getId_registro() {
        return id_registro;
    }

    public void setId_registro(int id_registro) {
        this.id_registro = id_registro;
    }

    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }

    public Integer getId_turno() {
        return id_turno;
    }

    public void setId_turno(Integer id_turno) {
        this.id_turno = id_turno;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHora_entrada() {
        return hora_entrada;
    }

    public void setHora_entrada(Date hora_entrada) {
        this.hora_entrada = hora_entrada;
    }

    public Date getHora_salida() {
        return hora_salida;
    }

    public void setHora_salida(Date hora_salida) {
        this.hora_salida = hora_salida;
    }

    public Float getHoras_trabajadas() {
        return horas_trabajadas;
    }

    public void setHoras_trabajadas(Float horas_trabajadas) {
        this.horas_trabajadas = horas_trabajadas;
    }

    public Registro_Asistencia() {
    }

    public Registro_Asistencia(int id_registro, int id_empleado, Integer id_turno, Date fecha, Date hora_entrada, Date hora_salida, Float horas_trabajadas) {
        this.id_registro = id_registro;
        this.id_empleado = id_empleado;
        this.id_turno = id_turno;
        this.fecha = fecha;
        this.hora_entrada = hora_entrada;
        this.hora_salida = hora_salida;
        this.horas_trabajadas = horas_trabajadas;
    }
    
}
