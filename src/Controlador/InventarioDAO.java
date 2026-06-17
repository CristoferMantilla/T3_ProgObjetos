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
public class InventarioDAO {
    public boolean insertar(Equipo eq) {
        String sql = "INSERT INTO Inventario (CodigoPatrimonial, Categoria, Marca, DetalleTecnico, Disponible) VALUES (?,?,?,?,1)";
        try (Connection cn = ConexionSQL.conectar();
             PreparedStatement pst = cn.prepareStatement(sql)) {
            pst.setString(1, eq.getCodigoPatrimonial());
            pst.setString(2, eq.getCategoria());
            pst.setString(3, eq.getMarca());
            pst.setString(4, eq.getDetalleTecnico());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public List<Equipo> listarTodo() {
        List<Equipo> lista = new ArrayList<>();
        String sql = "SELECT * FROM Inventario";
        try (Connection cn = ConexionSQL.conectar();
             PreparedStatement pst = cn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                // Instanciamos la clase hija Laptop al traer los datos
                lista.add(new Laptop(
                    rs.getInt("ID_Equipo"),
                    rs.getString("CodigoPatrimonial"),
                    rs.getString("Marca"),
                    rs.getString("DetalleTecnico"),
                    rs.getBoolean("Disponible")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar: " + e.getMessage());
        }
        return lista;
    }
}