package app;

import modelo.Visitante;
import java.util.Scanner;

/**
 * Punto de entrada del Sistema de Gestión del Parque Nacional Natural Tayrona.
 * Implementa menú interactivo para todos los requisitos funcionales (RF1-RF8).
 */
public class Main {

    private static SistemaParqueTayrona sistema;
    private static Scanner scanner;

    public static void main(String[] args) {
        sistema = new SistemaParqueTayrona();
        scanner = new Scanner(System.in);

        // RF1: Cargar datos al iniciar
        cargarDatosIniciales();

        // Menú principal
        boolean continuar = true;
        while (continuar) {
            mostrarMenu();
            int opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    registrarVisitante();
                    break;
                case 2:
                    buscarVisitante();
                    break;
                case 3:
                    eliminarVisitante();
                    break;
                case 4:
                    listarVisitantes();
                    break;
                case 5:
                    ingresarGrupoPorteria();
                    break;
                case 6:
                    atenderGrupoPorteria();
                    break;
                case 7:
                    asignarGuia();
                    break;
                case 8:
                    consultarHistorial();
                    break;
                case 9:
                    deshacerOperacion();
                    break;
                case 10:
                    calcularRutaMasCorta();
                    break;
                case 11:
                    puntosAlcanzables();
                    break;
                case 12:
                    verificarConectividad();
                    break;
                case 0:
                    continuar = false;
                    System.out.println("\n¡Gracias por usar el Sistema Parque Tayrona!\n");
                    break;
                default:
                    System.out.println("✗ Opción inválida. Intente nuevamente.");
            }
        }

