package mariaDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class AppS42_InsertarMatricula {


	private static final String SQL_INSERTAR_MATRICULAS = "INSERT INTO matriculas (id_alumno,asignatura) VALUES (?,?)";

	public static void main(String[] args) {

		insertarMatricula();

	}

	private static void insertarMatricula() {
		int idAlumno = -1;

		try (Connection conexion = ConexionDB.conectaDB();
				Scanner sc = new Scanner(System.in);
				PreparedStatement ps = conexion.prepareStatement(SQL_INSERTAR_MATRICULAS);) {
			
			System.out.println("Introduce el ID del Alumno");
			try {
				 idAlumno =Integer.parseInt(sc.nextLine());
				
			} catch (NumberFormatException e) {
				System.out.println("Error: " + e.getMessage());
			}
			
			System.out.print("Introduce la asignatura: ");
			String asignatura = sc.nextLine();
			
			 ps.setInt(1, idAlumno); 
			 ps.setString(2, asignatura); 
			 int filas = ps.executeUpdate();
			 System.out.println("Filas insertadas: " + filas);
			

		} catch (SQLException e) {
			
			System.out.println("Error: " + e.getMessage());

		}

	}
}