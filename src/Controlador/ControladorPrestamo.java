/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Controlador.PrestamoDAO;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author crist
 */
public class ControladorPrestamo {
    private PrestamoDAO dao = new PrestamoDAO();
    
    public DefaultTableModel cargarTablaDashboard() {
        return dao.obtenerModeloUltimosMovimientos();
    }
}
