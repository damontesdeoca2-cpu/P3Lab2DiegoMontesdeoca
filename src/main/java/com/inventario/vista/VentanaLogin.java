package com.inventario.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.inventario.excepciones.LoginException;
import com.inventario.modelo.Usuario;
import com.inventario.servicio.ServicioAutenticacion;

public class VentanaLogin extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnSalir;
    private ServicioAutenticacion servicioAutenticacion;

    public VentanaLogin() {
        this.servicioAutenticacion = new ServicioAutenticacion();
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Sistema de Inventario - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridBagLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel lblTitulo = new JLabel("🏢 SISTEMA DE INVENTARIO");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelPrincipal.add(lblTitulo, gbc);

        JLabel lblUsuario = new JLabel("👤 Usuario:");
        lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panelPrincipal.add(lblUsuario, gbc);

        txtUsuario = new JTextField(20);
        txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 1;
        panelPrincipal.add(txtUsuario, gbc);

        JLabel lblPassword = new JLabel("🔒 Contraseña:");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelPrincipal.add(lblPassword, gbc);

        txtPassword = new JPasswordField(20);
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 2;
        panelPrincipal.add(txtPassword, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout());

        btnLogin = new JButton("🚀 Iniciar Sesión");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setBackground(new Color(46, 204, 113));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarLogin();
            }
        });
        panelBotones.add(btnLogin);

        btnSalir = new JButton("❌ Salir");
        btnSalir.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSalir.setBackground(new Color(231, 76, 60));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFocusPainted(false);
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        panelBotones.add(btnSalir);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelPrincipal.add(panelBotones, gbc);

        add(panelPrincipal);
    }

    private void realizarLogin() {
        String usuario = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword());

        try {
            Usuario usuarioAutenticado = servicioAutenticacion.autenticar(usuario, password);

            JOptionPane.showMessageDialog(this,
                "¡Bienvenido " + usuarioAutenticado.getNombre() + "!",
                "Login Exitoso",
                JOptionPane.INFORMATION_MESSAGE);

            VentanaMenuPrincipal ventanaPrincipal = new VentanaMenuPrincipal(usuarioAutenticado.getNombre());
            ventanaPrincipal.setVisible(true);

            this.dispose();

        } catch (LoginException e) {
            JOptionPane.showMessageDialog(this,
                e.getMessage(),
                "Error de Autenticación",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}