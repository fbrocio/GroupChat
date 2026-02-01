-- 1. Se crean las tablas usuario, grupo y mensaje

CREATE TABLE usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    password VARCHAR(20) NOT NULL,
    alias VARCHAR(100) NOT NULL,
    UNIQUE (alias)
);

CREATE TABLE grupo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    clave VARCHAR(50) NOT NULL,
    puerto INT NOT NULL
);

CREATE TABLE mensaje (
    id INT AUTO_INCREMENT PRIMARY KEY,
    contenido VARCHAR(1000),
    fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    grupo_id INT NOT NULL,
    usuario_id INT NOT NULL,
    FOREIGN KEY (grupo_id) REFERENCES grupo(id),
    FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

-- 2. Se crean tres grupos
INSERT INTO grupo (nombre, clave, puerto) VALUES
('Acceso a datos', 'VFP-AD', 5001),
('Programación de Servicios y Procesos', 'VFP-PSP', 5002),
('Programación multimedia y dispositivos móviles', 'VFP-PMDM', 5003);

-- 3. Se crean tres usuarios de prueba
INSERT INTO usuario (password, alias) VALUES
('VigaraFP123', 'profesor1'),
('VigaraFP123', 'alumno_prueba'),
('VigaraFP123', 'administrador');
