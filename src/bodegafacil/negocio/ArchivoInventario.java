package bodegafacil.negocio;

import java.io.*;

public class ArchivoInventario {
    private String rutaArchivo;

    public ArchivoInventario(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public void guardarDatos(Inventario inventario) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo));
            oos.writeObject(inventario);
            oos.close();
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

    public Inventario cargarDatos() {
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            return new Inventario();
        }
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo));
            Inventario inventario = (Inventario) ois.readObject();
            ois.close();
            return inventario;
        } catch (Exception e) {
            System.out.println("Error al cargar: " + e.getMessage());
            return new Inventario();
        }
    }
}