        scanner.close();
    }

    private static void cargarDatosIniciales() {
        String rutaDatos = "datos/";
        sistema.cargarDatos(
                rutaDatos + "visitantes.csv",
                rutaDatos + "vertices.csv",
                rutaDatos + "aristas.csv"
        );

        // Agregar algunos eventos al historial de los primeros visitantes
        sistema.agregarHistorial("1234567890", "Ingreso portería");
        sistema.agregarHistorial("1234567890", "Asignado guía Carlos");
        sistema.agregarHistorial("1234567890", "Recorrido Cañaveral-Arrecifes");
        sistema.agregarHistorial("2345678901", "Ingreso portería");
        sistema.agregarHistorial("2345678901", "Asignado guía María");
    }

    private static void mostrarMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("      SISTEMA PARQUE NACIONAL NATURAL TAYRONA");
        System.out.println("=".repeat(50));
        System.out.println("\n--- GESTIÓN DE VISITANTES (RF2) ---");
        System.out.println("1.  Registrar visitante");
        System.out.println("2.  Buscar visitante");
        System.out.println("3.  Eliminar visitante");
        System.out.println("4.  Listar visitantes (AVL)");

        System.out.println("\n--- COLAS Y TURNOS (RF3, RF4) ---");
        System.out.println("5.  Ingresar grupo a portería");
        System.out.println("6.  Atender grupo en portería");
        System.out.println("7.  Asignar guía");

        System.out.println("\n--- HISTORIAL Y DESHACER (RF5, RF6) ---");
        System.out.println("8.  Consultar historial de visitante");
        System.out.println("9.  Deshacer última operación");

        System.out.println("\n--- ALGORITMOS SOBRE GRAFOS (RF7, RF8) ---");
        System.out.println("10. Calcular ruta más corta (A*)");
        System.out.println("11. Puntos alcanzables (BFS)");
        System.out.println("12. Verificar conectividad (DFS)");

        System.out.println("\n0.  Salir");
        System.out.println("\n" + "=".repeat(50));
        System.out.print("Seleccione opción: ");
    }

    private static int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // ==================== OPCIONES DEL MENÚ ====================

    private static void registrarVisitante() {
        System.out.println("\n--- REGISTRAR VISITANTE ---");

        System.out.print("Documento: ");
        String documento = scanner.nextLine().trim();

        if (documento.isEmpty()) {
            System.out.println("✗ Documento no puede estar vacío");
            return;
        }

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();

        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine().trim();

        sistema.registrarVisitante(documento, nombre, email, telefono);
    }

    private static void buscarVisitante() {
        System.out.println("\n--- BUSCAR VISITANTE ---");
        System.out.print("Documento: ");
        String documento = scanner.nextLine().trim();

        Visitante v = sistema.buscarVisitante(documento);

        if (v != null) {
            System.out.println("\n✓ Visitante encontrado:");
            System.out.println(v);
        } else {
            System.out.println("✗ ERROR: Visitante no encontrado");
        }
    }

    private static void eliminarVisitante() {
        System.out.println("\n--- ELIMINAR VISITANTE ---");
        System.out.print("Documento: ");
        String documento = scanner.nextLine().trim();

        sistema.eliminarVisitante(documento);
    }

    private static void listarVisitantes() {
        System.out.println("\n--- LISTAR VISITANTES ---");
        sistema.listarVisitantes();
    }

    private static void ingresarGrupoPorteria() {
        System.out.println("\n--- INGRESAR GRUPO A PORTERÍA ---");
        System.out.print("Nombre del grupo: ");
        String nombreGrupo = scanner.nextLine().trim();

        if (nombreGrupo.isEmpty()) {
            System.out.println("✗ Nombre del grupo no puede estar vacío");
            return;
        }

        sistema.ingresarGrupoPorteria(nombreGrupo);
    }

    private static void atenderGrupoPorteria() {
        System.out.println("\n--- ATENDER GRUPO EN PORTERÍA ---");
        sistema.atenderGrupoPorteria();
    }

    private static void asignarGuia() {
        System.out.println("\n--- ASIGNAR GUÍA ---");
        sistema.asignarGuia();
    }

    private static void consultarHistorial() {
        System.out.println("\n--- CONSULTAR HISTORIAL ---");
        System.out.print("Documento del visitante: ");
        String documento = scanner.nextLine().trim();

        sistema.consultarHistorial(documento, scanner);
    }

    private static void deshacerOperacion() {
        System.out.println("\n--- DESHACER OPERACIÓN ---");
        sistema.deshacer();
    }

    private static void calcularRutaMasCorta() {
        System.out.println("\n--- CALCULAR RUTA MÁS CORTA (A*) ---");

        System.out.print("Punto origen: ");
        String origen = scanner.nextLine().trim();

        System.out.print("Punto destino: ");
        String destino = scanner.nextLine().trim();

        if (origen.isEmpty() || destino.isEmpty()) {
            System.out.println("✗ Origen y destino no pueden estar vacíos");
            return;
        }

        sistema.calcularRutaMasCorta(origen, destino);
    }

    private static void puntosAlcanzables() {
        System.out.println("\n--- PUNTOS ALCANZABLES (BFS) ---");

        System.out.print("Punto origen: ");
        String origen = scanner.nextLine().trim();

        System.out.print("Número de saltos (k): ");
        int k;
        try {
            k = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("✗ Número de saltos inválido");
            return;
        }

        if (k < 0) {
            System.out.println("✗ El número de saltos debe ser positivo");
            return;
        }

        sistema.puntosAlcanzables(origen, k);
    }

    private static void verificarConectividad() {
        System.out.println("\n--- VERIFICAR CONECTIVIDAD (DFS) ---");

        System.out.print("Cerrar sendero desde: ");
        String puntoA = scanner.nextLine().trim();

        System.out.print("Cerrar sendero hasta: ");
        String puntoB = scanner.nextLine().trim();

        if (puntoA.isEmpty() || puntoB.isEmpty()) {
            System.out.println("✗ Los puntos no pueden estar vacíos");
            return;
        }

        sistema.verificarConectividad(puntoA, puntoB);
    }
}
