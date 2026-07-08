/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author crist
 */
public class Estudiante extends Usuario{
    private boolean estadoBloqueo;

    public Estudiante(int idUsuario, String codigoUPN, String nombre1, String nombre2, String apellido_paterno, String apellido_materno, boolean estadoBloqueo) {
        super(idUsuario, codigoUPN, nombre1, nombre2, apellido_paterno, apellido_materno);
        this.estadoBloqueo = estadoBloqueo;
    }

    public Estudiante() {
        
    }
    
    @Override
    public String getRol() { return "Estudiante"; }
    
    public boolean isEstadoBloqueo() 
    { 
        return estadoBloqueo; 
    }
    public void setEstadoBloqueo(boolean estadoBloqueo) 
    { 
        this.estadoBloqueo = estadoBloqueo; 
    }
}
