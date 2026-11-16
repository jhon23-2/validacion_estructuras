package app;

import estructuras.*;
import modelo.Visitante;
import java.io.*;
import java.util.*;

/**
 * Sistema principal del Parque Nacional Natural Tayrona.
 * Coordina todas las estructuras de datos y requisitos funcionales.
 */
public class SistemaParqueTayrona {

    // Estructuras de datos (E1-E6)
    private ListaDoble<String> historialGeneral;                      // E1
    private Map<String, ListaDoble<String>> historialesPorVisitante; // E1 por visitante
    private Pila<Operacion> pilaDeshacer;                            // E2
    private ColaArreglo<String> colaPorteria;                        // E3a
    private ColaCircular<String> colaGuias;                          // E3b
    private ArbolAVL<String, Visitante> arbolVisitantes;             // E4
    private Grafo grafo;                                             // E6 (usa E5 internamente)

    // Datos auxiliares
    private Map<String, Visitante> visitantesPorDoc;
    private List<String> guiasDisponibles;

    // Constantes
    private static final int CAPACIDAD_PORTERIA = 25;

    public SistemaParqueTayrona() {
        // Inicializar estructuras
        historialGeneral = new ListaDoble<>();
        historialesPorVisitante = new HashMap<>();
        pilaDeshacer = new Pila<>();
        colaPorteria = new ColaArreglo<>(CAPACIDAD_PORTERIA);
        colaGuias = new ColaCircular<>();
        arbolVisitantes = new ArbolAVL<>();
        grafo = new Grafo();
        visitantesPorDoc = new HashMap<>();
        guiasDisponibles = new ArrayList<>();
    }

    // ==================== RF1: CARGA DE DATOS ====================

    /**
     * RF1: Carga datos desde archivos CSV.
     */
    public void cargarDatos(String rutaVisitantes, String rutaVertices, String rutaAristas) {
        System.out.println("\n=== CARGANDO DATOS ===");

        try {
            // Cargar visitantes
            int visitantesCount = cargarVisitantes(rutaVisitantes);
            System.out.println("✓ Visitantes cargados: " + visitantesCount);

            // Cargar vértices del grafo
            int verticesCount = cargarVertices(rutaVertices);
            System.out.println("✓ Vértices cargados: " + verticesCount);

            // Inicializar matriz de pesos
            grafo.inicializarMatrizPesos();

            // Cargar aristas
            int aristasCount = cargarAristas(rutaAristas);
            System.out.println("✓ Aristas cargadas: " + aristasCount);

            // Cargar guías
            cargarGuias();
            System.out.println("✓ Guías disponibles: " + guiasDisponibles.size());

            System.out.println("\n✓ Datos cargados exitosamente\n");

        } catch (IOException e) {
            System.err.println("✗ ERROR al cargar datos: " + e.getMessage());
        }
    }

