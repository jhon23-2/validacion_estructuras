# Guía de Uso - Sistema Parque Tayrona

## 🚀 Cómo ejecutar el programa

### Requisitos previos
- Java 11 o superior
- Terminal/CMD

### Compilar
```bash
cd C:\Users\jhonattan.almanza\Documents\validacion_estructuras
javac -d bin -sourcepath src src/app/Main.java
```

### Ejecutar
```bash
java -cp bin app.Main
```

---

## 📋 Menú principal

```
=== SISTEMA PARQUE TAYRONA ===
1.  Registrar visitante
2.  Buscar visitante
3.  Eliminar visitante
4.  Listar visitantes (AVL)
5.  Ingresar grupo a portería
6.  Atender grupo en portería
7.  Asignar guía
8.  Consultar historial de visitante
9.  Deshacer última operación
10. Calcular ruta más corta (A*)
11. Puntos alcanzables (BFS)
12. Verificar conectividad (DFS)
0.  Salir
```

---

## 📖 Flujos de uso

### 1. Registrar un visitante (RF2)

```
Seleccione opción: 1
Documento: 1234567890
Nombre: Juan Pérez
Email: juan@mail.com
Teléfono: 3001234567

✓ Visitante registrado exitosamente
```

El visitante se agrega al **Árbol AVL** (E4) para búsqueda rápida.

---

### 2. Buscar visitante (RF2)

```
Seleccione opción: 2
Documento: 1234567890

Visitante encontrado:
Documento: 1234567890
Nombre: Juan Pérez
Email: juan@mail.com
Teléfono: 3001234567
```

Búsqueda O(log n) en el AVL.

---

### 3. Cola de portería (RF3)

**Ingresar grupo:**
```
Seleccione opción: 5
Nombre del grupo: Familia García
Cantidad de personas: 4

✓ Grupo ingresado a la cola (1/25)
```

**Atender siguiente:**
```
Seleccione opción: 6

Atendiendo grupo: Familia García (4 personas)
Grupos en cola: 0/25
```

Si la cola está llena (25 grupos):
```
✗ ERROR: Cola de portería llena. Cupo: 25 grupos.
```

---

### 4. Asignar guía (RF4)

```
Seleccione opción: 7

Guía asignado: Carlos Martínez
Grupo: Familia García
Estado: El guía volverá al final de la rotación al terminar el recorrido
```

El guía se asigna del frente de la **cola circular** (E3b) y automáticamente vuelve al final.

---

### 5. Historial de visitante (RF5)

```
Seleccione opción: 8
Documento: 1234567890

=== Historial de Juan Pérez ===
1. 2026-11-16 10:30 - Ingreso portería
2. 2026-11-16 10:45 - Asignado guía Carlos
3. 2026-11-16 14:20 - Recorrido finalizado

Navegación:
[A] Anterior | [S] Siguiente | [0] Salir
```

Usa **lista doble** (E1) para navegar adelante/atrás.

---

### 6. Deshacer operación (RF6)

```
Seleccione opción: 9

Deshaciendo: REGISTRAR_VISITANTE
✓ Operación deshecha. Visitante 9876543210 eliminado del sistema.

Operaciones en pila: 3
```

La **pila** (E2) almacena las últimas operaciones para revertirlas.

---

### 7. Calcular ruta más corta - A* (RF7)

```
Seleccione opción: 10
Punto origen: El Zaino
Punto destino: Cabo San Juan

Calculando ruta con A*...

=== RUTA MÁS CORTA ===
Origen: El Zaino
Destino: Cabo San Juan

Ruta: El Zaino → Cañaveral → Arrecifes → La Arenilla → La Piscina → Cabo San Juan
Costo total: 5150 metros
Nodos expandidos: 7

Detalles del recorrido:
1. El Zaino → Cañaveral (1250m)
2. Cañaveral → Arrecifes (1400m)
3. Arrecifes → La Arenilla (900m)
4. La Arenilla → La Piscina (750m)
5. La Piscina → Cabo San Juan (850m)
```

**Algoritmo A*** con:
- g(n) = costo acumulado
- h(n) = distancia euclidiana al destino
- f(n) = g(n) + h(n)

---

### 8. Puntos alcanzables - BFS (RF8)

