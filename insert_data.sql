-- Script para inserción de datos iniciales
USE myvirtualpharmacy;

-- Insertar roles básicos
INSERT INTO roles (nombre) VALUES 
('Gerente'),
('Administrador'),
('Farmaceutico');

-- Insertar usuarios
INSERT INTO usuarios (id_rol, fecha_ingreso, nombre, apellido, tipo_documento, numero_documento, 
                     fecha_nacimiento, correo, numero_telefono, direccion_residencia, 
                     municipio_residencia, departamento_residencia, genero, tipo_rh) VALUES 
(1, '2025-06-13', 'Juan', 'Pérez', 'CC', 123456789, '1990-05-15', 
 'juan.perez@virtualpharmacy.com', '3001234567', 'Calle 123 #45-67', 
 'Bogotá', 'Cundinamarca', 'Masculino', 'O+'),
 
(2, '2025-06-13', 'María', 'González', 'CC', 987654321, '1988-08-20', 
 'maria.gonzalez@virtualpharmacy.com', '3109876543', 'Carrera 78 #90-12', 
 'Medellín', 'Antioquia', 'Femenino', 'A+'),
 
(3, '2025-06-13', 'Carlos', 'Rodríguez', 'CC', 456789123, '1995-03-10', 
 'carlos.rodriguez@virtualpharmacy.com', '3507894561', 'Avenida 45 #23-56', 
 'Cali', 'Valle del Cauca', 'Masculino', 'B+');

-- Insertar credenciales para usuarios
INSERT INTO credenciales (id_usuario, contrasenia, usuario) VALUES 
(1, 'gerente123', 'gerente'),
(2, 'admin456', 'admin'),
(3, 'farmaceutico789', 'farmaceutico');
