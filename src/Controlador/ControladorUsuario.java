/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Estudiante;
import Controlador.SesionActiva;
import Controlador.UsuarioDAO;
import java.util.List;

/**
 *
 * @author crist
 */
public class ControladorUsuario {
    private UsuarioDAO dao = new UsuarioDAO();
    private int intentosFallidos = 0; // Contador en memoria para el REQ-03

    // Puente para el Login (REQ-01, REQ-03, REQ-04)
    public String procesarLogin(String codigo, String password) {
        // 1. Verificamos si ya está bloqueado antes de ir a la base de datos
        if (intentosFallidos >= 3) {
            return "BLOQUEADO"; 
        }
        
        // 2. Le pedimos al DAO que busque en SQL Server
        String nombreAdmin = dao.validarLogin(codigo, password);
        
        // 3. Evaluamos la respuesta
        if (nombreAdmin != null) {
            SesionActiva.nombreUsuarioActivo = nombreAdmin; // Guardamos quién entró (REQ-05)
            intentosFallidos = 0; // Reseteamos los intentos porque entró con éxito
            return "EXITO";
        } else {
            intentosFallidos++; // Sumamos un error
            return "Credenciales incorrectas. Intento " + intentosFallidos + " de 3.";
        }
    }

    // Puente para Registrar Estudiante (REQ-06)
    public String registrar(String codigo, String nombre1,String nombre2,String apepaterno,String apematerno) {
        if (codigo.isEmpty() || nombre1.isEmpty() || nombre2.isEmpty() || apepaterno.isEmpty() || apematerno.isEmpty()) {
            return "Error: Llene todos los campos.";
        }
        Estudiante est = new Estudiante(0, codigo, nombre1, nombre2, apepaterno, apematerno, false);
        if (dao.registrarEstudiante(est)) {
            return "Estudiante registrado correctamente.";
        } else {
            return "Error al registrar en la base de datos.";
        }
    }

    // Puente para Actualizar Estudiante (REQ-07)
    public String actualizar(String codigo, String nombre1, String nombre2, String apepaterno, String apematerno, boolean bloqueado) {
        if (codigo.isEmpty()) {
            return "Error: Especifique el código a actualizar.";
        }
        Estudiante est = new Estudiante(0, codigo, nombre1, nombre2, apepaterno, apematerno, bloqueado);
        if (dao.editarEstudiante(est)) {
            return "Datos actualizados correctamente.";
        } else {
            return "Error al actualizar (Verifique si el código existe).";
        }
    }

    // Puente para Eliminar Lógicamente (REQ-08)
    public String darDeBaja(String codigo) {
        if (codigo.isEmpty()) {
            return "Error: Ingrese el código a dar de baja.";
        }
        if (dao.eliminarEstudiante(codigo)) {
            return "El estudiante ha sido dado de baja y bloqueado.";
        } else {
            return "Error al dar de baja.";
        }
    }

    // Puente para Listar y Buscar (REQ-09 y REQ-10)
    public List<Estudiante> obtenerLista(String filtro) {
        return dao.listarEstudiantes(filtro);
    }
}
