package bodegafacil.interfaz;

import bodegafacil.modelo.ProductoNoPerecedero;
import bodegafacil.modelo.ProductoPerecedero;
import bodegafacil.negocio.ArchivoInventario;
import bodegafacil.negocio.Inventario;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        ArchivoInventario archivo = new ArchivoInventario("inventario.dat");
        Inventario inventario = archivo.cargarDatos();

        if (inventario.listarProductos().isEmpty()) {
            cargarProductosDePrueba(inventario);
        }

        MenuPrincipal menu = new MenuPrincipal(inventario, archivo);
        menu.iniciar();
    }

    private static void cargarProductosDePrueba(Inventario inventario) {
        LocalDate hoy = LocalDate.now();

        // Perecederos
        inventario.registrarProducto(new ProductoPerecedero(
                "P001", "Leche Entera 1L", 15, 10, 0.85, 1.15, hoy.plusDays(3)));   // Proximo a caducar
        inventario.registrarProducto(new ProductoPerecedero(
                "P002", "Yogurt Natural", 5, 8, 1.20, 1.75, hoy.minusDays(2)));     // Caducado y Stock bajo
        inventario.registrarProducto(new ProductoPerecedero(
                "P003", "Queso Fresco", 20, 5, 2.10, 2.80, hoy.plusDays(20)));      // Vigente

        // No Perecederos
        inventario.registrarProducto(new ProductoNoPerecedero(
                "NP001", "Arroz 2kg", 30, 10, 1.80, 2.40));
        inventario.registrarProducto(new ProductoNoPerecedero(
                "NP002", "Detergente 1kg", 4, 6, 2.50, 3.50));                      // Stock bajo
        inventario.registrarProducto(new ProductoNoPerecedero(
                "NP003", "Agua sin gas 1L", 25, 10, 0.40, 0.75));
    }
}