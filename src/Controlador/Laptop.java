/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

/**
 *
 * @author crist
 */
public class Laptop extends Equipo
{
    private String especificaciones;

    public Laptop(int idEquipo, String codigoPatrimonial, String marca, String especificaciones, boolean disponible) {
        // Llama al constructor del padre (Equipo)
        super(idEquipo, codigoPatrimonial, "Laptop", marca, disponible);
        this.especificaciones = especificaciones;
    }
    @Override
    public String getDetalleTecnico() {
        return especificaciones;
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
