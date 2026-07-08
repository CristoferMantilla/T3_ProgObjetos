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
    // ========================================================
    // MÉTODOS CONTROLADORES SOLICITADOS POR LA VISTA
    // ========================================================

    public String actualizarEquipo(int id, String codigo, String marca, String detalle, String categoria, boolean disponible) {
        // 1. Validamos que no hayan dejado las cajas en blanco
        if (codigo.trim().isEmpty() || marca.trim().isEmpty() || detalle.trim().isEmpty()) {
            return "Error: Por favor, complete todos los campos obligatorios.";
        }

        // 2. Aplicamos Polimorfismo según la categoría seleccionada
        Equipo equipoActualizado = null;
        if (categoria.equalsIgnoreCase("Laptop")) {
            equipoActualizado = new Laptop(id, codigo, marca, detalle, disponible);
        } else {
            // Lo tratamos como Componente
            equipoActualizado = new Componente(id, codigo, marca, detalle, disponible);
        }

        // 3. Mandamos a la base de datos
        if (dao.actualizar(equipoActualizado)) {
            return "¡Equipo actualizado exitosamente en la base de datos!";
        } else {
            return "Error: No se pudo actualizar el equipo en SQL Server.";
        }
    }

    public String eliminarEquipo(int id) {
        if (id <= 0) {
            return "Error: ID de equipo no válido.";
        }

        // Intentamos eliminarlo de la BD
        if (dao.eliminar(id)) {
            return "¡Equipo eliminado del inventario correctamente!";
        } else {
            return "Error: No se pudo eliminar. Es posible que este equipo esté vinculado a un Préstamo existente.";
        }
    }

    // Pide los datos al Modelo para dárselos a la Vista
    public Iterable<Equipo> obtainEquipos() {
        return dao.listarTodo();
    }
}