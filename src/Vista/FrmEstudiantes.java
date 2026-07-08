/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;
import javax.swing.JOptionPane;
import Controlador.ControladorUsuario;
import Modelo.Estudiante;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author crist
 */
public class FrmEstudiantes extends javax.swing.JFrame{
    private ControladorUsuario controlador = new ControladorUsuario();
    
    // Declaración de variables de NetBeans
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JButton btnLimpiar; // Botón nuevo para limpiar rápido
    private javax.swing.JCheckBox chkBloqueado;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblNombre1;
    private javax.swing.JLabel lblNombre2;
    private javax.swing.JLabel lblApePaterno;
    private javax.swing.JLabel lblApeMaterno;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblEstudiantes;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtNombre1;
    private javax.swing.JTextField txtNombre2;
    private javax.swing.JTextField txtApePaterno;
    private javax.swing.JTextField txtApeMaterno;

    public FrmEstudiantes() {
        initComponents();
        this.setLocationRelativeTo(null);
        cargarTabla(""); 
    }

    private void cargarTabla(String filtro) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new Object[]{"Código UPN", "1ER Nombre", "2DO Nombre", "Ape Paterno", "Ape Materno", "Estado actual"});
        tblEstudiantes.setModel(modelo);

        // Llamamos al controlador para que traiga la lista actualizada
        List<Estudiante> lista = controlador.obtenerLista(filtro);
        
        // Validamos que la lista no esté vacía para evitar errores
        if (lista != null) {
            for (Estudiante est : lista) {
                modelo.addRow(new Object[]{
                    est.getCodigoUPN(),
                    est.getNombre1(),
                    est.getNombre2(), 
                    est.getApellido_paterno(),
                    est.getApellido_materno(),
                    est.isEstadoBloqueo() ? "Bloqueado" : "Activo"
                });
            }
        }
    }

    private void initComponents() {

        lblCodigo = new javax.swing.JLabel("Código UPN:");
        txtCodigo = new javax.swing.JTextField();
        lblNombre1 = new javax.swing.JLabel("1ER Nombre:");
        lblNombre2 = new javax.swing.JLabel("2DO Nombre:");
        lblApePaterno = new javax.swing.JLabel("APE Paterno:");
        lblApeMaterno = new javax.swing.JLabel("APE Materno:");
        txtNombre1 = new javax.swing.JTextField();
        txtNombre2 = new javax.swing.JTextField();
        txtApePaterno = new javax.swing.JTextField();
        txtApeMaterno = new javax.swing.JTextField();
        chkBloqueado = new javax.swing.JCheckBox("¿Estudiante Bloqueado?");
        
        // =========================================================
        // ARREGLO ESTÉTICO: Bordes suaves para las cajas de texto
        // =========================================================
        javax.swing.border.Border bordeSuave = javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 200, 200));
        
        txtCodigo.setBorder(bordeSuave);
        txtNombre1.setBorder(bordeSuave);
        txtNombre2.setBorder(bordeSuave);
        txtApePaterno.setBorder(bordeSuave);
        txtApeMaterno.setBorder(bordeSuave);
        // =========================================================

        btnRegistrar = new javax.swing.JButton("Registrar");
        btnActualizar = new javax.swing.JButton("Actualizar");
        btnEliminar = new javax.swing.JButton("Dar de Baja");
        btnBuscar = new javax.swing.JButton("Buscar por Código");
        btnLimpiar = new javax.swing.JButton("Limpiar Formulario");
        
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEstudiantes = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestión de Estudiantes");

        // Eventos de los botones
        btnRegistrar.addActionListener(evt -> btnRegistrarActionPerformed(evt));
        btnActualizar.addActionListener(evt -> btnActualizarActionPerformed(evt));
        btnEliminar.addActionListener(evt -> btnEliminarActionPerformed(evt));
        btnBuscar.addActionListener(evt -> btnBuscarActionPerformed(evt));
        btnLimpiar.addActionListener(evt -> limpiarFormulario());

        jScrollPane1.setViewportView(tblEstudiantes);

        // =========================================================
        // NUEVO: EVENTO DE CLIC EN LA TABLA (Relleno automático)
        // =========================================================
        tblEstudiantes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int fila = tblEstudiantes.getSelectedRow();
                if (fila >= 0) {
                    txtCodigo.setText(tblEstudiantes.getValueAt(fila, 0).toString());
                    txtNombre1.setText(tblEstudiantes.getValueAt(fila, 1).toString());
                    txtNombre2.setText(tblEstudiantes.getValueAt(fila, 2).toString());
                    txtApePaterno.setText(tblEstudiantes.getValueAt(fila, 3).toString());
                    txtApeMaterno.setText(tblEstudiantes.getValueAt(fila, 4).toString());
                    
                    // Si el estado dice "Bloqueado", marcamos el check, sino lo desmarcamos
                    String estado = tblEstudiantes.getValueAt(fila, 5).toString();
                    chkBloqueado.setSelected(estado.equals("Bloqueado"));
                }
            }
        });

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
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNombre1)
                            .addComponent(txtNombre1, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNombre2)
                            .addComponent(txtNombre2, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblApePaterno)
                            .addComponent(txtApePaterno, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblApeMaterno)
                            .addComponent(txtApeMaterno, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkBloqueado)))
                    
                    // Botones
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnRegistrar, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                        .addGap(10, 10, 10)
                        .addComponent(btnActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                        .addGap(10, 10, 10)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                        .addGap(10, 10, 10)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                        .addGap(10, 10, 10)
                        .addComponent(btnLimpiar, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)))
                .addGap(25, 25, 25))
        );
        
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigo)
                    .addComponent(lblNombre1)
                    .addComponent(lblNombre2)
                    .addComponent(lblApePaterno)
                    .addComponent(lblApeMaterno))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombre1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombre2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtApePaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtApeMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkBloqueado)) 
                .addGap(18, 18, 18)
                    
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                .addGap(25, 25, 25))
        );
        pack();
    }

    private void limpiarFormulario() {
        txtCodigo.setText("");
        txtNombre1.setText("");
        txtNombre2.setText("");
        txtApePaterno.setText("");
        txtApeMaterno.setText("");
        chkBloqueado.setSelected(false);
        tblEstudiantes.clearSelection();
        cargarTabla(""); // Recarga la tabla completa por si había un filtro activo
    }

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {
        String msg = controlador.registrar(txtCodigo.getText(), txtNombre1.getText(), txtNombre2.getText(), txtApePaterno.getText(),txtApeMaterno.getText());
        JOptionPane.showMessageDialog(this, msg);
        limpiarFormulario();
    }

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {
        String msg = controlador.actualizar(txtCodigo.getText(), txtNombre1.getText(), txtNombre2.getText(), txtApePaterno.getText(),txtApeMaterno.getText(), chkBloqueado.isSelected());
        JOptionPane.showMessageDialog(this, msg);
        limpiarFormulario();
    }

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {
        String msg = controlador.darDeBaja(txtCodigo.getText());
        JOptionPane.showMessageDialog(this, msg);
        limpiarFormulario();
    }

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {
        cargarTabla(txtCodigo.getText().trim());
    }
}