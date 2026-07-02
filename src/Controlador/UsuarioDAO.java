/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Estudiante;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author crist
 */
public class UsuarioDAO {
    // REQ-01 y REQ-04: Validar Login verificando contraseña (simulando encriptación básica)
    public String validarLogin(String codigo, String password) {
        String sql = "SELECT Nombre1 FROM Usuarios WHERE CodigoUPN = ? AND Contrasena = ? AND Rol = 'Administrador'";
        try (Connection cn = ConexionSQL.conectar(); PreparedStatement pst = cn.prepareStatement(sql)) {
            pst.setString(1, codigo);
            pst.setString(2, password); // En un entorno real, aquí se usaría un hash (ej. SHA-256)
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) return rs.getString("Nombre1");
            }
        } catch (SQLException e) { System.out.println(e.getMessage()); }
        return null;
    }

    // REQ-06: Registrar un nuevo estudiante
    public boolean registrarEstudiante(Estudiante est) {
        String sql = "INSERT INTO Usuarios (CodigoUPN, Nombre1,Nombre2,ApePaterno,ApeMaterno, Rol, EstadoBloqueo) VALUES (?, ?, ?, ?, ?, 'Estudiante', ?)";
        try (Connection cn = ConexionSQL.conectar(); PreparedStatement pst = cn.prepareStatement(sql)) {
            pst.setString(1, est.getCodigoUPN());
            pst.setString(2, est.getNombre1());
            pst.setString(3, est.getNombre2());
            pst.setString(4, est.getApellido_paterno());
            pst.setString(5, est.getApellido_materno());
            pst.setBoolean(6, est.isEstadoBloqueo());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }

    // REQ-07: Editar datos personales
    public boolean editarEstudiante(Estudiante est) {
        String sql = "UPDATE Usuarios SET Nombre1 = ?,Nombre2 = ?, ApePaterno = ?,ApeMaterno = ?,EstadoBloqueo = ? WHERE CodigoUPN = ?";
        try (Connection cn = ConexionSQL.conectar(); PreparedStatement pst = cn.prepareStatement(sql)) {
            pst.setString(1, est.getNombre1());
            pst.setString(2, est.getNombre2());
            pst.setString(3, est.getApellido_paterno());
            pst.setString(4, est.getApellido_materno());
            pst.setBoolean(5, est.isEstadoBloqueo());
            pst.setString(6, est.getCodigoUPN());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }

    // REQ-08: Eliminar (Baja lógica cambiando estado en vez de borrar)
    public boolean eliminarEstudiante(String codigo) {
        String sql = "UPDATE Usuarios SET EstadoBloqueo = 1 WHERE CodigoUPN = ?";
        try (Connection cn = ConexionSQL.conectar(); PreparedStatement pst = cn.prepareStatement(sql)) {
            pst.setString(1, codigo);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }

    // REQ-09 y REQ-10: Listar y buscar estudiantes
    public List<Estudiante> listarEstudiantes(String filtroCodigo) {
        List<Estudiante> lista = new ArrayList<>();
        String sql = filtroCodigo.isEmpty() ? 
                     "SELECT * FROM Usuarios WHERE Rol = 'Estudiante'" : 
                     "SELECT * FROM Usuarios WHERE Rol = 'Estudiante' AND CodigoUPN = ?";
        
        try (Connection cn = ConexionSQL.conectar(); PreparedStatement pst = cn.prepareStatement(sql)) {
            if (!filtroCodigo.isEmpty()) pst.setString(1, filtroCodigo);
            
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    // Validamos el segundo nombre por si viene NULL desde la base de datos
                    String segundoNombre = rs.getString("Nombre2");
                    String nombre2Limpio = (segundoNombre == null) ? "" : segundoNombre;
                    
                    lista.add(new Estudiante(
                        rs.getInt("ID_Usuario"), 
                        rs.getString("CodigoUPN"),
                        rs.getString("Nombre1"),    // <-- Corregido
                        nombre2Limpio,              // <-- Corregido con validación NULL
                        rs.getString("ApePaterno"), // <-- Corregido
                        rs.getString("ApeMaterno"), // <-- Corregido
                        rs.getBoolean("EstadoBloqueo")
                    ));
                }
            }
        } catch (SQLException e) { 
            System.out.println(e.getMessage()); 
        }
        
        return lista;
    }
}
