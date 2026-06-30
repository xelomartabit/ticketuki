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
                          ┌──────────────────────────┐
        Cliente  ───────► │      ms-gateway :8080     │  ◄── única puerta de entrada
                          │  (enrutamiento + JWT)     │
                          └────────────┬─────────────┘
                                       │ enruta según el path
        ┌──────────────┬──────────────┼──────────────┬──────────────┐
        ▼              ▼              ▼              ▼              ▼
┌──────────────┐  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐
│   ms-auth    │  │  ms-usuario  │  │  ms-artista  │  │  ms-evento   │  │  ms-recinto  │
│   :8011      │  │   :8001      │  │   :8002      │  │   :8003      │  │   :8008      │
└──────────────┘  └──────────────┘  └──────────────┘  └──────────────┘  └──────────────┘

┌──────────────┐  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐
│  ms-estado   │  │  ms-ticket   │  │   ms-venta   │  │   ms-pago    │  │ ms-promocion │
│   :8004      │  │   :8005      │  │   :8006      │  │   :8009      │  │   :8007      │
└──────────────┘  └──────────────┘  └──────────────┘  └──────────────┘  └──────────────┘

                              ┌──────────────┐
                              │ ms-historial │
                              │   :8010      │
                              └──────────────┘
```

- **ms-gateway (:8080)** es el *API Gateway*: única puerta de entrada que enruta cada
  petición al microservicio correcto y valida el token JWT (ver sección *API Gateway y
  Autenticación*).
- **ms-auth (:8011)** emite los tokens JWT tras validar las credenciales.
- Cada microservicio de negocio posee su propia base de datos MariaDB aislada,
  siguiendo el patrón *Database per Service*.

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

### ms-auth — Puerto 8011 · BD: `auth_service`
Servicio de autenticación. Valida credenciales y emite tokens JWT.
- `POST /auth/login` — recibe `{username, password}` y devuelve un JWT firmado

### ms-gateway — Puerto 8080 · (sin BD)
API Gateway: única puerta de entrada al sistema. Enruta cada petición al
microservicio correspondiente y valida el JWT en cada llamada.

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
| API Gateway | Spring Cloud Gateway |
| Autenticación | JWT (jjwt) |
| Monitoreo | Spring Boot Actuator |
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

### 3. Levantar los microservicios

#### Opción A — Scripts automáticos (recomendado)

Desde la raíz del proyecto, levanta los 10 servicios en el orden correcto con un solo comando:

```bash
./start.sh    # levanta los 10 microservicios (8001-8010)
./stop.sh     # detiene los 10 microservicios
```

Para **reiniciar** todo (útil si ves el error `Port 8001 was already in use`):

```bash
./stop.sh && ./start.sh
```

Características de `start.sh`:

- **Fuerza Java 21** automáticamente (vía `/usr/libexec/java_home -v 21`). Esto evita el error de compilación de Lombok (`TypeTag :: UNKNOWN`) que ocurre si tu `JAVA_HOME` apunta a un JDK más nuevo (24+).
- **Libera los puertos 8001-8010** si quedaron instancias previas corriendo.
- **Respeta el orden de dependencias** y espera a que cada servicio quede activo antes de continuar.
- Guarda la salida de cada servicio en `logs/ms-<nombre>.log`. Para seguir un log en vivo: `tail -f logs/ms-pago.log`.

> Requiere tener un **JDK 21** instalado. Si no se encuentra, el script avisa y se detiene.

#### Opción B — Manual (una terminal por servicio)

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
| ms-auth | http://localhost:8011/auth/login |
| ms-gateway | http://localhost:8080 |

> A través del gateway, todas las rutas se consumen desde `http://localhost:8080`
> (ej. `http://localhost:8080/usuarios`). Requieren token JWT salvo `/auth/login`.

---

## API Gateway y Autenticación (JWT)

El sistema expone una **única puerta de entrada** a través del **ms-gateway (:8080)**,
construido con **Spring Cloud Gateway**. El cliente solo conoce el gateway; éste:

1. **Enruta** cada petición al microservicio correcto según el path (configurado en
   `ms-gateway/src/main/resources/application.yml`).
2. **Valida el JWT** en cada petición mediante un filtro global (`JwtAuthFilter`),
   antes de reenviarla. Todas las rutas requieren un token válido **excepto**
   `/auth/login`.

### Flujo de uso

```bash
# 1. Obtener un token (ruta pública). Usuario semilla: hodor / holdthedoor1234
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"hodor","password":"holdthedoor1234"}'
# → { "token": "eyJhbGciOiJI...", "tipo": "Bearer", "username": "hodor", "expiraEnMs": 3600000 }

# 2. Usar el token en cualquier ruta protegida
curl http://localhost:8080/ventas \
  -H "Authorization: Bearer <TOKEN>"
```

| Situación | Respuesta del gateway |
|---|---|
| Ruta protegida sin token | `401 Unauthorized` |
| Ruta protegida con token inválido/expirado | `401 Unauthorized` |
| Ruta protegida con token válido | Reenvía al microservicio (`200`, etc.) |
| `POST /auth/login` (pública) | `200` + token JWT |

### Detalles de implementación

- **ms-auth (:8011)** firma los tokens (`JwtUtil` + `signWith(secret)`); el **gateway**
  los valida con el **mismo `secret`** (variable de entorno `JWT_SECRET`, compartida).
