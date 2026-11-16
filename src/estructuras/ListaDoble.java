package estructuras;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * E1: Lista doblemente enlazada para historial de visitas.
 * Permite navegación hacia adelante y hacia atrás.
 *
 * VERSIÓN CON COLECCIONES JAVA (para prototipo rápido).
 */
public class ListaDoble<T> {
    private LinkedList<T> lista;
    private ListIterator<T> iterador;
    private int posicionActual;

    public ListaDoble() {
        this.lista = new LinkedList<>();
        this.posicionActual = -1;
    }

    /**
     * Inserta un elemento al inicio de la lista.
     */
    public void insertarInicio(T elemento) {
        lista.addFirst(elemento);
        posicionActual++;
    }

    /**
     * Inserta un elemento al final de la lista.
     */
    public void insertarFin(T elemento) {
        lista.addLast(elemento);
    }

    /**
     * Elimina un elemento por valor.
     * @return true si se eliminó, false si no se encontró.
     */
    public boolean eliminar(T elemento) {
        int index = lista.indexOf(elemento);
        if (index != -1) {
            lista.remove(index);
            if (posicionActual >= lista.size()) {
                posicionActual = lista.size() - 1;
            }
            return true;
        }
        return false;
    }

    /**
     * Busca un elemento en la lista.
     */
    public boolean buscar(T elemento) {
        return lista.contains(elemento);
    }

    /**
     * Retorna el tamaño de la lista.
     */
    public int tamanio() {
        return lista.size();
    }

    /**
     * Verifica si la lista está vacía.
     */
    public boolean estaVacia() {
        return lista.isEmpty();
    }

    /**
     * Inicializa el iterador para navegación.
     */
    public void iniciarNavegacion() {
        if (!lista.isEmpty()) {
            iterador = lista.listIterator();
            posicionActual = -1;
        }
    }

    /**
     * Avanza al siguiente elemento.
     * @return el siguiente elemento, o null si no hay más.
     */
    public T siguiente() {
        if (iterador != null && iterador.hasNext()) {
            posicionActual++;
            return iterador.next();
        }
        return null;
    }

    /**
     * Retrocede al elemento anterior.
     * @return el elemento anterior, o null si no hay más.
     */
    public T anterior() {
        if (iterador != null && iterador.hasPrevious()) {
            posicionActual--;
            return iterador.previous();
        }
        return null;
    }

    /**
     * Retorna el elemento en la posición actual.
     */
    public T obtenerActual() {
        if (posicionActual >= 0 && posicionActual < lista.size()) {
            return lista.get(posicionActual);
        }
        return null;
    }

    /**
     * Retorna todos los elementos como array.
     */
    public Object[] obtenerTodos() {
        return lista.toArray();
    }

    /**
     * Obtiene elemento por índice.
     */
    public T obtener(int indice) {
        if (indice >= 0 && indice < lista.size()) {
            return lista.get(indice);
        }
        return null;
    }

    @Override
    public String toString() {
        return lista.toString();
    }
}
