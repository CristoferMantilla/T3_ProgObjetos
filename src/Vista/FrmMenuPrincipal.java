/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;
import Controlador.SesionActiva;
/**
 *
 * @author crist
 */
public class FrmMenuPrincipal extends javax.swing.JFrame {

    // Declaración de variables de NetBeans
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JButton btnUsuarios;
    private javax.swing.JLabel lblBienvenida;
    private javax.swing.JLabel lblTitulo;

    public FrmMenuPrincipal() {
        initComponents();
        this.setLocationRelativeTo(null);
        lblBienvenida.setText("Usuario activo: " + SesionActiva.nombreUsuarioActivo);
    }

    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        btnUsuarios = new javax.swing.JButton();
        btnCerrarSesion = new javax.swing.JButton();
        lblBienvenida = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Menú Principal");

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 16)); 
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("PANEL DE CONTROL - LOGÍSTICA");

        btnUsuarios.setText("Módulo Gestión de Estudiantes");
        btnUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuariosActionPerformed(evt);
            }
        });

        lblBienvenida.setForeground(new java.awt.Color(0, 102, 204));
        lblBienvenida.setText("Usuario activo: ---");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblBienvenida, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                    .addComponent(btnUsuarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCerrarSesion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblBienvenida)
                .addGap(30, 30, 30)
                .addComponent(btnUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(btnCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        pack();
    }

    private void btnUsuariosActionPerformed(java.awt.event.ActionEvent evt) {
        new FrmEstudiantes().setVisible(true); 
    }

    private void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {
        SesionActiva.nombreUsuarioActivo = "";
        this.dispose();
        new FrmLogin().setVisible(true);
    }
}
