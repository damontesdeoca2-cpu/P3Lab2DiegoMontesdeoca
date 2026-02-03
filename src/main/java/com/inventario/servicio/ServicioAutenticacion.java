package com.inventario.servicio;

import com.inventario.excepciones.LoginException;
import com.inventario.modelo.Usuario;
import java.util.ArrayList;

public class ServicioAutenticacion {
    private ArrayList<Usuario> usuarios;

    public ServicioAutenticacion() {
        usuarios = new ArrayList<>();

        // Usuarios de prueba
        usuarios.add(new Usuario("admin", "admin123", "Administrador"));
        usuarios.add(new Usuario("usuario", "user123", "Usuario Regular"));
    }

    public Usuario autenticar(String username, String password) throws LoginException {
        if (username == null || username.trim().isEmpty()) {
            throw new LoginException("El nombre de usuario no puede estar vacío");
        }

        if (password == null || password.trim().isEmpty()) {
            throw new LoginException("La contraseña no puede estar vacía");
        }

        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username.trim())) {
                if (usuario.getPassword().equals(password)) {
                    return usuario;
                } else {
                    throw new LoginException("Contraseña incorrecta");
                }
            }
        }

        throw new LoginException("Usuario no encontrado");
    }
}