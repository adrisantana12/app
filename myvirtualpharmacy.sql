-- Crear la base de datos
CREATE DATABASE myvirtualpharmacy;
USE myvirtualpharmacy;

-- Crear tabla roles
CREATE TABLE roles (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(50) NOT NULL,
  descripcion VARCHAR(255),
  nivel_acceso INT NOT NULL DEFAULT 1,
  fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  fecha_modificacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  esta_activo BOOLEAN NOT NULL DEFAULT TRUE,
  
  -- Restricciones
  UNIQUE KEY uk_roles_nombre (nombre),
  CHECK (nivel_acceso BETWEEN 1 AND 10)
) COMMENT 'Almacena los roles y niveles de acceso del sistema';

-- Crear tabla usuarios
CREATE TABLE usuarios (
  -- Identificación y rol
  id INT AUTO_INCREMENT PRIMARY KEY,
  id_rol INT NOT NULL,
  
  -- Información personal básica
  nombre VARCHAR(100) NOT NULL,
  apellido VARCHAR(100) NOT NULL,
  fecha_nacimiento DATE NOT NULL,
  genero VARCHAR(20) NOT NULL CHECK (genero IN ('MASCULINO', 'FEMENINO', 'OTRO')),
  tipo_rh VARCHAR(5) NOT NULL CHECK (tipo_rh IN ('O+', 'O-', 'A+', 'A-', 'B+', 'B-', 'AB+', 'AB-')),
  
  -- Documentación
  tipo_documento VARCHAR(20) NOT NULL CHECK (tipo_documento IN ('CC', 'CE', 'TI', 'PASAPORTE')),
  numero_documento VARCHAR(20) NOT NULL,
  
  -- Contacto
  correo VARCHAR(255) NOT NULL,
  numero_telefono VARCHAR(20) NOT NULL,
  
  -- Ubicación
  direccion_residencia VARCHAR(255) NOT NULL,
  municipio_residencia VARCHAR(100) NOT NULL,
  departamento_residencia VARCHAR(100) NOT NULL,
  
  -- Metadata
  fecha_ingreso DATE NOT NULL DEFAULT CURRENT_DATE,
  fecha_modificacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  esta_activo BOOLEAN NOT NULL DEFAULT TRUE,
  
  -- Restricciones
  UNIQUE KEY uk_usuario_documento (tipo_documento, numero_documento),
  UNIQUE KEY uk_usuario_correo (correo),
  FOREIGN KEY (id_rol) REFERENCES roles(id)
) COMMENT 'Almacena la información de los usuarios del sistema';

-- Crear tabla credenciales
CREATE TABLE credenciales (
  id INT AUTO_INCREMENT PRIMARY KEY,
  id_usuario INT NOT NULL,
  usuario VARCHAR(50) NOT NULL,
  contrasenia VARCHAR(255) NOT NULL,
  ultimo_acceso TIMESTAMP NULL DEFAULT NULL,
  intentos_fallidos INT NOT NULL DEFAULT 0,
  bloqueada BOOLEAN NOT NULL DEFAULT FALSE,
  fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  fecha_modificacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  esta_activa BOOLEAN NOT NULL DEFAULT TRUE,
  
  -- Restricciones
  UNIQUE KEY uk_credenciales_usuario (usuario),
  FOREIGN KEY fk_credenciales_usuario (id_usuario) REFERENCES usuarios(id),
  
  -- Índices
  INDEX idx_credenciales_estado (esta_activa, bloqueada)
) COMMENT 'Almacena las credenciales de acceso de los usuarios';

-- Crear tabla inventario
CREATE TABLE inventario (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre_medicamento VARCHAR(500) NOT NULL,
  presentacion VARCHAR(500) NOT NULL,
  cantidad_min_alerta INT NOT NULL,
  stock INT NOT NULL,
  fecha_ingreso DATE NOT NULL,
  fecha_vencimiento DATE NOT NULL,
  via_administracion VARCHAR(225) NOT NULL,
  almacenamiento VARCHAR(250) NOT NULL,
  especificaciones VARCHAR(500) NOT NULL,
  precio INT NOT NULL,
  peso INT NOT NULL,
  categoria VARCHAR(225) NOT NULL,
  esta_inactivo VARCHAR(50) NOT NULL,
  usuario_registra VARCHAR(100) NOT NULL
);

