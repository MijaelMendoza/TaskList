import javax.swing.*;
import java.sql.SQLException;


public class Main {
    public static void main(String[] args) throws SQLException {
        SwingUtilities.invokeLater(() -> {
            try {
                new Ventana_Principal();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
