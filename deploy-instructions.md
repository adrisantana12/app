# Guía de Despliegue - Virtual Pharmacy

Esta guía proporciona instrucciones detalladas para desplegar la aplicación Virtual Pharmacy en Apache Tomcat.

## Requisitos Previos

- Java 8 instalado
- Maven instalado
- Apache Tomcat 9.0 instalado en `C:\Program Files\Apache Software Foundation\Tomcat 9.0`

Para verificar las versiones instaladas:

```powershell
java -version
mvn -version
```

## Paso 1: Preparación del Proyecto

1. Navega al directorio del proyecto:

```powershell
cd "c:\Users\Adriana Santana\Documents\NetBeansVirtualP\app"
```

## Paso 2: Compilar y Empaquetar la Aplicación

1. Limpia y compila el proyecto:

```powershell
mvn clean compile
```

2. Genera el archivo WAR:

```powershell
mvn package
```

El archivo WAR se generará en: `target\virtual-pharmacy-1.0-SNAPSHOT.war`

## Paso 3: Preparar Tomcat

1. Detén Tomcat si está en ejecución:

```powershell
& 'C:\Program Files\Apache Software Foundation\Tomcat 9.0\bin\shutdown.bat'
```

2. Limpia el directorio webapps de versiones anteriores:

```powershell
Remove-Item "C:\Program Files\Apache Software Foundation\Tomcat 9.0\webapps\virtual-pharmacy*" -Force -Recurse
```

3. Limpia los logs antiguos (opcional):

```powershell
Remove-Item "C:\Program Files\Apache Software Foundation\Tomcat 9.0\logs\*" -Force
```

## Paso 4: Desplegar la Aplicación

1. Copia el archivo WAR a la carpeta webapps:

```powershell
Copy-Item ".\target\virtual-pharmacy-1.0-SNAPSHOT.war" -Destination "C:\Program Files\Apache Software Foundation\Tomcat 9.0\webapps\virtual-pharmacy.war"
```

## Paso 5: Iniciar Tomcat

1. Inicia el servidor Tomcat:

```powershell
& 'C:\Program Files\Apache Software Foundation\Tomcat 9.0\bin\startup.bat'
```

## Paso 6: Verificar el Despliegue

1. Espera aproximadamente 30 segundos para que Tomcat despliegue la aplicación
2. Verifica que se haya creado la carpeta:
   `C:\Program Files\Apache Software Foundation\Tomcat 9.0\webapps\virtual-pharmacy`
3. Revisa los logs en:
   `C:\Program Files\Apache Software Foundation\Tomcat 9.0\logs\catalina.out`

## Paso 7: Acceder a la Aplicación

1. Abre un navegador web
2. Accede a: http://localhost:8080/virtual-pharmacy

## Comandos Útiles para Mantenimiento

### Reiniciar Tomcat

```powershell
& 'C:\Program Files\Apache Software Foundation\Tomcat 9.0\bin\shutdown.bat'
Start-Sleep -Seconds 5
& 'C:\Program Files\Apache Software Foundation\Tomcat 9.0\bin\startup.bat'
```

### Ver logs en tiempo real

```powershell
Get-Content "C:\Program Files\Apache Software Foundation\Tomcat 9.0\logs\catalina.out" -Wait
```

## Solución de Problemas

Si la aplicación no funciona:

1. Revisa los logs en `C:\Program Files\Apache Software Foundation\Tomcat 9.0\logs\`

2. Verifica que el puerto 8080 no esté en uso:

```powershell
netstat -ano | findstr :8080
```

3. Asegúrate de que todas las dependencias están correctamente configuradas en el `pom.xml`

4. Verifica que la conexión a la base de datos esté configurada correctamente en `DatabaseConnection.java`

## Script de Despliegue Automático

Se ha incluido un script PowerShell (`deploy.ps1`) que automatiza todo el proceso de despliegue. Para utilizarlo:

1. Abre PowerShell como administrador
2. Navega al directorio del proyecto
3. Ejecuta:

```powershell
.\deploy.ps1
```

El script se encargará de:

- Detener Tomcat
- Compilar el proyecto
- Eliminar versiones anteriores
- Desplegar la nueva versión
- Iniciar Tomcat
- Esperar a que la aplicación esté disponible

## Notas Importantes

- Asegúrate de tener permisos de administrador al ejecutar los scripts
- El tiempo de despliegue puede variar dependiendo del sistema
- Siempre verifica los logs en caso de errores
- Mantén un respaldo de la base de datos antes de actualizaciones importantes

## Estructura del Proyecto

```
app/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── virtualpharmacy/
│       │           ├── dao/
│       │           ├── model/
│       │           ├── servlet/
│       │           └── util/
│       └── webapp/
│           ├── WEB-INF/
│           │   └── jsp/
│           └── META-INF/
├── target/
├── pom.xml
├── deploy.ps1
└── deploy-instructions.md
```
