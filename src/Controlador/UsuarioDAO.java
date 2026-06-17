/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.ConexionSQL;

/**
 *
 * @author crist
 */
public class UsuarioDAO {
    // REQ-01 y REQ-04: Validar Login verificando contraseña (simulando encriptación básica)
    public String validarLogin(String codigo, String password) {
        String sql = "SELECT NombreCompleto FROM Usuarios WHERE CodigoUPN = ? AND Contrasena = ? AND Rol = 'Administrador'";
        try (Connection cn = ConexionSQL.conectar(); PreparedStatement pst = cn.prepareStatement(sql)) {
            pst.setString(1, codigo);
            pst.setString(2, password); // En un entorno real, aquí se usaría un hash (ej. SHA-256)
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) return rs.getString("NombreCompleto");
            }
        } catch (SQLException e) { System.out.println(e.getMessage()); }
        return null;
    }

    // REQ-06: Registrar un nuevo estudiante
    public boolean registrarEstudiante(Estudiante est) {
        String sql = "INSERT INTO Usuarios (CodigoUPN, NombreCompleto, Rol, EstadoBloqueo) VALUES (?, ?, 'Estudiante', ?)";
        try (Connection cn = ConexionSQL.conectar(); PreparedStatement pst = cn.prepareStatement(sql)) {
            pst.setString(1, est.getCodigoUPN());
            pst.setString(2, est.getNombreCompleto());
            pst.setBoolean(3, est.isEstadoBloqueo());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }

    // REQ-07: Editar datos personales
    public boolean editarEstudiante(Estudiante est) {
        String sql = "UPDATE Usuarios SET NombreCompleto = ?, EstadoBloqueo = ? WHERE CodigoUPN = ?";
        try (Connection cn = ConexionSQL.conectar(); PreparedStatement pst = cn.prepareStatement(sql)) {
            pst.setString(1, est.getNombreCompleto());
            pst.setBoolean(2, est.isEstadoBloqueo());
            pst.setString(3, est.getCodigoUPN());
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
                    lista.add(new Estudiante(
                        rs.getInt("ID_Usuario"), rs.getString("CodigoUPN"),
                        rs.getString("NombreCompleto"), rs.getBoolean("EstadoBloqueo")
                    ));
                }
            }
        } catch (SQLException e) { System.out.println(e.getMessage()); }
        return lista;
    }
}
