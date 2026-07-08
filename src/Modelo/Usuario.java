/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author crist
 */
public abstract class Usuario {

    public Usuario(int idUsuario, String codigoUPN, String nombre1, String nombre2, String apellido_paterno, String apellido_materno) {
        this.idUsuario = idUsuario;
        this.codigoUPN = codigoUPN;
        this.nombre1 = nombre1;
        this.nombre2 = nombre2;
        this.apellido_paterno = apellido_paterno;
        this.apellido_materno = apellido_materno;
    }
    public Usuario() {
        
    }
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCodigoUPN() {
        return codigoUPN;
    }

    public void setCodigoUPN(String codigoUPN) {
        this.codigoUPN = codigoUPN;
    }

    public String getNombre1() {
        return nombre1;
    }

    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    public String getNombre2() {
        return nombre2;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    public String getApellido_paterno() {
        return apellido_paterno;
    }

    public void setApellido_paterno(String apellido_paterno) {
        this.apellido_paterno = apellido_paterno;
    }

    public String getApellido_materno() {
        return apellido_materno;
    }

    public void setApellido_materno(String apellido_materno) {
        this.apellido_materno = apellido_materno;
    }
    protected int idUsuario;
    protected String codigoUPN;
    protected String nombre1;
    protected String nombre2;
    protected String apellido_paterno;
    protected String apellido_materno;
    
    public abstract String getRol();
}
