package bodegafacil.negocio;

import bodegafacil.modelo.Producto;
import bodegafacil.modelo.ProductoPerecedero;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Inventario implements Serializable {
    private ArrayList<Producto> productos;
    private int capacidadMaxima;
    private double presupuestoSemanal;
    private double gastoSemanal;
    private LocalDate fechaInicioSemana;

    public Inventario() {
        this.productos = new ArrayList<>();
        this.capacidadMaxima = 300;
        this.presupuestoSemanal = 450.0;
        this.gastoSemanal = 0.0;
        this.fechaInicioSemana = LocalDate.now();
    }

    public double getGastoSemanal() {
        return gastoSemanal;
    }

    public LocalDate getFechaInicioSemana() {
        return fechaInicioSemana;
    }

    public void setGastoSemanal(double gastoSemanal) {
        this.gastoSemanal = gastoSemanal;
    }

    public void setFechaInicioSemana(LocalDate fechaInicioSemana) {
        this.fechaInicioSemana = fechaInicioSemana;
    }

    public boolean registrarProducto(Producto producto) {
        if (producto == null || buscarPorCodigo(producto.getCodigo()) != null) {
            return false;
        }
        if (calcularTotalUnidades() + producto.getCantidad() > capacidadMaxima) {
            return false;
        }
        productos.add(producto);
        return true;
    }

    public ArrayList<Producto> listarProductos() {
        return productos;
    }

    public Producto buscarPorCodigo(String codigo) {
        for (Producto p : productos) {
            if (p.getCodigo().equalsIgnoreCase(codigo)) {
                return p;
            }
        }
        return null;
    }

    public ArrayList<Producto> buscarPorNombre(String nombre) {
        ArrayList<Producto> resultado = new ArrayList<>();
        for (Producto p : productos) {
            if (p.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    public String registrarEntrada(String codigo, int cantidad) {
        Producto producto = buscarPorCodigo(codigo);
        if (producto == null) {
            return "Error: No existe un producto con ese codigo";
        }
        reiniciarPresupuestoSiNuevaSemana(LocalDate.now());

        if (calcularTotalUnidades() + cantidad > capacidadMaxima) {
            return "Error: Supera la capacidad maxima de unidades. " +
                    "\nDisponible: " + obtenerEspacioDisponible();
        }

        double costoCompra = cantidad * producto.getCostoUnitario();
        if (gastoSemanal + costoCompra > presupuestoSemanal) {
            return "Error: La compra supera el presupuesto disponible " +
                    "\nPresupuesto disponible: $ " + obtenerPresupuestoDisponible();
        }

        producto.agregarUnidades(cantidad);
        gastoSemanal += costoCompra;
        return "Entrada registrada con exito. " +
                "\nNuevo stock: " + producto.getCantidad();
    }

    public String registrarSalida(String codigo, int cantidad) {
        Producto producto = buscarPorCodigo(codigo);
        if (producto == null) {
            return "Error: No existe un producto con ese codigo";
        }
        if (!producto.retirarUnidades(cantidad)) {
            return "Error: Stock insuficiente. " +
                    "\nDisponible: " + producto.getCantidad();
        }

        String mensaje = "Salida registrada con exito. Stock restante: " + producto.getCantidad();
        if (producto.estaBajoStock()) {
            mensaje += "\nALERTA: El producto esta en stock bajo";
        }
        return mensaje;
    }

    public ArrayList<Producto> obtenerProductosBajoStock() {
        ArrayList<Producto> resultado = new ArrayList<>();
        for (Producto p : productos) {
            if (p.estaBajoStock()) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    public ArrayList<Producto> obtenerProductosProximosACaducar(LocalDate fechaActual) {
        ArrayList<Producto> resultado = new ArrayList<>();
        for (Producto p : productos) {
            if (p instanceof ProductoPerecedero) {
                ProductoPerecedero pp = (ProductoPerecedero) p;
                if (!pp.getFechaCaducidad().isAfter(fechaActual.plusDays(7))) {
                    resultado.add(pp);
                }
            }
        }
        return resultado;
    }
    public double calcularValorTotalInventario() {
        double total = 0;
        for (Producto p : productos) {
            total += p.calcularValorInventario();
        }
        return total;
    }
    public boolean actualizarProducto(Producto productoActualizado) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo().equalsIgnoreCase(productoActualizado.getCodigo())) {
                productos.set(i, productoActualizado);
                return true;
            }
        }
        return false;
    }
    public int calcularTotalUnidades() {
        int total = 0;
        for (Producto p : productos) {
            total += p.getCantidad();
        }
        return total;
    }
    public int obtenerEspacioDisponible() {
        return capacidadMaxima - calcularTotalUnidades();
    }

    public double obtenerPresupuestoDisponible() {
        reiniciarPresupuestoSiNuevaSemana(LocalDate.now());
        return presupuestoSemanal - gastoSemanal;
    }

    public void reiniciarPresupuestoSiNuevaSemana(LocalDate fechaActual) {
        if (ChronoUnit.DAYS.between(fechaInicioSemana, fechaActual) >= 7) {
            gastoSemanal = 0.0;
            fechaInicioSemana = fechaActual;
        }
    }

    public String generarResumen() {
        reiniciarPresupuestoSiNuevaSemana(LocalDate.now());
        return "Productos registrados: " + productos.size()
                + "\nUnidades almacenadas: " + calcularTotalUnidades() + " / " + capacidadMaxima
                + "\nCapacidad disponible: " + obtenerEspacioDisponible()
                + "\nValor total del inventario: $" + calcularValorTotalInventario()
                + "\nPresupuesto utilizado: $" + gastoSemanal + " / $" + presupuestoSemanal
                + "\nPresupuesto restante: $" + obtenerPresupuestoDisponible();
    }

}

