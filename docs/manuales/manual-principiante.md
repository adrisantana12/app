# Manual para Principiantes - Virtual Pharmacy 🏥

## ¿Qué es Virtual Pharmacy? 💊

Virtual Pharmacy es un sistema para gestionar una farmacia, que te permite controlar:

- Usuarios y sus roles
- Inventario de medicamentos
- Registro de accesos
- Y mucho más!

## Lo que Necesitas para Empezar 🚀

1. **Base de Datos**

   - MySQL versión 8.0
   - Puerto: 3307
   - Usuario: root
   - Sin contraseña

2. **Servidor Web**
   - Apache Tomcat 9.0
   - Java 8 o más nuevo

## Pasos para Iniciar la Aplicación 🔥

### 1. Preparar la Base de Datos 📊

1. Abre MySQL (debe estar en puerto 3307)
2. Ejecuta el archivo `myvirtualpharmacy.sql`
   - Este archivo crea todas las tablas necesarias
3. Ejecuta el archivo `insert_data.sql`
   - Este archivo agrega los datos iniciales

### 2. Configurar el Servidor 🛠️

1. Copia el archivo `login.xml` a la carpeta de Tomcat
   - Ubicación: `[Tomcat]/conf/Catalina/localhost/virtual-pharmacy.xml`
2. Copia los archivos de la aplicación
   - El archivo `virtual-pharmacy.war` va en `[Tomcat]/webapps/`

### 3. Iniciar el Sistema 🌟

1. Inicia MySQL
2. Inicia Tomcat
3. Abre tu navegador
4. Ve a: `http://localhost:8080/virtual-pharmacy/`

### 4. Iniciar Sesión 👤

Usuarios predefinidos:

1. **Gerente**

   - Usuario: gerente
   - Contraseña: gerente123
   - Tiene acceso a todo

2. **Administrador**

   - Usuario: admin
   - Contraseña: admin456
   - Gestiona usuarios

3. **Farmacéutico**
   - Usuario: farmaceutico
   - Contraseña: farmaceutico789
   - Maneja inventario

## ¿Problemas Comunes? 🆘

### La página no carga

1. Verifica que Tomcat esté corriendo
2. Comprueba que MySQL esté en puerto 3307
3. Revisa la dirección web

### Error al iniciar sesión

1. Verifica que escribiste bien el usuario
2. Comprueba la contraseña
3. Asegúrate que la base de datos está funcionando

### No aparecen los datos

1. Revisa que ejecutaste `insert_data.sql`
2. Verifica la conexión a la base de datos
3. Comprueba que tienes el rol correcto

## Estructura de Archivos 📁

```
virtual-pharmacy/
├── docs/               # Documentación
│   └── manuales/      # Manuales de usuario
├── sql/               # Scripts de base de datos
│   ├── myvirtualpharmacy.sql
│   └── insert_data.sql
└── target/            # Archivos compilados
    └── virtual-pharmacy.war
```

## ¿Necesitas más ayuda? 🤝

- Revisa la documentación completa en la carpeta `docs`
- Consulta con el administrador del sistema
- Verifica los logs en la carpeta de Tomcat

## Consejos de Seguridad 🔒

1. Cambia las contraseñas predeterminadas
2. Cierra sesión al terminar
3. No compartas tus credenciales
4. Mantén actualizado el sistema

## Nota Importante ⚠️

Este manual está en la carpeta `docs/manuales` del proyecto. Puedes encontrar más documentación técnica en la misma ubicación.
