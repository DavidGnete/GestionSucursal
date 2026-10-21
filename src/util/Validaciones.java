package util;

import java.util.Scanner;

/** Lectura de datos desde teclado, repitiendo hasta recibir un valor válido. */
public class Validaciones {

    private static final Scanner LECTOR = new Scanner(System.in);

    /** Lee un entero dentro del rango [minimo, maximo]. */
    public static int leerEntero(String mensaje, int minimo, int maximo) {
        while (true) {
            System.out.print(mensaje);
            String texto = LECTOR.nextLine().trim();
            try {
                int numero = Integer.parseInt(texto);
                if (numero >= minimo && numero <= maximo) {
                    return numero;
                }
                Consola.error("Debe estar entre " + minimo + " y " + maximo + ".");
            } catch (NumberFormatException e) {
                Consola.error("Debe ingresar un número entero.");
            }
        }
    }

    /** Lee un entero positivo (usado para ids). */
    public static int leerEnteroPositivo(String mensaje) {
        return leerEntero(mensaje, 1, Integer.MAX_VALUE);
    }

    /** Lee un número real mayor o igual a cero (montos de venta). */
    public static double leerDoubleNoNegativo(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = LECTOR.nextLine().trim().replace(',', '.');
            try {
                double numero = Double.parseDouble(texto);
                if (numero >= 0) {
                    return numero;
                }
                Consola.error("El valor no puede ser negativo.");
            } catch (NumberFormatException e) {
                Consola.error("Debe ingresar un número válido (ej: 1500.50).");
            }
        }
    }

    /** Lee un texto que no esté vacío. */
    public static String leerTexto(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = LECTOR.nextLine().trim();
            if (!texto.isEmpty()) {
                return texto;
            }
            Consola.error("El texto no puede estar vacío.");
        }
    }

    /** Espera a que el usuario presione Enter para continuar. */
    public static void pausar() {
        System.out.print("\nPresione Enter para continuar...");
        LECTOR.nextLine();
    }
}
