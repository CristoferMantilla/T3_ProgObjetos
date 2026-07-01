/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Equipo;
import Controlador.InventarioDAO;
import Modelo.Componente;
import Modelo.Laptop;

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
    public String registrarNuevoEquipo(String codigo, String marca, String detalle, String tipoSeleccionado) {
        
        // 1. Validación inicial
        if (codigo.isEmpty() || marca.isEmpty()) {
            return "Error: El código y la marca son obligatorios.";
        }

        // 2. Aplicamos Polimorfismo: Declaramos el Padre vacío
        Equipo nuevoEquipo = null; 

        // 3. Instanciamos a la hija correspondiente
        if (tipoSeleccionado.equals("Laptop")) {
            nuevoEquipo = new Laptop(0, codigo, marca, detalle, true); 
        } else if (tipoSeleccionado.equals("Componente")) {
            nuevoEquipo = new Componente(0, codigo, marca, detalle, true); 
        } else {
            // Este es el retorno que Java extrañaba para evitar errores
            return "Error: Tipo de categoría no válido."; 
        }

        // 4. Ejecutamos la base de datos UNA sola vez
        if (dao.insertar(nuevoEquipo)) {
            return "¡Equipo registrado exitosamente en el inventario!";
        } else {
            return "Error en la base de datos al registrar.";
        }
    }

    // Pide los datos al Modelo para dárselos a la Vista
    public Iterable<Equipo> obtainEquipos() {
        return dao.listarTodo();
    }
}