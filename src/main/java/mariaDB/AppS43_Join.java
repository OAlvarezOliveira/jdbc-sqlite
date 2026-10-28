package mariaDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppS43_Join {
	private static String SQL_BUSCAR_MATRICULAS_FULL_DATOS="SELECT alumnos.nombre, alumnos.apellidos, " +
			 "matriculas.asignatura FROM alumnos " +
			 "INNER JOIN matriculas " +
			 "ON alumnos.id = matriculas.id_alumno " +
			 "ORDER BY alumnos.id, matriculas.id";


	public static void main(String[] args) {
		
		listarMatriculasFullDatos();

	}

	private static void listarMatriculasFullDatos() {
		try (Connection conexion = ConexionDB.conectaDB();
				PreparedStatement ps = conexion.prepareStatement(SQL_BUSCAR_MATRICULAS_FULL_DATOS);
						ResultSet rs = ps.executeQuery()) {
			
			System.out.println("MATRÍCULAS REGISTRADAS");
			
			while(rs.next()) {
				String alumnosNombre =rs.getString("nombre");
				String alumnosApellidos =rs.getString("apellidos");
				String matriculasAsignatura=rs.getString("asignatura");
				System.out.printf("Nombre: %s ; Apellidos: %s ; Matriculas: %s \n",alumnosNombre,alumnosApellidos,matriculasAsignatura);
			}

			
		} catch (SQLException e) {
			System.out.println("Error: " + e);

		}
		
		
	}

}