    private int cargarVisitantes(String ruta) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(ruta));
        String linea = br.readLine(); // Saltar encabezado

        int count = 0;
        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split(",");
            if (partes.length >= 4) {
                Visitante v = new Visitante(partes[0], partes[1], partes[2], partes[3]);
                arbolVisitantes.insertar(v.getDocumento(), v);
                visitantesPorDoc.put(v.getDocumento(), v);
                historialesPorVisitante.put(v.getDocumento(), new ListaDoble<>());
                count++;
            }
        }
        br.close();
        return count;
    }

    private int cargarVertices(String ruta) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(ruta));
        String linea = br.readLine(); // Saltar encabezado

        int count = 0;
        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split(",");
            if (partes.length >= 4) {
                int id = Integer.parseInt(partes[0]);
                String nombre = partes[1];
                double x = Double.parseDouble(partes[2]);
                double y = Double.parseDouble(partes[3]);
                grafo.agregarVertice(nombre, x, y);
                count++;
            }
        }
        br.close();
        return count;
    }

    private int cargarAristas(String ruta) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(ruta));
        String linea = br.readLine(); // Saltar encabezado

        int count = 0;
        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split(",");
            if (partes.length >= 3) {
                String origen = partes[0];
                String destino = partes[1];
                double longitud = Double.parseDouble(partes[2]);
                grafo.agregarArista(origen, destino, longitud);
                count++;
            }
        }
        br.close();
        return count;
    }

    private void cargarGuias() {
        guiasDisponibles.add("Carlos Martínez");
        guiasDisponibles.add("María Hernández");
        guiasDisponibles.add("Jorge Silva");
        guiasDisponibles.add("Ana Rodríguez");
        guiasDisponibles.add("Luis García");

        for (String guia : guiasDisponibles) {
            colaGuias.encolar(guia);
        }
    }

    // ==================== RF2: GESTIÓN DE VISITANTES ====================

    /**
     * RF2: Registra un nuevo visitante.
     */
    public boolean registrarVisitante(String documento, String nombre, String email, String telefono) {
        if (arbolVisitantes.contiene(documento)) {
            System.out.println("✗ ERROR: Ya existe un visitante con documento " + documento);
            return false;
        }

        Visitante v = new Visitante(documento, nombre, email, telefono);
        arbolVisitantes.insertar(documento, v);
        visitantesPorDoc.put(documento, v);
        historialesPorVisitante.put(documento, new ListaDoble<>());

        // Guardar en pila de deshacer
        pilaDeshacer.apilar(new Operacion("REGISTRAR_VISITANTE", documento));

        System.out.println("✓ Visitante registrado exitosamente");
        return true;
    }

    /**
     * RF2: Busca un visitante por documento.
     */
    public Visitante buscarVisitante(String documento) {
        return arbolVisitantes.buscar(documento);
    }

    /**
     * RF2: Elimina un visitante.
     */
    public boolean eliminarVisitante(String documento) {
        Visitante v = arbolVisitantes.buscar(documento);
        if (v == null) {
            System.out.println("✗ ERROR: Visitante no encontrado");
            return false;
        }

        // Guardar en pila de deshacer
        pilaDeshacer.apilar(new Operacion("ELIMINAR_VISITANTE", v));

        arbolVisitantes.eliminar(documento);
        visitantesPorDoc.remove(documento);
        historialesPorVisitante.remove(documento);

        System.out.println("✓ Visitante eliminado exitosamente");
        return true;
    }

    /**
     * RF2: Lista visitantes ordenados.
     */
    public void listarVisitantes() {
        List<Visitante> visitantes = arbolVisitantes.obtenerValores();

        if (visitantes.isEmpty()) {
            System.out.println("No hay visitantes registrados");
            return;
        }

        System.out.println("\n=== VISITANTES REGISTRADOS ===");
        System.out.println(arbolVisitantes);
        System.out.println("\nTotal: " + visitantes.size() + " visitantes\n");

        for (int i = 0; i < Math.min(10, visitantes.size()); i++) {
            System.out.println((i + 1) + ". " + visitantes.get(i));
        }

        if (visitantes.size() > 10) {
            System.out.println("... y " + (visitantes.size() - 10) + " más");
        }
    }

    // ==================== RF3: COLA DE PORTERÍA ====================

    /**
     * RF3: Ingresa un grupo a la cola de portería.
     */
    public boolean ingresarGrupoPorteria(String nombreGrupo) {
        if (colaPorteria.estaLlena()) {
            System.out.println("✗ ERROR: Cola de portería llena. Cupo máximo: " + CAPACIDAD_PORTERIA);
            return false;
        }

        colaPorteria.encolar(nombreGrupo);
        pilaDeshacer.apilar(new Operacion("INGRESAR_PORTERIA", nombreGrupo));

        System.out.println("✓ Grupo ingresado a la cola (" + colaPorteria.tamanio() + "/" + CAPACIDAD_PORTERIA + ")");
        return true;
    }

    /**
     * RF3: Atiende el siguiente grupo en portería.
     */
    public String atenderGrupoPorteria() {
        if (colaPorteria.estaVacia()) {
            System.out.println("✗ ERROR: No hay grupos en cola de portería");
            return null;
        }

        String grupo = colaPorteria.desencolar();
        pilaDeshacer.apilar(new Operacion("ATENDER_PORTERIA", grupo));

        System.out.println("✓ Atendiendo grupo: " + grupo);
        System.out.println("  Grupos en cola: " + colaPorteria.tamanio() + "/" + CAPACIDAD_PORTERIA);
        return grupo;
    }

    // ==================== RF4: TURNO DE GUÍAS ====================

    /**
     * RF4: Asigna un guía al siguiente grupo (rotación).
     */
    public String asignarGuia() {
        if (colaGuias.estaVacia()) {
            System.out.println("✗ ERROR: No hay guías disponibles");
            return null;
        }

        String guia = colaGuias.rotar(); // Saca del frente y vuelve al final
        pilaDeshacer.apilar(new Operacion("ASIGNAR_GUIA", guia));

        System.out.println("✓ Guía asignado: " + guia);
        System.out.println("  El guía volverá al final de la rotación al terminar el recorrido");
        return guia;
    }

    // ==================== RF5: HISTORIAL ====================

    /**
     * RF5: Agrega una entrada al historial de un visitante.
     */
    public void agregarHistorial(String documento, String evento) {
        if (!historialesPorVisitante.containsKey(documento)) {
            System.out.println("✗ ERROR: Visitante no encontrado");
            return;
        }

        String entrada = new Date() + " - " + evento;
        historialesPorVisitante.get(documento).insertarFin(entrada);
    }

    /**
     * RF5: Consulta y navega el historial de un visitante.
     */
    public void consultarHistorial(String documento, Scanner scanner) {
        Visitante v = buscarVisitante(documento);
        if (v == null) {
            System.out.println("✗ ERROR: Visitante no encontrado");
            return;
        }

        ListaDoble<String> historial = historialesPorVisitante.get(documento);
        if (historial.estaVacia()) {
            System.out.println("El visitante no tiene historial de visitas");
            return;
        }

        System.out.println("\n=== HISTORIAL DE " + v.getNombre() + " ===");

        historial.iniciarNavegacion();
        String actual = historial.siguiente();

        while (actual != null) {
            System.out.println("\n" + actual);
            System.out.println("\n[A] Anterior | [S] Siguiente | [0] Salir");
            System.out.print("Opción: ");

            String opcion = scanner.nextLine().trim().toUpperCase();

            if (opcion.equals("S")) {
                String sig = historial.siguiente();
                if (sig != null) {
                    actual = sig;
                } else {
                    System.out.println("Ya estás en el último elemento");
                }
            } else if (opcion.equals("A")) {
                String ant = historial.anterior();
                if (ant != null) {
                    actual = ant;
                } else {
                    System.out.println("Ya estás en el primer elemento");
                }
            } else if (opcion.equals("0")) {
                break;
            }
        }
    }

    // ==================== RF6: DESHACER ====================

    /**
     * RF6: Deshace la última operación.
     */
    public void deshacer() {
        if (pilaDeshacer.estaVacia()) {
            System.out.println("✗ ERROR: No hay operaciones para deshacer");
            return;
        }

        Operacion op = pilaDeshacer.desapilar();
        System.out.println("\nDeshaciendo: " + op.tipo);

        switch (op.tipo) {
            case "REGISTRAR_VISITANTE":
                String doc = (String) op.dato;
                arbolVisitantes.eliminar(doc);
                visitantesPorDoc.remove(doc);
                historialesPorVisitante.remove(doc);
                System.out.println("✓ Visitante " + doc + " eliminado");
                break;

            case "ELIMINAR_VISITANTE":
                Visitante v = (Visitante) op.dato;
                arbolVisitantes.insertar(v.getDocumento(), v);
                visitantesPorDoc.put(v.getDocumento(), v);
                historialesPorVisitante.put(v.getDocumento(), new ListaDoble<>());
                System.out.println("✓ Visitante " + v.getDocumento() + " restaurado");
                break;

            case "INGRESAR_PORTERIA":
                colaPorteria.desencolar();
                System.out.println("✓ Grupo removido de portería");
                break;

            case "ATENDER_PORTERIA":
                String grupo = (String) op.dato;
                colaPorteria.encolar(grupo);
                System.out.println("✓ Grupo devuelto a portería");
                break;

            default:
                System.out.println("Operación no reversible");
        }

        System.out.println("Operaciones en pila: " + pilaDeshacer.tamanio());
    }

    // ==================== RF7: RUTA MÁS CORTA (A*) ====================

    /**
     * RF7: Calcula la ruta más corta entre dos puntos usando A*.
     */
    public void calcularRutaMasCorta(String origen, String destino) {
        if (!grafo.existeVertice(origen)) {
            System.out.println("✗ ERROR: Punto '" + origen + "' no existe");
            mostrarPuntosDisponibles();
            return;
        }

        if (!grafo.existeVertice(destino)) {
            System.out.println("✗ ERROR: Punto '" + destino + "' no existe");
            mostrarPuntosDisponibles();
            return;
        }

        System.out.println("\nCalculando ruta con A*...\n");

        Grafo.ResultadoAStar resultado = grafo.aStar(origen, destino);

        if (resultado.ruta == null) {
            System.out.println("✗ No existe ruta entre " + origen + " y " + destino);
            return;
        }

        System.out.println("=== RUTA MÁS CORTA ===");
        System.out.println("Origen: " + origen);
        System.out.println("Destino: " + destino);
        System.out.println();

        System.out.print("Ruta: ");
        for (int i = 0; i < resultado.ruta.size(); i++) {
            System.out.print(resultado.ruta.get(i));
            if (i < resultado.ruta.size() - 1) {
                System.out.print(" → ");
            }
        }
        System.out.println();

        System.out.printf("Costo total: %.0f metros\n", resultado.costo);
        System.out.println("Nodos expandidos: " + resultado.nodosExpandidos);

        System.out.println("\nDetalles del recorrido:");
        for (int i = 0; i < resultado.ruta.size() - 1; i++) {
            String p1 = resultado.ruta.get(i);
            String p2 = resultado.ruta.get(i + 1);
            double peso = grafo.obtenerPeso(p1, p2);
            System.out.printf("%d. %s → %s (%.0fm)\n", i + 1, p1, p2, peso);
        }
    }

    // ==================== RF8: ANÁLISIS DE CONECTIVIDAD ====================

    /**
     * RF8: Muestra puntos alcanzables en k saltos (BFS).
     */
    public void puntosAlcanzables(String origen, int k) {
        if (!grafo.existeVertice(origen)) {
            System.out.println("✗ ERROR: Punto '" + origen + "' no existe");
            mostrarPuntosDisponibles();
            return;
        }

        System.out.println("\n=== PUNTOS ALCANZABLES EN " + k + " SALTOS ===");
        System.out.println("Desde: " + origen + "\n");

        Map<String, Integer> niveles = grafo.bfs(origen, k);

        // Agrupar por nivel
        Map<Integer, List<String>> porNivel = new TreeMap<>();
        for (Map.Entry<String, Integer> entry : niveles.entrySet()) {
            porNivel.putIfAbsent(entry.getValue(), new ArrayList<>());
            porNivel.get(entry.getValue()).add(entry.getKey());
        }

        for (Map.Entry<Integer, List<String>> entry : porNivel.entrySet()) {
            System.out.println("Nivel " + entry.getKey() + ": " + String.join(", ", entry.getValue()));
        }

        System.out.println("\nTotal de puntos alcanzables: " + niveles.size());
    }

    /**
     * RF8: Verifica conectividad al cerrar un sendero (DFS).
     */
    public void verificarConectividad(String puntoA, String puntoB) {
        if (!grafo.existeVertice(puntoA) || !grafo.existeVertice(puntoB)) {
            System.out.println("✗ ERROR: Uno o ambos puntos no existen");
            mostrarPuntosDisponibles();
            return;
        }

        System.out.println("\nSimulando cierre del sendero " + puntoA + " ↔ " + puntoB + "...\n");

        Set<String> aristasExcluidas = new HashSet<>();
        aristasExcluidas.add(puntoA + "-" + puntoB);
        aristasExcluidas.add(puntoB + "-" + puntoA);

        boolean hayConexion = grafo.dfs(puntoA, puntoB, aristasExcluidas);

        System.out.println("=== ANÁLISIS DE CONECTIVIDAD ===");
        if (hayConexion) {
            System.out.println("Estado: RED CONECTADA ✓");
            System.out.println("\nExisten rutas alternativas entre " + puntoA + " y " + puntoB);
            System.out.println("Recomendación: El sendero puede cerrarse temporalmente.");
        } else {
            System.out.println("Estado: RED DESCONECTADA ⚠️");
            System.out.println("\nNO existen rutas alternativas entre " + puntoA + " y " + puntoB);
            System.out.println("Recomendación: NO cerrar este sendero. Es crítico para la conectividad.");
        }
    }

    // ==================== UTILIDADES ====================

    private void mostrarPuntosDisponibles() {
        System.out.println("\nPuntos disponibles:");
        List<String> puntos = new ArrayList<>(grafo.obtenerVertices());
        Collections.sort(puntos);
        for (String punto : puntos) {
            System.out.println("  - " + punto);
        }
    }

    // Clase interna para operaciones deshacibles
    private static class Operacion {
        String tipo;
        Object dato;

        Operacion(String tipo, Object dato) {
            this.tipo = tipo;
            this.dato = dato;
        }
    }
}
