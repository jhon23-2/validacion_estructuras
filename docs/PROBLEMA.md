# Problema: Sistema de Gestión del Parque Nacional Natural Tayrona

## 📍 Contexto

El Parque Nacional Natural Tayrona recibe miles de visitantes por la entrada de El Zaíno (Troncal del Caribe). El sistema debe gestionar:

- **Ingreso de visitantes** por franjas horarias con cupo limitado
- **Asignación de guías locales** en turno rotativo y equitativo
- **Rutas óptimas** entre puntos turísticos del parque
- **Control de capacidad** en porterías y filas
- **Historial de visitas** de cada turista
- **Simulación de cierres** de senderos por mantenimiento o clima

---

## 🎯 Problema principal

Diseñar un sistema en consola que permita:

1. **Gestionar visitantes** (registro, búsqueda rápida, historial)
2. **Controlar colas de ingreso** con capacidad fija (25 grupos por franja)
3. **Asignar guías** de forma equitativa en rotación
4. **Calcular rutas óptimas** entre puntos turísticos usando A*
5. **Analizar conectividad** de la red de senderos (BFS/DFS)
6. **Deshacer operaciones** en caso de error

---

## 🗺️ Escenario geográfico

Red de senderos entre puntos del parque:

```
El Zaíno (entrada) → Cañaveral → Arrecifes → La Arenilla → La Piscina → Cabo San Juan → Playa Brava → Calabazo
                         ↓
                    Castilletes
                         ↓
                      Pueblito
```

Cada sendero tiene:
- **Longitud real** (metros)
- **Coordenadas** (x, y) de cada punto
- **Estado** (abierto/cerrado)

---

## 📊 Estructuras de datos requeridas

| Estructura | Uso en el sistema |
|------------|-------------------|
| **E1 - Lista Doble** | Historial de visitas de cada visitante (navegable hacia atrás/adelante) |
| **E2 - Pila** | Deshacer operaciones, DFS en grafos, reconstrucción de rutas A* |
| **E3a - Cola (arreglo)** | Grupos en espera en portería (capacidad fija: 25) |
| **E3b - Cola (circular)** | Turno rotativo de guías locales |
| **E4 - Árbol AVL** | Índice rápido de visitantes por número de documento |
| **E5 - Matriz Dispersa** | Pesos/longitudes de senderos (solo aristas existentes) |
| **E6 - Grafo** | Red de senderos con algoritmos BFS, DFS y A* |

---

## ✅ Requisitos funcionales

### RF1: Carga inicial de datos
Leer desde archivos CSV:
- `visitantes.csv`: Mínimo 30 registros ficticios
- `vertices.csv`: Mínimo 10 puntos con coordenadas (x, y)
- `aristas.csv`: Mínimo 15 senderos con longitudes

### RF2: Gestión de visitantes
- Registrar nuevo visitante
- Buscar por documento
- Eliminar visitante
- Listar visitantes (orden por clave)
- Mostrar árbol AVL por niveles (altura y factor de equilibrio)

### RF3: Cola de portería
- Ingresar grupo a la cola
- Atender siguiente grupo
- Advertir cuando la cola esté llena (cupo: 25)

### RF4: Turno de guías
- Asignar guía al siguiente grupo (rotación)
- Guía termina recorrido → vuelve al final de la cola

### RF5: Historial de visitas
- Consultar historial de un visitante
- Navegar hacia atrás y adelante en el historial

### RF6: Deshacer operaciones
- Deshacer últimas operaciones de RF2, RF3, RF4
- Restaurar estado anterior de todas las estructuras

### RF7: Ruta más corta (A*)
- Calcular ruta óptima entre dos puntos
- Mostrar: ruta completa, costo total, nodos expandidos
- Usar heurística de distancia euclidiana

### RF8: Análisis de conectividad
- **BFS**: Mostrar puntos alcanzables desde un origen en k saltos
- **DFS**: Verificar si cerrar un sendero deja áreas aisladas

---

## 🚫 Restricciones

1. **Datos ficticios**: No usar información real de visitantes (Ley 1581/2012)
2. **Interfaz**: Menú de consola (no se requiere GUI)
3. **Entradas inválidas**: Mostrar mensaje claro sin cerrar el programa
4. **Heurística admisible**: La longitud de cada sendero debe ser ≥ distancia euclidiana entre sus extremos

---

## 📏 Criterios de éxito

- ✅ Las 6 estructuras funcionan correctamente
- ✅ Los 8 requisitos funcionales están implementados
- ✅ El sistema carga datos desde CSV al iniciar
- ✅ Las operaciones responden en tiempo razonable
- ✅ Los errores se manejan sin cerrar el programa
- ✅ El código es legible y está documentado
