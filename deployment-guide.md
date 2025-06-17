# Guía de Despliegue - Virtual Pharmacy

## Estructura del Proyecto

### Base de Datos

- **Nombre**: myvirtualpharmacy
- **Puerto**: 3307
- **Usuario**: root
- **Contraseña**: (en blanco)

### Tablas Principales

1. **roles**

   - Almacena los roles del sistema (Gerente, Administrador, Farmacéutico)
   - Incluye niveles de acceso y auditoría

2. **usuarios**

   - Información personal de usuarios
   - Vinculada con roles mediante id_rol
   - Incluye validaciones y restricciones

3. **credenciales**

   - Gestión de acceso al sistema
   - Vinculada con usuarios mediante id_usuario
   - Contraseñas encriptadas con SHA-256

4. **inventario**

   - Gestión de medicamentos
   - Incluye stock y alertas

5. **usuarios_inactivos**
   - Registro histórico de usuarios inactivos
   - Vinculada con usuarios mediante id_usuario

## Configuración del Entorno

### Requisitos Previos

1. Java 8 o superior
2. Apache Tomcat 9.0
3. MySQL 8.0 en puerto 3307
4. Maven

### Archivos de Configuración

#### 1. Base de Datos (login.xml)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Context antiJARLocking="true" path="/virtual-pharmacy">
    <Resource
        name="jdbc/myvirtualpharmacy"
        auth="Container"
        type="javax.sql.DataSource"
        maxTotal="100"
        maxIdle="30"
        maxWaitMillis="10000"
        username="root"
        password=""
        driverClassName="com.mysql.cj.jdbc.Driver"
        url="jdbc:mysql://localhost:3307/myvirtualpharmacy?serverTimezone=UTC"/>
</Context>
```

#### 2. Seguridad (tomcat-users.xml)

```xml
<tomcat-users>
    <role rolename="GERENTE"/>
    <role rolename="ADMINISTRADOR"/>
    <role rolename="FARMACEUTICO"/>
    <user username="gerente" password="gerente123" roles="GERENTE"/>
</tomcat-users>
```

## Pasos de Despliegue

### 1. Preparación

1. Detener Tomcat si está en ejecución
2. Limpiar caché de aplicación anterior
3. Verificar que MySQL está corriendo en puerto 3307

### 2. Base de Datos

1. Ejecutar script myvirtualpharmacy.sql para crear estructura
2. Ejecutar script insert_data.sql para datos iniciales
3. Verificar conexión con TestConnection.java

### 3. Configuración de Tomcat

1. Copiar login.xml a conf/Catalina/localhost/virtual-pharmacy.xml
2. Actualizar tomcat-users.xml en conf/
3. Verificar permisos de directorios

### 4. Despliegue de Aplicación

1. Ejecutar `mvn clean package`
2. Copiar WAR a webapps/
3. Iniciar Tomcat
4. Esperar despliegue completo

### 5. Verificación

1. Acceder a http://localhost:8080/virtual-pharmacy/
2. Iniciar sesión con credenciales de prueba:
   - Usuario: gerente
   - Contraseña: gerente123

## Solución de Problemas

### Errores Comunes

1. **Error 404**

   - Verificar nombre del WAR
   - Revisar logs de Tomcat
   - Comprobar contexto en web.xml

2. **Error de Conexión DB**

   - Verificar puerto MySQL (3307)
   - Comprobar credenciales
   - Revisar nombre de base de datos

3. **Error de Autenticación**
   - Verificar tomcat-users.xml
   - Comprobar roles asignados
   - Revisar login.xml

### Logs y Diagnóstico

- Logs de Tomcat: `logs/catalina.out`
- Logs de aplicación: `logs/virtual-pharmacy.log`
- Consola de MySQL para queries directas

## Mantenimiento

### Respaldos

1. Base de datos: Export regular
2. Archivos de configuración
3. WAR desplegado

### Monitoreo

1. Verificar espacio en disco
2. Revisar logs regularmente
3. Monitorear conexiones a DB

## Seguridad

### Medidas Implementadas

1. Encriptación de contraseñas
2. Control de acceso por roles
3. Validación de entrada
4. Protección contra SQL Injection
5. Manejo de sesiones seguro

### Recomendaciones

1. Cambiar contraseñas default
2. Actualizar regularmente
3. Monitorear intentos de acceso
4. Revisar logs de seguridad
