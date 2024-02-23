public class Tarea {
    private int id;             // Identificador único de la tarea
    private String nombre;      // Nombre de la tarea
    private String descripcion; // Descripción detallada de la tarea
    private String estado;      // Estado actual de la tarea (por hacer, en progreso, completada, etc.)

    // Constructor que permite inicializar todos los atributos al crear una instancia de la clase
    public Tarea(int id, String nombre, String descripcion, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    // Métodos de acceso para obtener y modificar el ID de la tarea
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Métodos de acceso para obtener y modificar el nombre de la tarea
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Métodos de acceso para obtener y modificar la descripción de la tarea
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // Métodos de acceso para obtener y modificar el estado de la tarea
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}