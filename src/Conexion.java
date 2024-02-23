import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	static final String DB_URl = "jdbc:postgresql://localhost/tasklist";
    static final String USER ="postgres";
    static final String PASS = "3211";

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(DB_URl, USER, PASS);
    }
}
