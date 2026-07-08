/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;
import Controlador.Inventario;
import Modelo.Equipo;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author crist
 */
public class FrmInventario extends javax.swing.JFrame {

    private Inventario controlador = new Inventario();

    private int idSeleccionado = 0;
    private boolean disponibleSeleccionado = false;

    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblMarca;
    private javax.swing.JLabel lblDetalle;
    private javax.swing.JLabel lblCategoria;
    private javax.swing.JTable tblInventario;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDetalle;
    private javax.swing.JTextField txtMarca;
    private javax.swing.JComboBox<String> cmbRol;

    public FrmInventario() {
        initComponents();
        this.setLocationRelativeTo(null);
        cargarTabla();

        // =========================================================
        // TRUCO MAESTRO: Actualización Automática
        // =========================================================
        this.getContentPane().addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent evt) {
                cargarTabla();
                limpiarFormulario();
            }
        });
    }

    private void cargarTabla() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new Object[]{"ID", "Código Patrimonial", "Categoría", "Marca", "Detalle Técnico", "Estado"});
        tblInventario.setModel(modelo);

        for (Equipo eq : controlador.obtainEquipos()) {
            modelo.addRow(new Object[]{
                eq.getIdEquipo(),
                eq.getCodigoPatrimonial(),
                eq.getCategoria(),
                eq.getMarca(),
                eq.getDetalleTecnico(),
                // CORRECCIÓN: Si isDisponible() es true (1), escribe "Disponible"
                eq.isDisponible() ? "Disponible" : "Prestado" 
            });
        }
    }

    private void initComponents() {
        lblCodigo = new javax.swing.JLabel("Código Patrimonial:");
        lblMarca = new javax.swing.JLabel("Marca:");
        lblDetalle = new javax.swing.JLabel("Detalle Técnico:");
        lblCategoria = new javax.swing.JLabel("Categoría:");
        
        txtCodigo = new javax.swing.JTextField();
        txtMarca = new javax.swing.JTextField();
        txtDetalle = new javax.swing.JTextField();
        cmbRol = new javax.swing.JComboBox<>(new String[]{"Laptop", "Componente"});
        
        // =========================================================
        // ARREGLO ESTÉTICO: Bordes suaves SOLO para las cajas de texto
        // =========================================================
        javax.swing.border.Border bordeSuave = javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 200, 200));
        txtCodigo.setBorder(bordeSuave);
        txtMarca.setBorder(bordeSuave);
        txtDetalle.setBorder(bordeSuave);
        
        btnGuardar = new javax.swing.JButton("Guardar Equipo");
        btnActualizar = new javax.swing.JButton("Actualizar");
        btnEliminar = new javax.swing.JButton("Eliminar");
        btnLimpiar = new javax.swing.JButton("Limpiar Formulario");
        
        // La tabla y su panel ahora se crean limpios, como NetBeans los hace por defecto
        jScrollPane1 = new javax.swing.JScrollPane();
        tblInventario = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestión de Inventario de Equipos");

        jScrollPane1.setViewportView(tblInventario);

        // =========================================================
        // EVENTO DE CLIC EN LA TABLA (Relleno automático)
        // =========================================================
        tblInventario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int fila = tblInventario.getSelectedRow();
                if (fila >= 0) {
                    idSeleccionado = Integer.parseInt(tblInventario.getValueAt(fila, 0).toString());
                    txtCodigo.setText(tblInventario.getValueAt(fila, 1).toString());
                    cmbRol.setSelectedItem(tblInventario.getValueAt(fila, 2).toString());
                    txtMarca.setText(tblInventario.getValueAt(fila, 3).toString());
                    txtDetalle.setText(tblInventario.getValueAt(fila, 4).toString());
                    
                    String estado = tblInventario.getValueAt(fila, 5).toString();
                    disponibleSeleccionado = estado.equals("Disponible");
                }
            }
        });

        // Eventos de botones
        btnGuardar.addActionListener(evt -> btnGuardarActionPerformed(evt));
        btnActualizar.addActionListener(evt -> btnActualizarActionPerformed(evt));
        btnEliminar.addActionListener(evt -> btnEliminarActionPerformed(evt));
        btnLimpiar.addActionListener(evt -> limpiarFormulario());

        // ==========================================
        // DISTRIBUCIÓN GRÁFICA FLUIDA 
        // ==========================================
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 850, Short.MAX_VALUE)
                    
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCodigo)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMarca)
                            .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDetalle)
                            .addComponent(txtDetalle, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCategoria) 
                            .addComponent(cmbRol, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                        .addGap(10, 10, 10)
                        .addComponent(btnActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                        .addGap(10, 10, 10)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                        .addGap(10, 10, 10)
                        .addComponent(btnLimpiar, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)))
                .addGap(25, 25, 25))
        );
        
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigo)
                    .addComponent(lblMarca)
                    .addComponent(lblDetalle)
                    .addComponent(lblCategoria)) 
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbRol, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                .addGap(25, 25, 25))
        );

        pack();
    }

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {
        String tipoElegido = cmbRol.getSelectedItem().toString();
        String msg = controlador.registrarNuevoEquipo(
            txtCodigo.getText(), 
            txtMarca.getText(), 
            txtDetalle.getText(), 
            tipoElegido
        );
        JOptionPane.showMessageDialog(this, msg);
        limpiarFormulario();
    }

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {
        if (idSeleccionado <= 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un equipo de la tabla para actualizar.");
            return;
        }
        
        String tipoElegido = cmbRol.getSelectedItem().toString();
        String msg = controlador.actualizarEquipo(
            idSeleccionado,
            txtCodigo.getText(),
            txtMarca.getText(),
            txtDetalle.getText(),
            tipoElegido,
            disponibleSeleccionado
        );

        JOptionPane.showMessageDialog(this, msg);
        limpiarFormulario();
    }

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {
        if (idSeleccionado <= 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un equipo de la tabla para eliminar.");
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de eliminar el equipo con código \"" + txtCodigo.getText() + "\"?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            String msg = controlador.eliminarEquipo(idSeleccionado);
            JOptionPane.showMessageDialog(this, msg);
            limpiarFormulario();
        }
    }

    private void limpiarFormulario() {
        txtCodigo.setText("");
        txtMarca.setText("");
        txtDetalle.setText("");
        cmbRol.setSelectedIndex(0);
        idSeleccionado = 0;
        disponibleSeleccionado = true;
        tblInventario.clearSelection();
        cargarTabla(); // Recargamos para ver los cambios
    }
}