
// Importar las clases necesarias del paquete java.awt y javax.swing
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.Color;

// Declarar la clase principal Ventana_Principal implementando ActionListener
class Ventana_Principal implements ActionListener {
	// Declarar variables de instancia
	private JFrame frame;
	private JComboBox<String> opcionesMenu;
	private JButton botonEjecutar;
	private JTextArea textAreaResultado;
	private JTable tablaTareas;
	private TareaDAO tareaDAO;

	// Constructor de la clase Ventana_Principal
	public Ventana_Principal() throws SQLException {
		// Inicializar el JFrame
		frame = new JFrame("TaskList");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		// Inicializar el objeto TareaDAO
		tareaDAO = new TareaDAO(Conexion.obtenerConexion());

		// Declarar y asignar opciones para el JComboBox
		String[] opciones = { "Agregar nueva tarea", "Marcar tarea como completada", "Eliminar tarea",
				"Generar reporte de tareas en curso", "Generar reporte de tareas completadas",
				"Generar reporte de tareas pendientes" };
		opcionesMenu = new JComboBox<>(opciones);

		// Crear un panel superior que contiene el JComboBox y el JButton
		JPanel panelSuperior = new JPanel();
		panelSuperior.add(opcionesMenu);
		botonEjecutar = new JButton("Aceptar");
		botonEjecutar.addActionListener(this);
		panelSuperior.add(botonEjecutar);

		// Agregar el panel superior al JFrame en la posición BorderLayout.NORTH
		frame.add(panelSuperior, BorderLayout.NORTH);

		// Crear tabla para mostrar las tareas
		tablaTareas = new JTable();
		JScrollPane scrollPane = new JScrollPane(tablaTareas);
		frame.add(scrollPane, BorderLayout.CENTER);

		// Hacer que el JFrame sea visible
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	// Implementación del método actionPerformed de la interfaz ActionListener
	@Override
	public void actionPerformed(ActionEvent e) {
		// Obtener la opción seleccionada del JComboBox
		String opcionSeleccionada = (String) opcionesMenu.getSelectedItem();
		// Realizar acciones según la opción seleccionada
		switch (opcionSeleccionada) {
		case "Agregar nueva tarea":
			mostrarDialogoAgregarTarea();
			break;
		case "Marcar tarea como completada":
			mostrarVentanaDetalles("Marcar tarea como completada");
			break;
		case "Eliminar tarea":
			mostrarVentanaDetalles("Eliminar tarea");
			break;
		case "Generar reporte de tareas en curso":
			try {
				actualizarTablaTareas("En progreso");
			} catch (SQLException ex) {
				mostrarError("Error al generar el reporte: " + ex.getMessage());
			}
			break;
		case "Generar reporte de tareas completadas":
			try {
				actualizarTablaTareas("Completada");
			} catch (SQLException ex) {
				mostrarError("Error al generar el reporte: " + ex.getMessage());
			}
			break;
		case "Generar reporte de tareas pendientes":
			try {
				actualizarTablaTareas("Pendiente");
			} catch (SQLException ex) {
				mostrarError("Error al generar el reporte: " + ex.getMessage());
			}
			break;
		}
	}

	// Método privado para actualizar la tabla de tareas según el estado
	// especificado
	private void actualizarTablaTareas(String estado) throws SQLException {
		// Obtener la lista de tareas según el estado
		List<Tarea> tareas;
		if (estado.equals("Pendiente")) {
			tareas = tareaDAO.obtenerTareasPorEstado("Pendiente");
		}
		if (estado.equals("En progreso")) {
			tareas = tareaDAO.obtenerTareasPorEstado("En progreso");
		}
		else {
			tareas = tareaDAO.obtenerTareasPorEstado(estado);
		}

		// Verificar si la lista de tareas está vacía
		if (tareas.isEmpty()) {
			mostrarMensaje("No hay tareas en este estado.");
			tablaTareas.setVisible(false);
		} else {
			tablaTareas.setVisible(true);
			// Crear un modelo de tabla y asignar los datos de las tareas
			TareaTableModel modeloTabla = new TareaTableModel(tareas);
			tablaTareas.setModel(modeloTabla);
		}
	}

	// Clase interna que extiende AbstractTableModel para definir el modelo de la
	// tabla de tareas
	private static class TareaTableModel extends AbstractTableModel {
		private final List<Tarea> tareas;
		private final String[] columnNames = { "ID", "Nombre", "Descripción", "Estado" };

		// Constructor de la clase TareaTableModel
		public TareaTableModel(List<Tarea> tareas) {
			this.tareas = tareas;
		}

		// Métodos requeridos por AbstractTableModel
		@Override
		public int getRowCount() {
			return tareas.size();
		}

		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public String getColumnName(int columnIndex) {
			return columnNames[columnIndex];
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Tarea tarea = tareas.get(rowIndex);
			switch (columnIndex) {
			case 0:
				return tarea.getId();
			case 1:
				return tarea.getNombre();
			case 2:
				return tarea.getDescripcion();
			case 3:
				return tarea.getEstado();
			default:
				return null;
			}
		}
	}

	// Método privado para mostrar la ventana de detalles según la acción
	// especificada
	private void mostrarVentanaDetalles(String accion) {
		try {
			// Obtener todas las tareas disponibles
			List<Tarea> todasLasTareas = tareaDAO.obtenerTareas();
			tablaTareas.setVisible(true);
			// Verificar si no hay tareas disponibles
			if (todasLasTareas.isEmpty()) {
				tablaTareas.setVisible(false);
				mostrarMensaje("No hay tareas disponibles.");
				return;
			}

			// Filtrar tareas según su estado y la acción
			List<Tarea> tareasDisponibles = new ArrayList<>();
			for (Tarea tarea : todasLasTareas) {
				if (!(tarea.getEstado().equals("Completada") && accion.equals("Marcar tarea como completada"))
						&& !(tarea.getEstado().equals("Eliminada") && accion.equals("Eliminar tarea"))) {
					tareasDisponibles.add(tarea);
				}
			}

			// Verificar si no hay tareas disponibles para la acción
			if (tareasDisponibles.isEmpty()) {
				tablaTareas.setVisible(false);
				mostrarMensaje("No hay tareas disponibles para esta acción.");
				return;
			}

			// Crear lista de IDs de tareas
			List<String> idsTareas = new ArrayList<>();
			for (Tarea tarea : tareasDisponibles) {
				if(tarea.getEstado().equals("Pendiente")) {
					idsTareas.add(String.valueOf(tarea.getId()));	
				}
			}

			// Crear un JComboBox con la lista de IDs de tareas
			JComboBox<String> tareasComboBox = new JComboBox<>(idsTareas.toArray(new String[0]));

			// Crear un panel para mostrar el JComboBox
			JPanel panel = new JPanel(new GridLayout(0, 1));
			panel.add(new JLabel("Seleccione la tarea:"));
			panel.add(tareasComboBox);

			// Mostrar un cuadro de diálogo para seleccionar la tarea
			int resultado = JOptionPane.showConfirmDialog(null, panel, accion, JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE);

			// Verificar si se seleccionó "Aceptar"
			if (resultado == JOptionPane.OK_OPTION) {
				// Obtener el ID de la tarea seleccionada
				String idTareaSeleccionada = (String) tareasComboBox.getSelectedItem();
				// Buscar la tarea correspondiente al ID
				Tarea tareaSeleccionada = null;
				for (Tarea tarea : tareasDisponibles) {
					if (String.valueOf(tarea.getId()).equals(idTareaSeleccionada)) {
						tareaSeleccionada = tarea;
						break;
					}
				}

				// Verificar si se encontró la tarea seleccionada
				if (tareaSeleccionada != null) {
					mostrarVentanaDetallesTarea(tareaSeleccionada, accion);
				} else {
					mostrarError("Seleccione una tarea válida.");
				}
			}
		} catch (SQLException ex) {
			mostrarError("Error al obtener las tareas: " + ex.getMessage());
		}
	}

	// Método privado para mostrar la ventana de detalles de una tarea
	private void mostrarVentanaDetallesTarea(Tarea tarea, String accion) {
		// Crear un nuevo JFrame para mostrar los detalles de la tarea
		JFrame ventanaDetalles = new JFrame("Detalles de la tarea");
		ventanaDetalles.setLayout(new BorderLayout());

		// Crear un panel para mostrar los detalles de la tarea
		JPanel panelDetalles = new JPanel(new GridLayout(0, 1));
		panelDetalles.add(new JLabel("ID: " + tarea.getId()));
		panelDetalles.add(new JLabel("Nombre: " + tarea.getNombre()));
		panelDetalles.add(new JLabel("Descripción: " + tarea.getDescripcion()));
		panelDetalles.add(new JLabel("Estado: " + tarea.getEstado()));

		// Agregar el panel de detalles al JFrame
		ventanaDetalles.add(panelDetalles, BorderLayout.CENTER);

		// Crear un botón para confirmar la acción
		JButton botonConfirmar = new JButton(accion);
		botonConfirmar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					// Realizar la acción según la opción seleccionada
					if (accion.equals("Marcar tarea como completada")) {
						tareaDAO.marcarTareaComoCompletada(tarea.getId());
						mostrarMensaje("Tarea marcada como completada correctamente.");
					} else if (accion.equals("Eliminar tarea")) {
						tareaDAO.eliminarTarea(tarea.getId());
						mostrarMensaje("Tarea eliminada correctamente.");
					}
					// Cerrar la ventana de detalles
					ventanaDetalles.dispose();
				} catch (SQLException ex) {
					mostrarError("Error al realizar la acción: " + ex.getMessage());
				}
			}
		});

		// Agregar el botón de confirmación al JFrame
		ventanaDetalles.add(botonConfirmar, BorderLayout.SOUTH);

		// Hacer visible el JFrame de detalles
		ventanaDetalles.pack();
		ventanaDetalles.setLocationRelativeTo(null);
		ventanaDetalles.setVisible(true);
	}

	// Método privado para generar un reporte de tareas según el estado
	private void generarReporteTareas(String estado) throws SQLException {
		// Obtener la lista de tareas según el estado
		List<Tarea> tareas = null;
		if (estado.equals("Pendiente") || estado.equals("En progreso")) {
			tareas = tareaDAO.obtenerTareasPorEstado("Pendiente");
			tareas.addAll(tareaDAO.obtenerTareasPorEstado("En progreso"));
		} else {
			tareas = tareaDAO.obtenerTareasPorEstado(estado);
		}

		// Verificar si la lista de tareas está vacía
		if (tareas.isEmpty()) {
			mostrarMensaje("No hay tareas en este estado.");
		} else {
			// Crear un StringBuilder para construir el reporte de tareas
			StringBuilder reporte = new StringBuilder("Reporte de tareas " + estado + ":\n");
			for (Tarea tarea : tareas) {
				reporte.append("ID: ").append(tarea.getId()).append("\n");
				reporte.append("Nombre: ").append(tarea.getNombre()).append("\n");
				reporte.append("Descripción: ").append(tarea.getDescripcion()).append("\n");
				reporte.append("Estado: ").append(tarea.getEstado()).append("\n");
				reporte.append("-------------------------\n");
			}
			// Mostrar el reporte en el JTextArea
			textAreaResultado.setText(reporte.toString());
		}
	}

	// Método privado para mostrar el diálogo de agregar nueva tarea
	private void mostrarDialogoAgregarTarea() {
		// Crear componentes para el diálogo de agregar nueva tarea
		JTextField nombreField = new JTextField();
		JTextField descripcionField = new JTextField();
		JComboBox<String> estadoComboBox = new JComboBox<>(new String[] { "Pendiente", "En progreso", "Completada" });

		// Crear un panel para el diálogo con GridLayout
		JPanel panel = new JPanel(new GridLayout(0, 1));
		panel.add(new JLabel("Nombre de la tarea:"));
		panel.add(nombreField);
		panel.add(new JLabel("Descripción de la tarea:"));
		panel.add(descripcionField);
		panel.add(new JLabel("Estado de la tarea:"));
		panel.add(estadoComboBox);

		// Mostrar el diálogo y obtener el resultado
		int resultado = JOptionPane.showConfirmDialog(null, panel, "Agregar nueva tarea", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);

		// Verificar si se seleccionó "Aceptar"
		if (resultado == JOptionPane.OK_OPTION) {
			try {
				// Obtener los datos de la nueva tarea
				String nombre = nombreField.getText();
				String descripcion = descripcionField.getText();
				String estado = (String) estadoComboBox.getSelectedItem();
				// Crear una nueva tarea con ID 0 para que se genere automáticamente en la base
				// de datos
				Tarea nuevaTarea = new Tarea(0, nombre, descripcion, estado);
				// Agregar la nueva tarea a la base de datos
				tareaDAO.agregarTarea(nuevaTarea);
				// Mostrar mensaje de éxito
				mostrarMensaje("Tarea agregada correctamente.");
			} catch (SQLException ex) {
				// Mostrar mensaje de error en caso de excepción
				mostrarError("Error al agregar la tarea: " + ex.getMessage());
			}
		}
	}

	// Método privado para mostrar un mensaje en un cuadro de diálogo
	private void mostrarMensaje(String mensaje) {
		JOptionPane.showMessageDialog(frame, mensaje);
	}

	// Método privado para mostrar un mensaje de error en un cuadro de diálogo
	private void mostrarError(String mensaje) {
		JOptionPane.showMessageDialog(frame, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
	}
}
