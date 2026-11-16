# Solución: Arquitectura e Implementación

## 🏗️ Arquitectura del sistema

```
┌─────────────────────────────────────────────────────┐
│                   app/Main.java                      │
│              (Menú principal + flujo)                 │
└─────────────┬───────────────────────────────────────┘
              │
              ├─── Carga datos CSV
              │
              v
┌─────────────────────────────────────────────────────┐
│         app/SistemaParqueTayrona.java                │
│         (Lógica de negocio + coordinación)           │
└───┬─────────────────────────────────────────────┬───┘
    │                                             │
    ├──── Estructuras de datos ────────┐         │
    │                                   │         │
    v                                   v         v
┌─────────────┐  ┌──────────────┐  ┌─────────────────┐
│ Visitante   │  │ Estructuras  │  │  Datos CSV      │
│ (modelo)    │  │ E1..E6       │  │ visitantes.csv  │
└─────────────┘  └──────────────┘  │ vertices.csv    │
                                   │ aristas.csv     │
                                   └─────────────────┘
```

---

## 📦 Estructura de carpetas

```
validacion_estructuras/
├── src/
│   ├── estructuras/
│   │   ├── ListaDoble.java       (E1)
│   │   ├── Pila.java              (E2)
│   │   ├── ColaArreglo.java       (E3a)
│   │   ├── ColaCircular.java      (E3b)
│   │   ├── ArbolAVL.java          (E4)
│   │   ├── MatrizDispersa.java    (E5)
│   │   └── Grafo.java             (E6)
│   ├── modelo/
│   │   └── Visitante.java
│   └── app/
│       ├── Main.java
│       └── SistemaParqueTayrona.java
├── datos/
│   ├── visitantes.csv
│   ├── vertices.csv
│   └── aristas.csv
├── docs/
│   ├── PROBLEMA.md
│   ├── SOLUCION.md
│   └── USO.md
└── README.md
```

---

## 🔧 Implementación de estructuras

### E1: Lista Doble
- **Implementación**: Lista doblemente enlazada con nodos
- **Operaciones**: insertar inicio/fin, eliminar, buscar, navegar adelante/atrás
- **Uso**: Historial de visitas por visitante

```
Nodo: [anterior | dato | siguiente]
      ←─────────────────────────→
```

### E2: Pila
- **Implementación**: Pila con nodos enlazados (LIFO)
- **Operaciones**: push, pop, peek, isEmpty
- **Usos**: 
  - Deshacer operaciones
  - DFS en grafos
  - Reconstrucción de rutas A*

### E3a: Cola en Arreglo
- **Implementación**: Arreglo circular con índices frente/final
- **Capacidad**: Fija (25 grupos)
- **Operaciones**: enqueue, dequeue, isFull, isEmpty
- **Uso**: Cola de portería

```
Arreglo circular:
[_, _, X, X, X, _, _]
       ^frente  ^final
```

### E3b: Cola Circular
- **Implementación**: Lista circular simplemente enlazada
- **Operaciones**: enqueue, dequeue, rotate
- **Uso**: Turno rotativo de guías

```
Guía1 → Guía2 → Guía3 → Guía4 ⤴
  ^──────────────────────────┘
```

### E4: Árbol AVL
- **Implementación**: Árbol binario autobalanceado
- **Rotaciones**: II (izq-izq), DD (der-der), ID (izq-der), DI (der-izq)
- **Operaciones**: insertar, eliminar, buscar, recorridos
- **Uso**: Índice de visitantes por documento

```
Balance: |altura(izq) - altura(der)| ≤ 1
```

### E5: Matriz Dispersa
- **Implementación**: Vector de filas; cada fila = lista ordenada de (columna, valor)
- **Ventaja**: Solo almacena valores != 0
- **Uso**: Pesos de senderos (grafo disperso)

```
Fila 0: [(2, 1250), (5, 2100)]
Fila 1: []
Fila 2: [(0, 1250), (3, 1400)]
```

### E6: Grafo
- **Implementación**: Grafo no dirigido con lista de adyacencia
- **Pesos**: Almacenados en E5 (Matriz Dispersa)
- **Algoritmos**:
  - **BFS**: Cola E3, distancia por niveles
  - **DFS**: Pila E2, detección de componentes
  - **A***: Heurística euclidiana + reconstrucción con E2

---

## 🧮 Algoritmos clave

### A* (Ruta más corta)

