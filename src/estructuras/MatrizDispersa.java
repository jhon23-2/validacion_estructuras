package estructuras;

import java.util.HashMap;
import java.util.Map;

/**
 * E5: Matriz dispersa por filas
 * Usada para: Pesos/longitudes de senderos (solo almacena valores != 0)
 *
 * VERSIÓN CON COLECCIONES JAVA (para prototipo rápido).
 * Usa HashMap de HashMap para almacenar solo valores no nulos.
 */
public class MatrizDispersa {
    private Map<Integer, Map<Integer, Double>> filas;
    private int numFilas;
    private int numColumnas;

    public MatrizDispersa(int numFilas, int numColumnas) {
        this.numFilas = numFilas;
        this.numColumnas = numColumnas;
        this.filas = new HashMap<>();
    }

    /**
     * Asigna un valor en la posición (fila, columna).
     * Si el valor es 0, lo elimina (no lo almacena).
     */
    public void asignar(int fila, int columna, double valor) {
        if (fila < 0 || fila >= numFilas || columna < 0 || columna >= numColumnas) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }

        if (valor == 0) {
            // No almacenar valores cero
            if (filas.containsKey(fila)) {
                filas.get(fila).remove(columna);
                if (filas.get(fila).isEmpty()) {
                    filas.remove(fila);
                }
            }
        } else {
            filas.putIfAbsent(fila, new HashMap<>());
            filas.get(fila).put(columna, valor);
        }
    }

    /**
     * Obtiene el valor en la posición (fila, columna).
     * @return el valor, o 0 si no existe.
     */
    public double obtener(int fila, int columna) {
        if (fila < 0 || fila >= numFilas || columna < 0 || columna >= numColumnas) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }

        if (filas.containsKey(fila) && filas.get(fila).containsKey(columna)) {
            return filas.get(fila).get(columna);
        }
        return 0.0;
    }

    /**
     * Elimina el valor en la posición (fila, columna).
     */
    public void eliminar(int fila, int columna) {
        asignar(fila, columna, 0);
    }

    /**
     * Cuenta el número de valores no nulos.
     */
    public int contarNoNulos() {
        int count = 0;
        for (Map<Integer, Double> fila : filas.values()) {
            count += fila.size();
        }
        return count;
    }

    /**
     * Obtiene todos los valores no nulos de una fila.
     */
    public Map<Integer, Double> obtenerFila(int fila) {
        if (filas.containsKey(fila)) {
            return new HashMap<>(filas.get(fila));
        }
        return new HashMap<>();
    }

    /**
     * Retorna el número de filas.
     */
    public int numFilas() {
        return numFilas;
    }

    /**
     * Retorna el número de columnas.
     */
    public int numColumnas() {
        return numColumnas;
    }

    /**
     * Calcula la densidad de la matriz (% de valores no nulos).
     */
    public double densidad() {
        int total = numFilas * numColumnas;
        if (total == 0) return 0.0;
        return (double) contarNoNulos() / total * 100.0;
    }

    @Override
    public String toString() {
        return String.format("MatrizDispersa [%dx%d, %d valores no nulos, %.2f%% densidad]",
                numFilas, numColumnas, contarNoNulos(), densidad());
    }
}
