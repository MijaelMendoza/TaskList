import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TareaDAO {
    private Connection conexion;

    public TareaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void agregarTarea(Tarea tarea) throws SQLException {
        String query = "INSERT INTO Tareas (nombre, descripcion, estado) VALUES (?, ?, ?)";
        try (PreparedStatement st = conexion.prepareStatement(query)) {
            st.setString(1, tarea.getNombre());
            st.setString(2, tarea.getDescripcion());
            st.setString(3, tarea.getEstado());
            st.executeUpdate();
        }
    }

    public void marcarTareaComoCompletada(int id) throws SQLException {
        String query = "UPDATE Tareas SET estado = 'Completada' WHERE id = ?";
        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public void eliminarTarea(int id) throws SQLException {
        String query = "DELETE FROM Tareas WHERE id = ?";
        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

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
