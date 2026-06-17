/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;
import java.util.List;
/**
 *
 * @author crist
 */
public class Inventario {
    private InventarioDAO dao;

    public Inventario() {
        this.dao = new InventarioDAO(); // Conecta con el Modelo
    }

    // Lógica para registrar validando que no lleguen datos vacíos
    public String registrarNuevoEquipo(String codigo, String marca, String detalle) {
        if (codigo.isEmpty() || marca.isEmpty()) {
            return "Error: El código y la marca son obligatorios.";
        }
        
        Equipo nuevoEquipo = new Laptop(0, codigo, marca, detalle, true);
        
        if (dao.insertar(nuevoEquipo)) {
            return "¡Equipo registrado exitosamente en el inventario!";
        } else {
            return "Error en la base de datos al registrar.";
        }
    }

    // Pide los da  tos al Modelo para dárselos a la Vista

    public Iterable<Equipo> obtainEquipos() {
        return dao.listarTodo();
    }
}
