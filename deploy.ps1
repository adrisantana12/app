# Detener Tomcat
Write-Host "Deteniendo Tomcat..."
& 'C:\Program Files\Apache Software Foundation\Tomcat 9.0\bin\shutdown.bat'
Start-Sleep -Seconds 5

# Limpiar y compilar el proyecto
Write-Host "Compilando el proyecto..."
mvn clean package

# Eliminar versiones anteriores
Write-Host "Eliminando versiones anteriores..."
Remove-Item "C:\Program Files\Apache Software Foundation\Tomcat 9.0\webapps\virtual-pharmacy*" -Force -Recurse -ErrorAction SilentlyContinue

# Copiar el nuevo WAR
Write-Host "Desplegando nueva versión..."
Copy-Item ".\target\virtual-pharmacy-1.0-SNAPSHOT.war" -Destination "C:\Program Files\Apache Software Foundation\Tomcat 9.0\webapps\virtual-pharmacy.war"

# Iniciar Tomcat
Write-Host "Iniciando Tomcat..."
& 'C:\Program Files\Apache Software Foundation\Tomcat 9.0\bin\startup.bat'

Write-Host "Esperando el despliegue..."
Start-Sleep -Seconds 30

Write-Host "¡Despliegue completado!"
Write-Host "La aplicación estará disponible en: http://localhost:8080/virtual-pharmacy"
