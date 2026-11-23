package bodegafacil.modelo;

import java.time.LocalDate;

public class ProductoNoPerecedero extends Producto {

    public ProductoNoPerecedero(String codigo, String nombre, int cantidad, int stockMinimo,
                                double costoUnitario, double precioVenta) {
        super(codigo, nombre, cantidad, stockMinimo, costoUnitario, precioVenta);
    }

    @Override
    public String obtenerEstadoCaducidad(LocalDate fechaActual) {
        return "Este producto no se caduca";
    }

    @Override
    public String mostrarInformacion() {
        return super.mostrarInformacion()
                + "\nTipo: No Perecedero"
                + "\nCaducidad: " + obtenerEstadoCaducidad(LocalDate.now());
    }
}
