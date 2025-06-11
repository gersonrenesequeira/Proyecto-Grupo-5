/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalDateTime;

public class Administrador {
    private int idAdministrador;
    private int idEmpleado;
    private String login;
    private String password;
    private LocalDateTime ultimaActividad;

    public Administrador() {
    }

    public int getIdAdministrador() {
        return idAdministrador;
    }

    public void setIdAdministrador(int idAdministrador) {
        this.idAdministrador = idAdministrador;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
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

    public LocalDateTime getUltimaActividad() {
        return ultimaActividad;
    }

    public void setUltimaActividad(LocalDateTime ultimaActividad) {
        this.ultimaActividad = ultimaActividad;
    }

    public Administrador(int idAdministrador, int idEmpleado, String login, String password, LocalDateTime ultimaActividad) {
        this.idAdministrador = idAdministrador;
        this.idEmpleado = idEmpleado;
        this.login = login;
        this.password = password;
        this.ultimaActividad = ultimaActividad;
    }

}