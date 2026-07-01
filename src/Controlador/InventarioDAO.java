/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Componente;
import Modelo.Equipo;
import Modelo.Laptop;
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
public class InventarioDAO {
    public List<Equipo> listarTodo() {
        List<Equipo> lista = new ArrayList<>();
        // Hacemos el SELECT respetando los nombres exactos de tus columnas en SQL Server
        String sql = "SELECT ID_Equipo, CodigoPatrimonial, Categoria, Marca, DetalleTecnico, Disponible FROM Inventario";

        try (Connection cn = ConexionSQL.conectar(); // Cambia ConexionSQL por el nombre de tu clase de conexión
             PreparedStatement pst = cn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                // 1. Extraemos los datos de la fila actual
                int id = rs.getInt("ID_Equipo");
                String codigo = rs.getString("CodigoPatrimonial");
                String categoria = rs.getString("Categoria");
                String marca = rs.getString("Marca");
                String detalle = rs.getString("DetalleTecnico");
                boolean disponible = rs.getBoolean("Disponible"); // SQL Server convierte el 1/0 a true/false

                // 2. Aplicamos Polimorfismo: Creamos el objeto hijo según la categoría
                Equipo equipo = null;

                if ("Laptop".equalsIgnoreCase(categoria)) {
                    equipo = new Laptop(id, codigo, marca, detalle, disponible);
                } 
                else if ("Componente".equalsIgnoreCase(categoria)) {
                    equipo = new Componente(id, codigo, marca, detalle, disponible);
                }
                else {
                    // Solución para tu registro "Redes" u otros de prueba: 
                    // Lo tratamos genéricamente como un Componente para que no se caiga el sistema.
                    equipo = new Componente(id, codigo, marca, detalle, disponible);
                    // OJO: Si tienes la clase 'Redes' creada, pon: equipo = new Redes(...);
                }

                // 3. Agregamos el objeto a la lista
                if (equipo != null) {
                    lista.add(equipo);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error crítico al listar inventario: " + e.getMessage());
        }
        
        return lista;
    }
    // Método que recibe CUALQUIER hijo de la clase abstracta Equipo
    public boolean insertar(Equipo equipo) {
        // No enviamos el ID_Equipo porque en SQL Server le pusimos IDENTITY(1,1) para que sea automático
        String sql = "INSERT INTO Inventario (CodigoPatrimonial, Categoria, Marca, DetalleTecnico, Disponible) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection cn = ConexionSQL.conectar(); // Usa tu clase de conexión
             PreparedStatement pst = cn.prepareStatement(sql)) {
             
            // Extraemos los datos del objeto usando los métodos de la clase padre
            pst.setString(1, equipo.getCodigoPatrimonial());
            pst.setString(2, equipo.getCategoria());
            pst.setString(3, equipo.getMarca());
            pst.setString(4, equipo.getDetalleTecnico()); // Si te marca error aquí, asegúrate de que el método getDetalleTecnico() esté en tu clase padre Equipo
            pst.setBoolean(5, equipo.isDisponible());
            
            // Ejecutamos la consulta. Si afecta a 1 o más filas, devuelve true.
            int filasAfectadas = pst.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.out.println("Error al registrar equipo en BD: " + e.getMessage());
            return false; // Si explota la base de datos, devuelve falso para que tu controlador lance el mensaje de error
        }
    }
}
