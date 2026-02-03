package com.inventario;

import javax.swing.SwingUtilities;
import com.inventario.vista.VentanaLogin;

public class Main {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                VentanaLogin ventanaLogin = new VentanaLogin();
                ventanaLogin.setVisible(true);
            }
        });
    }
}
