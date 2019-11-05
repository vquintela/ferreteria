package model;

import java.sql.Date;

public class Venta {
    private int id;
    private Empleado empleado;
    private Cliente cliente;
    private String fecha;
    private boolean Cancelado;

    public Venta(Empleado empleado, Cliente cliente, String fecha, boolean Cancelado) {
        this.empleado = empleado;
        this.cliente = cliente;
        this.fecha = fecha;
        this.Cancelado = Cancelado;
    }

    public Venta(int id, Empleado empleado, Cliente cliente, String fecha, boolean Cancelado) {
        this.id = id;
        this.empleado = empleado;
        this.cliente = cliente;
        this.fecha = fecha;
        this.Cancelado = Cancelado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public boolean isCancelado() {
        return Cancelado;
    }

    public void setCancelado(boolean Cancelado) {
        this.Cancelado = Cancelado;
    }
}
