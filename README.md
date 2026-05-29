# Ticketuki — Plataforma de Venta de Tickets por Microservicios

Ticketuki es una plataforma de venta y gestión de tickets para eventos, construida sobre una arquitectura de microservicios con Spring Boot y MariaDB. Cada dominio de negocio se encuentra aislado en su propio servicio independiente, con comunicación sincrónica vía HTTP (WebClient).

---

## Integrantes

| Nombre | GitHub |
|---|---|
| Paulina Gonzalez C. | [@Lillium1](https://github.com/Lillium1), [@pauligonzalezc](https://github.com/pauligonzalezc) |
| Marcelo Martabit D. | [@xelomartabit](https://github.com/xelomartabit) |

---

## Arquitectura

```
┌──────────────┐  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐
│  ms-usuario  │  │  ms-artista  │  │  ms-evento   │  │  ms-recinto  │  │  ms-estado   │
│   :8001      │  │   :8002      │  │   :8003      │  │   :8008      │  │   :8004      │
└──────────────┘  └──────────────┘  └──────────────┘  └──────────────┘  └──────────────┘

┌──────────────┐  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐
│  ms-ticket   │  │   ms-venta   │  │   ms-pago    │  │ ms-promocion │  │ ms-historial │
│   :8005      │  │   :8006      │  │   :8009      │  │   :8007      │  │   :8010      │
└──────────────┘  └──────────────┘  └──────────────┘  └──────────────┘  └──────────────┘
```

Cada microservicio posee su propia base de datos MariaDB aislada, siguiendo el patrón *Database per Service*.

---

## Microservicios y Funcionalidades

### ms-usuario — Puerto 8001 · BD: `usuario_service`
Gestión del ciclo de vida de los usuarios de la plataforma.
- Listar todos los usuarios
- Crear, consultar, actualizar y eliminar usuarios

### ms-artista — Puerto 8002 · BD: `artista_service`
Gestión de artistas y su relación con eventos.
- CRUD completo de artistas
- Filtro de artistas por género musical
- Asociar / desasociar un artista a un evento
- Consultar artistas de un evento específico

### ms-evento — Puerto 8003 · BD: `evento_service`
Gestión de eventos culturales y su ciclo de vida.
- CRUD completo de eventos
- Filtro de eventos por fecha y por recinto
- Cambio de estado de un evento (activo, cancelado, finalizado, etc.)

### ms-estado — Puerto 8004 · BD: `estado_service`
Catálogo centralizado de estados para eventos, ventas y tickets.
- CRUD completo de estados de evento, venta y ticket
- Endpoint consolidado para consultar todos los estados disponibles

### ms-ticket — Puerto 8005 · BD: `ticket_service`
Gestión del ciclo de vida de los tickets.
- CRUD completo de tickets
- Consulta de tickets por evento, venta, sector, titular (RUN) y código QR
- Cambio de estado de un ticket
- Validación de ticket por código QR (control de acceso)
- Transferencia de ticket entre usuarios

### ms-venta — Puerto 8006 · BD: `venta_service`
Orquestación del proceso de compra (integra tickets y usuarios).
- Crear y consultar ventas
- Cambio de estado de una venta
- Filtro de ventas por rango de fechas
- Consulta de detalle de venta y de detalles por venta

### ms-promocion — Puerto 8007 · BD: `promocion_service`
Gestión de promociones y descuentos aplicables a las ventas.
- CRUD completo de promociones
- Consulta de promociones activas
- Filtro de promociones por empresa

### ms-recinto — Puerto 8008 · BD: `recinto_service`
Gestión de recintos y sus sectores (datos de referencia del sistema).
- Crear, consultar y actualizar recintos
- Crear, consultar y actualizar sectores dentro de cada recinto
- Consulta de sectores por recinto

### ms-pago — Puerto 8009 · BD: `pago_service`
Procesamiento y seguimiento del estado de pagos (integra ventas y usuarios).
- Iniciar un pago para una venta
- Consultar pagos por ID, venta, usuario, estado o rango de fechas
- Completar, reembolsar o cancelar un pago

### ms-historial — Puerto 8010 · BD: `historial_service`
Registro de auditoría de operaciones realizadas sobre las entidades del sistema.
- Registrar entradas de historial
- Consultar historial por ID, entidad, usuario y rango de fechas

---

## Tecnologías Utilizadas

| Capa | Tecnología |
|---|---|
| Lenguaje | Java 21 |
| Framework | Spring Boot 3.3 |
| Persistencia | Spring Data JPA / Hibernate |
| Base de datos | MariaDB |
| Migraciones | Flyway |
| Comunicación inter-servicios | Spring WebFlux (WebClient) |
| Validaciones | Jakarta Bean Validation |
| Build | Maven (Wrapper incluido) |

---

## Requisitos Previos

- Java 21 o superior
- Maven 3.9+ (o usar el wrapper `./mvnw` incluido en cada módulo)
- MariaDB 10.6+ corriendo localmente en el puerto **3306**
- Usuario de base de datos: `root` sin contraseña (configurable en `application.properties`)

---

## Pasos para Ejecutar

### 1. Clonar el repositorio

```bash
git clone https://github.com/Lillium1/ticketuki.git
cd ticketuki
```

### 2. Configurar credenciales (si es necesario)

Cada servicio tiene su archivo de configuración en:

```
ms-<nombre>/src/main/resources/application.properties
```

Ajustar los campos de conexión si el usuario o la contraseña de MariaDB difieren:

```properties
spring.datasource.username=root
spring.datasource.password=
```

> Las bases de datos se crean automáticamente al levantar cada servicio gracias a `createDatabaseIfNotExist=true`.
> Los esquemas de tablas y datos iniciales son gestionados automáticamente por **Flyway** al iniciar cada servicio.

### 3. Levantar cada microservicio

Abrir una terminal por servicio y ejecutar desde el directorio raíz del proyecto.
Se recomienda respetar el orden de inicio para evitar errores de conexión entre servicios:

```bash
# 1. ms-estado (levantar primero — es referenciado por ms-ticket, ms-venta y ms-pago)
cd ms-estado && ./mvnw spring-boot:run

# 2. ms-recinto (referenciado por ms-evento y ms-ticket)
cd ms-recinto && ./mvnw spring-boot:run

# 3. ms-usuario (referenciado por ms-pago)
cd ms-usuario && ./mvnw spring-boot:run

# 4. ms-artista
cd ms-artista && ./mvnw spring-boot:run

# 5. ms-evento (referenciado por ms-ticket)
cd ms-evento && ./mvnw spring-boot:run

# 6. ms-promocion (referenciada por ms-venta)
cd ms-promocion && ./mvnw spring-boot:run

# 7. ms-historial
cd ms-historial && ./mvnw spring-boot:run

# 8. ms-venta (depende de ms-estado y ms-promocion)
cd ms-venta && ./mvnw spring-boot:run

# 9. ms-ticket (depende de ms-estado, ms-evento, ms-recinto y ms-venta)
cd ms-ticket && ./mvnw spring-boot:run

# 10. ms-pago (depende de ms-venta, ms-ticket, ms-usuario y ms-estado)
cd ms-pago && ./mvnw spring-boot:run
```

### 4. Verificar que los servicios están activos

| Servicio | URL base |
|---|---|
| ms-usuario | http://localhost:8001/usuarios |
| ms-artista | http://localhost:8002/artistas |
| ms-evento | http://localhost:8003/eventos |
| ms-estado | http://localhost:8004/estados |
| ms-ticket | http://localhost:8005/tickets |
| ms-venta | http://localhost:8006/ventas |
| ms-promocion | http://localhost:8007/promociones |
| ms-recinto | http://localhost:8008/recintos |
| ms-pago | http://localhost:8009/pagos |
| ms-historial | http://localhost:8010/historial |

---

## Estructura del Repositorio

```
ticketuki/
├── ms-artista/
├── ms-estado/
├── ms-evento/
├── ms-historial/
├── ms-pago/
├── ms-promocion/
├── ms-recinto/
├── ms-ticket/
├── ms-usuario/
└── ms-venta/
```

Cada módulo sigue la estructura estándar de Spring Boot:

```
ms-<nombre>/
└── src/main/java/com/ticketuki/<nombre>service/
    ├── controller/   # Endpoints REST
    ├── service/      # Lógica de negocio
    ├── repository/   # Acceso a datos (JPA)
    ├── model/        # Entidades JPA
    ├── dto/          # Objetos de transferencia de datos
    ├── exception/    # Manejo global de errores
    └── config/       # Configuraciones (WebClient, etc.)
```

---
