package model;

public class Inventario {
    private int id_inventario;
    private int cantidad;
    private int id_producto;
    private String categorizacionABC;

    private String nombreProducto; // NUEVO campo para el nombre

    // Getters y Setters
    public int getId_inventario() {
        return id_inventario;
    }

    public void setId_inventario(int id_inventario) {
        this.id_inventario = id_inventario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getCategorizacionABC() {
        return categorizacionABC;
    }

    public void setCategorizacionABC(String categorizacionABC) {
        this.categorizacionABC = categorizacionABC;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }
}
