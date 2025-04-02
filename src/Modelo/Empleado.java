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
public class Empleado {
    private int id_empleado;    // INT AUTO_INCREMENT PRIMARY KEY
    private String nombre;      // VARCHAR(50) NOT NULL
    private String apellido;    // VARCHAR(50) NOT NULL
    private String cedula;      // VARCHAR(20) NOT NULL UNIQUE
    private String email;       // VARCHAR(100) - Puede ser null
    private String rol;         // VARCHAR(20) NOT NULL

    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Empleado() {
    }

    public Empleado(int id_empleado, String nombre, String apellido, String cedula, String email, String rol) {
        this.id_empleado = id_empleado;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.email = email;
        this.rol = rol;
    }
    
}
