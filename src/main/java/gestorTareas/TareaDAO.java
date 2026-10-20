package gestorTareas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TareaDAO {

	// BBDD
	private static final String URL = "jdbc:sqlite:tareas.db";

	// SQL
	private static final String SQL_CREA_TABLA = "CREATE TABLE IF NOT EXISTS tarea(id INTEGER PRIMARY KEY AUTOINCREMENT, titulo TEXT NOT NULL, hecha INTEGER NOT NULL)";
	private static final String SQL_INSERT_TAREA = "INSERT INTO tarea(titulo,hecha) VALUES(?, ?);";
	private static final String SQL_LISTAR_TODAS = "SELECT * FROM tarea ORDER BY titulo";
	private static final String SQL_ELIMINAR = "DELETE FROM tarea  WHERE id = ?";

	public static void crearTabla() throws SQLException {

		try (Connection conexion = DriverManager.getConnection(URL);
				PreparedStatement ps = conexion.prepareStatement(SQL_CREA_TABLA)

		) {
			ps.executeUpdate();
		}
	}

	private static Tarea mapear(ResultSet rs) throws SQLException {

		return new Tarea(rs.getInt("id"), rs.getString("titulo"), rs.getBoolean("hecha"));

	}

	public static void insertar(Tarea t) throws SQLException {

		try (Connection conexion = DriverManager.getConnection(URL);
				PreparedStatement ps = conexion.prepareStatement(SQL_INSERT_TAREA)

		) {
			ps.setString(1, t.getTitulo());
			ps.setBoolean(2, t.isHecha());
			ps.executeUpdate();
		}
	}

	public static ArrayList<Tarea> listarTodos() throws SQLException {

		ArrayList<Tarea> lista = new ArrayList<Tarea>();

		try (Connection conexion = DriverManager.getConnection(URL);
				PreparedStatement ps = conexion.prepareStatement(SQL_LISTAR_TODAS)

		) {
			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {

					lista.add(mapear(rs));
				}

			}

		}

		return lista;
	}

	public static boolean eliminar(int id) throws SQLException {

		boolean ejecutado = false;

		try (Connection conexion = DriverManager.getConnection(URL);
				PreparedStatement ps = conexion.prepareStatement(SQL_ELIMINAR)) {
			ps.setInt(1, id);

			ejecutado = ps.executeUpdate() > 0;
		}

		return ejecutado;
	}
}
