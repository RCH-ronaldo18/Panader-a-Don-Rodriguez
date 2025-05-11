package model;

import java.util.HashMap;
import java.util.Map;

public class Carrito {
    private Map<Integer, Integer> productos; // Almacena el ID del producto y la cantidad
    private Map<Integer, String> nombres; // Almacena los nombres de los productos
    private Map<Integer, Double> precios; // Almacena los precios de los productos

    public Carrito() {
        productos = new HashMap<>();
        nombres = new HashMap<>();
        precios = new HashMap<>();
    }

    public void agregarProducto(int idProducto, int cantidad, String nombre, double precio) {
        productos.put(idProducto, productos.getOrDefault(idProducto, 0) + cantidad);
        nombres.put(idProducto, nombre);
        precios.put(idProducto, precio);
    }

    public void eliminarProducto(int idProducto) {
        productos.remove(idProducto);
        nombres.remove(idProducto);
        precios.remove(idProducto);
    }
    public void actualizarProducto(int idProducto, int cantidad) {
        if (productos.containsKey(idProducto)) {
            if (cantidad > 0) {
                productos.put(idProducto, cantidad); // Actualiza la cantidad del producto
            } else {
                eliminarProducto(idProducto); // Si la cantidad es 0 o menor, elimina el producto del carrito
            }
        }
    }

    public Map<Integer, Integer> getProductos() {
        return productos;
    }

    public String getNombreProducto(int idProducto) {
        return nombres.get(idProducto);
    }

    public double getPrecioProducto(int idProducto) {
        return precios.get(idProducto);
    }

    public void vaciarCarrito() {
        productos.clear();
        nombres.clear();
        precios.clear();
    }
}

