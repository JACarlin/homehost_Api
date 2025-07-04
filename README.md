# 🏨 FrankCasa - API REST para Alquiler de Propiedades

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![JAX-RS](https://img.shields.io/badge/JAX--RS-orange?style=for-the-badge)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)

> Backend completo para una plataforma de alquiler de propiedades al estilo Airbnb. Esta API REST, desarrollada en Java, gestiona usuarios, departamentos (propiedades) y reservaciones.

## 📜 Sobre el Proyecto

**FrankCasa** es un proyecto académico backend que replica la funcionalidad principal de plataformas como Airbnb. Esta API constituye el corazón del sistema, proveyendo todos los servicios necesarios para que una aplicación cliente (web o móvil) pueda operar.

La arquitectura sigue un patrón en capas para una clara separación de responsabilidades:
* **Capa de Modelo:** Entidades de datos como `Usuario`, `Departamento`, `Reservacion` e `Imagen`.
* **Capa de Controlador:** Lógica de negocio y acceso a datos mediante JDBC.
* **Capa de Recursos (REST):** Endpoints HTTP definidos con JAX-RS que exponen la funcionalidad del sistema.

<br>

## ✨ Características

* **Gestión de Usuarios:** Registro, login, actualización y consulta de perfiles de usuario.
* **Gestión de Propiedades:** Registro y actualización de departamentos, incluyendo múltiples imágenes. Búsqueda de propiedades disponibles y por ID.
* **Sistema de Reservaciones:** Creación de nuevas reservaciones y consulta de las reservaciones asociadas a un usuario.

<br>

## 🛠️ Construido Con

* [Java (JDK 11+)](https://www.oracle.com/java/technologies/downloads/)
* [JAX-RS (Jakarta RESTful Web Services)](https://projects.eclipse.org/projects/ee4j.jaxrs)
* [Gson](https://github.com/google/gson) para el manejo de JSON.
* [MySQL](https://www.mysql.com/) como base de datos relacional.
* [JDBC](https://docs.oracle.com/javase/tutorial/jdbc/basics/index.html) para la conectividad con la base de datos.
* [Apache Maven](https://maven.apache.org/) para la gestión del proyecto.

<br>

## 🚀 Empezando

Sigue estos pasos para configurar y ejecutar el proyecto en tu máquina local.

### Prerrequisitos

* JDK 11 o superior.
* Apache Maven.
* Un servidor de MySQL activo.
* Un servidor de aplicaciones Java como [Apache Tomcat](https://tomcat.apache.org/) o [Payara](https://www.payara.fish/).

### Instalación

1.  **Clona el repositorio:**
    ```bash
    git clone https://github.com/JACarlin/homehost_Api.git
    ```
2.  **Configura la Base de Datos:**
    * Crea una base de datos en MySQL (ej. `frankcasa_db`).
    * Ejecuta los siguientes scripts SQL para crear las tablas necesarias:
        ```sql
        -- Tabla de Usuarios
        CREATE TABLE usuario (
            idUsuario INT PRIMARY KEY AUTO_INCREMENT,
            nombre VARCHAR(255) NOT NULL,
            email VARCHAR(255) NOT NULL UNIQUE,
            password VARCHAR(255) NOT NULL,
            rol VARCHAR(50) -- ej. 'anfitrion', 'huesped'
        );

        -- Tabla de Departamentos (Propiedades)
        CREATE TABLE departamento (
            idDepartamento INT PRIMARY KEY AUTO_INCREMENT,
            idAnfitrion INT NOT NULL,
            titulo VARCHAR(255) NOT NULL,
            descripcion TEXT,
            precioPorNoche DECIMAL(10, 2) NOT NULL,
            ubicacion VARCHAR(255),
            disponible BOOLEAN DEFAULT TRUE,
            FOREIGN KEY (idAnfitrion) REFERENCES usuario(idUsuario)
        );

        -- Tabla para las imágenes de los departamentos
        CREATE TABLE imagen (
            idImagen INT PRIMARY KEY AUTO_INCREMENT,
            idDepartamento INT NOT NULL,
            urlImagen TEXT NOT NULL,
            FOREIGN KEY (idDepartamento) REFERENCES departamento(idDepartamento) ON DELETE CASCADE
        );

        -- Tabla de Reservaciones
        CREATE TABLE reservacion (
            idReservacion INT PRIMARY KEY AUTO_INCREMENT,
            idHuesped INT NOT NULL,
            idDepartamento INT NOT NULL,
            fechaInicio DATE NOT NULL,
            fechaFin DATE NOT NULL,
            costoTotal DECIMAL(10, 2) NOT NULL,
            estado VARCHAR(50), -- ej. 'confirmada', 'cancelada'
            FOREIGN KEY (idHuesped) REFERENCES usuario(idUsuario),
            FOREIGN KEY (idDepartamento) REFERENCES departamento(idDepartamento)
        );
        ```
3.  **Configura la Conexión:**
    * Localiza la clase de conexión a la base de datos en el código fuente.
    * Ajusta las credenciales (URL de la BD, usuario, contraseña) para que coincidan con tu entorno.

4.  **Construye el Proyecto:**
    ```bash
    mvn clean install
    ```
    Esto creará un archivo `.war` en la carpeta `target/`.

5.  **Despliega la Aplicación:**
    * Despliega el archivo `.war` en tu servidor de aplicaciones (ej. en la carpeta `webapps` de Tomcat).

<br>

## 📖 Uso de la API

La URL base de la API será `http://localhost:8080/nombre-del-war/api/`.

### Módulo de Usuario (`/usuario`)
* `POST /login`: Autentica a un usuario.
* `POST /registrar`: Crea un nuevo usuario.
* `POST /actualizar`: Modifica los datos de un usuario existente.
* `GET /obtenerporid`: Obtiene el perfil de un usuario por su ID.

### Módulo de Departamento (`/departamento`)
* `POST /registrarDepartamento`: Registra una nueva propiedad con sus imágenes.
* `POST /actualizarDepartamento`: Actualiza los datos de una propiedad y sus imágenes.
* `GET /obtenertodosdisponibles`: Devuelve una lista de todas las propiedades disponibles.
* `GET /obtenerporid`: Obtiene los detalles de una propiedad específica por su ID.

### Módulo de Reservación (`/reservacion`)
* `POST /crearRegistro`: Crea una nueva reservación para una propiedad.
* `GET /obtenerReservacionesPorUsuario`: Devuelve el historial de reservaciones de un usuario.

<br>
