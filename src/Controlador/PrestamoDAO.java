/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.sql.*;
import modelo.ConexionSQL;

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
}
