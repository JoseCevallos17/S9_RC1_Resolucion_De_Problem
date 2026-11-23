package bodegafacil.interfaz;

import bodegafacil.modelo.Producto;
import bodegafacil.modelo.ProductoNoPerecedero;
import bodegafacil.modelo.ProductoPerecedero;
import bodegafacil.negocio.ArchivoInventario;
import bodegafacil.negocio.Inventario;
import bodegafacil.interfaz.Validaciones;

import java.time.LocalDate;
import java.util.ArrayList;

public class MenuPrincipal {
    private Inventario inventario;
    private ArchivoInventario archivo;

    public MenuPrincipal(Inventario inventario, ArchivoInventario archivo) {
        this.inventario = inventario;
        this.archivo = archivo;
    }

    public void iniciar() {
        int opcion = -1;
        while (opcion != 0) {
            mostrarMenu();
            opcion = Validaciones.validarIntRango("Seleccione una opcion", 0, 10);
            procesarOpcion(opcion);
        }
    }

    public void mostrarMenu() {
        System.out.println("\n--- BODEGA FACIL ---");
        System.out.println("1. Registrar producto");
        System.out.println("2. Listar productos");
        System.out.println("3. Buscar producto");
        System.out.println("4. Registrar entrada");
        System.out.println("5. Registrar salida");
        System.out.println("6. Consultar stock bajo");
        System.out.println("7. Consultar caducidades");
        System.out.println("8. Consultar valor del inventario");
        System.out.println("9. Actualizar producto");
        System.out.println("10. Mostrar resumen");
        System.out.println("0. Guardar y salir");
    }

    private void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                registrarProducto();
                break;
            case 2:
                listarProductos();
                break;
            case 3:
                buscarProducto();
                break;
            case 4:
                registrarEntrada();
                break;
            case 5:
                registrarSalida();
                break;
            case 6:
                consultarStockBajo();
                break;
            case 7:
                consultarCaducidades();
                break;
            case 8:
                consultarValorInventario();
                break;
            case 9:
                actualizarProducto();
                break;
            case 10:
                mostrarResumen();
                break;
            case 0:
                guardarYCerrar();
                break;
        }
    }

    private void registrarProducto() {
        System.out.println("\n--- Registrar Producto ---");
        String codigo = Validaciones.leerString("Codigo");
        if (inventario.buscarPorCodigo(codigo) != null) {
            System.out.println("Error: Ya existe un producto con ese codigo.");
            return;
        }

        String nombre = Validaciones.leerString("Nombre");
        int tipo = Validaciones.validarIntRango("Tipo (1. Perecedero / 2. No Perecedero)", 1, 2);
        int stockMinimo = Validaciones.validarIntPositivo("Stock minimo");
            double costo = Validaciones.validarDoublePositivo("Costo unitario");
        double precio = Validaciones.validarDoublePositivo("Precio de venta");

        Producto p;
        if (tipo == 1) {
            LocalDate fecha = Validaciones.validarFecha("Fecha de caducidad");
            p = new ProductoPerecedero(codigo, nombre, 0, stockMinimo, costo, precio, fecha);
        } else {
            p = new ProductoNoPerecedero(codigo, nombre, 0, stockMinimo, costo, precio);
        }

        if (inventario.registrarProducto(p)) {
            System.out.println("Producto registrado con exito.");
        } else {
            System.out.println("Error: No se pudo registrar el producto.");
        }
    }

    private void listarProductos() {
        System.out.println("\n--- Lista de Productos ---");
        ArrayList<Producto> lista = inventario.listarProductos();
        if (lista.isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }
        for (Producto p : lista) {
            System.out.println(p.mostrarInformacion());
            System.out.println("------------------------");
        }
    }

    private void buscarProducto() {
        System.out.println("\n--- Buscar Producto ---");
        int tipo = Validaciones.validarIntRango("1. Por codigo / 2. Por nombre", 1, 2);
        if (tipo == 1) {
            String codigo = Validaciones.leerString("Ingrese codigo");
            Producto p = inventario.buscarPorCodigo(codigo);
            if (p != null) {
                System.out.println(p.mostrarInformacion());
            } else {
                System.out.println("Producto no encontrado.");
            }
        } else {
            String nombre = Validaciones.leerString("Ingrese nombre");
            ArrayList<Producto> lista = inventario.buscarPorNombre(nombre);
            if (lista.isEmpty()) {
                System.out.println("No se encontraron productos.");
            } else {
                for (Producto p : lista) {
                    System.out.println(p.mostrarInformacion());
                    System.out.println("------------------------");
                }
            }
        }
    }

    private void registrarEntrada() {
        System.out.println("\n--- Registrar Entrada ---");
        String codigo = Validaciones.leerString("Codigo del producto");
        int cantidad = Validaciones.validarIntPositivo("Cantidad a ingresar");
        System.out.println(inventario.registrarEntrada(codigo, cantidad));
    }

    private void registrarSalida() {
        System.out.println("\n--- Registrar Salida ---");
        String codigo = Validaciones.leerString("Codigo del producto");
        int cantidad = Validaciones.validarIntPositivo("Cantidad a retirar");
        System.out.println(inventario.registrarSalida(codigo, cantidad));
    }

    private void consultarStockBajo() {
        System.out.println("\n--- Productos con Stock Bajo ---");
        ArrayList<Producto> lista = inventario.obtenerProductosBajoStock();
        if (lista.isEmpty()) {
            System.out.println("No hay productos con stock bajo.");
        } else {
            for (Producto p : lista) {
                System.out.println(p.mostrarInformacion());
                System.out.println("------------------------");
            }
        }
    }

    private void consultarCaducidades() {
        System.out.println("\n--- Productos Caducados o Proximos a Caducar ---");
        ArrayList<Producto> lista = inventario.obtenerProductosProximosACaducar(LocalDate.now());
        if (lista.isEmpty()) {
            System.out.println("No hay productos caducados ni proximos a caducar.");
        } else {
            for (Producto p : lista) {
                System.out.println(p.mostrarInformacion());
                System.out.println("------------------------");
            }
        }
    }

    private void consultarValorInventario() {
        System.out.println("\nValor total del inventario: $" + inventario.calcularValorTotalInventario());
    }

    private void actualizarProducto() {
        System.out.println("\n--- Actualizar Producto ---");
        String codigo = Validaciones.leerString("Codigo del producto");
        Producto p = inventario.buscarPorCodigo(codigo);
        if (p == null) {
            System.out.println("Error: Producto no encontrado.");
            return;
        }

        p.setNombre(Validaciones.leerString("Nuevo nombre"));
        p.setStockMinimo(Validaciones.validarIntPositivo("Nuevo stock minimo"));
        p.setCostoUnitario(Validaciones.validarDoublePositivo("Nuevo costo unitario"));
        p.setPrecioVenta(Validaciones.validarDoublePositivo("Nuevo precio de venta"));

        if (p instanceof ProductoPerecedero) {
            ProductoPerecedero pp = (ProductoPerecedero) p;
            pp.setFechaCaducidad(Validaciones.validarFecha("Nueva fecha de caducidad"));
        }

        if (inventario.actualizarProducto(p)) {
            System.out.println("Producto actualizado con exito.");
        }
    }

    private void mostrarResumen() {
        System.out.println("\n--- Resumen del Inventario ---");
        System.out.println(inventario.generarResumen());
    }

    private void guardarYCerrar() {
        archivo.guardarDatos(inventario);
        System.out.println("Datos guardados. Saliendo del sistema...");
    }
}