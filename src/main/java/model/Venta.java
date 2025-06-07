package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Venta {
    private long idVenta;
    private long idCliente;
    private LocalDate fechaVenta;
    private LocalTime horaVenta;
    private double total;
    private int idEstado;
    private String nombreCliente;
    // Getters y Setters
    public long getIdVenta() {
        return idVenta;
    }

    public int getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(int idEstado) {
		this.idEstado = idEstado;
	}

	public void setIdVenta(long idVenta) {
        this.idVenta = idVenta;
    }

    public long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(long idCliente) {
        this.idCliente = idCliente;
    }

    public LocalDate getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public LocalTime getHoraVenta() {
        return horaVenta;
    }

    public void setHoraVenta(LocalTime horaVenta) {
        this.horaVenta = horaVenta;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }


    public String obtenerEstadoVenta() {
        switch (this.idEstado) {
            case 1:
                return "Por Atender";
            case 2:
                return "Completada";
            case 3:
                return "Atendido";
            // Agregar más casos si es necesario
            default:
                return "Desconocido";
        }
    }

}

