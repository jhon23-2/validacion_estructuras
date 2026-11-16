# Sistema de Gestión del Parque Nacional Natural Tayrona

## 📋 Información del proyecto

**Curso:** 706132 - Estructuras de Datos  
**Escenario:** D - Parque Nacional Natural Tayrona  
**Lenguaje:** Java 11+  
**Versión:** 1.0 (Prototipo con colecciones Java)

---

## 🎯 Descripción

Sistema de consola para gestionar visitantes, colas de ingreso, asignación de guías y cálculo de rutas óptimas en el Parque Nacional Natural Tayrona. Implementa las 6 estructuras de datos requeridas y los 8 requisitos funcionales.

### Estructuras implementadas

| Código | Estructura | Archivo | Uso |
|--------|-----------|---------|-----|
| **E1** | Lista doble | `ListaDoble.java` | Historial de visitas |
| **E2** | Pila | `Pila.java` | Deshacer, DFS, reconstrucción de rutas |
| **E3a** | Cola en arreglo | `ColaArreglo.java` | Cola de portería (cap. 25) |
| **E3b** | Cola circular | `ColaCircular.java` | Turno rotativo de guías |
| **E4** | Árbol AVL | `ArbolAVL.java` | Índice de visitantes |
| **E5** | Matriz dispersa | `MatrizDispersa.java` | Pesos de senderos |
| **E6** | Grafo | `Grafo.java` | Red de senderos (BFS, DFS, A*) |

### Requisitos funcionales

- ✅ **RF1:** Carga de datos desde CSV
- ✅ **RF2:** Gestión de visitantes (AVL)
- ✅ **RF3:** Cola de portería
- ✅ **RF4:** Turno de guías
- ✅ **RF5:** Historial navegable
- ✅ **RF6:** Deshacer operaciones
- ✅ **RF7:** Ruta más corta (A*)
- ✅ **RF8:** Análisis de conectividad (BFS/DFS)

---

## 📂 Estructura del proyecto

```
validacion_estructuras/
├── src/
│   ├── estructuras/          # E1-E6: Estructuras de datos
│   │   ├── ListaDoble.java
│   │   ├── Pila.java
│   │   ├── ColaArreglo.java
│   │   ├── ColaCircular.java
│   │   ├── ArbolAVL.java
│   │   ├── MatrizDispersa.java
│   │   └── Grafo.java
│   ├── modelo/               # Modelo de datos
│   │   └── Visitante.java
│   └── app/                  # Aplicación
│       ├── Main.java
│       └── SistemaParqueTayrona.java
├── datos/                    # Archivos CSV
│   ├── visitantes.csv        (30 registros ficticios)
│   ├── vertices.csv          (10 puntos del parque)
│   └── aristas.csv           (15 senderos)
├── docs/                     # Documentación
│   ├── PROBLEMA.md           (Contexto y requisitos)
│   ├── SOLUCION.md           (Arquitectura e implementación)
│   └── USO.md                (Guía de uso del sistema)
└── README.md                 (Este archivo)
```

---

## 🚀 Cómo ejecutar

### 1. Compilar

```bash
cd C:\Users\jhonattan.almanza\Documents\validacion_estructuras

# Compilar todo el proyecto
javac -d bin -sourcepath src src/app/Main.java
```

### 2. Ejecutar

```bash
java -cp bin app.Main
```

### 3. Compilar y ejecutar en un solo comando (Git Bash)

```bash
javac -d bin -sourcepath src src/app/Main.java && java -cp bin app.Main
```

---

## 🧪 Casos de prueba rápidos

### Caso 1: Flujo completo de visitante
```
1. Opción 1: Registrar visitante (doc: 9999999999, nombre: Test User)
2. Opción 5: Ingresar grupo "Familia Test"
3. Opción 7: Asignar guía
4. Opción 8: Consultar historial (doc: 1234567890)
```

