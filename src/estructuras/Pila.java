package estructuras;

import java.util.Stack;

/**
 * E2: Pila (LIFO - Last In First Out)
 * Usada para:
 * - Deshacer operaciones
 * - DFS en grafos
 * - Reconstrucción de rutas en A*
 *
 * VERSIÓN CON COLECCIONES JAVA (para prototipo rápido).
 */
public class Pila<T> {
    private Stack<T> pila;

    public Pila() {
        this.pila = new Stack<>();
    }

    /**
     * Apila un elemento en el tope.
     */
    public void apilar(T elemento) {
        pila.push(elemento);
    }

    /**
     * Desapila y retorna el elemento del tope.
     * @return el elemento del tope, o null si está vacía.
     */
    public T desapilar() {
        if (!pila.isEmpty()) {
            return pila.pop();
        }
        return null;
    }

    /**
     * Consulta el elemento del tope sin removerlo.
     * @return el elemento del tope, o null si está vacía.
     */
    public T cima() {
        if (!pila.isEmpty()) {
            return pila.peek();
        }
        return null;
    }

    /**
     * Verifica si la pila está vacía.
     */
    public boolean estaVacia() {
        return pila.isEmpty();
    }

    /**
     * Retorna el número de elementos en la pila.
     */
    public int tamanio() {
        return pila.size();
    }

    /**
     * Limpia todos los elementos de la pila.
     */
    public void limpiar() {
        pila.clear();
    }

    @Override
    public String toString() {
        return pila.toString();
    }
}
