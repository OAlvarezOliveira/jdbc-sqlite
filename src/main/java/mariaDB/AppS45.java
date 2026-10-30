package mariaDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class AppS45 {

	private static final String SQL_INSERTA_ALUMNO = "INSERT INTO alumnos (nombre, apellidos, nota) VALUES (?,?,?)";
	private static final String SQL_INSERTA_MATRICULAS = "INSERT INTO matriculas (id_alumno, asignatura) VALUES (?,?)";
    private static final Scanner sc = new Scanner(System.in); 
    
	public static void main(String[] args) {

		try (Connection conexion = ConexionDB.conectaDB();
				PreparedStatement ps = conexion.prepareStatement(SQL_INSERTA_ALUMNO, Statement.RETURN_GENERATED_KEYS);
				PreparedStatement psMatricula = conexion.prepareStatement(SQL_INSERTA_MATRICULAS)) {

			int filas = 0;
			int cantidadAlumnos = 0;
			double nota = 0;
			int cantidadAsignaturas = 0;
			System.out.print("Cuantos Alumnos deseas introducir: ");
			try {

				cantidadAlumnos = Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Error:" + e);
				return; // Si el número total de alumnos está mal, cerramos el programa de forma segura
			}

			for (int i = 0; i < cantidadAlumnos; i++) {
				int filasMatricula = 0;
				System.out.print("Nombre: ");
				String nombre = sc.nextLine();
				System.out.print("Apellidos: ");
				String apellidos = sc.nextLine();
				System.out.print("Nota: ");

				try {
					nota = Double.parseDouble(sc.nextLine().replace(',', '.'));
				} catch (NumberFormatException e) {
					System.out.println("Error:" + e);
					continue;
				}

				ps.setString(1, nombre);
				ps.setString(2, apellidos);
				ps.setDouble(3, nota);
				filas += ps.executeUpdate();
				try (ResultSet rs = ps.getGeneratedKeys()) {
					if (rs.next()) {

						int idAlumno = rs.getInt(1);

						try {
							System.out.print("En cuantas asignaturas deseas matricular: ");
							cantidadAsignaturas = Integer.parseInt(sc.nextLine());
						} catch (NumberFormatException e) {
							System.out.println("Error:" + e);
						}

						for (int j = 0; j < cantidadAsignaturas; j++) {
							System.out.printf("Asignatura %d: \n", j);
							String asignatura = sc.nextLine();
							psMatricula.setInt(1, idAlumno);
							psMatricula.setString(2, asignatura);
							filasMatricula += psMatricula.executeUpdate();
						}

						System.out.println("Matriculas insertadas :" + filasMatricula);

					}

				}
			}
			System.out.println("Filas insertadas :" + filas);

		} catch (SQLException e) {
			System.out.println("Error:" + e);
		}

		sc.close();
	}
	

}