-- Crear tabla usuarios_inactivos
CREATE TABLE usuarios_inactivos (
  id INT AUTO_INCREMENT PRIMARY KEY,
  id_usuario INT NOT NULL,
  razon VARCHAR(500) NOT NULL,
  descripcion VARCHAR(500) NOT NULL,
  FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
);

-- Crear tabla auditoria
CREATE TABLE auditoria (
  id INT AUTO_INCREMENT PRIMARY KEY,
  tabla VARCHAR(100) NOT NULL,
  accion VARCHAR(50) NOT NULL,
  id_registro INT NOT NULL,
  detalles TEXT NOT NULL,
  id_usuario INT,
  fecha_hora TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  ip_address VARCHAR(50),
  FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
);

-- Insertar roles básicos
INSERT INTO roles (nombre, descripcion, nivel_acceso, esta_activo) VALUES 
('GERENTE', 'Control total del sistema y acceso a todos los módulos', 10, TRUE),
('ADMINISTRADOR', 'Gestión de usuarios y acceso a módulos administrativos', 8, TRUE),
('FARMACEUTICO', 'Gestión de inventario y atención a clientes', 5, TRUE);

-- Insertar usuarios de ejemplo
INSERT INTO usuarios (
    id_rol, nombre, apellido, fecha_nacimiento, genero, tipo_rh,
    tipo_documento, numero_documento, correo, numero_telefono,
    direccion_residencia, municipio_residencia, departamento_residencia,
    fecha_ingreso
) VALUES 
-- Gerente
(1, 'Juan', 'Pérez', '1980-05-15', 'MASCULINO', 'O+',
'CC', '123456789', 'juan.perez@virtualpharmacy.com', '3001234567',
'Calle 123 #45-67', 'Bogotá', 'Cundinamarca',
'2025-06-17'),

-- Administrador
(2, 'María', 'González', '1985-08-22', 'FEMENINO', 'A+',
'CC', '987654321', 'maria.gonzalez@virtualpharmacy.com', '3157894561',
'Carrera 78 #12-34', 'Medellín', 'Antioquia',
'2025-06-17'),

-- Farmacéutico
(3, 'Carlos', 'Rodríguez', '1990-03-10', 'MASCULINO', 'B+',
'CC', '456789123', 'carlos.rodriguez@virtualpharmacy.com', '3209876543',
'Avenida 45 #89-12', 'Cali', 'Valle del Cauca',
'2025-06-17');

-- Insertar credenciales para los usuarios
INSERT INTO credenciales (
    id_usuario, 
    usuario, 
    contrasenia, 
    fecha_creacion, 
    esta_activa
) VALUES 
(1, 'gerente', SHA2(CONCAT('gerente123', 'VirtualPharmacy2025'), 256), CURRENT_TIMESTAMP, TRUE),
(2, 'admin', SHA2(CONCAT('admin456', 'VirtualPharmacy2025'), 256), CURRENT_TIMESTAMP, TRUE),
(3, 'farmaceutico', SHA2(CONCAT('farmaceutico789', 'VirtualPharmacy2025'), 256), CURRENT_TIMESTAMP, TRUE);

-- Índices para mejorar el rendimiento
CREATE INDEX idx_usuarios_rol ON usuarios(id_rol);
CREATE INDEX idx_credenciales_usuario ON credenciales(usuario);
CREATE INDEX idx_inventario_nombre ON inventario(nombre_medicamento);
CREATE INDEX idx_auditoria_fecha ON auditoria(fecha_hora);
CREATE INDEX idx_auditoria_tabla ON auditoria(tabla);
CREATE INDEX idx_auditoria_usuario ON auditoria(id_usuario);