/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.util.Date;

/**
 *
 * @author crist
 */
public class Prestamo {

    public int getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }
    private int idPrestamo;
    // Composición: Un préstamo está compuesto por un Usuario y un Equipo completos
    private Usuario usuario; 
    private Equipo equipo;   
    private Date fechaSalida;

    public Prestamo(int idPrestamo, Usuario usuario, Equipo equipo, Date fechaSalida) {
        this.idPrestamo = idPrestamo;
        this.usuario = usuario;
        this.equipo = equipo;
        this.fechaSalida = fechaSalida;
    }
}
