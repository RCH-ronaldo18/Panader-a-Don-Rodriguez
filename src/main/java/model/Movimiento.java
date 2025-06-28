package model;

import java.sql.Date;

public class Movimiento {
private int idMovimiento;
private int idInventario;
private Date fecha;
private String detalle;
private int entrada;
private int salida;



    // NUEVOS CAMPOS
// NUEVOS CAMPOS
private double costoUnitario;
private double totalMovimiento;
private String nombreProducto;
private int existenciaActual; // NUEVO

// Getters y setters existentes
public int getIdMovimiento() { return idMovimiento; }
public void setIdMovimiento(int idMovimiento) { this.idMovimiento = idMovimiento; }

public int getIdInventario() { return idInventario; }
public void setIdInventario(int idInventario) { this.idInventario = idInventario; }

public Date getFecha() { return fecha; }
public void setFecha(Date fecha) { this.fecha = fecha; }

public String getDetalle() { return detalle; }
public void setDetalle(String detalle) { this.detalle = detalle; }

public int getEntrada() { return entrada; }
public void setEntrada(int entrada) { this.entrada = entrada; }

public int getSalida() { return salida; }
public void setSalida(int salida) { this.salida = salida; }

// NUEVOS Getters y Setters
public double getCostoUnitario() { return costoUnitario; }
public void setCostoUnitario(double costoUnitario) { this.costoUnitario = costoUnitario; }

public double getTotalMovimiento() { return totalMovimiento; }
public void setTotalMovimiento(double totalMovimiento) { this.totalMovimiento = totalMovimiento; }

public String getNombreProducto() { return nombreProducto; }
public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }

public int getExistenciaActual() { return existenciaActual; }
public void setExistenciaActual(int existenciaActual) { this.existenciaActual = existenciaActual; }
}
