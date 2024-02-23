import javax.swing.*;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        // Invoca el hilo de eventos en el hilo de despacho de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            try {
                // Crea una nueva instancia de la clase Ventana_Principal
                new Ventana_Principal();
            } catch (SQLException e) {
                // Maneja excepciones relacionadas con SQL imprimiendo la traza de la pila
                e.printStackTrace();
            }
        });
    }
}
