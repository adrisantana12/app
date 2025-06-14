-- Crear la base de datos
CREATE DATABASE myvirtualpharmacy;
USE myvirtualpharmacy;

-- Crear tabla roles
CREATE TABLE roles (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(500) NOT NULL
);

-- Crear tabla usuarios
CREATE TABLE usuarios(
  id INT AUTO_INCREMENT PRIMARY KEY,
  id_rol INT NOT NULL,
  fecha_ingreso DATE NOT NULL,
  nombre VARCHAR(255) NOT NULL,
  apellido VARCHAR(255) NOT NULL,
  tipo_documento VARCHAR(50) NOT NULL,
  numero_documento INT NOT NULL,
  fecha_nacimiento DATE NOT NULL,
  correo VARCHAR(500) NOT NULL,
  numero_telefono VARCHAR(200) NOT NULL,
  direccion_residencia VARCHAR(500) NOT NULL,
  municipio_residencia VARCHAR(500) NOT NULL,
  departamento_residencia VARCHAR(255) NOT NULL,
  genero VARCHAR(50) NOT NULL,
  tipo_rh VARCHAR(10) NOT NULL,
  FOREIGN KEY (id_rol) REFERENCES roles(id)
);

-- Crear tabla credenciales
CREATE TABLE credenciales (
  id INT AUTO_INCREMENT PRIMARY KEY,
  id_usuario INT NOT NULL,
  contrasenia VARCHAR(225) NOT NULL,
  usuario VARCHAR(225) NOT NULL,
  FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
);

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