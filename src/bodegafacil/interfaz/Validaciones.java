package bodegafacil.interfaz;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Validaciones {
    private static Scanner sc = new Scanner(System.in);

    public static String leerString (String mensaje){
        String nombre;
        do {
            System.out.print(mensaje + " : ");
            nombre = sc.nextLine();
        }while(nombre==null || nombre.trim().isEmpty());
        return nombre;
    }

    public static int validarIntPositivo(String mensaje) {
        int numero = 0;
        boolean valido = false;
        do {
            try {
                System.out.print(mensaje + " : ");
                numero = Integer.parseInt(sc.nextLine());
                valido = true;
            } catch (NumberFormatException e) {
                valido=false;
            }
        } while (!valido || numero <= 0);
        return numero;
    }

    public static double validarDoublePositivo(String mensaje) {
        double numero = 0;
        boolean valido = false;
        do {
            try {
                System.out.print(mensaje + " : ");
                numero = Double.parseDouble(sc.nextLine());
                valido = true;
            } catch (NumberFormatException e) {
                valido=false;
            }
        } while (!valido || numero <= 0);
        return numero;
    }

    public static int validarIntRango(String mensaje, int min, int max) {
        int numero = 0;
        boolean valido = false;
        do {
            try {
                System.out.print(mensaje + " : ");
                numero = Integer.parseInt(sc.nextLine());
                valido = true;
            } catch (NumberFormatException e) {
                valido = false;
            }
        } while (!valido || numero < min || numero > max);
        return numero;
    }

    public static double validarDoubleRango(String mensaje, double min, double max) {
        double numero = 0;
        boolean valido = false;
        do {
            try {
                System.out.print(mensaje + " : ");
                numero = Double.parseDouble(sc.nextLine());
                valido = true;
            } catch (NumberFormatException e) {
                valido = false;
            }
        } while (!valido || numero < min || numero > max);
        return numero;
    }

    public static LocalDate validarFecha(String mensaje) {
        LocalDate fecha = null;
        boolean valido = false;
        do {
            try {
                System.out.print(mensaje + " (AAAA-MM-DD) : ");
                fecha = LocalDate.parse(sc.nextLine().trim());
                valido = true;
            } catch (DateTimeParseException e) {
                valido = false;
            }
        } while (!valido);
        return fecha;
    }
}




