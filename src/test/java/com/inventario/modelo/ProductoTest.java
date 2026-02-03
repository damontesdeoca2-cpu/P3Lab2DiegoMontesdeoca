package com.inventario.modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductoTest {

    @Test
    void testConstructorAndGetters() {
        Producto producto = new Producto(1, "Laptop", 10);

        assertEquals(1, producto.getId());
        assertEquals("Laptop", producto.getNombre());
        assertEquals(10, producto.getCantidad());
    }

    @Test
    void testSetters() {
        Producto producto = new Producto(1, "Laptop", 10);

        producto.setId(2);
        producto.setNombre("Mouse");
        producto.setCantidad(5);

        assertEquals(2, producto.getId());
        assertEquals("Mouse", producto.getNombre());
        assertEquals(5, producto.getCantidad());
    }

    @Test
    void testToString() {
        Producto producto = new Producto(1, "Laptop", 10);
        String expected = "ID: 1 | Laptop | Cantidad: 10";
        assertEquals(expected, producto.toString());
    }
}