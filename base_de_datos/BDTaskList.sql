-- Creación de la tabla 'Tareas'
CREATE TABLE Tareas (
    id SERIAL PRIMARY KEY,                          -- Columna de identificación autoincremental y clave primaria
    nombre VARCHAR(100) NOT NULL,                   -- Columna para el nombre de la tarea, no puede ser nulo
    descripcion TEXT,                               -- Columna para la descripción más detallada de la tarea
    estado VARCHAR(20) NOT NULL DEFAULT 'Pendiente',-- Columna para el estado de la tarea, predeterminado a 'Pendiente'
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,  -- Columna para la fecha de creación, predeterminado a la fecha y hora actual
    fecha_actualizacion TIMESTAMP,                  -- Columna para la fecha de actualización de la tarea
    completada BOOLEAN NOT NULL DEFAULT FALSE      -- Columna booleana que indica si la tarea está completada, predeterminado a 'FALSE'
);

-- Consulta para seleccionar todos los registros de la tabla 'Tareas'
SELECT * FROM tareas;