package mariaDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppS42 {


	private static final String SQL_LISTAR_MATRICULAS = "SELECT * FROM matriculas ORDER BY id";

	public static void main(String[] args) {

		listarTodasMatriculas();
		
	}

	private static void listarTodasMatriculas() {
		try (Connection conexion = ConexionDB.conectaDB();
				PreparedStatement ps = conexion.prepareStatement(SQL_LISTAR_MATRICULAS);
				ResultSet rs = ps.executeQuery()) {
			
			System.out.println("MATRICULAS REGISTRADAS");
			while (rs.next()) {
				int id = rs.getInt("id");
				int idAlumno = rs.getInt("id_alumno");
				String asignatura = rs.getString("asignatura");
				System.out.println(id + " - Alumno " + idAlumno + " - " + asignatura);
			}
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}
