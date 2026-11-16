package estructuras;

import java.util.*;

/**
 * E6: Grafo ponderado no dirigido
 * Usada para: Red de senderos del parque
 * Algoritmos: BFS, DFS, A* (ruta más corta)
 *
 * VERSIÓN CON COLECCIONES JAVA (para prototipo rápido).
 */
public class Grafo {

    // Clase interna para representar un punto con coordenadas
    public static class Punto {
        String nombre;
        double x;
        double y;

        public Punto(String nombre, double x, double y) {
            this.nombre = nombre;
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return nombre;
        }
    }

    // Clase interna para representar una arista
    private static class Arista {
        String destino;
        double peso;

        Arista(String destino, double peso) {
            this.destino = destino;
            this.peso = peso;
        }
    }

    // Estructura del grafo
    private Map<String, Punto> vertices;
    private Map<String, List<Arista>> adyacencias;
    private MatrizDispersa pesos; // E5 para almacenar pesos
    private Map<String, Integer> indiceVertices;

    public Grafo() {
        this.vertices = new HashMap<>();
        this.adyacencias = new HashMap<>();
        this.indiceVertices = new HashMap<>();
    }

    /**
     * Agrega un vértice con coordenadas.
     */
    public void agregarVertice(String nombre, double x, double y) {
        if (!vertices.containsKey(nombre)) {
            vertices.put(nombre, new Punto(nombre, x, y));
            adyacencias.put(nombre, new ArrayList<>());
            indiceVertices.put(nombre, indiceVertices.size());
        }
    }

    /**
     * Inicializa la matriz dispersa después de agregar todos los vértices.
     */
    public void inicializarMatrizPesos() {
        int n = vertices.size();
        this.pesos = new MatrizDispersa(n, n);
    }

    /**
     * Agrega una arista no dirigida con peso.
     */
    public void agregarArista(String origen, String destino, double peso) {
        if (!vertices.containsKey(origen) || !vertices.containsKey(destino)) {
            throw new IllegalArgumentException("Vértice no existe");
        }

        // Agregar en ambas direcciones (no dirigido)
        adyacencias.get(origen).add(new Arista(destino, peso));
        adyacencias.get(destino).add(new Arista(origen, peso));

        // Guardar en matriz dispersa
        if (pesos != null) {
            int i = indiceVertices.get(origen);
            int j = indiceVertices.get(destino);
            pesos.asignar(i, j, peso);
            pesos.asignar(j, i, peso);
        }
    }

    /**
     * Obtiene los vecinos de un vértice.
     */
    public List<String> obtenerVecinos(String vertice) {
        List<String> vecinos = new ArrayList<>();
        if (adyacencias.containsKey(vertice)) {
            for (Arista a : adyacencias.get(vertice)) {
                vecinos.add(a.destino);
            }
        }
        return vecinos;
    }

    /**
     * Obtiene el peso de una arista.
     */
    public double obtenerPeso(String origen, String destino) {
        if (adyacencias.containsKey(origen)) {
            for (Arista a : adyacencias.get(origen)) {
                if (a.destino.equals(destino)) {
                    return a.peso;
                }
            }
        }
        return Double.POSITIVE_INFINITY;
    }

