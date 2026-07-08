/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;
import Controlador.PrestamoDAO;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author crist
 */
public class FrmDevoluciones extends javax.swing.JFrame {

    private javax.swing.JLabel lblIdPrestamo;
    private javax.swing.JLabel lblFechaReal; // NUEVO
    private javax.swing.JLabel lblObservaciones;
    
    private javax.swing.JTextField txtIdPrestamo;
    private javax.swing.JSpinner spnFechaDevolucionReal; // NUEVO
    private javax.swing.JTextField txtObservaciones;
    
    private javax.swing.JButton btnProcesarDevolucion;
    private javax.swing.JTable tblPrestamosActivos;
    private javax.swing.JScrollPane scrollPrestamos;

    private PrestamoDAO prestamoDAO = new PrestamoDAO();

    public FrmDevoluciones() {
        initComponents();
        this.setLocationRelativeTo(null);
        cargarTablaActivos(); // Carga inicial

        // =========================================================
        // TRUCO MAESTRO: Actualización Automática
        // =========================================================
        // Este evento detecta cada vez que el panel "aparece" en la pantalla
        this.getContentPane().addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent evt) {
                // Volvemos a consultar la Base de Datos para traer los préstamos recién hechos
                cargarTablaActivos();
                
                // Limpiamos las cajas de texto por seguridad
                txtIdPrestamo.setText("");
                txtObservaciones.setText("");
            }
        });
    }

    private void cargarTablaActivos() {
        DefaultTableModel modelo = prestamoDAO.listarPrestamosActivos();
        tblPrestamosActivos.setModel(modelo);
    }

    private void initComponents() {
        lblIdPrestamo = new javax.swing.JLabel("ID del Préstamo:");
        lblFechaReal = new javax.swing.JLabel("Fecha Real de Devolución:"); // NUEVO
        lblObservaciones = new javax.swing.JLabel("Observaciones (Ej. Rayones - Opcional):");
        
        txtIdPrestamo = new javax.swing.JTextField();
        txtObservaciones = new javax.swing.JTextField();
        
        // CONFIGURACIÓN DEL SPINNER DE FECHA
        spnFechaDevolucionReal = new javax.swing.JSpinner();
        spnFechaDevolucionReal.setModel(new javax.swing.SpinnerDateModel()); 
        javax.swing.JSpinner.DateEditor dateEditor = new javax.swing.JSpinner.DateEditor(spnFechaDevolucionReal, "dd/MM/yyyy HH:mm");
        spnFechaDevolucionReal.setEditor(dateEditor);
        
        btnProcesarDevolucion = new javax.swing.JButton("Registrar Devolución");

        // Bloqueamos el ID para evitar errores
        txtIdPrestamo.setEditable(false);
        txtIdPrestamo.setBackground(new java.awt.Color(240, 240, 240));
        
        // Bordes suaves
        javax.swing.border.Border bordeSuave = javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 200, 200));
        txtIdPrestamo.setBorder(bordeSuave);
        txtObservaciones.setBorder(bordeSuave);
        spnFechaDevolucionReal.setBorder(bordeSuave);
        
        tblPrestamosActivos = new javax.swing.JTable();
        scrollPrestamos = new javax.swing.JScrollPane(tblPrestamosActivos);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Recepción y Devolución de Equipos");

        // EVENTO: Clic en la tabla
        tblPrestamosActivos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int fila = tblPrestamosActivos.getSelectedRow();
                if (fila >= 0) {
                    txtIdPrestamo.setText(tblPrestamosActivos.getValueAt(fila, 0).toString());
                }
            }
        });
        
        btnProcesarDevolucion.addActionListener(e -> btnProcesarDevolucionActionPerformed());

        // ==========================================
        // DISEÑO FLUIDO
        // ==========================================
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPrestamos, javax.swing.GroupLayout.DEFAULT_SIZE, 850, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblIdPrestamo)
                            .addComponent(txtIdPrestamo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFechaReal)
                            .addComponent(spnFechaDevolucionReal, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblObservaciones)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtObservaciones))
                        .addGap(15, 15, 15)
                        .addComponent(btnProcesarDevolucion, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25))
        );
        
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIdPrestamo)
                    .addComponent(lblFechaReal)
                    .addComponent(lblObservaciones))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIdPrestamo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spnFechaDevolucionReal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtObservaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProcesarDevolucion, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(scrollPrestamos, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                .addGap(25, 25, 25))
        );

        pack();
    }

    private void btnProcesarDevolucionActionPerformed() {
        String idStr = txtIdPrestamo.getText().trim();
        String observaciones = txtObservaciones.getText().trim();

        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un préstamo de la tabla inferior.");
            return;
        }

        int idPrestamo = Integer.parseInt(idStr);
        
        // 1. Extraemos la fecha del Spinner
        java.util.Date fechaUtil = (java.util.Date) spnFechaDevolucionReal.getValue();
        java.sql.Timestamp fechaSQL = new java.sql.Timestamp(fechaUtil.getTime());
        
        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Confirmar la recepción del equipo del préstamo #" + idPrestamo + "?", "Confirmar Devolución", JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            // 2. Le pasamos la fechaSQL a tu nuevo método en el DAO
            String msg = prestamoDAO.registrarDevolucion(idPrestamo, observaciones, fechaSQL);
            JOptionPane.showMessageDialog(this, msg);
            
            txtIdPrestamo.setText("");
            txtObservaciones.setText("");
            cargarTablaActivos(); 
        }
    }
}