- El gateway no expone lógica de negocio (no tiene controllers): solo **rutas** (YAML)
  y **filtros** (`JwtAuthFilter`).
- Al validar, el gateway propaga el usuario autenticado a los microservicios en la
  cabecera `X-Usuario`.

---

## Monitoreo (Actuator)

Todos los servicios incluyen **Spring Boot Actuator** para verificar su estado.

| Endpoint | Dónde | Para qué |
|---|---|---|
| `/actuator/health` | gateway y los 11 microservicios | Comprobar que el servicio está vivo (`{"status":"UP"}`) |
| `/actuator/gateway/routes` | solo el gateway | Listar en runtime las 11 rutas de enrutamiento |

```bash
curl http://localhost:8080/actuator/health          # gateway
curl http://localhost:8001/actuator/health          # cualquier microservicio
curl http://localhost:8080/actuator/gateway/routes  # rutas activas del gateway
```

> El endpoint `gateway` se habilita con `management.endpoint.gateway.enabled: true`
> además de exponerlo en `management.endpoints.web.exposure.include`.

---

## Pruebas Unitarias

Cada microservicio de negocio incluye pruebas unitarias (JUnit 5 + Mockito + AssertJ),
sin dependencia de base de datos: todo se *mockea*.

### Cómo ejecutarlas

```bash
# Todas las pruebas de un microservicio
cd ms-usuario && mvn test

# Todas las pruebas del proyecto (desde la raíz)
mvn test
```

> Requiere **Java 21** (`export JAVA_HOME=$(/usr/libexec/java_home -v 21)`).

### Tipos de prueba

- **Tests de Service** (`*ServiceTest`): prueban la lógica de negocio aislada con
  `@Mock` (repositorios y clientes HTTP) e `@InjectMocks`. Cubren casos de éxito,
  errores con validación del mensaje de la excepción (`assertThatThrownBy(...).hasMessage(...)`),
  filtrados, cálculos y verificación de interacciones (`verify(...)`).
- **Tests de Controller** (`*ControllerTest`): usan `@WebMvcTest` + `MockMvc` con el
  service *mockeado* (`@MockBean`). Validan el código HTTP y el cuerpo JSON, tanto en
  respuestas exitosas (200) como en errores de validación `@Valid` (400, mensaje
  `"Validación fallida"` del `GlobalExceptionHandler`).
- **ArgumentCaptor**: en `ms-usuario` y `ms-venta` se captura la entidad enviada a
  `save(...)` para verificar que el service la **construye correctamente** (p. ej. que
  el `monto_total` calculado quedó persistido en la entidad `Venta`).

### Cobertura por microservicio

| Microservicio | N° de pruebas |
|---|---|
| ms-usuario | 6 |
| ms-artista | 6 |
| ms-evento | 5 |
| ms-ticket | 6 |
| ms-venta | 7 |
| ms-promocion | 5 |
| ms-recinto | 5 |
| ms-pago | 5 |

> Los microservicios que se comunican con otros (`ms-venta`, `ms-ticket`, `ms-pago`)
> mockean el cliente HTTP (`EstadoVentaClient`) o se construyen con los `WebClient` en
> `null` para probar la lógica sin llamadas de red.

---

## Solución de Problemas

### El campo `nombre` de un objeto relacionado llega en `null` (p. ej. `estado_venta`)

Si al crear/consultar una venta (u otra entidad) ves algo como:

```json
"estado_venta": { "id": 1, "nombre": null }
```

…significa que el microservicio no pudo consultar por HTTP al servicio dueño del
dato (ms-estado en este caso) y aplicó su fallback defensivo (devuelve el `id`
con el `nombre` en `null`). En el log del servicio llamador aparece:

```
No se pudo consultar ms-estado para id 1: Failed to resolve 'localhost' ...
```

**Causa (macOS):** el cliente HTTP reactivo (`WebClient` sobre Reactor Netty) usa
por defecto el **resolver DNS nativo de Netty**, que en macOS no respeta
`/etc/hosts` y por tanto no resuelve `localhost`.

**Solución aplicada:** los `WebClientConfig` configuran el `HttpClient` para usar
el **resolver DNS de la JVM** (`DefaultAddressResolverGroup`), que sí respeta
`/etc/hosts`. Así se mantiene `localhost` en las URLs sin tocar la configuración:

```java
HttpClient httpClient = HttpClient.create()
        .resolver(io.netty.resolver.DefaultAddressResolverGroup.INSTANCE);

WebClient.builder()
        .baseUrl(url)
        .clientConnector(new ReactorClientHttpConnector(httpClient))
        .build();
```

### Error de compilación de Lombok (`TypeTag :: UNKNOWN`)

Ocurre si Maven usa un JDK más nuevo que el soportado por Lombok (p. ej. JDK 24).
El proyecto requiere **Java 21**. Usa el script `./start.sh` (que fuerza Java 21
automáticamente) o exporta el JDK correcto antes de compilar:

```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 21)
```

### `Port 80xx was already in use`

Ya hay una instancia previa corriendo en ese puerto. Reinicia todo con:

```bash
./stop.sh && ./start.sh
```

---

## Estructura del Repositorio

```
ticketuki/
├── ms-gateway/     # API Gateway (:8080) — enrutamiento + validación JWT
├── ms-auth/        # Autenticación (:8011) — emite tokens JWT
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