```
Seleccione opción: 11
Punto origen: El Zaino
Número de saltos (k): 2

=== PUNTOS ALCANZABLES EN 2 SALTOS ===
Desde: El Zaino

Nivel 0: El Zaino
Nivel 1: Cañaveral
Nivel 2: Castilletes, Arrecifes

Total de puntos alcanzables: 4
```

Usa **BFS con cola** (E3) para recorrer por niveles.

---

### 9. Verificar conectividad - DFS (RF8)

```
Seleccione opción: 12
Cerrar sendero desde: Arrecifes
Cerrar sendero hasta: La Arenilla

Simulando cierre del sendero Arrecifes ↔ La Arenilla...

=== ANÁLISIS DE CONECTIVIDAD ===
Estado: RED DESCONECTADA ⚠️

Puntos aislados:
- La Arenilla
- La Piscina
- Cabo San Juan
- Playa Brava
- Calabazo

Recomendación: NO cerrar este sendero. Dejaría 5 puntos sin acceso desde El Zaino.
```

Usa **DFS con pila** (E2) para verificar componentes conectados.

---

## 📂 Archivos de datos

### visitantes.csv
Cargados automáticamente al inicio (30 registros ficticios).

### vertices.csv
10 puntos del parque con coordenadas (x, y) en metros.

### aristas.csv
15 senderos con longitudes reales.

---

## ⚠️ Manejo de errores

### Documento inexistente
```
Seleccione opción: 2
Documento: 9999999999

✗ ERROR: Visitante no encontrado
```

### Cola vacía
```
Seleccione opción: 6

✗ ERROR: No hay grupos en cola de portería
```

### Punto desconocido
```
Seleccione opción: 10
Punto origen: Punto Falso

✗ ERROR: Punto 'Punto Falso' no existe en el mapa
Puntos disponibles: El Zaino, Cañaveral, Castilletes, ...
```

### Sin operaciones para deshacer
```
Seleccione opción: 9

✗ ERROR: No hay operaciones para deshacer
```

---

## 🎯 Tips de uso

1. **Primero cargue datos**: El sistema carga automáticamente 30 visitantes, 10 puntos y 15 senderos al iniciar.

2. **Pruebe el flujo completo**:
   - Registre visitante → Ingrese a cola → Asigne guía → Consulte historial

3. **Experimente con rutas**:
   - Compare rutas desde diferentes orígenes
   - Observe cómo A* expande menos nodos que BFS

4. **Simule cierres**:
   - Pruebe cerrar senderos críticos vs periféricos
   - Vea el impacto en la conectividad

5. **Use deshacer**:
   - Si comete un error, use opción 9
   - Puede deshacer múltiples operaciones en orden inverso

---

## 🧪 Casos de prueba sugeridos

### Caso 1: Flujo completo de visitante
```
1. Registrar visitante (doc: 1111111111)
2. Ingresar grupo a portería
3. Asignar guía
4. Consultar historial del visitante
```

### Caso 2: Llenar cola de portería
```
1. Ingresar 25 grupos (opción 5)
2. Intentar ingresar el grupo 26 → debe fallar
3. Atender un grupo (opción 6)
4. Ahora puede ingresar otro grupo
```

### Caso 3: Comparar rutas
```
1. Ruta El Zaino → Calabazo (directa por costa)
2. Ruta El Zaino → Pueblito (por interior)
3. Observar diferencias de costo
```

### Caso 4: Sendero crítico
```
1. Verificar conectividad cerrando Cañaveral ↔ Arrecifes
2. Este sendero es crítico: aísla toda la costa este
```

### Caso 5: Deshacer en cascada
```
1. Registrar 3 visitantes
2. Ingresar 2 grupos a cola
3. Deshacer 5 veces seguidas
4. Verificar que todo vuelve al estado inicial
```

---

## 📊 Salidas del sistema

El sistema muestra:
- ✓ Confirmación de operaciones exitosas
- ✗ Errores con mensajes claros
- 📊 Tablas formateadas para resultados
- 🗺️ Visualización de rutas paso a paso
- ⚠️ Advertencias sobre operaciones críticas

Todas las operaciones vuelven al menú principal sin cerrar el programa.
