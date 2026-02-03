package com.inventario.vista;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;
import com.inventario.excepciones.ProductoException;
import com.inventario.modelo.Producto;
import com.inventario.servicio.ServicioProducto;

public class VentanaMenuPrincipal extends JFrame {
    private static final long serialVersionUID = 1L;
    private String nombreUsuario;
    private CardLayout cardLayout;
    private JPanel panelContenido;
    private ServicioProducto servicioProducto;
    private DefaultTableModel modeloInventario;
    private JTable tablaInventario;
    
    public VentanaMenuPrincipal(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
        this.servicioProducto = new ServicioProducto();
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        setTitle("Sistema de Gestión de Inventario");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        JPanel panelSuperior = new JPanel();
        panelSuperior.setBackground(new Color(44, 62, 80));
        panelSuperior.setLayout(new BorderLayout());
        panelSuperior.setPreferredSize(new Dimension(getWidth(), 70));
        
        JLabel lblTitulo = new JLabel("🏢 SISTEMA DE GESTIÓN DE INVENTARIO");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel lblUsuario = new JLabel("👤 Usuario: " + nombreUsuario);
        lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblUsuario.setForeground(new Color(236, 240, 241));
        lblUsuario.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        
        panelSuperior.add(lblTitulo, BorderLayout.CENTER);
        panelSuperior.add(lblUsuario, BorderLayout.WEST);
        JPanel panelLateral = new JPanel();
        panelLateral.setBackground(new Color(52, 73, 94));
        panelLateral.setPreferredSize(new Dimension(250, getHeight()));
        panelLateral.setLayout(new BoxLayout(panelLateral, BoxLayout.Y_AXIS));
        JLabel lblMenuTitulo = new JLabel("📋 MENÚ PRINCIPAL");
        lblMenuTitulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblMenuTitulo.setForeground(new Color(189, 195, 199));
        lblMenuTitulo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        lblMenuTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelLateral.add(lblMenuTitulo);
        panelLateral.add(Box.createRigidArea(new Dimension(0, 10)));
        String[] opcionesFuncionales = {
            "🔐 Cerrar Sesión",
            "➕ Registrar Producto",
            "📊 Generar Reporte Stock",
            "📋 Ver Inventario"
        };
        
        String[] opcionesDiseno = {
            "👥 Gestión Usuarios",
            "⚙️ Configuración",
            "📈 Estadísticas"
        };
        JLabel lblFuncional = new JLabel("FUNCIONAL");
        lblFuncional.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblFuncional.setForeground(new Color(149, 165, 166));
        lblFuncional.setBorder(BorderFactory.createEmptyBorder(10, 20, 5, 20));
        lblFuncional.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelLateral.add(lblFuncional);
        
        for (String opcion : opcionesFuncionales) {
            JButton btnOpcion = crearBotonMenu(opcion);
            
            if (opcion.contains("Cerrar Sesión")) {
                btnOpcion.addActionListener(e -> cerrarSesion());
            } else if (opcion.contains("Registrar Producto")) {
                btnOpcion.addActionListener(e -> mostrarRegistrarProducto());
            } else if (opcion.contains("Generar Reporte Stock")) {
                btnOpcion.addActionListener(e -> mostrarReporteStock());
            } else if (opcion.contains("Ver Inventario")) {
                btnOpcion.addActionListener(e -> mostrarInventario());
            }
            
            panelLateral.add(btnOpcion);
        }
        
        panelLateral.add(Box.createRigidArea(new Dimension(0, 20)));
        JLabel lblDiseno = new JLabel("DISEÑO");
        lblDiseno.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblDiseno.setForeground(new Color(149, 165, 166));
        lblDiseno.setBorder(BorderFactory.createEmptyBorder(10, 20, 5, 20));
        lblDiseno.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelLateral.add(lblDiseno);
        
        for (String opcion : opcionesDiseno) {
            JButton btnOpcion = crearBotonMenu(opcion);
            btnOpcion.addActionListener(e -> mostrarInterfazDiseno(opcion));
            panelLateral.add(btnOpcion);
        }
        
        panelLateral.add(Box.createVerticalGlue());
        JLabel lblVersion = new JLabel("v1.0.0 © 2024");
        lblVersion.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblVersion.setForeground(new Color(149, 165, 166));
        lblVersion.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        lblVersion.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelLateral.add(lblVersion);
        cardLayout = new CardLayout();
        panelContenido = new JPanel(cardLayout);
        panelContenido.setBackground(Color.WHITE);
        JPanel panelInicio = crearPanelInicio();
        panelContenido.add(panelInicio, "INICIO");
        JPanel panelRegistrarProducto = crearPanelRegistrarProducto();
        panelContenido.add(panelRegistrarProducto, "REGISTRAR_PRODUCTO");
        JPanel panelReporteStock = crearPanelReporteStock();
        panelContenido.add(panelReporteStock, "REPORTE_STOCK");
        JPanel panelInventario = crearPanelInventario();
        panelContenido.add(panelInventario, "INVENTARIO");
        JPanel panelGestionUsuarios = crearPanelGestionUsuarios();
        panelContenido.add(panelGestionUsuarios, "GESTION_USUARIOS");
        JPanel panelConfiguracion = crearPanelConfiguracion();
        panelContenido.add(panelConfiguracion, "CONFIGURACION");
        JPanel panelEstadisticas = crearPanelEstadisticas();
        panelContenido.add(panelEstadisticas, "ESTADISTICAS");
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(panelLateral, BorderLayout.WEST);
        panelPrincipal.add(panelContenido, BorderLayout.CENTER);
        
        setContentPane(panelPrincipal);
    }
    
