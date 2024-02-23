import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    // URL de conexión a la base de datos PostgreSQL
    static final String DB_URL = "jdbc:postgresql://localhost/tasklist";
    // Nombre de usuario de la base de datos
    static final String USER = "postgres";
    // Contraseña de la base de datos
    static final String PASS = "1234";

    // Método para obtener una conexión a la base de datos
    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}