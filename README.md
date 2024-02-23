# TaskList


## _Descripcion_
Aplicación desarrollada en java que permite a los usuarios agregar nuevas tareas, marcarlas como completadas, eliminarlas de la lista, generar reportes por estado de tareas en curso y tareas completadas

![N|Solid](https://images.vexels.com/media/users/3/166401/isolated/lists/b82aa7ac3f736dd78570dd3fa3fa9e24-icono-del-lenguaje-de-programacion-java.png)

## _Planificacion_
Como equipo planeamos desarrollar el programa en el trascurso de la semana, desde la fecha 16/02/2024 hasta 23/02/2024. Los integrantes del quipo son:

- 16/02/2024: definición de actividades correspondientes a cada miembro del equipo.
- 19/02/2024: primera presentacion para el desarrollo
- 20/02/2024: segunda presentacion para el desarrollo
- 22/02/2024: ultima presentacion para el desarrollo y merge en main

## Diseño
- Pantalla principal
- Pantalla de ver/añadir tareas

## Clases
- Main
- Conexion
- Task
- Task UI Principal
- Task UI de ver/añadir tareas

## Ramas
- Main
- Base de Datos
- Clases
- Diseño

## Funciones

- Cesar Vera (Base de Datos)
- Christian Mendoza (Clases)
- Ignacio Garcia (Diseño)
- 
 ## Configuración del Proyecto
Para utilizar este programa, sigue los pasos a continuación:

1. Crear la Base de Datos en PostgreSQL
Abre tu cliente de PostgreSQL y ejecuta el script SQL proporcionado en el archivo nombre_del_archivo.sql en la carpeta database del repositorio.
2. Modificar la Clase de Conexión
Abre el archivo Conexion.java ubicado en src/Conexion.java.
Cambia la contraseña en la línea correspondiente con las credenciales de tu base de datos PostgreSQL.


// Conexion.java

private static final String URL = "jdbc:postgresql://localhost:5432/tu_basededatos";
private static final String USER = "tu_usuario";
private static final String PASSWORD = "tu_contraseña";


3. Agregar la Librería al Build Path
Incluye la librería postgresql-42.7.0.jar en la carpeta lib en el Build Path de tu proyecto.
4. Ejecutar el Programa
Compila y ejecuta tu programa Java.
5. ¡Disfruta!
