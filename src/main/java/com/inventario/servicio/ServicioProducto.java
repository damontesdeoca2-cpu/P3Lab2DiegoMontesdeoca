package com.inventario.servicio;

import com.inventario.excepciones.ProductoException;
import com.inventario.modelo.Producto;
import java.util.ArrayList;

public class ServicioProducto {
    private ArrayList<Producto> productos;
    private int siguienteId;
    
    public ServicioProducto() {
        productos = new ArrayList<>();
        siguienteId = 1;
    }
    
    public Producto registrarProducto(String nombre, String descripcion, double precio, int cantidad) throws ProductoException {
        // Validación de nombre
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ProductoException("El nombre del producto no puede estar vacío");
        }
        
        // Validación de precio no negativo
        if (precio < 0) {
            throw new ProductoException("El precio no puede ser negativo");
        }
        
        // Validación de cantidad no negativa
        if (cantidad < 0) {
            throw new ProductoException("La cantidad no puede ser negativa");
        }
        
        // Verificar si el producto ya existe (case insensitive)
        for (Producto p : productos) {
            if (p.getNombre().equalsIgnoreCase(nombre.trim())) {
                throw new ProductoException("Ya existe un producto con el nombre: " + nombre);
            }
        }
        
        Producto nuevoProducto = new Producto(siguienteId++, nombre.trim(), descripcion != null ? descripcion.trim() : "", precio, cantidad);
        productos.add(nuevoProducto);
        return nuevoProducto;
    }
    
    public ArrayList<Producto> obtenerTodosLosProductos() {
        return new ArrayList<>(productos);
    }
    
    public Producto buscarPorId(int id) throws ProductoException {
        for (Producto p : productos) {
            if (p.getId() == id) {
                return p;
            }
        }
        throw new ProductoException("Producto no encontrado con ID: " + id);
    }
    
    public void actualizarCantidad(int id, int nuevaCantidad) throws ProductoException {
        if (nuevaCantidad < 0) {
            throw new ProductoException("La cantidad no puede ser negativa");
        }
        
        Producto producto = buscarPorId(id);
        producto.setCantidad(nuevaCantidad);
    }
    
    public void actualizarProducto(int id, String nuevoNombre, String nuevaDescripcion, double nuevoPrecio, int nuevaCantidad) throws ProductoException {
        if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
            throw new ProductoException("El nombre del producto no puede estar vacío");
        }
        if (nuevoPrecio < 0) {
            throw new ProductoException("El precio no puede ser negativo");
        }
        if (nuevaCantidad < 0) {
            throw new ProductoException("La cantidad no puede ser negativa");
        }
        
        Producto producto = buscarPorId(id);
        
        // Verificar si el nuevo nombre ya existe en otro producto
        for (Producto p : productos) {
            if (p.getId() != id && p.getNombre().equalsIgnoreCase(nuevoNombre.trim())) {
                throw new ProductoException("Ya existe un producto con el nombre: " + nuevoNombre);
            }
        }
        
        producto.setNombre(nuevoNombre.trim());
        producto.setDescripcion(nuevaDescripcion != null ? nuevaDescripcion.trim() : "");
        producto.setPrecio(nuevoPrecio);
        producto.setCantidad(nuevaCantidad);
    }
    
    public void eliminarProducto(int id) throws ProductoException {
        Producto producto = buscarPorId(id);
        productos.remove(producto);
    }
    
    // Método para generar reporte de stock bajo usando Streams
    public ArrayList<Producto> obtenerProductosStockBajo(int cantidadMinima) {
        return productos.stream()
                .filter(p -> p.getCantidad() < cantidadMinima)
                .collect(java.util.stream.Collectors.toCollection(ArrayList::new));
    }
    
    // Método para imprimir reporte en consola
    public void imprimirReporteStockBajo(int cantidadMinima) {
        System.out.println("\n========================================");
        System.out.println("   REPORTE DE STOCK BAJO");
        System.out.println("   Productos con menos de " + cantidadMinima + " unidades");
        System.out.println("========================================");
        
        ArrayList<Producto> productosStockBajo = obtenerProductosStockBajo(cantidadMinima);
        
        if (productosStockBajo.isEmpty()) {
            System.out.println("No hay productos con stock bajo.");
        } else {
            System.out.println(String.format("%-10s %-30s %-10s", "ID", "NOMBRE", "CANTIDAD"));
            System.out.println("----------------------------------------");
            
            productosStockBajo.forEach(p -> {
                System.out.println(String.format("%-10d %-30s %-10d", 
                    p.getId(), 
                    p.getNombre(), 
                    p.getCantidad()));
            });
            
            System.out.println("----------------------------------------");
            System.out.println("Total de productos con stock bajo: " + productosStockBajo.size());
        }
        
        System.out.println("========================================\n");
    }
    
    // Método para obtener estadísticas
    public int getTotalProductos() {
        return productos.size();
    }
    
    public int getProductosStockBajo(int cantidadMinima) {
        return (int) productos.stream()
                .filter(p -> p.getCantidad() < cantidadMinima)
                .count();
    }
    
    public int getProductosAgotados() {
        return (int) productos.stream()
                .filter(p -> p.getCantidad() == 0)
                .count();
    }
    
    public void limpiarProductos() {
        productos.clear();
        siguienteId = 1;
    }
}