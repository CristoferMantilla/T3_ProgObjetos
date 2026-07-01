/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author crist
 */
public class Componente extends Equipo{
    private String tipoSensor;

    public Componente(int idEquipo, String codigoPatrimonial, String marca, String tipoSensor, boolean disponible) {
        // Llama al constructor del padre (Equipo)
        super(idEquipo, codigoPatrimonial, "Componente", marca, disponible);
        this.tipoSensor = tipoSensor;
    }
    
    @Override
    public String getDetalleTecnico() {
        return tipoSensor;
    }

    @Override
    public boolean prestar() {
        if(disponible) { this.disponible = false; return true; }
        return false;
    }

    @Override
    public void devolver() {
        this.disponible = true;
    }
}
