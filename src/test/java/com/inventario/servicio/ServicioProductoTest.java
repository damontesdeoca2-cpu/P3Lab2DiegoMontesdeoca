package com.inventario.servicio;

import com.inventario.excepciones.ProductoException;
import com.inventario.modelo.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class ServicioProductoTest {

    private ServicioProducto servicio;

    @BeforeEach
    void setUp() {
        servicio = new ServicioProducto();
    }

    @Test
    void testRegistrarProductoExitoso() throws ProductoException {
        Producto producto = servicio.registrarProducto("Producto Test", "Descripción de prueba", 50.0, 5);

        assertNotNull(producto);
        assertEquals("Producto Test", producto.getNombre());
        assertEquals("Descripción de prueba", producto.getDescripcion());
        assertEquals(50.0, producto.getPrecio());
        assertEquals(5, producto.getCantidad());
        assertTrue(producto.getId() > 0);
    }

    @Test
    void testRegistrarProductoNombreVacio() {
        ProductoException exception = assertThrows(ProductoException.class, () -> {
            servicio.registrarProducto("", "Descripción", 10.0, 5);
        });
        assertTrue(exception.getMessage().contains("vacío"));
    }

    @Test
    void testRegistrarProductoNombreNulo() {
        ProductoException exception = assertThrows(ProductoException.class, () -> {
            servicio.registrarProducto(null, "Descripción", 10.0, 5);
        });
        assertTrue(exception.getMessage().contains("vacío"));
    }

    @Test
    void testRegistrarProductoCantidadNegativa() {
        ProductoException exception = assertThrows(ProductoException.class, () -> {
            servicio.registrarProducto("Producto Test", "Descripción", 10.0, -1);
        });
        assertTrue(exception.getMessage().contains("negativa"));
    }

    @Test
    void testRegistrarProductoPrecioNegativo() {
        ProductoException exception = assertThrows(ProductoException.class, () -> {
            servicio.registrarProducto("Producto Test", "Descripción", -10.0, 5);
        });
        assertTrue(exception.getMessage().contains("negativo"));
    }

    @Test
    void testRegistrarProductoDuplicado() throws ProductoException {
        servicio.registrarProducto("Producto Único", "Descripción única", 20.0, 10);

        ProductoException exception = assertThrows(ProductoException.class, () -> {
            servicio.registrarProducto("Producto Único", "Otra descripción", 15.0, 5);
        });
        assertTrue(exception.getMessage().contains("Ya existe"));
    }

    @Test
    void testObtenerTodosLosProductos() throws ProductoException {
        servicio.registrarProducto("Producto 1", "Descripcion Producto 1", 10.0, 10);
        servicio.registrarProducto("Producto 2", "Descripcion Producto 2", 10.0, 20);

        ArrayList<Producto> productos = servicio.obtenerTodosLosProductos();

        assertEquals(2, productos.size());
    }

    @Test
    void testBuscarPorIdExistente() throws ProductoException {
        Producto registrado = servicio.registrarProducto("Producto Test", "Descripcion Producto Test", 10.0, 5);
        Producto encontrado = servicio.buscarPorId(registrado.getId());

        assertEquals(registrado.getId(), encontrado.getId());
        assertEquals(registrado.getNombre(), encontrado.getNombre());
    }

    @Test
    void testBuscarPorIdInexistente() {
        ProductoException exception = assertThrows(ProductoException.class, () -> {
            servicio.buscarPorId(999);
        });
        assertTrue(exception.getMessage().contains("no encontrado"));
    }

    @Test
    void testActualizarCantidadExitosa() throws ProductoException {
        Producto registrado = servicio.registrarProducto("Producto Test", "Descripcion Producto Test", 10.0, 5);
        servicio.actualizarCantidad(registrado.getId(), 15);

        Producto actualizado = servicio.buscarPorId(registrado.getId());
        assertEquals(15, actualizado.getCantidad());
    }

    @Test
    void testActualizarCantidadNegativa() throws ProductoException {
        Producto registrado = servicio.registrarProducto("Producto Test", "Descripcion Producto Test", 10.0, 5);

        ProductoException exception = assertThrows(ProductoException.class, () -> {
            servicio.actualizarCantidad(registrado.getId(), -1);
        });
        assertTrue(exception.getMessage().contains("negativa"));
    }

    @Test
    void testActualizarProductoCompleto() throws ProductoException {
        Producto registrado = servicio.registrarProducto("Producto Original", "Descripción original", 100.0, 5);
        servicio.actualizarProducto(registrado.getId(), "Producto Actualizado", "Descripción actualizada", 150.0, 10);

        Producto actualizado = servicio.buscarPorId(registrado.getId());
        assertEquals("Producto Actualizado", actualizado.getNombre());
        assertEquals("Descripción actualizada", actualizado.getDescripcion());
        assertEquals(150.0, actualizado.getPrecio());
        assertEquals(10, actualizado.getCantidad());
    }

    @Test
    void testEliminarProductoExistente() throws ProductoException {
        Producto registrado = servicio.registrarProducto("Producto Test", "Descripcion Producto Test", 10.0, 5);
        servicio.eliminarProducto(registrado.getId());

        ProductoException exception = assertThrows(ProductoException.class, () -> {
            servicio.buscarPorId(registrado.getId());
        });
        assertTrue(exception.getMessage().contains("no encontrado"));
    }

    @Test
    void testEliminarProductoInexistente() {
        ProductoException exception = assertThrows(ProductoException.class, () -> {
            servicio.eliminarProducto(999);
        });
        assertTrue(exception.getMessage().contains("no encontrado"));
    }

    @Test
    void testObtenerProductosStockBajo() throws ProductoException {
        servicio.registrarProducto("Producto Bajo", "Descripcion Producto Bajo", 10.0, 3);
        servicio.registrarProducto("Producto Normal", "Descripcion Producto Normal", 10.0, 10);
        servicio.registrarProducto("Producto Agotado", "Descripcion Producto Agotado", 10.0, 0);

        ArrayList<Producto> bajos = servicio.obtenerProductosStockBajo(5);

        assertEquals(2, bajos.size()); // Bajo y Agotado
    }

    @Test
    void testEstadisticas() throws ProductoException {
        servicio.registrarProducto("Producto 1", "Descripcion Producto 1", 10.0, 10);
        servicio.registrarProducto("Producto 2", "Descripcion Producto 2", 10.0, 0);
        servicio.registrarProducto("Producto 3", "Descripcion Producto 3", 10.0, 0);

        assertEquals(3, servicio.getTotalProductos());
        assertEquals(2, servicio.getProductosStockBajo(5));
        assertEquals(2, servicio.getProductosAgotados());
    }
}
