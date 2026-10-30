package mariaDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class AppS44 {
	
	private static final String SQL_INSERTA_ALUMNO = "INSERT INTO alumnos (nombre, apellidos, nota) VALUES (?,?,?)" ;
	private static final Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		try (Connection conexion = ConexionDB.conectaDB();
				PreparedStatement ps = conexion.prepareStatement(SQL_INSERTA_ALUMNO, Statement.RETURN_GENERATED_KEYS);){
			int filas =0;
			int cantidadAlumnos=0;
			double nota=0;

			System.out.print("Cuantos Alumnos deseas introducir: ");
			try {
				cantidadAlumnos = Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Error:" + e);
			}
			
			
			for (int i = 0; i < cantidadAlumnos; i++) {
				System.out.print("Nombre: ");
				String nombre = sc.nextLine();
				System.out.print("Apellidos: ");
				String apellidos = sc.nextLine();
				System.out.print("Nota: ");

				try {
					nota = Double.parseDouble(sc.nextLine().replace(',', '.'));
				} catch (NumberFormatException e) {
					System.out.println("Error:" + e);
				}
				ps.setString(1, nombre);
				ps.setString(2, apellidos);
				ps.setDouble(3, nota);
				filas = +ps.executeUpdate();
				try (ResultSet rs = ps.getGeneratedKeys()) {
					if (rs.next()) {

						int idGenerado = rs.getInt(1);

						System.out.println("ID generado :" + idGenerado);
					}

				} 
			}
			System.out.println("Filas insertadas :" + filas);
			
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		}

	}

}
