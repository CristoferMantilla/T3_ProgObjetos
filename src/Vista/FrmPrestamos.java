/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;
import javax.swing.JOptionPane;
/**
 *
 * @author crist
 */
public class FrmPrestamos extends javax.swing.JFrame {

    private javax.swing.JButton btnProcesar;
    private javax.swing.JLabel lblEq;
    private javax.swing.JLabel lblUser;
    private javax.swing.JTextField txtCodEquipo;
    private javax.swing.JTextField txtCodEstudiante;

    public FrmPrestamos() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    private void initComponents() {
        lblUser = new javax.swing.JLabel("Código del Alumno (N00...):");
        lblEq = new javax.swing.JLabel("Código Patrimonial del Equipo:");
        txtCodEstudiante = new javax.swing.JTextField();
        txtCodEquipo = new javax.swing.JTextField();
        btnProcesar = new javax.swing.JButton("Registrar Préstamo de Salida");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblEq)
                    .addComponent(lblUser)
                    .addComponent(txtCodEstudiante)
                    .addComponent(txtCodEquipo)
                    .addComponent(btnProcesar, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblUser)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCodEstudiante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblEq)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCodEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnProcesar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        btnProcesar.addActionListener(e -> btnProcesarActionPerformed());
        pack();
    }

    private void btnProcesarActionPerformed() {
        String estudiante = txtCodEstudiante.getText().trim();
        String equipo = txtCodEquipo.getText().trim();

        if(estudiante.isEmpty() || equipo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe completar ambos campos para la verificación.");
            return;
        }

        // Simulación lógica de validaciones del REQ-25 al REQ-28 antes de ejecutar el DAO
        if(estudiante.equals("N00033333")) { // Código mock bloqueado en SQL
            JOptionPane.showMessageDialog(this, "Denegado: El estudiante se encuentra Bloqueado en el sistema.");
        } else {
            JOptionPane.showMessageDialog(this, "Procesando transacción con base de datos relacional...");
            // Aquí haces el llamado directo a tu PrestamoDAO a través de la capa lógica
        }
    }
}   