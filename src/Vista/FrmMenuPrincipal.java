/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Controlador.SesionActiva;
import Controlador.ControladorPrestamo;
import java.awt.*;
import javax.swing.*;

public class FrmMenuPrincipal extends JFrame {

    private JPanel pnlMenuLateral;
    private JPanel pnlCentral;
    private CardLayout cardLayout;

    private JButton btnInicio;
    private JButton btnRegEquipos;
    private JButton btnRegEstudiantes;
    private JButton btnRegPrestamos;
    private JButton btnDevPrestamos;
    private JButton btnSalir;

    // 1. ADICIÓN: Variables globales para la tabla y el controlador del Dashboard
    private JTable tablaMovimientos;
    private ControladorPrestamo ctrlPrestamo = new ControladorPrestamo();

    private final Color COLOR_NORMAL = new Color(47, 53, 66); 
    private final Color COLOR_ACTIVO = new Color(75, 85, 105); 
    private final Color TEXTO_NORMAL = new Color(200, 200, 200);
    private final Color TEXTO_ACTIVO = Color.WHITE;

    public FrmMenuPrincipal() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        activarBoton(btnInicio);
    }

    private void initComponents() {
        setTitle("Sistema de Préstamos - Laboratorio UPN");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ==========================================
        // 1. MENÚ LATERAL (OESTE)
        // ==========================================
        pnlMenuLateral = new JPanel();
        pnlMenuLateral.setBackground(new Color(30, 39, 46));
        pnlMenuLateral.setPreferredSize(new Dimension(250, 0));
        pnlMenuLateral.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));

        JLabel lblMenuTitulo = new JLabel("MENÚ PRINCIPAL");
        lblMenuTitulo.setForeground(Color.WHITE);
        lblMenuTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblMenuTitulo.setPreferredSize(new Dimension(200, 30));
        
        JLabel lblUsuario = new JLabel("Usuario: " + SesionActiva.nombreUsuarioActivo);
        lblUsuario.setForeground(new Color(153, 204, 255));
        lblUsuario.setPreferredSize(new Dimension(200, 30));

        btnInicio = crearBotonMenu("Inicio");
        btnRegEquipos = crearBotonMenu("Registro Equipos");
        btnRegEstudiantes = crearBotonMenu("Registro Estudiantes");
        btnRegPrestamos = crearBotonMenu("Registro Préstamos");
        btnDevPrestamos = crearBotonMenu("Devolucion Préstamos");
        btnSalir = crearBotonMenu("Cerrar Sesión");
        btnSalir.setForeground(new Color(255, 102, 102));

        pnlMenuLateral.add(lblMenuTitulo);
        pnlMenuLateral.add(lblUsuario);
        pnlMenuLateral.add(btnInicio);
        pnlMenuLateral.add(btnRegEquipos);
        pnlMenuLateral.add(btnRegEstudiantes);
        pnlMenuLateral.add(btnRegPrestamos);
        pnlMenuLateral.add(btnDevPrestamos);
        pnlMenuLateral.add(btnSalir);

        // ==========================================
        // 2. PANEL CENTRAL (CARDLAYOUT)
        // ==========================================
        cardLayout = new CardLayout();
        pnlCentral = new JPanel(cardLayout);
        
        JPanel pnlInicio = construirPanelDashboard();
        JPanel pnlEquipos = (JPanel) new FrmInventario().getContentPane();
        JPanel pnlEstudiantes = (JPanel) new FrmEstudiantes().getContentPane();
        JPanel pnlPrestamos = (JPanel) new FrmPrestamos().getContentPane();
        JPanel pnlDevoluciones = (JPanel) new FrmDevoluciones().getContentPane();
        
        pnlCentral.add(pnlInicio, "INICIO");
        pnlCentral.add(pnlEquipos, "EQUIPOS");
        pnlCentral.add(pnlEstudiantes, "ESTUDIANTES");
        pnlCentral.add(pnlPrestamos, "PRESTAMOS");
        pnlCentral.add(pnlDevoluciones, "DEVOLUCIONES");

        // ==========================================
        // 3. NAVEGACIÓN DE BOTONES
        // ==========================================
        btnInicio.addActionListener(e -> {
            activarBoton(btnInicio);
            actualizarDashboard(); // <-- SOLUCIÓN: Volvemos a consultar la BD antes de mostrar la pantalla
            cardLayout.show(pnlCentral, "INICIO");
        });
        
        btnRegEquipos.addActionListener(e -> {
            activarBoton(btnRegEquipos);
            cardLayout.show(pnlCentral, "EQUIPOS");
        });
        
        btnRegEstudiantes.addActionListener(e -> {
            activarBoton(btnRegEstudiantes);
            cardLayout.show(pnlCentral, "ESTUDIANTES");
        });
        
        btnRegPrestamos.addActionListener(e -> {
            activarBoton(btnRegPrestamos);
            cardLayout.show(pnlCentral, "PRESTAMOS");
        });
        
        btnDevPrestamos.addActionListener(e -> {
            activarBoton(btnDevPrestamos);
            cardLayout.show(pnlCentral, "DEVOLUCIONES");
        });
        
        btnSalir.addActionListener(e -> {
            SesionActiva.nombreUsuarioActivo = "";
            this.dispose();
            new FrmLogin().setVisible(true);
        });

        add(pnlMenuLateral, BorderLayout.WEST);
        add(pnlCentral, BorderLayout.CENTER);
    }

    // ==========================================
    // NUEVO MÉTODO: Refresca el modelo de la tabla
    // ==========================================
    public void actualizarDashboard() {
        if (tablaMovimientos != null) {
            // Le pide al controlador un modelo limpio con los nuevos SELECT de la BD
            tablaMovimientos.setModel(ctrlPrestamo.cargarTablaDashboard());
            tablaMovimientos.setRowHeight(30); // Mantiene el tamaño de las filas
        }
    }

    private void resetearBotonesMenu() {
        JButton[] botones = {btnInicio, btnRegEquipos, btnRegEstudiantes, btnRegPrestamos, btnDevPrestamos};
        for (JButton btn : botones) {
            if (btn != null) {
                btn.setBackground(COLOR_NORMAL);
                btn.setForeground(TEXTO_NORMAL);
                btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
            }
        }
    }

    private void activarBoton(JButton botonSeleccionado) {
        resetearBotonesMenu();
        if (botonSeleccionado != null) {
            botonSeleccionado.setBackground(COLOR_ACTIVO);
            botonSeleccionado.setForeground(TEXTO_ACTIVO);
        }
    }

    private JButton crearBotonMenu(String texto) {
        JButton boton = new JButton(texto);
        boton.setPreferredSize(new Dimension(220, 45));
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setHorizontalAlignment(SwingConstants.LEFT);
        boton.setFocusPainted(false);
        boton.setBackground(COLOR_NORMAL);
        boton.setForeground(TEXTO_NORMAL);
        boton.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }

    private JPanel construirPanelDashboard() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setBackground(new Color(245, 246, 250));

        JLabel lblTitulo = new JLabel("RESUMEN DE INVENTARIO");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        panel.add(lblTitulo, BorderLayout.NORTH);

        JPanel pnlResumen = new JPanel(new GridLayout(1, 2, 20, 0));
        pnlResumen.setBackground(new Color(245, 246, 250));

        JLabel lblDisponibles = new JLabel("Equipos Disponibles: --");
        lblDisponibles.setFont(new Font("Segoe UI", Font.BOLD, 18));
        
        JLabel lblPrestamo = new JLabel("En Préstamo: --");
        lblPrestamo.setFont(new Font("Segoe UI", Font.BOLD, 18));

        pnlResumen.add(lblDisponibles);
        pnlResumen.add(lblPrestamo);

        JPanel pnlTabla = new JPanel(new BorderLayout(0, 10));
        pnlTabla.setBackground(new Color(245, 246, 250));
        
        JLabel lblTabla = new JLabel("ÚLTIMOS MOVIMIENTOS");
        lblTabla.setFont(new Font("Segoe UI", Font.BOLD, 16));
        
        // CORRECCIÓN: Quitamos el tipo de dato por delante para usar la variable global
        tablaMovimientos = new JTable(ctrlPrestamo.cargarTablaDashboard());
        tablaMovimientos.setRowHeight(30);
        JScrollPane scrollTabla = new JScrollPane(tablaMovimientos);

        pnlTabla.add(lblTabla, BorderLayout.NORTH);
        pnlTabla.add(scrollTabla, BorderLayout.CENTER);

        JPanel pnlCentroGlobal = new JPanel(new BorderLayout(0, 30));
        pnlCentroGlobal.setBackground(new Color(245, 246, 250));
        pnlCentroGlobal.add(pnlResumen, BorderLayout.NORTH);
        pnlCentroGlobal.add(pnlTabla, BorderLayout.CENTER);

        panel.add(pnlCentroGlobal, BorderLayout.CENTER);

        return panel;
    }
}