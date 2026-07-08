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

            // CORRECCIÓN: Usamos subconsultas (SELECT) para obtener automáticamente los IDs internos usando los Códigos String visibles en la pantalla
            String sqlPrestamo = "INSERT INTO Prestamos (ID_Usuario, ID_Equipo, FechaDevolucionEsperada, EstadoPrestamo) " +
                                 "VALUES ((SELECT TOP 1 ID_Usuario FROM Usuarios WHERE CodigoUPN = ?), " +
                                 "(SELECT TOP 1 ID_Equipo FROM Inventario WHERE CodigoPatrimonial = ?), ?, 'En Curso')";
            
            pstPrestamo = cn.prepareStatement(sqlPrestamo);
            
            // Pasamos los códigos de cadena que empaquetamos desde la interfaz
            pstPrestamo.setString(1, prestamoActual.getUsuario().getCodigoUPN());
            pstPrestamo.setString(2, prestamoActual.getEquipo().getCodigoPatrimonial());
            
            // Pasamos la fecha exacta elegida en el JSpinner
            pstPrestamo.setTimestamp(3, (Timestamp) prestamoActual.getFechaDevolucion()); 
            pstPrestamo.executeUpdate();

            // CORRECCIÓN: Modificamos el UPDATE para buscar por CódigoPatrimonial que es el dato que maneja la interfaz
            String sqlUpdate = "UPDATE Inventario SET Disponible = 0 WHERE CodigoPatrimonial = ?";
            pstUpdate = cn.prepareStatement(sqlUpdate);
            pstUpdate.setString(1, prestamoActual.getEquipo().getCodigoPatrimonial());
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
        
        // Unimos las 3 tablas para traer los datos legibles. 
        // CORRECCIÓN: Cambiamos p.Estado por p.EstadoPrestamo
        String sql = "SELECT p.ID_Prestamo, u.CodigoUPN, i.CodigoPatrimonial, p.EstadoPrestamo " +
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
                    rs.getString("EstadoPrestamo") // <-- CORRECCIÓN AQUÍ TAMBIÉN
                });
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar movimientos: " + e.getMessage());
        }
        return modelo;
    }
    // =================================================================
    // NUEVOS MÉTODOS PARA EL PROCESO DE DEVOLUCIÓN
    // =================================================================

    // 1. Lista solo los préstamos que aún no han sido devueltos
    public DefaultTableModel listarPrestamosActivos() {
        String[] columnas = {"ID Préstamo", "Estudiante (UPN)", "Equipo", "Fecha Salida", "Vencimiento"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        
        String sql = "SELECT p.ID_Prestamo, u.CodigoUPN, i.CodigoPatrimonial, p.FechaSalida, p.FechaDevolucionEsperada " +
                     "FROM Prestamos p " +
                     "INNER JOIN Usuarios u ON p.ID_Usuario = u.ID_Usuario " +
                     "INNER JOIN Inventario i ON p.ID_Equipo = i.ID_Equipo " +
                     "WHERE p.EstadoPrestamo = 'En Curso' " +
                     "ORDER BY p.FechaSalida DESC";
                     
        try (Connection cn = ConexionSQL.conectar();
             PreparedStatement pst = cn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
             
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("ID_Prestamo"),
                    rs.getString("CodigoUPN"),
                    rs.getString("CodigoPatrimonial"),
                    rs.getTimestamp("FechaSalida"),
                    rs.getTimestamp("FechaDevolucionEsperada")
                });
            }
        } catch (SQLException e) {
            System.out.println("Error al listar préstamos activos: " + e.getMessage());
        }
        return modelo;
    }

    // 2. Procesa la devolución actualizando ambas tablas
    public String registrarDevolucion(int idPrestamo, String observaciones, java.sql.Timestamp fechaReal) {
        
        try (java.sql.Connection cn = ConexionSQL.conectar()) {
            cn.setAutoCommit(false); 

            // 1. Actualizamos el estado del préstamo
            String sqlPrestamo = "UPDATE Prestamos SET FechaDevolucionReal = ?, EstadoPrestamo = 'Devuelto', Observaciones = ? WHERE ID_Prestamo = ?";
            
            // 2. CORRECCIÓN AQUÍ: Ponemos Disponible = 0 para "Liberar" el equipo según tu base de datos
            String sqlInventario = "UPDATE Inventario SET Disponible = 1 WHERE ID_Equipo = (SELECT ID_Equipo FROM Prestamos WHERE ID_Prestamo = ?)";

            try (java.sql.PreparedStatement pstPrestamo = cn.prepareStatement(sqlPrestamo);
                 java.sql.PreparedStatement pstInventario = cn.prepareStatement(sqlInventario)) {

                // Asignamos la fecha real capturada
                pstPrestamo.setTimestamp(1, fechaReal);

                // Asignamos las observaciones
                if (observaciones.isEmpty()) {
                    pstPrestamo.setNull(2, java.sql.Types.VARCHAR);
                } else {
                    pstPrestamo.setString(2, observaciones);
                }
                
                // Asignamos el ID
                pstPrestamo.setInt(3, idPrestamo);
                pstPrestamo.executeUpdate();

                // Liberamos el equipo pasándole el ID
                pstInventario.setInt(1, idPrestamo);
                pstInventario.executeUpdate();

                cn.commit(); 
                return "¡Equipo devuelto y liberado en el inventario exitosamente!";

            } catch (java.sql.SQLException e) {
                cn.rollback(); 
                return "Error al registrar la devolución. Cambios revertidos: " + e.getMessage();
            }

        } catch (java.sql.SQLException e) {
            return "Error de conexión con la base de datos: " + e.getMessage();
        }
    }
}
