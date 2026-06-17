/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

/**
 *
 * @author crist
 */
public abstract class Equipo implements IPrestable{

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getCodigoPatrimonial() {
        return codigoPatrimonial;
    }

    public void setCodigoPatrimonial(String codigoPatrimonial) {
        this.codigoPatrimonial = codigoPatrimonial;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
    protected int idEquipo;
    protected String codigoPatrimonial;
    protected String categoria;
    protected String marca;
    protected boolean disponible;

    public Equipo(int idEquipo, String codigoPatrimonial, String categoria, String marca, boolean disponible) {
        this.idEquipo = idEquipo;
        this.codigoPatrimonial = codigoPatrimonial;
        this.categoria = categoria;
        this.marca = marca;
        this.disponible = disponible;
    }
    public abstract String getDetalleTecnico();
}
