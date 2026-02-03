package com.inventario.servicio;

import com.inventario.excepciones.LoginException;
import com.inventario.modelo.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ServicioAutenticacionTest {

    private ServicioAutenticacion servicio;

    @BeforeEach
    void setUp() {
        servicio = new ServicioAutenticacion();
    }

    @Test
    void testAutenticacionExitosa() throws LoginException {
        Usuario usuario = servicio.autenticar("admin", "admin123");

        assertNotNull(usuario);
        assertEquals("admin", usuario.getUsername());
        assertEquals("Administrador", usuario.getNombre());
    }

    @Test
    void testAutenticacionUsuarioIncorrecto() {
        LoginException exception = assertThrows(LoginException.class, () -> {
            servicio.autenticar("usuario_inexistente", "admin123");
        });
        assertTrue(exception.getMessage().contains("no encontrado"));
    }

    @Test
    void testAutenticacionClaveIncorrecta() {
        LoginException exception = assertThrows(LoginException.class, () -> {
            servicio.autenticar("admin", "clave_incorrecta");
        });
        assertTrue(exception.getMessage().contains("incorrecta"));
    }

    @Test
    void testAutenticacionUsuarioNulo() {
        LoginException exception = assertThrows(LoginException.class, () -> {
            servicio.autenticar(null, "admin123");
        });
        assertTrue(exception.getMessage().contains("vacío"));
    }

    @Test
    void testAutenticacionUsuarioVacio() {
        LoginException exception = assertThrows(LoginException.class, () -> {
            servicio.autenticar("", "admin123");
        });
        assertTrue(exception.getMessage().contains("vacío"));
    }

    @Test
    void testAutenticacionClaveNula() {
        LoginException exception = assertThrows(LoginException.class, () -> {
            servicio.autenticar("admin", null);
        });
        assertTrue(exception.getMessage().contains("vacía"));
    }

    @Test
    void testAutenticacionClaveVacia() {
        LoginException exception = assertThrows(LoginException.class, () -> {
            servicio.autenticar("admin", "");
        });
        assertTrue(exception.getMessage().contains("vacía"));
    }


}