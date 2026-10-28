package mariaDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AppS43_BuscarMatriculas {

    private static final String BUSCAR_MATRICULAS_ID = 
            "SELECT alumnos.nombre, alumnos.apellidos, matriculas.asignatura " +
            "FROM alumnos " +
            "INNER JOIN matriculas ON alumnos.id = matriculas.id_alumno " +
            "WHERE alumnos.id = ? " +
            "ORDER BY matriculas.id";

	public static void main(String[] args) {

		buscarMatriculasID();

	}

	private static void buscarMatriculasID() {
		int id = -1;
		int contador = 0;
		try (Connection conexion = ConexionDB.conectaDB();
				PreparedStatement ps = conexion.prepareStatement(BUSCAR_MATRICULAS_ID);
				Scanner sc = new Scanner(System.in);) {
			System.out.print("Introduce el ID del alumno: ");

			try {
				id = Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Error:" + e);
			}
			ps.setInt(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				boolean encontrado = false;

				while (rs.next()) {
					if (!encontrado) {
						System.out.println(
								"Asignaturas de " + rs.getString("nombre") + " " + rs.getString("apellidos") + ":");
					}
					System.out.println("- " + rs.getString("asignatura"));
					contador++;
					encontrado = true;
				}
				if (!encontrado) {
					System.out.println("No hay matrículas para ese alumno.");
				}else {
					 System.out.println("Total de asignaturas: " + contador);
				}
			}

		} catch (SQLException e) {
			System.out.println("Error: " + e);
		}
	}

}
