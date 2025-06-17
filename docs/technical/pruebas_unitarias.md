# Guía de Pruebas Unitarias - Virtual Pharmacy

## ¿Qué son las pruebas unitarias?

Las pruebas unitarias son una práctica fundamental en el desarrollo de software que consiste en verificar el funcionamiento correcto de unidades individuales de código fuente. Una "unidad" es la parte más pequeña testeable de una aplicación, típicamente:

- Un método individual
- Una clase completa
- Un componente específico

## Características principales

1. **Automatizadas**: Se ejecutan automáticamente sin intervención manual
2. **Repetibles**: Siempre producen el mismo resultado bajo las mismas condiciones
3. **Aisladas**: No dependen de recursos externos (base de datos, archivos, red)
4. **Independientes**: Cada prueba es independiente de las demás
5. **Profesionales**: Son parte del código fuente y siguen los mismos estándares

## Beneficios

1. **Detección temprana de errores**

   - Encuentra bugs antes de llegar a producción
   - Reduce costos de corrección

2. **Facilita los cambios**

   - Permite refactorizar con confianza
   - Verifica que los cambios no rompan funcionalidad existente

3. **Documenta el código**

   - Las pruebas sirven como ejemplos de uso
   - Ayudan a entender el propósito del código

4. **Mejora el diseño**
   - Fomenta código modular y desacoplado
   - Identifica dependencias innecesarias

## Estructura de una prueba unitaria

Una prueba unitaria típicamente sigue el patrón AAA:

1. **Arrange (Preparar)**

   - Configurar los objetos necesarios
   - Establecer las condiciones iniciales

2. **Act (Actuar)**

   - Ejecutar el método a probar
   - Realizar la operación que se quiere verificar

3. **Assert (Afirmar)**
   - Verificar que el resultado es el esperado
   - Comprobar que el estado es correcto

## Ejemplo práctico en Virtual Pharmacy

Consideremos la clase `Rol`. Una prueba unitaria típica sería:

```java
public class RolTest {
    @Test
    public void testSetNivelAcceso_ValidLevel_ShouldSetCorrectly() {
        // Arrange
        Rol rol = new Rol();
        int nivelEsperado = 5;

        // Act
        rol.setNivelAcceso(nivelEsperado);

        // Assert
        assertEquals(nivelEsperado, rol.getNivelAcceso());
    }

    @Test
    public void testSetNivelAcceso_InvalidLevel_ShouldThrowException() {
        // Arrange
        Rol rol = new Rol();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            rol.setNivelAcceso(11); // Nivel inválido > 10
        });
    }
}
```

## Herramientas para pruebas unitarias en Java

1. **JUnit**

   - Framework principal para pruebas en Java
   - Proporciona anotaciones y aserciones
   - Fácil integración con Maven

2. **Mockito**

   - Biblioteca para crear objetos simulados
   - Útil para aislar componentes
   - Simula comportamientos

3. **AssertJ**
   - Aserciones más legibles y fluidas
   - Mejor soporte para colecciones
   - Mensajes de error descriptivos

## Implementación en Virtual Pharmacy

Para implementar pruebas unitarias en el proyecto:

1. **Agregar dependencias en pom.xml**:

```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.8.2</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <version>4.5.1</version>
    <scope>test</scope>
</dependency>
```

2. **Estructura de carpetas**:

```
src/
├── main/java/
│   └── com/virtualpharmacy/
└── test/java/
    └── com/virtualpharmacy/
        ├── dao/
        ├── model/
        └── servlet/
```

## Áreas a probar en Virtual Pharmacy

1. **Modelos (com.virtualpharmacy.model)**

   - Validación de datos
   - Getters y setters
   - Lógica de negocio

2. **DAOs (com.virtualpharmacy.dao)**

   - Operaciones CRUD
   - Manejo de errores
   - Consultas especiales

3. **Servlets (com.virtualpharmacy.servlet)**
   - Procesamiento de peticiones
   - Validación de parámetros
   - Manejo de sesiones

## Mejores prácticas

1. **Nombrado de pruebas**

   - Descriptivo y claro
   - Indica qué se prueba
   - Menciona el resultado esperado

2. **Cobertura de código**

   - Probar casos exitosos
   - Probar casos de error
   - Cubrir casos límite

3. **Mantenimiento**
   - Actualizar pruebas al cambiar código
   - Mantener pruebas simples
   - Evitar duplicación

## Conclusión

Las pruebas unitarias son fundamentales para:

- Asegurar la calidad del código
- Facilitar el mantenimiento
- Documentar el comportamiento
- Permitir refactorizaciones seguras

Se recomienda implementarlas desde el inicio del desarrollo y mantenerlas actualizadas con cada cambio en el código.
