package bodegafacil.modelo;

import java.io.Serializable;
import java.time.LocalDate;

public class Producto implements Serializable {
    private String codigo;
    private String nombre;
    private int cantidad;
    private int stockMinimo;
    private double costoUnitario;
    private double precioVenta;

    public Producto(String codigo, String nombre, int cantidad, int stockMinimo,
                    double costoUnitario, double precioVenta) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.stockMinimo = stockMinimo;
        this.costoUnitario = costoUnitario;
        this.precioVenta = precioVenta;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }

    public double getCostoUnitario() {
        return costoUnitario;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public void setCostoUnitario(double costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public double calcularValorInventario() {
        return cantidad * costoUnitario;
    }

    public boolean estaBajoStock() {
        return cantidad <= stockMinimo;
    }

    public void agregarUnidades(int cantidad) {
        if (cantidad > 0) {
            this.cantidad += cantidad;
        }
    }

    public boolean retirarUnidades(int cantidad) {
        if (cantidad > 0 && cantidad <= this.cantidad) {
            this.cantidad -= cantidad;
            return true;
        }
        return false;
    }

    public String obtenerEstadoCaducidad(LocalDate fechaActual) {
        return "Sin definir";
    }

    public String mostrarInformacion() {
        return "Codigo: " + codigo
                + "\nNombre: " + nombre
                + "\nCantidad: " + cantidad
                + "\nStock minimo: " + stockMinimo
                + "\nCosto unitario: $" + costoUnitario
                + "\nPrecio venta: $" + precioVenta;
    }
}
