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
    
    private javax.swing.JButton btnGuardar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblMarca;
    private javax.swing.JLabel lblDetalle;
    private javax.swing.JLabel lblCategoria;
    private javax.swing.JTable tblInventario;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDetalle;
    private javax.swing.JTextField txtMarca;
    private javax.swing.JTextField txtCategoria;
    private javax.swing.JComboBox<String> cmbRol;

    public FrmInventario() {
        initComponents();
        this.setLocationRelativeTo(null);
        cargarTabla();
    }

    private void cargarTabla() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new Object[]{"ID", "Código Patrimonial","Categoria","Marca","Detalle Tecnico", "Estado"});
        tblInventario.setModel(modelo);

        for (Equipo eq : controlador.obtainEquipos()) {
            modelo.addRow(new Object[]{
                eq.getIdEquipo(),
                eq.getCodigoPatrimonial(),
                eq.getCategoria(),
                eq.getMarca(),
                eq.getDetalleTecnico(),
                eq.isDisponible() ? "Disponible" : "Prestado"
            });
        }
    }

    private void initComponents() {
        lblCodigo = new javax.swing.JLabel("Código Patrimonial:");
        lblMarca = new javax.swing.JLabel("Marca:");
        lblDetalle = new javax.swing.JLabel("Detalle Técnico:");
        lblCategoria = new javax.swing.JLabel("Categoria");
        txtCodigo = new javax.swing.JTextField();
        txtCategoria = new javax.swing.JTextField();
        txtMarca = new javax.swing.JTextField();
        txtDetalle = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton("Guardar Equipo");
        jScrollPane1 = new javax.swing.JScrollPane();
        tblInventario = new javax.swing.JTable();
        cmbRol = new javax.swing.JComboBox<>(new String[]{"Laptop", "Componente"});

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jScrollPane1.setViewportView(tblInventario);

        // Distribución gráfica FLUIDA (Se adapta a toda la pantalla)
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING) // <-- Quitamos el 'false' para que pueda crecer
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE) // <-- Se estira al máximo
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCodigo)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCategoria)
                            .addComponent(txtCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMarca)
                            .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDetalle)
                            .addComponent(txtDetalle, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)) // <-- El detalle toma el espacio sobrante
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbRol, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)) // <-- El botón se estira
                .addGap(25, 25, 25))
        );
        
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigo)
                    .addComponent(lblCategoria)
                    .addComponent(lblMarca)
                    .addComponent(lblDetalle))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE) // <-- Se estira hacia abajo al máximo
                .addGap(25, 25, 25))
        );

        btnGuardar.addActionListener(evt -> btnGuardarActionPerformed(evt));
        pack();
    }

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {
        // 1. Capturamos lo que el usuario eligió en el desplegable
    String tipoElegido = cmbRol.getSelectedItem().toString();
    
    // 2. Se lo pasamos al controlador (nota que agregamos tipoElegido al final)
    String msg = controlador.registrarNuevoEquipo(
        txtCodigo.getText(), 
        txtMarca.getText(), 
        txtDetalle.getText(), 
        tipoElegido
    );
    
    // 3. Mostramos el mensaje y actualizamos la tabla
    JOptionPane.showMessageDialog(this, msg);
    cargarTabla();
    }
}
