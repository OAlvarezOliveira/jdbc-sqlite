package alumnosCSV;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AppS51 {

	private static Scanner sc = new Scanner(System.in);

	private static String SQL_BUSCAR_TODOS = "SELECT * FROM alumnos";
	private static String SQL_BUSCAR_POR_ID = "SELECT * FROM alumnos WHERE id = ?";
	private static String SQL_INSERTAR_ALUMNO = "INSERT INTO alumnos (nombre,apellidos,nota) VALUES (?,?,?)";
	private static String SQL_MODIFICAR_POR_ID = "UPDATE alumnos SET nombre =? , apellidos=?,nota=? WHERE id = ?";
	private static String SQL_ELIMINA_POR_ID = "DELETE FROM alumnos WHERE id = ?";

	public static void main(String[] args) {
		int opcion;

		do {

			System.out.println("===== GESTION DE ALUMNOS =====");
			System.out.println("1. Mostrar Alumnos");
			System.out.println("2. Buscar Alumno por DNI");
			System.out.println("3. Insertar Alumno");
			System.out.println("4. Modificar Alumno");
			System.out.println("5. Eliminar");
			System.out.println("0. Salir");
			System.out.println("Elige una opcion");

			try {
				opcion = Integer.parseInt(sc.nextLine());
				switch (opcion) {
				case 1 -> {
					System.out.println("Mostrar Alumnos");
					mostrarAlumnos();
				}
				case 2 -> {
					System.out.println("Buscar Alumno");
					buscarAlumno();
				}
				case 3 -> {
					System.out.println("Insertar Alumno");
					insertarAlumno();
				}
				case 4 -> {
					System.out.println("Modificar Alumno");
					modificarAlumno();
				}
				case 5 -> {
					System.out.println("Eliminar Alumno");
					eliminarAlumno();
				}
				case 0 -> {
					System.out.println("Saliendo");
					break;
				}
				default -> System.out.println("Opcion incorrecta");
				}

			} catch (NumberFormatException e) {
				System.out.println("Introduce un numero valido");
				opcion = -1;
			}

		} while (opcion != 0);

		sc.close();

	}

	private static void eliminarAlumno() {
		
		System.out.println("Introduce el ID del alumno a modificar:");
		int id = sc.nextInt();
		sc.nextLine();
		
		System.out.println("Seguro que quieres eliminarlo? (s/n):");
		String respuesta = sc.nextLine();

		
		if(!respuesta.equalsIgnoreCase("s")) {
			
			System.out.println("Eliminacion cancelada");
			return;
		}
		
		try (Connection conexion = ConexionDB.conectaDB();
				PreparedStatement ps = conexion.prepareStatement(SQL_ELIMINA_POR_ID)) {
			
			ps.setInt(1, id);
			
			int filas = ps.executeUpdate();

			if(filas>0) {
				System.out.println("Alumno eliminado correctamente");
			}else {
				System.out.println("No existe ningún alumno con ese ID");
			}
		
		} catch (SQLException e) {
			System.out.println("Error:" + e);
			e.printStackTrace();
		}
	}

	private static void modificarAlumno() {

		System.out.println("Introduce el ID del alumno a modificar:");
		int id = sc.nextInt();
		sc.nextLine();

		System.out.println("Introduce el nombre del alumno:");
		String nombre = sc.nextLine();

		System.out.println("Introduce el apellido del alumno");
		String apellidos = sc.nextLine();

		System.out.println("Introduce la nota del alu,mno");
		double nota = sc.nextDouble();

		try (Connection conexion = ConexionDB.conectaDB();
				PreparedStatement ps = conexion.prepareStatement(SQL_MODIFICAR_POR_ID)) {
			

			ps.setString(1, nombre);
			ps.setString(2, apellidos);
			ps.setDouble(3, nota);
			ps.setInt(4, id);
			
			int filas = ps.executeUpdate();

			if(filas>0) {
				System.out.println("Alumno modificado correctamente");
			}else {
				System.out.println("No existe ningún alumno con ese ID");
			}

		} catch (SQLException e) {
			System.out.println("Error:" + e);
			e.printStackTrace();
		}
	}

	private static void insertarAlumno() {

		System.out.println("Introduce el nombre del alumno:");
		String nombre = sc.nextLine();

		System.out.println("Introduce el apellido del alumno");
		String apellidos = sc.nextLine();

		System.out.println("Introduce la nota del alu,mno");
		double nota = sc.nextDouble();

		try (Connection conexion = ConexionDB.conectaDB();
				PreparedStatement ps = conexion.prepareStatement(SQL_INSERTAR_ALUMNO)) {

			ps.setString(1, nombre);
			ps.setString(2, apellidos);
			ps.setDouble(3, nota);

			int filas = ps.executeUpdate();

			System.out.printf("Alumnos insertados: %d \n" + filas);

		} catch (SQLException e) {
			System.out.println("Error:" + e);
			e.printStackTrace();
		}

	}

	private static void buscarAlumno() {

		System.out.println("Introduce el ID del alumno:");
		int id = Integer.parseInt(sc.nextLine());

		try (Connection conexion = ConexionDB.conectaDB();
				PreparedStatement ps = conexion.prepareStatement(SQL_BUSCAR_POR_ID)) {

			ps.setInt(1, id);
			try (ResultSet rs = ps.executeQuery()) {

				if (rs.next()) {

					System.out.printf("ID:%d - nombre: %s %s - Nota:%.2f \n", rs.getInt("id"), rs.getString("nombre"),
							rs.getString("apellidos"), rs.getDouble("nota"));

				} else {
					System.out.println("Alumno no encontrado");

				}

			}

		} catch (SQLException e) {
			System.out.println("Error:" + e);
			e.printStackTrace();
		}

	}

	private static void mostrarAlumnos() {
		try (Connection conexion = ConexionDB.conectaDB();
				PreparedStatement ps = conexion.prepareStatement(SQL_BUSCAR_TODOS);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {

				System.out.printf("ID:%d - nombre: %s %s - Nota:%.2f \n", rs.getInt("id"), rs.getString("nombre"),
						rs.getString("apellidos"), rs.getDouble("nota"));

			}

		} catch (SQLException e) {
			System.out.println("Error:" + e);
			e.printStackTrace();
		}

	}

}
