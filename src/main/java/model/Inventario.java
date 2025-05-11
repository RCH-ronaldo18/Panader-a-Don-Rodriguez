package model;

public class Inventario {
		private Long id_inventario;
	    private int cantidad;
	    private double precio;
	    private String fecha_ingreso;
	    private String hora_ingreso;
	    private String fecha_salida;
	    private String hora_salida;
	    private Long id_producto;
	    private String nombre;
	    private String descripcion;

	    
	    public Inventario(Long id_inventario, int cantidad, double precio, String fecha_ingreso, String hora_ingreso,
				String fecha_salida, String hora_salida, Long id_producto, String nombre, String descripcion) {
			super();
			this.id_inventario = id_inventario;
			this.cantidad = cantidad;
			this.precio = precio;
			this.fecha_ingreso = fecha_ingreso;
			this.hora_ingreso = hora_ingreso;
			this.fecha_salida = fecha_salida;
			this.hora_salida = hora_salida;
			this.id_producto = id_producto;
			this.nombre = nombre;
			this.descripcion = descripcion;
		}

		// Getters y Setters
	    public Long getId_inventario() {
	        return id_inventario;
	    }

	    public void setId_inventario(Long id_inventario) {
	        this.id_inventario = id_inventario;
	    }

	    public int getCantidad() {
	        return cantidad;
	    }

	    public void setCantidad(int cantidad) {
	        this.cantidad = cantidad;
	    }

	    public double getPrecio() {
	        return precio;
	    }

	    public void setPrecio(double precio) {
	        this.precio = precio;
	    }

	    public String getFecha_ingreso() {
	        return fecha_ingreso;
	    }

	    public void setFecha_ingreso(String fecha_ingreso) {
	        this.fecha_ingreso = fecha_ingreso;
	    }

	    public String getHora_ingreso() {
	        return hora_ingreso;
	    }

	    public void setHora_ingreso(String hora_ingreso) {
	        this.hora_ingreso = hora_ingreso;
	    }

	    public String getFecha_salida() {
	        return fecha_salida;
	    }

	    public void setFecha_salida(String fecha_salida) {
	        this.fecha_salida = fecha_salida;
	    }

	    public String getHora_salida() {
	        return hora_salida;
	    }

	    public void setHora_salida(String hora_salida) {
	        this.hora_salida = hora_salida;
	    }

	    public Long getId_producto() {
	        return id_producto;
	    }

	    public void setId_producto(Long id_producto) {
	        this.id_producto = id_producto;
	    }

	    public String getNombre() {
	        return nombre;
	    }

	    public void setNombre(String nombre) {
	        this.nombre = nombre;
	    }

	    public String getDescripcion() {
	        return descripcion;
	    }

	    public void setDescripcion(String descripcion) {
	        this.descripcion = descripcion;
	    }
}
