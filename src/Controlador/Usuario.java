/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

/**
 *
 * @author crist
 */
public abstract class Usuario {
    protected int idUsuario;
    protected String codigoUPN;
    protected String nombreCompleto;
    
    public Usuario(int idUsuario, String codigoUPN, String nombreCompleto) {
        this.idUsuario = idUsuario;
        this.codigoUPN = codigoUPN;
        this.nombreCompleto = nombreCompleto;
    }
    public int getIdUsuario() { return idUsuario; }
    public String getCodigoUPN() { return codigoUPN; }
    public String getNombreCompleto() { return nombreCompleto; }
    
    public abstract String getRol();
}