```
1. Inicializar:
   - listaAbierta = {inicio}
   - listaCerrada = {}
   - g[inicio] = 0
   - h[inicio] = distancia(inicio, destino)
   - f[inicio] = g + h

2. Mientras listaAbierta no vacía:
   - actual = nodo con menor f en listaAbierta
   - si actual == destino:
       → reconstruir ruta desde padres (usando Pila E2)
   
   - mover actual a listaCerrada
   
   - para cada vecino de actual:
       si vecino en listaCerrada: continuar
       
       g_nuevo = g[actual] + peso(actual, vecino)
       
       si vecino no en listaAbierta O g_nuevo < g[vecino]:
           padres[vecino] = actual
           g[vecino] = g_nuevo
           h[vecino] = distanciaEuclidiana(vecino, destino)
           f[vecino] = g[vecino] + h[vecino]
           agregar vecino a listaAbierta

3. Si listaAbierta queda vacía → no hay ruta
```

**Heurística admisible**: 
```java
h(n) = √((x₂-x₁)² + (y₂-y₁)²)
```

### BFS (Alcance en k saltos)

```
1. visited = {inicio}
   cola = {(inicio, 0)}  // (nodo, nivel)

2. Mientras cola no vacía:
   - (actual, nivel) = dequeue()
   - si nivel > k: terminar
   
   - para cada vecino de actual:
       si vecino no visitado:
           visited.add(vecino)
           enqueue((vecino, nivel+1))

3. Retornar visited
```

### DFS (Conectividad)

```
1. Simular cierre de arista (u, v)
2. Ejecutar DFS desde u
3. Si v no alcanzable → red desconectada
```

---

## 💾 Formato de datos CSV

### visitantes.csv
```csv
documento,nombre,email,telefono
1234567890,Juan Pérez,juan@mail.com,3001234567
...
```

### vertices.csv
```csv
id,nombre,x,y
1,El Zaino,0,0
2,Cañaveral,1200,300
...
```

### aristas.csv
```csv
origen,destino,longitud
El Zaino,Cañaveral,1250
Cañaveral,Arrecifes,1400
...
```

**Restricción**: `longitud ≥ distanciaEuclidiana(origen, destino)` para que la heurística sea admisible.

---

## ⚙️ Flujo de ejecución

1. **Inicio**: Cargar datos CSV → estructuras
2. **Menú principal**: Mostrar opciones 1-10
3. **Procesamiento**: Ejecutar operación seleccionada
4. **Validación**: Manejar errores sin cerrar el programa
5. **Resultado**: Mostrar salida y volver al menú
6. **Salida**: Opción 0 para salir

---

## 🔄 Operación "Deshacer" (RF6)

Cada operación que modifica el estado guarda un snapshot en la pila:

```java
class Operacion {
    String tipo;           // "REGISTRAR", "ATENDER_COLA", etc.
    Object estadoAnterior; // Estado previo de la estructura
}
```

Al deshacer:
1. Pop de la pila de operaciones
2. Restaurar estado anterior según tipo
3. Revertir cambios en todas las estructuras afectadas

---

## 📊 Complejidad temporal

| Estructura | Operación | Complejidad |
|------------|-----------|-------------|
| **ListaDoble** | Insertar inicio/fin | O(1) |
|                | Buscar/eliminar | O(n) |
| **Pila** | Push/Pop | O(1) |
| **ColaArreglo** | Enqueue/Dequeue | O(1) |
| **ColaCircular** | Enqueue/Dequeue | O(1) |
| **ArbolAVL** | Insertar/Buscar/Eliminar | O(log n) |
| **MatrizDispersa** | Obtener/Asignar | O(k), k = no nulos en fila |
| **Grafo BFS** | Recorrido | O(V + E) |
| **Grafo DFS** | Recorrido | O(V + E) |
| **Grafo A*** | Ruta más corta | O((V + E) log V) con heap |

---

## 🎯 Decisiones de diseño

### ¿Por qué Matriz Dispersa para el grafo?
- **Grafo disperso**: En un parque con 10 puntos, hay ~15 senderos (no 45)
- **Ahorro de memoria**: Solo almacena conexiones existentes
- **Eficiencia**: Acceso O(k) donde k = conexiones de un punto

### ¿Por qué dos implementaciones de cola?
- **E3a (arreglo)**: Capacidad fija conocida, memoria contigua (cache-friendly)
- **E3b (circular)**: Tamaño dinámico, rotación natural de guías

### ¿Por qué AVL y no BST simple?
- **Garantía O(log n)**: Búsqueda rápida incluso con datos ordenados
- **Caso de uso**: Visitantes pueden registrarse en orden de llegada
