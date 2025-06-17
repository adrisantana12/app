# Informe Técnico - Virtual Pharmacy

## 1. Descripción General del Proyecto

Virtual Pharmacy es una aplicación web Java desarrollada con tecnologías JEE para la gestión integral de una farmacia. El sistema permite administrar usuarios, roles, inventario y otros aspectos fundamentales del negocio.

## 2. Especificaciones Técnicas

### 2.1 Entorno de Desarrollo

- IDE: NetBeans
- JDK: Version 1.8
- Servidor de Aplicaciones: Apache Tomcat
- Sistema de Control de Versiones: Git
- Gestor de Dependencias: Maven

### 2.2 Tecnologías Utilizadas

- Java EE (Jakarta EE)
- Servlets & JSP
- JSTL (JavaServer Pages Standard Tag Library)
- Bootstrap 5.1.3
- MySQL 8.0

### 2.3 Configuración del Servidor

- URL de la aplicación: http://localhost:8080/virtual-pharmacy/
- Puerto de la base de datos MySQL: 3307
- Nombre de la base de datos: myvirtualpharmacy

## 3. Arquitectura del Sistema

### 3.1 Patrón de Diseño

El proyecto implementa el patrón MVC (Modelo-Vista-Controlador):

- **Modelo**: Clases Java en el paquete `com.virtualpharmacy.model`
- **Vista**: Archivos JSP en `/WEB-INF/jsp/`
- **Controlador**: Servlets en el paquete `com.virtualpharmacy.servlet`

### 3.2 Estructura de Paquetes

```
com.virtualpharmacy
├── model/       # Entidades del dominio
├── dao/         # Acceso a datos
├── servlet/     # Controladores
└── util/        # Utilidades y configuración
```

### 3.3 Componentes Principales

1. **Gestión de Usuarios**

   - Registro y administración de usuarios
   - Roles y permisos
   - Autenticación y autorización

2. **Gestión de Inventario**

   - Control de medicamentos
   - Stock y alertas
   - Registro de movimientos

3. **Gestión de Roles**
   - CRUD completo de roles
   - Niveles de acceso
   - Permisos granulares

## 4. Base de Datos

### 4.1 Conexión

La conexión a la base de datos se realiza mediante un pool de conexiones JDBC configurado en el archivo `context.xml`:

```xml
<Resource
    name="jdbc/VirtualPharmacyDB"
    url="jdbc:mysql://localhost:3307/myvirtualpharmacy"
    ...
/>
```

### 4.2 Principales Tablas

- usuarios
- roles
- credenciales
- inventario
- usuarios_inactivos

## 5. Características del Frontend

### 5.1 Tecnologías Cliente

- HTML5
- CSS3 (Bootstrap)
- JavaScript
- JSTL para plantillas

### 5.2 Formularios

Todos los formularios implementan:

- Validación del lado del cliente
- Validación del lado del servidor
- Mensajes de error y éxito
- Diseño responsive

## 6. Seguridad

### 6.1 Medidas Implementadas

- Autenticación de usuarios
- Control de sesiones
- Validación de entradas
- Prevención de SQL Injection
- Encriptación de contraseñas

### 6.2 Control de Acceso

- Roles con niveles jerárquicos
- Validación de permisos por URL
- Filtros de seguridad

## 7. Requerimientos del Sistema

### 7.1 Servidor

- Java 8 o superior
- Apache Tomcat 9.0 o superior
- MySQL 8.0
- 2GB RAM mínimo
- 500MB espacio en disco

### 7.2 Cliente

- Navegador web moderno
- JavaScript habilitado
- Conexión a Internet

## 8. Despliegue

### 8.1 Pasos de Instalación

1. Configurar la base de datos MySQL en puerto 3307
2. Ejecutar el script myvirtualpharmacy.sql
3. Desplegar el archivo WAR en Tomcat
4. Configurar el context.xml con las credenciales correctas
5. Iniciar el servidor Tomcat

### 8.2 Verificación

- Acceder a http://localhost:8080/virtual-pharmacy/
- Verificar la conexión a la base de datos
- Comprobar el funcionamiento del login

## 9. Mantenimiento

### 9.1 Logs

- Logs de aplicación en catalina.out
- Logs de base de datos
- Registro de errores y excepciones

### 9.2 Backups

- Backup diario de la base de datos
- Versionamiento del código fuente en Git
- Copias de seguridad de configuración

## 10. Conclusiones y Recomendaciones

### 10.1 Puntos Fuertes

- Arquitectura modular y escalable
- Interfaz intuitiva y responsive
- Sistema robusto de gestión de roles
- Pool de conexiones eficiente

### 10.2 Recomendaciones

- Implementar caché para mejorar el rendimiento
- Configurar HTTPS en producción
- Realizar pruebas de carga
- Monitorear el uso de recursos
