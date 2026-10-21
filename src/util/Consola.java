package util;

import modelo.Sucursal;

/** Presentación en consola: encabezados, mensajes y reportes. */
public class Consola {

    private static final String LINEA = "==========================================================================";
    private static final String GUION = "--------------------------------------------------------------------------";

    /** Imprime un encabezado enmarcado. */
    public static void titulo(String texto) {
        System.out.println("\n" + LINEA);
        System.out.println("  " + texto);
        System.out.println(LINEA);
    }

    public static void exito(String texto) {
        System.out.println("[OK] " + texto);
    }

    public static void error(String texto) {
        System.out.println("[ERROR] " + texto);
    }

    public static void info(String texto) {
        System.out.println("[i] " + texto);
    }

    /** Muestra el menú principal. */
    public static void menu(int total, int capacidad) {
        titulo(Mensajes.TITULO);
        System.out.println("  Sucursales registradas: " + total + " / " + capacidad);
        System.out.println(GUION);
        System.out.println("  1. Agregar sucursal");
        System.out.println("  2. Buscar sucursal");
        System.out.println("  3. Modificar sucursal");
        System.out.println("  4. Eliminar sucursal");
        System.out.println("  5. Insertar sucursal en posición");
        System.out.println("  6. Registrar/Modificar venta");
        System.out.println("  7. Mostrar sistema");
        System.out.println("  0. Salir");
        System.out.println(GUION);
    }

    /** Lista las categorías con su índice. */
    public static void listarCategorias() {
        for (int i = 0; i < Mensajes.CATEGORIAS.length; i++) {
            System.out.println("  " + i + ". " + Mensajes.CATEGORIAS[i]);
        }
    }

    /** Lista los trimestres con su índice. */
    public static void listarTrimestres() {
        for (int i = 0; i < Mensajes.TRIMESTRES.length; i++) {
            System.out.println("  " + i + ". " + Mensajes.TRIMESTRES[i]);
        }
    }

    /**
     * Imprime los datos de una sucursal y su matriz 3x4
     * con totales por fila, por columna y total general.
     */
    public static void sucursal(Sucursal s, int posicion) {
        double[][] matriz = s.ventas.valores;
        double[] totalesColumna = new double[4];
        double totalGeneral = 0;

        System.out.println(GUION);
        System.out.printf("  Posición: %d | ID: %d | Nombre: %s%n", posicion, s.id, s.nombre);
        System.out.println(GUION);

        // Encabezado de columnas
        System.out.printf("  %-12s", "Categoría");
        for (int col = 0; col < 4; col++) {
            System.out.printf("%12s", Mensajes.TRIMESTRES[col]);
        }
        System.out.printf("%14s%n", "Total");

        // Filas: se suman mientras se imprimen y se acumulan las columnas
        for (int fila = 0; fila < 3; fila++) {
            double totalFila = 0;
            System.out.printf("  %-12s", Mensajes.CATEGORIAS[fila]);
            for (int col = 0; col < 4; col++) {
                System.out.printf("%12.2f", matriz[fila][col]);
                totalFila += matriz[fila][col];
                totalesColumna[col] += matriz[fila][col];
            }
            System.out.printf("%14.2f%n", totalFila);
            totalGeneral += totalFila;
        }

        // Fila de totales por trimestre
        System.out.printf("  %-12s", "Total");
        for (int col = 0; col < 4; col++) {
            System.out.printf("%12.2f", totalesColumna[col]);
        }
        System.out.printf("%14.2f%n", totalGeneral);
    }
}
