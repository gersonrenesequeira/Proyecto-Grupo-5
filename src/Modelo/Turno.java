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
public class Turno {
    private int id_turno;       // INT AUTO_INCREMENT PRIMARY KEY
    private int id_empleado;    // INT NOT NULL
    private Date fecha;         // DATE NOT NULL
    private Date hora_inicio;   // TIME NOT NULL
    private Date hora_fin;      // TIME NOT NULL
    private String tipo_turno;  // VARCHAR(20) NOT NUL

    public int getId_turno() {
        return id_turno;
    }

    public void setId_turno(int id_turno) {
        this.id_turno = id_turno;
    }

    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(Date hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public Date getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(Date hora_fin) {
        this.hora_fin = hora_fin;
    }

    public String getTipo_turno() {
        return tipo_turno;
    }

    public void setTipo_turno(String tipo_turno) {
        this.tipo_turno = tipo_turno;
    }

    public Turno() {
    }

    public Turno(int id_turno, int id_empleado, Date fecha, Date hora_inicio, Date hora_fin, String tipo_turno) {
        this.id_turno = id_turno;
        this.id_empleado = id_empleado;
        this.fecha = fecha;
        this.hora_inicio = hora_inicio;
        this.hora_fin = hora_fin;
        this.tipo_turno = tipo_turno;
    }
    
}
