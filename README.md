# Gestión de Sucursales y Ventas

Aplicación de consola en Java para administrar hasta **100 sucursales** de una cadena de tiendas y registrar sus ventas en una matriz de **3 categorías × 4 trimestres**.

Proyecto de la asignatura **Estructura de Datos – Unidad 1, Tarea 1** (Ingeniería en Software). Todo el manejo de datos usa **arreglos nativos** y desplazamientos manuales, sin colecciones (`ArrayList`, etc.) ni utilidades como `System.arraycopy`.

---

## Requisitos

| Herramienta | Versión | Para qué |
|---|---|---|
| [Java JDK](https://www.oracle.com/java/technologies/downloads/) | 17 o superior | Compilar y ejecutar (obligatorio) |
| [Git](https://git-scm.com/downloads) | Cualquiera | Clonar el repositorio (opcional, también se puede descargar en ZIP) |
| [Visual Studio Code](https://code.visualstudio.com/) + extensión **Extension Pack for Java** | Cualquiera | Ejecutar desde el editor (opcional) |

Para verificar que Java está instalado, abre una terminal y ejecuta:

```bash
java -version
javac -version
```

Ambos comandos deben mostrar un número de versión. Si dice *"no se reconoce como comando"*, instala el JDK (no solo el JRE) y reinicia la terminal.

---

## 1. Descargar el proyecto

**Opción A – Con Git:**

```bash
git clone https://github.com/DavidGnete/GestionSucursal.git
cd GestionSucursal
```

**Opción B – Sin Git:** en la página del repositorio, haz clic en **Code → Download ZIP**, descomprime el archivo y abre una terminal dentro de la carpeta.

---

## 2. Ejecutar el programa

### Opción A – Visual Studio Code (recomendada)

1. Abre VS Code y ve a **File → Open Folder…** y selecciona la carpeta del proyecto.
2. Si VS Code lo sugiere, instala **Extension Pack for Java**.
3. Espera a que la barra inferior termine de cargar el proyecto Java (unos segundos la primera vez).
4. Abre `src/app/Main.java` y haz clic en **Run** encima del método `main` (o presiona `F5`).
5. El menú aparece en la **terminal integrada**. Escribe ahí las opciones.

La carpeta `.vscode/` ya incluye la configuración necesaria para que VS Code reconozca `src` como carpeta de código.

### Opción B – Desde la terminal (Windows, macOS o Linux)

Desde la carpeta raíz del proyecto:

```bash
# 1. Compilar (genera los .class en la carpeta bin)
javac -encoding UTF-8 -d bin -sourcepath src src/app/Main.java

# 2. Ejecutar
java -cp bin app.Main
```

> En Windows, si las tildes se ven como `�`, ejecuta `chcp 65001` en la terminal antes de correr el programa.

---

## 3. Uso

Al iniciar se muestra el menú principal:

```
  1. Agregar sucursal
  2. Buscar sucursal
  3. Modificar sucursal
  4. Eliminar sucursal
  5. Insertar sucursal en posición
  6. Registrar/Modificar venta
  7. Mostrar sistema
  0. Salir
```

| Opción | Qué hace |
|---|---|
| 1. Agregar | Añade una sucursal al final del arreglo. El `id` se asigna automáticamente (consecutivo). |
| 2. Buscar | Búsqueda lineal por `id` o por nombre; muestra la posición y toda la información. |
| 3. Modificar | Cambia el nombre de una sucursal localizada por su `id`. |
| 4. Eliminar | Quita una sucursal por `id` y desplaza las siguientes a la izquierda. |
| 5. Insertar | Inserta una sucursal en una posición entre `0` y el total actual, desplazando las demás a la derecha. |
| 6. Venta | Pide `id`, categoría (0 Electrónica, 1 Ropa, 2 Alimentos) y trimestre (0–3). Si la celda está en 0.0 la registra; si no, la modifica. |
| 7. Mostrar | Imprime todas las sucursales con su matriz 3×4 y totales por fila, columna y general. |

Todas las entradas se validan: si se escribe un dato inválido (letras, números fuera de rango, ventas negativas o nombres vacíos), el programa vuelve a preguntar.

**Prueba rápida:** agrega dos sucursales (opción 1), registra una venta (opción 6) y luego elige la opción 7 para ver el reporte.

---

## Estructura del proyecto

```
src/
├── app/
│   └── Main.java               Menú interactivo (punto de entrada)
├── logica/
│   └── GestorSucursales.java   Arreglo, contador y operaciones (agregar, buscar, insertar, eliminar, ventas)
├── modelo/
│   ├── Sucursal.java           Registro: id, nombre, ventas (sin métodos)
│   └── MatrizVentas.java       Registro: matriz double[3][4] (sin métodos)
└── util/
    ├── Consola.java            Presentación: menú, mensajes y tabla de la matriz
    ├── Estados.java            Códigos de resultado de las operaciones
    ├── Mensajes.java           Textos que se muestran al usuario
    └── Validaciones.java       Lectura segura de datos desde teclado
```

---

## Problemas comunes

| Problema | Solución |
|---|---|
| `'javac' no se reconoce como un comando` | Instala el **JDK** y reinicia la terminal. En Windows, verifica que la carpeta `bin` del JDK esté en la variable `PATH`. |
| `Error: Could not find or load main class app.Main` | Ejecuta el comando desde la carpeta raíz del proyecto y compila primero (paso 1 de la opción B). |
| VS Code muestra *"Incorrect Package"* en rojo | Abre la **carpeta raíz** del proyecto (no `src`) y recarga con `Ctrl+Shift+P` → *Developer: Reload Window*. |
| En VS Code no se puede escribir en el menú | Asegúrate de usar la pestaña **Terminal** y no *Debug Console*. |
| Tildes con símbolos raros (`�`) | Ejecuta `chcp 65001` (Windows) antes de iniciar el programa. |

---

## Autores

- David Agudelo
- _(Nombre del compañero)_
