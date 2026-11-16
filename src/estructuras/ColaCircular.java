package estructuras;

import java.util.LinkedList;

/**
 * E3b: Cola circular (lista circular simplemente enlazada)
 * Usada para: Turno rotativo de guías locales
 *
 * VERSIÓN CON COLECCIONES JAVA (para prototipo rápido).
 * El elemento atendido vuelve automáticamente al final.
 */
public class ColaCircular<T> {
    private LinkedList<T> cola;

    public ColaCircular() {
        this.cola = new LinkedList<>();
    }

    /**
     * Encola un elemento al final.
     */
    public void encolar(T elemento) {
        cola.addLast(elemento);
    }

    /**
     * Desencola el elemento del frente (sin rotación).
     * @return el elemento del frente, o null si está vacía.
     */
    public T desencolar() {
        if (estaVacia()) {
            return null;
        }
        return cola.removeFirst();
    }

    /**
     * Rota: saca el elemento del frente y lo vuelve a encolar al final.
     * Simula la rotación natural de un turno.
     * @return el elemento rotado.
     */
    public T rotar() {
        if (estaVacia()) {
            return null;
        }
        T elemento = cola.removeFirst();
        cola.addLast(elemento);
        return elemento;
    }

    /**
     * Consulta el elemento del frente sin removerlo.
     */
    public T frente() {
        if (estaVacia()) {
            return null;
        }
        return cola.getFirst();
    }

    /**
     * Verifica si la cola está vacía.
     */
    public boolean estaVacia() {
        return cola.isEmpty();
    }

    /**
     * Retorna el número de elementos en la cola.
     */
    public int tamanio() {
        return cola.size();
    }

    /**
     * Limpia todos los elementos de la cola.
     */
    public void limpiar() {
        cola.clear();
    }

    /**
     * Obtiene todos los elementos (para visualización).
     */
    public Object[] obtenerTodos() {
        return cola.toArray();
    }

    @Override
    public String toString() {
        return "ColaCircular: " + cola.toString();
    }
}
