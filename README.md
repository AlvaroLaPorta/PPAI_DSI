# PPAI 2026 - Caso de Uso 28: Registrar Recepción de Bolsín

**Universidad Tecnológica Nacional - Facultad Regional Córdoba (UTN-FRC)**  
**Cátedra:** Diseño de Sistemas de Información | **Curso:** 3K2 | **Grupo:** 8

Implementación del MVP correspondiente a la **Entrega 2: Diseño**, aplicando el patrón de diseño **State** (Gamma) con referencias inversas en los tres contextos del dominio (`Bolsin`, `Remito` y `Documentacion`), alineado con el Diagrama de Clases de Rediseño v2.1 y el Diagrama de Secuencia v2_2.

---

## Requisitos Previos

* **Java JDK 17** o superior (`java -version`)
* **Apache Maven 3.8+** (`mvn -version`)

---

## Estructura del Repositorio

```text
ppai-bolsines-cu28/
├── pom.xml
├── README.md
└── src/main/
    ├── java/ar/edu/utn/frc/dsi/bolsines/
    │   ├── BolsinesApplication.java
    │   ├── boundary/
    │   │   └── PantallaAdmRecpBolsin.java
    │   ├── control/
    │   │   └── GestorRecpBolsin.java
    │   ├── domain/
    │   │   ├── Bolsin.java
    │   │   ├── CambioEstadoBolsin.java
    │   │   ├── CambioEstadoDocumentacion.java
    │   │   ├── ComisionMedica.java
    │   │   ├── DetalleRemito.java
    │   │   ├── Documentacion.java
    │   │   ├── Empleado.java
    │   │   ├── Remito.java
    │   │   ├── Sesion.java
    │   │   ├── TipoDocumentacion.java
    │   │   ├── Usuario.java
    │   │   └── state/
    │   │       ├── Estado.java
    │   │       ├── bolsin/
    │   │       ├── remito/
    │   │       └── doc/
    │   ├── repository/
    │   │   ├── BolsinRepository.java
    │   │   ├── EmpleadoRepository.java
    │   │   ├── EstadoRepository.java
    │   │   └── SesionRepository.java
    │   └── config/
    │       └── DataSeeder.java
    └── resources/
        ├── application.properties
        └── static/
            └── index.html
```

---

## Pasos para Iniciar la Aplicación

1. **Ubicate en la raíz del proyecto:**
   ```bash
   cd ppai-bolsines-cu28
   ```
   *(Si existe un archivo `bolsines.db` de una versión anterior, eliminalo antes de iniciar para que SQLite regenere el esquema actualizado).*

2. **Compilá y ejecutá el servidor Spring Boot:**
   ```bash
   mvn spring-boot:run
   ```

3. **Abrí la interfaz web en el navegador:**
   Ingresá a [http://localhost:8080](http://localhost:8080).

---

## Escenarios de Prueba Precargados (`DataSeeder`)

* **Operador logueado:** José Aita Tagle (`jaita`) — **CM N° 05 - Córdoba (CMC)**
* **Bolsín #1042** (desde Villa María): Para ejecutar el **Flujo Principal** (`ACEPTAR` -> `RecibidoEnCMDestino` / `RecibidoYAceptado` / `RecibidaYAceptada`).
* **Bolsín #1043** (desde Río Cuarto): Para ejecutar **Flujo Alternativo 1** (`DENEGAR` -> `NoRecibida`).
* **Bolsín #1044** (desde Villa María): Para ejecutar **Flujo Alternativo 2** (`RECHAZAR` -> `RecibidaYRechazada` o `REDIRIGIR` -> `ParaRedirigir`).
* **Bolsín #2099** (destino Río Cuarto): Filtrado automáticamente por `esTuCmDestino()`.
