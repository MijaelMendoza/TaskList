import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TareaDAO {
    private Connection conexion;

    // Constructor que recibe una conexión a la base de datos
    public TareaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // Agrega una nueva tarea a la base de datos
    public void agregarTarea(Tarea tarea) throws SQLException {
        String query = "INSERT INTO Tareas (nombre, descripcion, estado) VALUES (?, ?, ?)";
        try (PreparedStatement st = conexion.prepareStatement(query)) {
            st.setString(1, tarea.getNombre());
            st.setString(2, tarea.getDescripcion());
            st.setString(3, tarea.getEstado());
            st.executeUpdate();
        }
    }

    // Marca una tarea específica como completada en la base de datos
    public void marcarTareaComoCompletada(int id) throws SQLException {
        String query = "UPDATE Tareas SET estado = 'Completada' WHERE id = ?";
        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    // Elimina una tarea específica de la base de datos
    public void eliminarTarea(int id) throws SQLException {
        String query = "DELETE FROM Tareas WHERE id = ?";
        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    // Obtiene una lista de tareas basada en su estado (por hacer, en progreso, completada, etc.)
    public List<Tarea> obtenerTareasPorEstado(String estado) throws SQLException {
        List<Tarea> tareas = new ArrayList<>();
        String query = "SELECT id, nombre, descripcion, estado FROM Tareas WHERE estado = ?";
        try (PreparedStatement st = conexion.prepareStatement(query)) {
            st.setString(1, estado);
            try (ResultSet resultSet = st.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String nombre = resultSet.getString("nombre");
                    String descripcion = resultSet.getString("descripcion");
                    String estadoTarea = resultSet.getString("estado");
                    Tarea tarea = new Tarea(id, nombre, descripcion, estadoTarea);
                    tareas.add(tarea);
                }
            }
        }
        return tareas;
    }

    // Verifica si una tarea específica existe en la base de datos
    public boolean existeTarea(int id) throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM Tareas WHERE id = ?";
        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    return count > 0;
                }
            }
        }
        return false;
    }

    // Obtiene todas las tareas almacenadas en la base de datos
    public List<Tarea> obtenerTareas() throws SQLException {
        List<Tarea> tareas = new ArrayList<>();
        String query = "SELECT id, nombre, descripcion, estado FROM Tareas";
        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String nombre = resultSet.getString("nombre");
                    String descripcion = resultSet.getString("descripcion");
                    String estadoTarea = resultSet.getString("estado");
                    Tarea tarea = new Tarea(id, nombre, descripcion, estadoTarea);
                    tareas.add(tarea);
                }
            }
        }
        return tareas;
    }
}
