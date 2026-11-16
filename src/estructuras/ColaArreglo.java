package estructuras;

import java.util.ArrayList;

/**
 * E3a: Cola con capacidad fija (FIFO - First In First Out)
 * Usada para: Cola de portería con cupo limitado (25 grupos)
 *
 * VERSIÓN CON COLECCIONES JAVA (para prototipo rápido).
 * Simula arreglo circular con capacidad fija.
 */
public class ColaArreglo<T> {
    private ArrayList<T> cola;
    private int capacidad;

    public ColaArreglo(int capacidad) {
        this.capacidad = capacidad;
        this.cola = new ArrayList<>(capacidad);
    }

    /**
     * Encola un elemento al final.
     * @return true si se pudo encolar, false si está llena.
     */
    public boolean encolar(T elemento) {
        if (estaLlena()) {
            return false;
        }
        cola.add(elemento);
        return true;
    }

    /**
     * Desencola el elemento del frente.
     * @return el elemento del frente, o null si está vacía.
     */
    public T desencolar() {
        if (estaVacia()) {
            return null;
        }
        return cola.remove(0);
    }

    /**
     * Consulta el elemento del frente sin removerlo.
     */
    public T frente() {
        if (estaVacia()) {
            return null;
        }
        return cola.get(0);
    }

    /**
     * Verifica si la cola está vacía.
     */
    public boolean estaVacia() {
        return cola.isEmpty();
    }

    /**
     * Verifica si la cola está llena.
     */
    public boolean estaLlena() {
        return cola.size() >= capacidad;
    }

    /**
     * Retorna el número de elementos en la cola.
     */
    public int tamanio() {
        return cola.size();
    }

    /**
     * Retorna la capacidad máxima de la cola.
     */
    public int capacidad() {
        return capacidad;
    }

    /**
     * Limpia todos los elementos de la cola.
     */
    public void limpiar() {
        cola.clear();
    }

    @Override
    public String toString() {
        return String.format("Cola [%d/%d]: %s", cola.size(), capacidad, cola);
    }
}
