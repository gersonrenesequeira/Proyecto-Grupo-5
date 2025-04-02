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
public class Administrador {
    private int id_administrador;  // INT AUTO_INCREMENT PRIMARY KEY
    private int id_empleado;       // INT NOT NULL UNIQUE
    private String login;          // VARCHAR(50) NOT NULL UNIQUE
    private String password;       // VARCHAR(100) NOT NULL
    private Date ultima_actividad; // DATETIME - Puede ser null

    public int getId_administrador() {
        return id_administrador;
    }

    public void setId_administrador(int id_administrador) {
        this.id_administrador = id_administrador;
    }

    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getUltima_actividad() {
        return ultima_actividad;
    }

    public void setUltima_actividad(Date ultima_actividad) {
        this.ultima_actividad = ultima_actividad;
    }

    public Administrador() {
    }

    public Administrador(int id_administrador, int id_empleado, String login, String password, Date ultima_actividad) {
        this.id_administrador = id_administrador;
        this.id_empleado = id_empleado;
        this.login = login;
        this.password = password;
        this.ultima_actividad = ultima_actividad;
    }
}

