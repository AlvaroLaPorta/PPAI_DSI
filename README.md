# PPAI 2026 - Caso de Uso 28: "Registrar recepción de bolsín"
**Diseño de Sistemas de Información (UTN FRC)**  
**Grupo 8 — Curso 3K2**

Este repositorio contiene la implementación del MVP (Producto Mínimo Viable) correspondiente a la Entrega 2 del Proyecto Práctico de Aplicación Integrador (PPAI), aplicando el **Patrón State** sobre la jerarquía de estados de `Bolsin`, `Remito` y `Documentacion`, con persistencia en base de datos SQLite y una interfaz web desacoplada.

---

## Prerrequisitos

Para ejecutar este proyecto de manera local, asegúrese de tener instalado:
* **Java Development Kit (JDK) 17** o superior.
* **Apache Maven 3.6+** (para la gestión de dependencias y ejecución del proyecto).
* Un navegador web moderno (Google Chrome, Firefox, Edge, etc.).

---

## Instrucciones para Iniciar la Aplicación

Siga estos pasos desde su terminal o entorno de desarrollo (como Visual Studio Code):

### 1. Ejecutar la aplicación con Maven
Inicie el servidor de Spring Boot ejecutando el siguiente comando:
```bash
mvn spring-boot:run
```

El framework compilará el código fuente, levantará el servidor embebido en el puerto `8080` y precargará automáticamente en la base de datos SQLite (`bolsines.db`) el catálogo de estados polimórficos, las comisiones médicas, el empleado logueado y los bolsines de prueba.

### 2. Acceder a la Interfaz Web
Abra su navegador web e ingrese a la siguiente URL:
```text
http://localhost:8080
```

---

## Guía rápida de uso y demostración (Defensa Oral)

1. **Pantalla inicial (Contexto):** Visualizará el usuario logueado (*Aita Tagle, José*) y su Comisión Médica destino (*CM N° 05 - Córdoba (CMC)*), junto con la tabla de bolsines pendientes en estado **Enviado**.
2. **Selección de Bolsín:** Haga clic en **Seleccionar** en cualquiera de los bolsines listados para cargar su detalle de remitos y documentaciones.
3. **Flujo de Recepción (Patrón State):**
   * Seleccione la opción de recepción deseada (por ejemplo, *Aceptar recepción* para el flujo principal o *Denegar recepción* / *Rechazar* para los flujos alternativos).
   * Haga clic en **Continuar a Confirmación** y luego en **Confirmar Recepción**.
4. **Verificación de Trazabilidad:** El sistema mostrará el resultado de la ejecución, el cambio de estado actualizado en cascada y el historial persistido en SQLite.
5. **Reiniciar Demo:** Si desea repetir la demostración con los bolsines iniciales durante la defensa, puede hacer clic en el botón superior **Reiniciar BD Demo** en cualquier momento.