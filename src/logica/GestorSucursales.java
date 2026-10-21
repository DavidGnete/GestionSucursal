package logica;

import modelo.Sucursal;
import util.Estados;

/** Administra el arreglo de sucursales con control manual de índices. */
public class GestorSucursales {

    public static final int CAPACIDAD = 100;

    private Sucursal[] sucursales = new Sucursal[CAPACIDAD];
    private int total = 0;        // cantidad de sucursales reales en el arreglo
    private int siguienteId = 1;  // id consecutivo; no se reutiliza al eliminar

    /** Retorna la cantidad de sucursales registradas. */
    public int obtenerTotal() {
        return total;
    }

    /** Retorna la sucursal de una posición válida (0 a total-1). */
    public Sucursal obtenerSucursal(int posicion) {
        return sucursales[posicion];
    }

    /** Indica si el arreglo alcanzó la capacidad máxima. */
    public boolean estaLleno() {
        return total == CAPACIDAD;
    }

    /** Crea una sucursal con id consecutivo y matriz en ceros. */
    private Sucursal crearSucursal(String nombre) {
        Sucursal nueva = new Sucursal();
        nueva.id = siguienteId;
        nueva.nombre = nombre;
        siguienteId++;
        return nueva;
    }

    /** Agrega una sucursal al final del arreglo activo. */
    public int agregar(String nombre) {
        if (total == CAPACIDAD) {
            return Estados.LLENO;
        }
        sucursales[total] = crearSucursal(nombre);
        total++;
        return Estados.OK;
    }

    /** Búsqueda lineal por id. Retorna la posición o -1 si no existe. */
    public int buscarPorId(int id) {
        for (int i = 0; i < total; i++) {
            if (sucursales[i].id == id) {
                return i;
            }
        }
        return -1;
    }

    /** Búsqueda lineal por nombre (sin distinguir mayúsculas). Retorna la posición o -1. */
    public int buscarPorNombre(String nombre) {
        for (int i = 0; i < total; i++) {
            if (sucursales[i].nombre.equalsIgnoreCase(nombre)) {
                return i;
            }
        }
        return -1;
    }

    /** Cambia el nombre de la sucursal con el id indicado. */
    public int modificarNombre(int id, String nuevoNombre) {
        int pos = buscarPorId(id);
        if (pos == -1) {
            return Estados.NO_EXISTE;
        }
        sucursales[pos].nombre = nuevoNombre;
        return Estados.OK;
    }

    /** Elimina por id desplazando los elementos siguientes una posición a la izquierda. */
    public int eliminar(int id) {
        int pos = buscarPorId(id);
        if (pos == -1) {
            return Estados.NO_EXISTE;
        }
        for (int i = pos; i < total - 1; i++) {
            sucursales[i] = sucursales[i + 1];
        }
        sucursales[total - 1] = null; // la última posición quedó duplicada
        total--;
        return Estados.OK;
    }

    /** Inserta en el índice dado desplazando los elementos una posición a la derecha. */
    public int insertar(int indice, String nombre) {
        if (total == CAPACIDAD) {
            return Estados.LLENO;
        }
        if (indice < 0 || indice > total) {
            return Estados.INDICE_INVALIDO;
        }
        // Se recorre desde el final para no sobrescribir datos antes de moverlos
        for (int i = total; i > indice; i--) {
            sucursales[i] = sucursales[i - 1];
        }
        sucursales[indice] = crearSucursal(nombre);
        total++;
        return Estados.OK;
    }

    /** Asigna una venta en [categoria][trimestre] e indica si fue nueva o modificada. */
    public int registrarVenta(int id, int categoria, int trimestre, double valor) {
        int pos = buscarPorId(id);
        if (pos == -1) {
            return Estados.NO_EXISTE;
        }
        if (categoria < 0 || categoria > 2 || trimestre < 0 || trimestre > 3) {
            return Estados.INDICE_INVALIDO;
        }
        double[][] matriz = sucursales[pos].ventas.valores; // referencia, no copia
        boolean estabaVacia = matriz[categoria][trimestre] == 0.0;
        matriz[categoria][trimestre] = valor;
        return estabaVacia ? Estados.VENTA_REGISTRADA : Estados.VENTA_MODIFICADA;
    }
}