    /**
     * Calcula la distancia euclidiana entre dos puntos.
     */
    public double distanciaEuclidiana(String p1, String p2) {
        Punto punto1 = vertices.get(p1);
        Punto punto2 = vertices.get(p2);
        if (punto1 == null || punto2 == null) return 0;

        double dx = punto2.x - punto1.x;
        double dy = punto2.y - punto1.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * BFS: Puntos alcanzables desde un origen en k saltos.
     * Usa ColaArreglo (E3a) o LinkedList como cola.
     */
    public Map<String, Integer> bfs(String inicio, int maxSaltos) {
        Map<String, Integer> niveles = new HashMap<>();
        Queue<String> cola = new LinkedList<>();
        Map<String, Integer> distancias = new HashMap<>();

        cola.add(inicio);
        distancias.put(inicio, 0);
        niveles.put(inicio, 0);

        while (!cola.isEmpty()) {
            String actual = cola.poll();
            int nivelActual = distancias.get(actual);

            if (nivelActual >= maxSaltos) continue;

            for (String vecino : obtenerVecinos(actual)) {
                if (!distancias.containsKey(vecino)) {
                    distancias.put(vecino, nivelActual + 1);
                    niveles.put(vecino, nivelActual + 1);
                    cola.add(vecino);
                }
            }
        }

        return niveles;
    }

    /**
     * DFS: Verifica si hay camino de origen a destino.
     * Usa Pila (E2) o Stack.
     */
    public boolean dfs(String origen, String destino, Set<String> aritasExcluidas) {
        Stack<String> pila = new Stack<>();
        Set<String> visitados = new HashSet<>();

        pila.push(origen);

        while (!pila.isEmpty()) {
            String actual = pila.pop();

            if (actual.equals(destino)) {
                return true;
            }

            if (visitados.contains(actual)) {
                continue;
            }

            visitados.add(actual);

            for (String vecino : obtenerVecinos(actual)) {
                String arista = actual + "-" + vecino;
                String aristaInv = vecino + "-" + actual;

                // Saltar aristas excluidas
                if (aritasExcluidas != null &&
                    (aritasExcluidas.contains(arista) || aritasExcluidas.contains(aristaInv))) {
                    continue;
                }

                if (!visitados.contains(vecino)) {
                    pila.push(vecino);
                }
            }
        }

        return false;
    }

    /**
     * A*: Ruta más corta con heurística euclidiana.
     */
    public ResultadoAStar aStar(String inicio, String destino) {
        // Estructuras de A*
        PriorityQueue<NodoAStar> listaAbierta = new PriorityQueue<>();
        Set<String> listaCerrada = new HashSet<>();
        Map<String, Double> g = new HashMap<>(); // Costo acumulado
        Map<String, String> padres = new HashMap<>();

        // Inicializar
        g.put(inicio, 0.0);
        double h = distanciaEuclidiana(inicio, destino);
        listaAbierta.add(new NodoAStar(inicio, 0.0, h));

        int nodosExpandidos = 0;

        while (!listaAbierta.isEmpty()) {
            NodoAStar actual = listaAbierta.poll();
            nodosExpandidos++;

            if (actual.nombre.equals(destino)) {
                // Reconstruir ruta usando pila
                List<String> ruta = reconstruirRuta(padres, inicio, destino);
                double costoTotal = g.get(destino);
                return new ResultadoAStar(ruta, costoTotal, nodosExpandidos);
            }

            if (listaCerrada.contains(actual.nombre)) {
                continue;
            }

            listaCerrada.add(actual.nombre);

            // Explorar vecinos
            for (String vecino : obtenerVecinos(actual.nombre)) {
                if (listaCerrada.contains(vecino)) {
                    continue;
                }

                double pesoArista = obtenerPeso(actual.nombre, vecino);
                double gNuevo = g.get(actual.nombre) + pesoArista;

                if (!g.containsKey(vecino) || gNuevo < g.get(vecino)) {
                    g.put(vecino, gNuevo);
                    padres.put(vecino, actual.nombre);
                    double heuristica = distanciaEuclidiana(vecino, destino);
                    listaAbierta.add(new NodoAStar(vecino, gNuevo, heuristica));
                }
            }
        }

        // No hay ruta
        return new ResultadoAStar(null, Double.POSITIVE_INFINITY, nodosExpandidos);
    }

    /**
     * Reconstruye la ruta desde inicio a destino usando el mapa de padres.
     */
    private List<String> reconstruirRuta(Map<String, String> padres, String inicio, String destino) {
        Stack<String> pila = new Stack<>();
        String actual = destino;

        while (actual != null && !actual.equals(inicio)) {
            pila.push(actual);
            actual = padres.get(actual);
        }
        pila.push(inicio);

        List<String> ruta = new ArrayList<>();
        while (!pila.isEmpty()) {
            ruta.add(pila.pop());
        }

        return ruta;
    }

    /**
     * Obtiene todos los nombres de vértices.
     */
    public Set<String> obtenerVertices() {
        return new HashSet<>(vertices.keySet());
    }

    /**
     * Verifica si existe un vértice.
     */
    public boolean existeVertice(String nombre) {
        return vertices.containsKey(nombre);
    }

    // Clase interna para nodos en A*
    private static class NodoAStar implements Comparable<NodoAStar> {
        String nombre;
        double g; // Costo acumulado
        double h; // Heurística
        double f; // f = g + h

        NodoAStar(String nombre, double g, double h) {
            this.nombre = nombre;
            this.g = g;
            this.h = h;
            this.f = g + h;
        }

        @Override
        public int compareTo(NodoAStar otro) {
            return Double.compare(this.f, otro.f);
        }
    }

    // Clase para resultado de A*
    public static class ResultadoAStar {
        public List<String> ruta;
        public double costo;
        public int nodosExpandidos;

        ResultadoAStar(List<String> ruta, double costo, int nodosExpandidos) {
            this.ruta = ruta;
            this.costo = costo;
            this.nodosExpandidos = nodosExpandidos;
        }
    }
}