### Caso 2: Probar cola llena
```
1. Opción 5: Ingresar 25 grupos
2. Opción 5: Intentar ingresar grupo 26 → debe fallar
3. Opción 6: Atender un grupo
4. Opción 5: Ahora puede ingresar otro grupo
```

### Caso 3: Ruta más corta (A*)
```
1. Opción 10: Calcular ruta
   - Origen: El Zaino
   - Destino: Cabo San Juan
   
Resultado esperado: El Zaino → Cañaveral → Arrecifes → La Arenilla → La Piscina → Cabo San Juan
Costo: ~5150 metros
```

### Caso 4: Verificar conectividad crítica (DFS)
```
1. Opción 12: Verificar conectividad
   - Desde: Cañaveral
   - Hasta: Arrecifes
   
Resultado esperado: RED DESCONECTADA ⚠️ (sendero crítico)
```

### Caso 5: Deshacer operaciones
```
1. Opción 1: Registrar 2 visitantes
2. Opción 5: Ingresar 1 grupo
3. Opción 9: Deshacer 3 veces
4. Opción 4: Verificar que los visitantes ya no están
```

---

## 📊 Datos del sistema

### Puntos del parque (10 vértices)
- El Zaino (entrada principal)
- Cañaveral
- Castilletes
- Arrecifes
- La Arenilla
- La Piscina
- Cabo San Juan del Guía
- Playa Brava
- Calabazo
- Pueblito

### Senderos (15 aristas)
Todos con longitudes realistas (≥ distancia euclidiana entre extremos).

### Visitantes
30 registros ficticios cargados desde `visitantes.csv`.

### Guías
5 guías locales en turno rotativo.

---

## 🔧 Tecnologías utilizadas

- **Java 11+**: Lenguaje de programación
- **Colecciones Java**: HashMap, ArrayList, LinkedList, TreeMap, Stack, Queue, PriorityQueue
- **CSV**: Formato de datos

⚠️ **NOTA:** Esta es la **versión 1.0 (prototipo)** que usa colecciones de Java. La **versión 2.0** implementará las estructuras desde cero sin usar colecciones prohibidas.

---

## 📖 Documentación adicional

- **[PROBLEMA.md](docs/PROBLEMA.md)**: Contexto del problema y requisitos funcionales
- **[SOLUCION.md](docs/SOLUCION.md)**: Arquitectura, algoritmos y decisiones de diseño
- **[USO.md](docs/USO.md)**: Guía detallada de uso con ejemplos

---

## 🎓 Algoritmos implementados

### A* (Ruta más corta)
```
f(n) = g(n) + h(n)
donde:
  g(n) = costo acumulado desde el inicio
  h(n) = distancia euclidiana al destino (heurística admisible)
```

### BFS (Búsqueda en anchura)
Usado para encontrar puntos alcanzables en k saltos desde un origen.

### DFS (Búsqueda en profundidad)
Usado para verificar conectividad al simular el cierre de un sendero.

---

## 📝 Notas importantes

1. **Datos ficticios**: Todos los visitantes son ficticios (cumple Ley 1581/2012)
2. **Heurística admisible**: Todas las longitudes de senderos son ≥ distancia euclidiana
3. **Manejo de errores**: El sistema maneja entradas inválidas sin cerrarse
4. **Operaciones reversibles**: Las operaciones críticas pueden deshacerse (RF6)

---

## 👨‍💻 Autor

**Jhonattan Almanza**  
Validación de Estructuras de Datos  
Universidad Cooperativa de Colombia

---

## 📅 Versiones

- **v1.0** (2026-11-16): Prototipo con colecciones Java
- **v2.0** (próximamente): Estructuras implementadas desde cero (sin colecciones prohibidas)

---

## 🎯 Próximos pasos

- [ ] Implementar estructuras propias sin colecciones Java
- [ ] Agregar casos de prueba automatizados
- [ ] Generar video de sustentación
- [ ] Documentar comparación de complejidades
- [ ] Profundización: Visualización de rutas (HTML)
