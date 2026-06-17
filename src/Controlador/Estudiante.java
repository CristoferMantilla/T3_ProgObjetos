/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

/**
 *
 * @author crist
 */
public class Estudiante extends Usuario{
    private boolean estadoBloqueo;

    public Estudiante(int idUsuario, String codigoUPN, String nombreCompleto, boolean estadoBloqueo) {
        super(idUsuario, codigoUPN, nombreCompleto);
        this.estadoBloqueo = estadoBloqueo;
    }
    
    @Override
    public String getRol() { return "Estudiante"; }
    
    public boolean isEstadoBloqueo() { return estadoBloqueo; }
    public void setEstadoBloqueo(boolean estadoBloqueo) { this.estadoBloqueo = estadoBloqueo; }
}
