package miniRetos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Reto07 {

	private static final String DB = "jdbc:sqlite:reto07.db";

	private static final Scanner sc = new Scanner(System.in);

	private static final String SQL_BUSCA_ASIGNATURAS_POR_ID = 
			"""
			SELECT
			m.id_alumno,
			m.asignatura,
			a.nombre
			FROM matriculas AS m INNER JOIN alumnos as a ON m.id_alumno = a.id
			WHERE a.id =?
			ORDER BY m.id
			""";

	public static void main(String[] args) {

		try (Connection conexion = DriverManager.getConnection(DB);
				PreparedStatement ps = conexion.prepareStatement(SQL_BUSCA_ASIGNATURAS_POR_ID);) {

			System.out.println("Introduce el ID del alumno");

			try {

				int id = Integer.parseInt(sc.nextLine());
				ps.setInt(1, id);

				int contador=0;
				int total = 0;

				try (ResultSet rs = ps.executeQuery()) {

						while (rs.next()) {
							
							if(contador == 0) {
								
								
								System.out.printf("Alumno: %s\n", rs.getString("nombre"));
								System.out.printf("Asignaturas: \n");
								contador++;
							}

							System.out.printf("- %s\n", rs.getString("asignatura"));
							total++; 

						}
						if(contador > 0) {
							System.out.printf("Total de asignaturas:%d\n", total);

						}else {
							
							System.out.printf("El alumno no tiene matrículas \n");

						}

					}


			} catch (NumberFormatException e) {

				System.out.println("Error:");
				e.printStackTrace();

			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

}
