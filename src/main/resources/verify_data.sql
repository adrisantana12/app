-- Script para verificar la estructura y datos
USE myvirtualpharmacy;

-- Verificar tablas existentes
SELECT TABLE_NAME, TABLE_ROWS 
FROM information_schema.TABLES 
WHERE TABLE_SCHEMA = 'myvirtualpharmacy';

-- Verificar roles
SELECT * FROM roles;

-- Verificar usuarios
SELECT * FROM usuarios;

-- Verificar credenciales
SELECT 
    c.id,
    c.usuario,
    u.nombre,
    u.apellido,
    r.nombre as rol
FROM credenciales c
JOIN usuarios u ON c.id_usuario = u.id
JOIN roles r ON u.id_rol = r.id;

-- Verificar inventario
SELECT * FROM inventario;

-- Verificar usuarios_inactivos
SELECT 
    ui.id,
    u.nombre,
    u.apellido,
    ui.razon,
    ui.descripcion
FROM usuarios_inactivos ui
JOIN usuarios u ON ui.id_usuario = u.id;
