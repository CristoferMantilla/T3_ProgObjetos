/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;
import Controlador.ControladorUsuario;
import Controlador.Inventario;
import Controlador.PrestamoDAO;
import Modelo.Componente;
import Modelo.Equipo;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import Modelo.Estudiante;
import Modelo.Prestamo;
// Asume que tienes importados tus controladores y modelos aquí
public class FrmPrestamos extends javax.swing.JFrame {

    private javax.swing.JButton btnProcesar;
    private javax.swing.JLabel lblEq;
    private javax.swing.JLabel lblUser;
    private javax.swing.JLabel lblFechaDevolucion; // NUEVO
    private javax.swing.JTextField txtCodEquipo;
    private javax.swing.JTextField txtCodEstudiante;
    private javax.swing.JSpinner spnFechaDevolucion; // NUEVO
    
    // Agregamos las tablas y sus ScrollPanes
    private javax.swing.JTable tblEstudiantes;
    private javax.swing.JTable tblEquipos;
    private javax.swing.JScrollPane scrollEstudiantes;
    private javax.swing.JScrollPane scrollEquipos;

    // Controladores (Asegúrate de que los nombres coincidan con los tuyos)
    private ControladorUsuario ctrlUsuario = new ControladorUsuario();
    private Inventario ctrlInventario = new Inventario();

