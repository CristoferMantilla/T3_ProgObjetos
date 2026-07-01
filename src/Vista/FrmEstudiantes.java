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
                    est.getNombre2(), // Ahora sí carga el segundo nombre (o el espacio vacío si es null)
                    est.getApellido_paterno(),
                    est.getApellido_materno(),
                    est.isEstadoBloqueo() ? "Bloqueado" : "Activo"
                });
            }
        }
    }

    private void initComponents() {

        lblCodigo = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        lblNombre1 = new javax.swing.JLabel();
        lblNombre2 = new javax.swing.JLabel();
        lblApePaterno = new javax.swing.JLabel();
        lblApeMaterno = new javax.swing.JLabel();
        txtNombre1 = new javax.swing.JTextField();
        txtNombre2 = new javax.swing.JTextField();
        txtApePaterno = new javax.swing.JTextField();
        txtApeMaterno = new javax.swing.JTextField();
        chkBloqueado = new javax.swing.JCheckBox();
        btnRegistrar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEstudiantes = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestión de Estudiantes");

        lblCodigo.setText("Código UPN:");
        lblNombre1.setText("1ER Nombre:");
        lblNombre2.setText("2DO Nombre:");
        lblApePaterno.setText("APE Paterno:");
        lblApeMaterno.setText("APE Materno:");
        chkBloqueado.setText("¿Estudiante Bloqueado?");

        btnRegistrar.setText("Registrar");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });
        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        btnEliminar.setText("Dar de Baja");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        btnBuscar.setText("Buscar por Código");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        jScrollPane1.setViewportView(tblEstudiantes);
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCodigo)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNombre1)
                            .addComponent(txtNombre1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNombre2)
                            .addComponent(txtNombre2, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblApePaterno)
                            .addComponent(txtApePaterno, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblApeMaterno)
                            .addComponent(txtApeMaterno, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkBloqueado))
                    ) // <-- SOLUCIÓN: Faltaba este paréntesis para cerrar el grupo de los textfields
                    .addGroup(layout.createSequentialGroup()
                    .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(30) // <-- Separación fija de 25 píxeles
                    .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(30) // <-- Separación fija de 25 píxeles
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(30) // <-- Separación fija de 25 píxeles (Adiós al MAX_VALUE)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                )
            )
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
                    .addComponent(btnRegistrar)
                    .addComponent(btnActualizar)
                    .addComponent(btnEliminar)
                    .addComponent(btnBuscar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        pack();
    }
    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {
        String msg = controlador.registrar(txtCodigo.getText(), txtNombre1.getText(), txtNombre2.getText(), txtApePaterno.getText(),txtApeMaterno.getText());
        JOptionPane.showMessageDialog(this, msg);
        cargarTabla("");
    }
    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {
        String msg = controlador.actualizar(txtCodigo.getText(), txtNombre1.getText(), txtNombre2.getText(), txtApePaterno.getText(),txtApeMaterno.getText(), chkBloqueado.isSelected());
        JOptionPane.showMessageDialog(this, msg);
        cargarTabla("");
    }
    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {
        String msg = controlador.darDeBaja(txtCodigo.getText());
        JOptionPane.showMessageDialog(this, msg);
        cargarTabla("");
    }
    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {
        cargarTabla(txtCodigo.getText().trim());
    }
}