    private JButton crearBotonMenu(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        boton.setForeground(Color.WHITE);
        boton.setBackground(new Color(52, 73, 94));
        boton.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        boton.setHorizontalAlignment(SwingConstants.LEFT);
        boton.setAlignmentX(Component.LEFT_ALIGNMENT);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setMaximumSize(new Dimension(250, 45));
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(44, 62, 80));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(52, 73, 94));
            }
        });
        
        return boton;
    }
    
    private JPanel crearPanelInicio() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 245));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        
        JLabel lblBienvenida = new JLabel("<html><div style='text-align: center;'>"
                + "<h1 style='color: #2c3e50; margin-bottom: 30px;'>¡Bienvenido al Sistema de Inventario!</h1>"
                + "<div style='display: flex; justify-content: center; gap: 20px;'>"
                + "<div style='background: white; padding: 25px; border-radius: 10px; width: 300px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);'>"
                + "<h3 style='color: #3498db; margin-top: 0;'>🏭 Inventario</h3>"
                + "<p>Gestiona todos los productos del almacén</p>"
                + "</div>"
                + "<div style='background: white; padding: 25px; border-radius: 10px; width: 300px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);'>"
                + "<h3 style='color: #2ecc71; margin-top: 0;'>➕ Nuevos Productos</h3>"
                + "<p>Registra nuevos productos en el sistema</p>"
                + "</div>"
                + "<div style='background: white; padding: 25px; border-radius: 10px; width: 300px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);'>"
                + "<h3 style='color: #9b59b6; margin-top: 0;'>📊 Reportes</h3>"
                + "<p>Genera reportes de stock y estadísticas</p>"
                + "</div>"
                + "</div>"
                + "<div style='margin-top: 40px; color: #7f8c8d;'>"
                + "<p>Seleccione una opción del menú lateral para comenzar</p>"
                + "</div>"
                + "</div></html>");
        lblBienvenida.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblBienvenida, gbc);
        
        return panel;
    }
    private JPanel crearPanelRegistrarProducto() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 245));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 20, 10, 20);
        JLabel lblTitulo = new JLabel("➕ REGISTRAR NUEVO PRODUCTO");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(44, 62, 80));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(lblTitulo, gbc);
        
        JLabel lblDescTitulo = new JLabel("Complete el formulario para agregar un nuevo producto al inventario");
        lblDescTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblDescTitulo.setForeground(new Color(127, 140, 141));
        gbc.gridy = 1;
        panel.add(lblDescTitulo, gbc);
        gbc.gridwidth = 1;
        gbc.insets = new Insets(15, 20, 5, 20);
        
        JLabel lblNombre = new JLabel("Nombre del Producto:");
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 12));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(lblNombre, gbc);
        
        JTextField txtNombre = new JTextField(20);
        txtNombre.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtNombre.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        txtNombre.setBackground(new Color(248, 248, 248));
        gbc.gridx = 1;
        panel.add(txtNombre, gbc);
        
        JLabel lblDescripcion = new JLabel("Descripción:");
        lblDescripcion.setFont(new Font("Segoe UI", Font.BOLD, 12));
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(lblDescripcion, gbc);
        
        JTextField txtDescripcion = new JTextField(20);
        txtDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtDescripcion.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        txtDescripcion.setBackground(new Color(248, 248, 248));
        gbc.gridx = 1;
        panel.add(txtDescripcion, gbc);
        
        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setFont(new Font("Segoe UI", Font.BOLD, 12));
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(lblPrecio, gbc);
        
        JSpinner spinnerPrecio = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 10000.0, 0.01));
        ((JSpinner.DefaultEditor) spinnerPrecio.getEditor()).getTextField().setFont(new Font("Segoe UI", Font.PLAIN, 14));
        ((JSpinner.DefaultEditor) spinnerPrecio.getEditor()).getTextField().setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        gbc.gridx = 1;
        panel.add(spinnerPrecio, gbc);
        
        JLabel lblCantidad = new JLabel("Cantidad Inicial:");
        lblCantidad.setFont(new Font("Segoe UI", Font.BOLD, 12));
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(lblCantidad, gbc);
        
        JSpinner spinnerCantidad = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
        ((JSpinner.DefaultEditor) spinnerCantidad.getEditor()).getTextField().setFont(new Font("Segoe UI", Font.PLAIN, 14));
        ((JSpinner.DefaultEditor) spinnerCantidad.getEditor()).getTextField().setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        gbc.gridx = 1;
        panel.add(spinnerCantidad, gbc);
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panelBotones.setBackground(new Color(245, 245, 245));
        
        JButton btnRegistrar = new JButton("📝 REGISTRAR PRODUCTO");
        btnRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setBackground(new Color(46, 204, 113));
        btnRegistrar.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));
        btnRegistrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegistrar.addActionListener(e -> {
            try {
                String nombre = txtNombre.getText().trim();
                String descripcion = txtDescripcion.getText().trim();
                double precio = (Double) spinnerPrecio.getValue();
                int cantidad = (Integer) spinnerCantidad.getValue();
                if (nombre.isEmpty()) {
                    JOptionPane.showMessageDialog(this, 
                        "El nombre del producto no puede estar vacío", 
                        "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (precio < 0) {
                    JOptionPane.showMessageDialog(this, 
                        "El precio no puede ser negativo", 
                        "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (cantidad < 0) {
                    JOptionPane.showMessageDialog(this, 
                        "La cantidad no puede ser negativa", 
                        "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Producto nuevoProducto = servicioProducto.registrarProducto(nombre, descripcion, precio, cantidad);
                
                String mensaje = "✅ Producto registrado exitosamente:\n" +
                                "ID: " + nuevoProducto.getId() + "\n" +
                                "Nombre: " + nombre + "\n" +
                                "Descripción: " + descripcion + "\n" +
                                "Precio: $" + String.format("%.2f", precio) + "\n" +
                                "Cantidad: " + cantidad;
                
                JOptionPane.showMessageDialog(this,
                    mensaje,
                    "Registro Exitoso",
                    JOptionPane.INFORMATION_MESSAGE);
                txtNombre.setText("");
                txtDescripcion.setText("");
                spinnerPrecio.setValue(0.0);
                spinnerCantidad.setValue(0);
                txtNombre.requestFocus();
                actualizarTablaInventario();
                
            } catch (ProductoException ex) {
                JOptionPane.showMessageDialog(this,
                    "❌ " + ex.getMessage(),
                    "Error al Registrar",
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        
        JButton btnLimpiar = new JButton("🧹 LIMPIAR FORMULARIO");
        btnLimpiar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnLimpiar.setForeground(Color.WHITE);
        btnLimpiar.setBackground(new Color(52, 152, 219));
        btnLimpiar.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));
        btnLimpiar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLimpiar.addActionListener(e -> {
            txtNombre.setText("");
            spinnerCantidad.setValue(0);
            txtNombre.requestFocus();
            JOptionPane.showMessageDialog(this,
                "Formulario limpiado exitosamente",
                "Formulario Limpio",
                JOptionPane.INFORMATION_MESSAGE);
        });
        
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnLimpiar);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(30, 20, 20, 20);
        panel.add(panelBotones, gbc);
        
        return panel;
    }
    private JPanel crearPanelReporteStock() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 245));
        JPanel panelSuperior = new JPanel(new GridBagLayout());
        panelSuperior.setBackground(new Color(245, 245, 245));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel lblTitulo = new JLabel("📊 REPORTE DE STOCK BAJO");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(44, 62, 80));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelSuperior.add(lblTitulo, gbc);
        
        JLabel lblDescripcion = new JLabel("Productos con menos de 5 unidades en inventario");
        lblDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblDescripcion.setForeground(new Color(127, 140, 141));
        gbc.gridy = 1;
        panelSuperior.add(lblDescripcion, gbc);
        JButton btnGenerarReporte = new JButton("🖨️ GENERAR REPORTE");
        btnGenerarReporte.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnGenerarReporte.setForeground(Color.WHITE);
        btnGenerarReporte.setBackground(new Color(155, 89, 182));
        btnGenerarReporte.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        btnGenerarReporte.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGenerarReporte.addActionListener(e -> {
            servicioProducto.imprimirReporteStockBajo(5);
            
            JOptionPane.showMessageDialog(this,
                "✅ Reporte generado exitosamente.\n" +
                "📄 Consulte la consola de Eclipse para ver los resultados.",
                "Reporte Generado",
                JOptionPane.INFORMATION_MESSAGE);
        });
        
        gbc.gridy = 2;
        panelSuperior.add(btnGenerarReporte, gbc);
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setBackground(Color.WHITE);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JTextArea txtResultados = new JTextArea();
        txtResultados.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtResultados.setEditable(false);
        txtResultados.setBackground(new Color(248, 249, 250));
        txtResultados.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1));
        ArrayList<Producto> productosBajo = servicioProducto.obtenerProductosStockBajo(5);
        StringBuilder sb = new StringBuilder();
        sb.append("REPORTE DE STOCK BAJO - ").append(new java.util.Date()).append("\n");
        sb.append("==================================================\n");
        sb.append(String.format("%-8s %-30s %-10s\n", "ID", "NOMBRE", "CANTIDAD"));
        sb.append("--------------------------------------------------\n");
        
        if (productosBajo.isEmpty()) {
            sb.append("No hay productos con stock bajo.\n");
        } else {
            for (Producto p : productosBajo) {
                sb.append(String.format("%-8d %-30s %-10d\n", 
                    p.getId(), 
                    p.getNombre(), 
                    p.getCantidad()));
            }
            sb.append("--------------------------------------------------\n");
            sb.append("Total de productos con stock bajo: ").append(productosBajo.size()).append("\n");
        }
        sb.append("==================================================\n");
        
        txtResultados.setText(sb.toString());
        
        JScrollPane scrollResultados = new JScrollPane(txtResultados);
        panelCentral.add(new JLabel("📋 Vista previa del reporte:"), BorderLayout.NORTH);
        panelCentral.add(scrollResultados, BorderLayout.CENTER);
        
        panel.add(panelSuperior, BorderLayout.NORTH);
        panel.add(panelCentral, BorderLayout.CENTER);
        
        return panel;
    }
    private JPanel crearPanelInventario() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 245));
        JPanel panelEncabezado = new JPanel(new BorderLayout());
        panelEncabezado.setBackground(Color.WHITE);
        panelEncabezado.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel lblTitulo = new JLabel("📋 INVENTARIO DE PRODUCTOS");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(44, 62, 80));
        JButton btnActualizar = new JButton("🔄 ACTUALIZAR");
        btnActualizar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.setBackground(new Color(52, 152, 219));
        btnActualizar.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btnActualizar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnActualizar.addActionListener(e -> actualizarTablaInventario());
        JButton btnEditar = new JButton("✏️ EDITAR");
        btnEditar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnEditar.setForeground(Color.WHITE);
        btnEditar.setBackground(new Color(255, 193, 7));
        btnEditar.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btnEditar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEditar.addActionListener(e -> editarProductoSeleccionado());
        JButton btnEliminar = new JButton("🗑️ ELIMINAR");
        btnEliminar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setBackground(new Color(220, 53, 69));
        btnEliminar.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btnEliminar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEliminar.addActionListener(e -> eliminarProductoSeleccionado());
        JButton btnExportar = new JButton("📤 EXPORTAR");
        btnExportar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnExportar.setForeground(Color.WHITE);
        btnExportar.setBackground(new Color(46, 204, 113));
        btnExportar.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btnExportar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnExportar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                "📄 Exportando inventario...\n" +
                "(Función de exportación en desarrollo)",
                "Exportar Inventario",
                JOptionPane.INFORMATION_MESSAGE);
        });
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelBotones.setBackground(Color.WHITE);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnExportar);
        panelBotones.add(btnActualizar);
        
        panelEncabezado.add(lblTitulo, BorderLayout.WEST);
        panelEncabezado.add(panelBotones, BorderLayout.EAST);
        String[] columnas = {"ID", "Nombre", "Descripción", "Precio", "Cantidad", "Estado"};
        modeloInventario = new DefaultTableModel(columnas, 0);
        
        tablaInventario = new JTable(modeloInventario);
        tablaInventario.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaInventario.setRowHeight(35);
        tablaInventario.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tablaInventario.getTableHeader().setBackground(new Color(52, 73, 94));
        tablaInventario.getTableHeader().setForeground(Color.WHITE);
        tablaInventario.setSelectionBackground(new Color(220, 237, 200));
        tablaInventario.setSelectionForeground(Color.BLACK);
        tablaInventario.getColumnModel().getColumn(3).setCellRenderer(new EstadoCellRenderer());
        
        JScrollPane scrollPane = new JScrollPane(tablaInventario);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1));
        JPanel panelEstadisticas = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        panelEstadisticas.setBackground(Color.WHITE);
        panelEstadisticas.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        actualizarTablaInventario();
        actualizarEstadisticas(panelEstadisticas);
        
        panel.add(panelEncabezado, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(panelEstadisticas, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void actualizarTablaInventario() {
        modeloInventario.setRowCount(0);
        ArrayList<Producto> productos = servicioProducto.obtenerTodosLosProductos();
        
        for (Producto producto : productos) {
            String estado;
            if (producto.getCantidad() == 0) {
                estado = "Agotado";
            } else if (producto.getCantidad() < 5) {
                estado = "Stock Bajo";
            } else {
                estado = "Disponible";
            }
            
            modeloInventario.addRow(new Object[]{
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getCantidad(),
                estado
            });
        }
    }
    
    private void actualizarEstadisticas(JPanel panelEstadisticas) {
        panelEstadisticas.removeAll();
        
        ArrayList<Producto> productos = servicioProducto.obtenerTodosLosProductos();
        int total = productos.size();
        int bajo = 0;
        int agotado = 0;
        
        for (Producto p : productos) {
            if (p.getCantidad() == 0) agotado++;
            else if (p.getCantidad() < 5) bajo++;
        }
        
        JLabel lblTotal = new JLabel("📦 Total: " + total + " productos");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTotal.setForeground(new Color(52, 73, 94));
        
        JLabel lblBajo = new JLabel("⚠️ Stock bajo: " + bajo);
        lblBajo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblBajo.setForeground(new Color(243, 156, 18));
        
        JLabel lblAgotado = new JLabel("❌ Agotados: " + agotado);
        lblAgotado.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblAgotado.setForeground(new Color(231, 76, 60));
        
        panelEstadisticas.add(lblTotal);
        panelEstadisticas.add(Box.createHorizontalStrut(20));
        panelEstadisticas.add(lblBajo);
        panelEstadisticas.add(Box.createHorizontalStrut(20));
        panelEstadisticas.add(lblAgotado);
        
        panelEstadisticas.revalidate();
        panelEstadisticas.repaint();
    }
    class EstadoCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            String estado = (String) value;
            switch (estado) {
                case "Agotado":
                    c.setForeground(new Color(231, 76, 60));
                    c.setFont(c.getFont().deriveFont(Font.BOLD));
                    break;
                case "Stock Bajo":
                    c.setForeground(new Color(243, 156, 18));
                    c.setFont(c.getFont().deriveFont(Font.BOLD));
                    break;
                case "Disponible":
                    c.setForeground(new Color(46, 204, 113));
                    break;
            }
            
            return c;
        }
    }
    private JPanel crearPanelGestionUsuarios() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 245));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        
        JLabel lblTitulo = new JLabel("👥 GESTIÓN DE USUARIOS");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(44, 62, 80));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblTitulo, gbc);
        
        JLabel lblDescripcion = new JLabel("<html><div style='text-align: center;'>"
                + "<p style='color: #7f8c8d;'>Esta interfaz está en modo diseño</p>"
                + "<p style='color: #95a5a6; font-size: 12px;'>Aquí se gestionarían los usuarios del sistema</p>"
                + "</div></html>");
        lblDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridy = 1;
        panel.add(lblDescripcion, gbc);
        JPanel panelUsuarios = new JPanel(new GridLayout(1, 3, 20, 0));
        panelUsuarios.setBackground(new Color(245, 245, 245));
        panelUsuarios.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        
        String[] usuarios = {"Administrador", "Almacenista", "Supervisor"};
        String[] colores = {"#3498db", "#2ecc71", "#9b59b6"};
        
        for (int i = 0; i < 3; i++) {
            final int index = i; // Variable final para usar en la clase interna
            JPanel card = new JPanel(new BorderLayout());
            card.setBackground(Color.WHITE);
            card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.decode(colores[i]), 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
            ));
            card.setPreferredSize(new Dimension(200, 150));
            
            JLabel lblUsuario = new JLabel(usuarios[i]);
            lblUsuario.setFont(new Font("Segoe UI", Font.BOLD, 16));
            lblUsuario.setForeground(Color.decode(colores[i]));
            lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);
            
            JLabel lblIcono = new JLabel("👤");
            lblIcono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
            lblIcono.setHorizontalAlignment(SwingConstants.CENTER);
            
            card.add(lblIcono, BorderLayout.CENTER);
            card.add(lblUsuario, BorderLayout.SOUTH);
            panelUsuarios.add(card);
        }
        
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(panelUsuarios, gbc);
        
        return panel;
    }
    private JPanel crearPanelConfiguracion() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 245));
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBackground(Color.WHITE);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel lblTitulo = new JLabel("⚙️ CONFIGURACIÓN DEL SISTEMA");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(44, 62, 80));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panelPrincipal.add(lblTitulo, gbc);
        String[] opciones = {
            "Configuración General",
            "Preferencias de Usuario", 
            "Opciones de Seguridad",
            "Configuración de Reportes",
            "Backup y Restauración"
        };
        
        for (int i = 0; i < opciones.length; i++) {
            JLabel lblOpcion = new JLabel("• " + opciones[i]);
            lblOpcion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            lblOpcion.setForeground(new Color(52, 73, 94));
            gbc.gridx = 0;
            gbc.gridy = i + 1;
            gbc.gridwidth = 1;
            panelPrincipal.add(lblOpcion, gbc);
            
            JButton btnConfigurar = new JButton("Configurar");
            btnConfigurar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            btnConfigurar.setForeground(Color.WHITE);
            btnConfigurar.setBackground(new Color(52, 152, 219));
            btnConfigurar.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
            btnConfigurar.setEnabled(false);
            gbc.gridx = 1;
            panelPrincipal.add(btnConfigurar, gbc);
        }
        JLabel lblNota = new JLabel("<html><div style='text-align: center; color: #7f8c8d; margin-top: 30px;'>"
                + "<i>Esta sección está en modo diseño. Las funcionalidades estarán disponibles próximamente.</i>"
                + "</div></html>");
        gbc.gridx = 0;
        gbc.gridy = opciones.length + 2;
        gbc.gridwidth = 2;
        panelPrincipal.add(lblNota, gbc);
        
        panel.add(panelPrincipal, BorderLayout.CENTER);
        
        return panel;
    }
    private JPanel crearPanelEstadisticas() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 245));
        JPanel panelEncabezado = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelEncabezado.setBackground(new Color(44, 62, 80));
        panelEncabezado.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        JLabel lblTitulo = new JLabel("📈 ESTADÍSTICAS DEL SISTEMA");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        panelEncabezado.add(lblTitulo);
        JPanel panelGraficos = new JPanel(new GridLayout(2, 2, 20, 20));
        panelGraficos.setBackground(new Color(245, 245, 245));
        panelGraficos.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        
        String[] graficos = {
            "📊 Productos por Categoría",
            "📈 Ventas Mensuales", 
            "📉 Stock vs Tiempo",
            "👥 Actividad de Usuarios"
        };
        String[] coloresGraficos = {"#3498db", "#2ecc71", "#e74c3c", "#9b59b6"};
        
        for (int i = 0; i < 4; i++) {
            final int index = i; // Variable final para usar en la clase interna
            final Color color = Color.decode(coloresGraficos[i]);
            
            JPanel cardGrafico = new JPanel(new BorderLayout());
            cardGrafico.setBackground(Color.WHITE);
            cardGrafico.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
            ));
            
            JLabel lblGrafico = new JLabel(graficos[i]);
            lblGrafico.setFont(new Font("Segoe UI", Font.BOLD, 14));
            lblGrafico.setForeground(color);
            lblGrafico.setHorizontalAlignment(SwingConstants.CENTER);
            JPanel panelSimulacion = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(color);
                    g2d.fillRect(20, 20, 50, 50);
                    g2d.setColor(Color.BLACK);
                    g2d.drawString("Gráfico", 25, 85);
                }
            };
            panelSimulacion.setPreferredSize(new Dimension(100, 100));
            panelSimulacion.setBackground(new Color(248, 248, 248));
            
            cardGrafico.add(lblGrafico, BorderLayout.NORTH);
            cardGrafico.add(panelSimulacion, BorderLayout.CENTER);
            
            panelGraficos.add(cardGrafico);
        }
        
        panel.add(panelEncabezado, BorderLayout.NORTH);
        panel.add(panelGraficos, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void mostrarRegistrarProducto() {
        cardLayout.show(panelContenido, "REGISTRAR_PRODUCTO");
    }
    
    private void mostrarReporteStock() {
        cardLayout.show(panelContenido, "REPORTE_STOCK");
    }
    
    private void mostrarInventario() {
        cardLayout.show(panelContenido, "INVENTARIO");
    }
    
    private void mostrarInterfazDiseno(String opcion) {
        if (opcion.contains("Gestión Usuarios")) {
            cardLayout.show(panelContenido, "GESTION_USUARIOS");
        } else if (opcion.contains("Configuración")) {
            cardLayout.show(panelContenido, "CONFIGURACION");
        } else if (opcion.contains("Estadísticas")) {
            cardLayout.show(panelContenido, "ESTADISTICAS");
        }
    }
    
    private void editarProductoSeleccionado() {
        int filaSeleccionada = tablaInventario.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione un producto de la tabla para editar.",
                "Producto no seleccionado",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int id = (Integer) modeloInventario.getValueAt(filaSeleccionada, 0);
        String nombreActual = (String) modeloInventario.getValueAt(filaSeleccionada, 1);
        String descripcionActual = (String) modeloInventario.getValueAt(filaSeleccionada, 2);
        double precioActual = (Double) modeloInventario.getValueAt(filaSeleccionada, 3);
        int cantidadActual = (Integer) modeloInventario.getValueAt(filaSeleccionada, 4);
        JTextField txtNombre = new JTextField(nombreActual);
        JTextField txtDescripcion = new JTextField(descripcionActual);
        JSpinner spinnerPrecio = new JSpinner(new SpinnerNumberModel(precioActual, 0.0, 10000.0, 0.01));
        JSpinner spinnerCantidad = new JSpinner(new SpinnerNumberModel(cantidadActual, 0, 10000, 1));
        
        JPanel panelEdicion = new JPanel(new GridLayout(4, 2, 10, 10));
        panelEdicion.add(new JLabel("Nombre:"));
        panelEdicion.add(txtNombre);
        panelEdicion.add(new JLabel("Descripción:"));
        panelEdicion.add(txtDescripcion);
        panelEdicion.add(new JLabel("Precio:"));
        panelEdicion.add(spinnerPrecio);
        panelEdicion.add(new JLabel("Cantidad:"));
        panelEdicion.add(spinnerCantidad);
        
        int resultado = JOptionPane.showConfirmDialog(this, panelEdicion,
            "Editar Producto (ID: " + id + ")", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (resultado == JOptionPane.OK_OPTION) {
            try {
                String nuevoNombre = txtNombre.getText().trim();
                String nuevaDescripcion = txtDescripcion.getText().trim();
                double nuevoPrecio = (Double) spinnerPrecio.getValue();
                int nuevaCantidad = (Integer) spinnerCantidad.getValue();
                
                if (nuevoNombre.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                        "El nombre del producto no puede estar vacío",
                        "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (nuevoPrecio < 0) {
                    JOptionPane.showMessageDialog(this,
                        "El precio no puede ser negativo",
                        "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (nuevaCantidad < 0) {
                    JOptionPane.showMessageDialog(this,
                        "La cantidad no puede ser negativa",
                        "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                servicioProducto.actualizarProducto(id, nuevoNombre, nuevaDescripcion, nuevoPrecio, nuevaCantidad);
                
                JOptionPane.showMessageDialog(this,
                    "✅ Producto actualizado exitosamente:\n" +
                    "ID: " + id + "\n" +
                    "Nombre: " + nuevoNombre + "\n" +
                    "Descripción: " + nuevaDescripcion + "\n" +
                    "Precio: $" + String.format("%.2f", nuevoPrecio) + "\n" +
                    "Cantidad: " + nuevaCantidad,
                    "Actualización Exitosa",
                    JOptionPane.INFORMATION_MESSAGE);
                
                actualizarTablaInventario();
                
            } catch (ProductoException ex) {
                JOptionPane.showMessageDialog(this,
                    "❌ " + ex.getMessage(),
                    "Error al Actualizar",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void eliminarProductoSeleccionado() {
        int filaSeleccionada = tablaInventario.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione un producto de la tabla para eliminar.",
                "Producto no seleccionado",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int id = (Integer) modeloInventario.getValueAt(filaSeleccionada, 0);
        String nombre = (String) modeloInventario.getValueAt(filaSeleccionada, 1);
        
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro que desea eliminar el producto?\n\n" +
            "ID: " + id + "\n" +
            "Nombre: " + nombre + "\n\n" +
            "Esta acción no se puede deshacer.",
            "Confirmar Eliminación",
            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                servicioProducto.eliminarProducto(id);
                
                JOptionPane.showMessageDialog(this,
                    "✅ Producto eliminado exitosamente:\n" +
                    "ID: " + id + "\n" +
                    "Nombre: " + nombre,
                    "Eliminación Exitosa",
                    JOptionPane.INFORMATION_MESSAGE);
                
                actualizarTablaInventario();
                
            } catch (ProductoException ex) {
                JOptionPane.showMessageDialog(this,
                    "❌ " + ex.getMessage(),
                    "Error al Eliminar",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void cerrarSesion() {
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro que desea cerrar sesión?",
            "Confirmar Cierre de Sesión",
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            dispose();
            VentanaLogin login = new VentanaLogin();
            login.setVisible(true);
        }
    }
}
