package app;

import logica.GestorSucursales;
import modelo.Sucursal;
import util.Consola;
import util.Estados;
import util.Mensajes;
import util.Validaciones;

/** Punto de entrada: menú interactivo del sistema de sucursales. */
public class Main {

    private static GestorSucursales gestor = new GestorSucursales();

    public static void main(String[] args) {
        int opcion;
        do {
            Consola.menu(gestor.obtenerTotal(), GestorSucursales.CAPACIDAD);
            opcion = Validaciones.leerEntero("Seleccione una opción: ", 0, 7);
            switch (opcion) {
                case 1: agregarSucursal(); break;
                case 2: buscarSucursal(); break;
                case 3: modificarSucursal(); break;
                case 4: eliminarSucursal(); break;
                case 5: insertarSucursal(); break;
                case 6: registrarVenta(); break;
                case 7: mostrarSistema(); break;
                case 0: Consola.info(Mensajes.DESPEDIDA); break;
                default: Consola.error(Mensajes.OPCION_INVALIDA);
            }
            if (opcion != 0) {
                Validaciones.pausar();
            }
        } while (opcion != 0);
    }

    /** Opción 1: agrega una sucursal al final. */
    private static void agregarSucursal() {
        Consola.titulo("AGREGAR SUCURSAL");
        if (gestor.estaLleno()) {
            Consola.error(Mensajes.LLENO);
            return;
        }
        String nombre = Validaciones.leerTexto("Nombre de la sucursal: ");
        if (gestor.agregar(nombre) == Estados.OK) {
            Consola.exito(Mensajes.AGREGADA);
        } else {
            Consola.error(Mensajes.LLENO);
        }
    }

    /** Opción 2: busca por id o por nombre y muestra toda su información. */
    private static void buscarSucursal() {
        Consola.titulo("BUSCAR SUCURSAL");
        if (gestor.obtenerTotal() == 0) {
            Consola.info(Mensajes.SIN_SUCURSALES);
            return;
        }
        System.out.println("  1. Buscar por ID");
        System.out.println("  2. Buscar por nombre");
        int tipo = Validaciones.leerEntero("Seleccione: ", 1, 2);

        int pos;
        if (tipo == 1) {
            pos = gestor.buscarPorId(Validaciones.leerEnteroPositivo("ID: "));
        } else {
            pos = gestor.buscarPorNombre(Validaciones.leerTexto("Nombre: "));
        }

        if (pos == -1) {
            Consola.error(Mensajes.NO_EXISTE);
        } else {
            Consola.exito("Sucursal encontrada en la posición " + pos + ".");
            Consola.sucursal(gestor.obtenerSucursal(pos), pos);
        }
    }

    /** Opción 3: cambia el nombre de una sucursal por id. */
    private static void modificarSucursal() {
        Consola.titulo("MODIFICAR SUCURSAL");
        if (gestor.obtenerTotal() == 0) {
            Consola.info(Mensajes.SIN_SUCURSALES);
            return;
        }
        int id = Validaciones.leerEnteroPositivo("ID de la sucursal: ");
        int pos = gestor.buscarPorId(id);
        if (pos == -1) {
            Consola.error(Mensajes.NO_EXISTE);
            return;
        }
        Consola.info("Nombre actual: " + gestor.obtenerSucursal(pos).nombre);
        String nuevoNombre = Validaciones.leerTexto("Nuevo nombre: ");
        gestor.modificarNombre(id, nuevoNombre);
        Consola.exito(Mensajes.MODIFICADA);
    }

    /** Opción 4: elimina una sucursal por id. */
    private static void eliminarSucursal() {
        Consola.titulo("ELIMINAR SUCURSAL");
        if (gestor.obtenerTotal() == 0) {
            Consola.info(Mensajes.SIN_SUCURSALES);
            return;
        }
        int id = Validaciones.leerEnteroPositivo("ID de la sucursal a eliminar: ");
        if (gestor.eliminar(id) == Estados.OK) {
            Consola.exito(Mensajes.ELIMINADA);
        } else {
            Consola.error(Mensajes.NO_EXISTE);
        }
    }

    /** Opción 5: inserta una sucursal en una posición entre 0 y total. */
    private static void insertarSucursal() {
        Consola.titulo("INSERTAR SUCURSAL EN POSICIÓN");
        if (gestor.estaLleno()) {
            Consola.error(Mensajes.LLENO);
            return;
        }
        int total = gestor.obtenerTotal();
        int indice = Validaciones.leerEntero("Posición (0 a " + total + "): ", 0, total);
        String nombre = Validaciones.leerTexto("Nombre de la sucursal: ");
        int estado = gestor.insertar(indice, nombre);
        if (estado == Estados.OK) {
            Consola.exito(Mensajes.INSERTADA);
        } else if (estado == Estados.LLENO) {
            Consola.error(Mensajes.LLENO);
        } else {
            Consola.error(Mensajes.INDICE_INVALIDO);
        }
    }

    /** Opción 6: registra o modifica una venta en la matriz de una sucursal. */
    private static void registrarVenta() {
        Consola.titulo("REGISTRAR / MODIFICAR VENTA");
        if (gestor.obtenerTotal() == 0) {
            Consola.info(Mensajes.SIN_SUCURSALES);
            return;
        }
        int id = Validaciones.leerEnteroPositivo("ID de la sucursal: ");
        int pos = gestor.buscarPorId(id);
        if (pos == -1) {
            Consola.error(Mensajes.NO_EXISTE);
            return;
        }

        System.out.println("Categorías:");
        Consola.listarCategorias();
        int categoria = Validaciones.leerEntero("Categoría (fila): ", 0, 2);

        System.out.println("Trimestres:");
        Consola.listarTrimestres();
        int trimestre = Validaciones.leerEntero("Trimestre (columna): ", 0, 3);

        Sucursal s = gestor.obtenerSucursal(pos);
        Consola.info("Valor actual: " + s.ventas.valores[categoria][trimestre]);
        double valor = Validaciones.leerDoubleNoNegativo("Nuevo valor de venta: ");

        int estado = gestor.registrarVenta(id, categoria, trimestre, valor);
        if (estado == Estados.VENTA_REGISTRADA) {
            Consola.exito(Mensajes.VENTA_REGISTRADA);
        } else if (estado == Estados.VENTA_MODIFICADA) {
            Consola.exito(Mensajes.VENTA_MODIFICADA);
        } else {
            Consola.error(Mensajes.INDICE_INVALIDO);
        }
    }

    /** Opción 7: muestra todas las sucursales con su matriz y totales. */
    private static void mostrarSistema() {
        Consola.titulo("REPORTE GENERAL DEL SISTEMA");
        int total = gestor.obtenerTotal();
        if (total == 0) {
            Consola.info(Mensajes.SIN_SUCURSALES);
            return;
        }
        for (int i = 0; i < total; i++) {
            Consola.sucursal(gestor.obtenerSucursal(i), i);
        }
    }
}
