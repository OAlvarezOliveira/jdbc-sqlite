package mariaDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AppS41_MariaDB {

	private static final String SQL_LISTAR_TODOS = "SELECT * FROM alumnos";
	private static final String SQL_LISTAR_POR_NOTA_MINIMA = "SELECT * FROM alumnos WHERE nota >= ?";


	public static void main(String[] args) {

			listarTodos();
			buscarPorNota();

	}

	private static void buscarPorNota() {
		try (Connection conexion = ConexionDB.conectaDB();
				PreparedStatement ps = conexion.prepareStatement(SQL_LISTAR_POR_NOTA_MINIMA);
				Scanner sc = new Scanner(System.in);) {
			
			int encontrados=0;
			System.out.print("Introduce la nota mínima: ");

			double notaMinima = Double.parseDouble(sc.nextLine());

			ps.setDouble(1, notaMinima);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				int id = rs.getInt("id");
				String nombre = rs.getString("nombre");
				String apellidos = rs.getString("apellidos");
				double nota = rs.getDouble("nota");
				System.out.println(id + " - " + nombre + " " + apellidos + " - Nota: " + nota);

			}
			
			System.out.print("Se han encontrado: " + encontrados);

		} catch (NumberFormatException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}

	}

	private static void listarTodos()  {
		try (Connection conexion = ConexionDB.conectaDB();
				PreparedStatement ps = conexion.prepareStatement(SQL_LISTAR_TODOS); ResultSet rs = ps.executeQuery();) {

			while (rs.next()) {

				int id = rs.getInt("id");
				String nombre = rs.getString("nombre");
				String apellidos = rs.getString("apellidos");
				double nota = rs.getDouble("nota");
				System.out.println(id + " - " + nombre + " " + apellidos + " - Nota: " + nota);

			}

			rs.close();
			ps.close();

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

}
