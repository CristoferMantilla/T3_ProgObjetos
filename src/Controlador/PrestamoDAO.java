/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Prestamo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author crist
 */
public class PrestamoDAO {
    public String registrarPrestamo(Prestamo prestamoActual) {
        Connection cn = null;
        PreparedStatement pstPrestamo = null;
        PreparedStatement pstUpdate = null;
        
        try {
            cn = ConexionSQL.conectar();
            cn.setAutoCommit(false); 

            String sqlPrestamo = "INSERT INTO Prestamos (ID_Usuario, ID_Equipo, FechaDevolucionEsperada) VALUES (?, ?, DATEADD(day, 2, GETDATE()))";
            pstPrestamo = cn.prepareStatement(sqlPrestamo);
            // Obtenemos los IDs desde los objetos que componen el préstamo
            pstPrestamo.setInt(1, prestamoActual.getUsuario().getIdUsuario());
            pstPrestamo.setInt(2, prestamoActual.getEquipo().getIdEquipo());
            pstPrestamo.executeUpdate();

            String sqlUpdate = "UPDATE Inventario SET Disponible = 0 WHERE ID_Equipo = ?";
            pstUpdate = cn.prepareStatement(sqlUpdate);
            pstUpdate.setInt(1, prestamoActual.getEquipo().getIdEquipo());
            pstUpdate.executeUpdate();

            cn.commit(); 
            return "¡Préstamo registrado correctamente!";
            
        } catch (SQLException e) {
            if (cn != null) {
                try { cn.rollback(); } catch (SQLException ex) { System.out.println(ex.getMessage()); }
            }
            return "Error en la transacción: " + e.getMessage();
        } finally {
            try {
                if (pstPrestamo != null) pstPrestamo.close();
                if (pstUpdate != null) pstUpdate.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public DefaultTableModel obtenerModeloUltimosMovimientos() {
        String[] columnas = {"ID Préstamo", "Usuario (UPN)", "Equipo", "Estado"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        
        // Unimos las 3 tablas para traer los datos legibles
        String sql = "SELECT p.ID_Prestamo, u.CodigoUPN, i.CodigoPatrimonial, p.Estado " +
                     "FROM Prestamos p " +
                     "INNER JOIN Usuarios u ON p.ID_Usuario = u.ID_Usuario " +
                     "INNER JOIN Inventario i ON p.ID_Equipo = i.ID_Equipo " +
                     "ORDER BY p.FechaSalida DESC";
                     
        try (Connection cn = ConexionSQL.conectar();
             PreparedStatement pst = cn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
             
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("ID_Prestamo"),
                    rs.getString("CodigoUPN"),
                    rs.getString("CodigoPatrimonial"),
                    rs.getString("Estado")
                });
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar movimientos: " + e.getMessage());
        }
        return modelo;
    }
}
