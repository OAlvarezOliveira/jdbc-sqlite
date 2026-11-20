package miniRetos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Reto03 {
	
	private static final Scanner sc = new Scanner(System.in);
	private static final String URL = "jdbc:sqlite:miniretos.db";

	private static final String SQL_ACTUALIZA_NOTA = "UPDATE alumnos SET nota = ? WHERE id = ?";
	private static final String SQL_BUSCAR_TODOS = "SELECT * FROM alumnos";
	private static final String SQL_BORRA_ALUMNO = "DELETE FROM alumnos WHERE id = ?";

			
	public static void main(String[] args) {
		
		try (Connection conexion = DriverManager.getConnection(URL);
				PreparedStatement ps = conexion.prepareStatement(SQL_ACTUALIZA_NOTA);
					PreparedStatement psEliminar = conexion.prepareStatement(SQL_BORRA_ALUMNO );
								PreparedStatement psMostrarResto = conexion.prepareStatement(SQL_BUSCAR_TODOS)) {
			
			
			// NOTAS
			
			System.out.println("Introduce el ID del alumno para actualizar la nota:");
			int id = Integer.parseInt(sc.nextLine().trim());
			ps.setInt(2, id);
			System.out.println("Introduce la nueva nota del alumno");
			double nota = Double.parseDouble(sc.nextLine().trim());
			ps.setDouble(1, nota);
			int filasActualizadas = ps.executeUpdate();
			
			if(filasActualizadas > 0 ) {
				
				System.out.printf("Se han modificado %d registros \n",filasActualizadas);
			} else {
				
				System.out.printf("Alumno no encontrado \n" );

			}
			
			// ELIMINAR
			System.out.println("Introduce el ID del alumno para eliminar:");
			int idDelete = Integer.parseInt(sc.nextLine().trim());
			psEliminar.setInt(1,idDelete);
			int filasElimindas = psEliminar.executeUpdate();
			
			if(filasElimindas > 0 ) {
				
				System.out.printf("Se han eliminado %d registros \n",filasElimindas);
			} else {
				
				System.out.printf("Alumno no encontrado \n");

			}
			ResultSet restoDB = psMostrarResto.executeQuery();
			
			while(restoDB.next()) {
				
			int idFinal=	        restoDB.getInt("id");
			String nombreFinal=		restoDB.getString("nombre");
			Double notaFinal =	    restoDB.getDouble("nota");

				System.out.printf(" %d %s %.2f \n",idFinal,nombreFinal,notaFinal);
			}
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

	}

}
