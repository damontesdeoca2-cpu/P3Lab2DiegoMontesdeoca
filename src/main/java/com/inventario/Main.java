package com.inventario;

import javax.swing.SwingUtilities;
import com.inventario.vista.VentanaLogin;

/**
 * Clase principal del Sistema de Inventario.
 * Punto de entrada de la aplicación que inicia la interfaz gráfica.
 * 
 * @author Sistema de Inventario
 * @version 1.0.0
 */
public class Main {
    
    public static void main(String[] args) {
        System.out.println("Iniciando Sistema de Inventario...");
        
        try {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Creando ventana de login...");
                    VentanaLogin ventanaLogin = new VentanaLogin();
                    ventanaLogin.setVisible(true);
                    System.out.println("Ventana de login mostrada correctamente.");
                }
            });
        } catch (Exception e) {
            System.err.println("Error al iniciar la aplicación: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