    public FrmPrestamos() {
        initComponents();
        this.setLocationRelativeTo(null);
        
        // Cargamos los datos la primera vez que arranca el sistema
        cargarTablaEstudiantes();
        cargarTablaEquipos();

        // =========================================================
        // TRUCO MAESTRO: Actualización Automática
        // =========================================================
        // Este evento detecta cada vez que el panel "aparece" en la pantalla
        this.getContentPane().addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent evt) {
                // Volvemos a consultar la Base de Datos
                cargarTablaEstudiantes();
                cargarTablaEquipos();
                
                // Limpiamos las cajas de texto por si quedó un código seleccionado de antes
                txtCodEstudiante.setText("");
                txtCodEquipo.setText("");
            }
        });
    }

   private void cargarTablaEstudiantes() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new Object[]{"Código UPN", "Nombres", "Apellidos", "Estado"});
        tblEstudiantes.setModel(modelo);

        // Llamas a tu controlador para obtener la lista (usa el nombre de tu variable controlador)
        List<Estudiante> lista = ctrlUsuario.obtenerLista(""); 
        
        if (lista != null) {
            for (Estudiante est : lista) {
                
                // =======================================================
                // EL FILTRO: Solo dibuja la fila si el estudiante NO está bloqueado
                // =======================================================
                if (!est.isEstadoBloqueo()) {
                    
                    // Aseguramos que no salgan "null" si falta el segundo nombre
                    String nombres = est.getNombre1() + (est.getNombre2() != null && !est.getNombre2().isEmpty() ? " " + est.getNombre2() : "");
                    String apellidos = est.getApellido_paterno() + " " + est.getApellido_materno();

                    modelo.addRow(new Object[]{
                        est.getCodigoUPN(),
                        nombres,
                        apellidos,
                        "Activo" // Todos los que pasen este filtro serán Activos
                    });
                }
            }
        }
    }

    private void cargarTablaEquipos() {
        DefaultTableModel modelo = new DefaultTableModel();
        // Ajusta los nombres de tus columnas según lo que tengas
        modelo.setColumnIdentifiers(new Object[]{"Cód. Patrimonial", "Categoría", "Detalle", "Estado"});
        tblEquipos.setModel(modelo);

        // Llamamos al controlador de inventario
        List<Equipo> listaEquipos = (List<Equipo>) ctrlInventario.obtainEquipos(); 
        
        if (listaEquipos != null) {
            for (Equipo eq : listaEquipos) {
                
                // =======================================================
                // EL FILTRO: Solo dibuja la fila si el equipo SÍ está disponible (true / 1)
                // =======================================================
                if (eq.isDisponible()) { 
                    
                    modelo.addRow(new Object[]{
                        eq.getCodigoPatrimonial(),
                        eq.getCategoria(),
                        eq.getDetalleTecnico(),
                        "Disponible" // Como ya filtramos, todos los que pasen dirán "Disponible"
                    });
                }
            }
        }
    }

    private void initComponents() {
        lblUser = new javax.swing.JLabel("Alumno Seleccionado:");
        lblEq = new javax.swing.JLabel("Equipo Seleccionado:");
        lblFechaDevolucion = new javax.swing.JLabel("Fecha de Devolución:"); // NUEVO LABEL
        
        txtCodEstudiante = new javax.swing.JTextField();
        txtCodEquipo = new javax.swing.JTextField();
        
        // NUEVO SPINNER DE FECHA
        spnFechaDevolucion = new javax.swing.JSpinner();
        spnFechaDevolucion.setModel(new javax.swing.SpinnerDateModel()); 
        javax.swing.JSpinner.DateEditor dateEditor = new javax.swing.JSpinner.DateEditor(spnFechaDevolucion, "dd/MM/yyyy HH:mm");
        spnFechaDevolucion.setEditor(dateEditor);
        
        btnProcesar = new javax.swing.JButton("Registrar Préstamo de Salida");

        // Hacemos que las cajas no se puedan editar manualmente para evitar errores
        txtCodEstudiante.setEditable(false);
        txtCodEquipo.setEditable(false);
        
        // Bordes suaves
        javax.swing.border.Border bordeSuave = javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 200, 200));
        txtCodEstudiante.setBorder(bordeSuave);
        txtCodEquipo.setBorder(bordeSuave);
        spnFechaDevolucion.setBorder(bordeSuave);
        
        // Inicializamos las tablas
        tblEstudiantes = new javax.swing.JTable();
        tblEquipos = new javax.swing.JTable();
        scrollEstudiantes = new javax.swing.JScrollPane(tblEstudiantes);
        scrollEquipos = new javax.swing.JScrollPane(tblEquipos);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestión de Préstamos");

        // ==========================================
        // EVENTOS CLAVE: Clic en las tablas
        // ==========================================
        tblEstudiantes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int fila = tblEstudiantes.getSelectedRow();
                if (fila >= 0) {
                    // Sube el código UPN (Columna 0) a la caja de texto
                    txtCodEstudiante.setText(tblEstudiantes.getValueAt(fila, 0).toString());
                }
            }
        });

        tblEquipos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int fila = tblEquipos.getSelectedRow();
                if (fila >= 0) {
                    // Sube el código patrimonial (Columna 0) a la caja de texto
                    txtCodEquipo.setText(tblEquipos.getValueAt(fila, 0).toString());
                }
            }
        });
        
        btnProcesar.addActionListener(e -> btnProcesarActionPerformed());

        // ==========================================
        // DISEÑO FLUIDO CON TABLAS LADO A LADO Y SPINNER
        // ==========================================
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    // Fila 1: Cajas de texto, Spinner y Botón
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUser)
                            .addComponent(txtCodEstudiante, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEq)
                            .addComponent(txtCodEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFechaDevolucion)
                            .addComponent(spnFechaDevolucion, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addComponent(btnProcesar, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                    
                    // Fila 2: Las dos tablas separadas equitativamente
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(scrollEstudiantes, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                        .addGap(20, 20, 20)
                        .addComponent(scrollEquipos, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)))
                .addGap(25, 25, 25))
        );
        
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                // Fila 1: Labels
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUser)
                    .addComponent(lblEq)
                    .addComponent(lblFechaDevolucion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                // Fila 2: Cajas, Spinner y Botón
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodEstudiante, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spnFechaDevolucion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProcesar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                // Fila 3: Las tablas se estiran hacia abajo
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollEstudiantes, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                    .addComponent(scrollEquipos, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
                .addGap(25, 25, 25))
        );

        pack();
    }

    private void btnProcesarActionPerformed() {
        String estudiante = txtCodEstudiante.getText().trim();
        String equipo = txtCodEquipo.getText().trim();
        
        // 1. Validación de campos vacíos
        if(estudiante.isEmpty() || equipo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un Estudiante y un Equipo de las tablas.");
            return;
        }
        
        // ====================================================================
        // NUEVO: DOBLE CANDADO DE VALIDACIÓN PARA EL EQUIPO
        // ====================================================================
        boolean estaDisponible = false;
        // Buscamos el equipo en el inventario actual para verificar su estado real
        if (ctrlInventario != null && ctrlInventario.obtainEquipos() != null) {
            for (Equipo e : ctrlInventario.obtainEquipos()) {
                if (e.getCodigoPatrimonial().equalsIgnoreCase(equipo)) {
                    estaDisponible = e.isDisponible();
                    break;
                }
            }
        }

        // Si el equipo no está disponible (o no se encontró), detenemos el proceso
        if (!estaDisponible) {
            JOptionPane.showMessageDialog(this, "Error: Este equipo ya se encuentra en curso en otro préstamo.", "Equipo Ocupado", JOptionPane.WARNING_MESSAGE);
            return; // Detiene el proceso por completo
        }
        // ====================================================================
        
        // Obtenemos la fecha del Spinner y la convertimos para SQL
        java.util.Date fechaUtil = (java.util.Date) spnFechaDevolucion.getValue();
        java.sql.Timestamp fechaSQL = new java.sql.Timestamp(fechaUtil.getTime());

        // 2. Creamos objetos temporales para empaquetar los códigos String seleccionados
        Estudiante est = new Estudiante();
        est.setCodigoUPN(estudiante);
        
        Equipo eq = new Componente(); // Instancia genérica para transportar el código patrimonial
        eq.setCodigoPatrimonial(equipo);
        
        // 3. Construimos nuestro objeto Prestamo con toda la información recolectada
        Prestamo nuevoPrestamo = new Prestamo();
        nuevoPrestamo.setUsuario(est);
        nuevoPrestamo.setEquipo(eq);
        nuevoPrestamo.setFechaDevolucion(fechaSQL); 

        // 4. Ejecutamos la inserción real en la Base de Datos a través del DAO
        PrestamoDAO dao = new PrestamoDAO();
        String msg = dao.registrarPrestamo(nuevoPrestamo);
        
        // 5. Mostramos el resultado del procedimiento al usuario
        JOptionPane.showMessageDialog(this, msg);
        
        // 6. Refrescamos las tablas para actualizar los cambios de inmediato
        cargarTablaEquipos(); 
        cargarTablaEstudiantes(); // También refrescamos estudiantes por consistencia
        
        // 7. Limpiamos las cajas de texto para el siguiente registro
        txtCodEstudiante.setText("");
        txtCodEquipo.setText("");
    }
}