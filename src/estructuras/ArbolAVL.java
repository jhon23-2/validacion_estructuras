package estructuras;

import java.util.TreeMap;
import java.util.ArrayList;
import java.util.List;

/**
 * E4: Árbol AVL (árbol binario de búsqueda balanceado)
 * Usada para: Índice rápido de visitantes por documento
 *
 * VERSIÓN CON COLECCIONES JAVA (para prototipo rápido).
 * TreeMap implementa un árbol rojo-negro que también garantiza O(log n).
 */
public class ArbolAVL<K extends Comparable<K>, V> {
    private TreeMap<K, V> arbol;

    public ArbolAVL() {
        this.arbol = new TreeMap<>();
    }

    /**
     * Inserta un par clave-valor en el árbol.
     */
    public void insertar(K clave, V valor) {
        arbol.put(clave, valor);
    }

    /**
     * Busca un valor por clave.
     * @return el valor asociado, o null si no existe.
     */
    public V buscar(K clave) {
        return arbol.get(clave);
    }

    /**
     * Elimina un elemento por clave.
     * @return true si se eliminó, false si no existía.
     */
    public boolean eliminar(K clave) {
        if (arbol.containsKey(clave)) {
            arbol.remove(clave);
            return true;
        }
        return false;
    }

    /**
     * Verifica si existe una clave.
     */
    public boolean contiene(K clave) {
        return arbol.containsKey(clave);
    }

    /**
     * Retorna el número de elementos.
     */
    public int tamanio() {
        return arbol.size();
    }

    /**
     * Verifica si el árbol está vacío.
     */
    public boolean estaVacio() {
        return arbol.isEmpty();
    }

    /**
     * Recorrido inorden (izquierda-raíz-derecha): retorna lista ordenada.
     */
    public List<V> recorridoInorden() {
        return new ArrayList<>(arbol.values());
    }

    /**
     * Retorna todas las claves ordenadas.
     */
    public List<K> obtenerClaves() {
        return new ArrayList<>(arbol.keySet());
    }

    /**
     * Retorna todos los valores.
     */
    public List<V> obtenerValores() {
        return new ArrayList<>(arbol.values());
    }

    /**
     * Recorrido por niveles (BFS).
     * NOTA: TreeMap no expone su estructura interna, así que retornamos ordenado.
     */
    public List<V> recorridoPorNiveles() {
        // En TreeMap no podemos acceder a la estructura real del árbol
        // Retornamos los elementos en orden
        return recorridoInorden();
    }

    /**
     * Limpia el árbol.
     */
    public void limpiar() {
        arbol.clear();
    }

    /**
     * Altura del árbol (estimación).
     * TreeMap no expone altura, estimamos log₂(n).
     */
    public int altura() {
        if (arbol.isEmpty()) return 0;
        return (int) Math.ceil(Math.log(arbol.size()) / Math.log(2));
    }

    /**
     * Representación en string ordenado.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ArbolAVL [").append(arbol.size()).append(" elementos]\n");
        sb.append("Altura estimada: ").append(altura()).append("\n");
        sb.append("Elementos (inorden): ");
        int count = 0;
        for (K clave : arbol.keySet()) {
            if (count > 0) sb.append(", ");
            sb.append(clave);
            count++;
            if (count >= 10) {
                sb.append(", ...");
                break;
            }
        }
        return sb.toString();
    }
}
