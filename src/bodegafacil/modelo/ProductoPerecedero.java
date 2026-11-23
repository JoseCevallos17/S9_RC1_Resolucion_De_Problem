package bodegafacil.modelo;

import java.time.LocalDate;

public class ProductoPerecedero extends Producto {
    private LocalDate fechaCaducidad;

    public ProductoPerecedero(String codigo, String nombre, int cantidad, int stockMinimo,
                              double costo, double precio, LocalDate fechaCaducidad) {
        super(codigo, nombre, cantidad, stockMinimo, costo, precio);
        this.fechaCaducidad = fechaCaducidad;
    }

    public LocalDate getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(LocalDate fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    @Override
    public String obtenerEstadoCaducidad(LocalDate fechaActual) {
        if (fechaCaducidad.isBefore(fechaActual)) {
            return "Caducado: " + fechaCaducidad;
        } else if (!fechaCaducidad.isAfter(fechaActual.plusDays(7))) {
            return "Proximo a caducar: " + fechaCaducidad;
        }
        return "Vigente: " + fechaCaducidad;
    }

    @Override
    public String mostrarInformacion() {
        return super.mostrarInformacion()
                + "\nTipo: Perecedero"
                + "\nCaducidad: " + obtenerEstadoCaducidad(LocalDate.now());
    }

}
