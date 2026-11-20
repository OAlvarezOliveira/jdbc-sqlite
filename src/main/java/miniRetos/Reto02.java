package miniRetos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class Reto02 {

	private static final String URL = "jdbc:sqlite:miniretos.db";

	private static final Scanner sc = new Scanner(System.in);

	private static final String SQL_INSERTAR = """
INSERT INTO alumnos (nombre, nota) VALUES (?, ?);
			""";

	private static final String SQL_MOSTRAR_ALUMNOS = """
			SELECT * FROM alumnos;""";

	public static void main(String[] args) {
		int filas = 0;

		try (Connection conexion = DriverManager.getConnection(URL);
				PreparedStatement ps = conexion.prepareStatement(SQL_INSERTAR);
					PreparedStatement psListar = conexion.prepareStatement(SQL_MOSTRAR_ALUMNOS)) {

			System.out.println("introduce nombre:");
			String nombre = sc.nextLine().trim();
			System.out.println("introduce nota:");
			double nota = Double.parseDouble(sc.nextLine().trim());
			ps.setString(1, nombre);
			ps.setDouble(2, nota);
			filas = ps.executeUpdate();
			
			System.out.println("Alumno insertado correctamente.");
			

			try (ResultSet rsListar = psListar.executeQuery()) {
				while (rsListar.next()) {
				     nombre = rsListar.getString("nombre");
				     nota = rsListar.getDouble("nota");
				    System.out.println("Alumno: " + nombre + " - Nota: " + nota);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("Filas insertadas: "+ filas);


	}

}
