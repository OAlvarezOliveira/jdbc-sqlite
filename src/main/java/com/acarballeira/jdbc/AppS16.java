package com.acarballeira.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class AppS16 {
	
	private static String url = "jdbc:sqlite:java26.db";
	private static Scanner teclado = new Scanner(System.in);

	public static void main(String[] args) {

		try (Connection conexion = DriverManager.getConnection(url);
			 Statement sentencia = conexion.createStatement()) {
			
			int opcion;
			
			do {
				System.out.println(""" 
					GESTION DE ALUMNOS
						
						1.Insertar alumno
						2.Listar alumnos
						3.Modificar alumno
						4.Eliminar alumno
						0.Salir
						""");
				
				System.out.println("Elige una opcion:");
				opcion = Integer.parseInt(teclado.nextLine());
				
				switch (opcion) {
				case 1 -> {
					System.out.println("Opción 1: Insertar alumno");
				}
				case 2 -> {
					System.out.println("Listado de alumnos");	
					
					ResultSet resultado = sentencia.executeQuery("SELECT * FROM alumno ORDER BY apellido1, apellido2, nombre");
						
					while (resultado.next()) {
						Object eva1 = resultado.getObject("eva1");
						Object eva2 = resultado.getObject("eva2");
						Object eva3 = resultado.getObject("eva3");
						Object notaFinalBD = resultado.getObject("notaFinal");

						System.out.println(
							resultado.getString("dni") + " - " +
							resultado.getString("apellido1") + " " +
							resultado.getString("apellido2") + ", " +
							resultado.getString("nombre") + " - " +
							resultado.getString("ciclo") + " - Curso " +
							resultado.getInt("curso") + " - Notas: " +
							(eva1 == null ? "sin nota" : eva1) + ", " +
							(eva2 == null ? "sin nota" : eva2) + ", " +
							(eva3 == null ? "sin nota" : eva3) + " - Final: " +
							(notaFinalBD == null ? "sin calcular" : notaFinalBD)
						);
					}
				}
				case 3 -> {
					System.out.println("Introduce el DNI del alumno a modificar:");
					String dni = teclado.nextLine();
					
					System.out.println(""" 
							¿Qué campo quieres modificar?
							1.Nombre
							2.Primer apellido
							3.Segundo apellido
							4.Ciclo
							5.Curso
							6.DNI
							7.Notas
							""");
					int opcion1 = Integer.parseInt(teclado.nextLine());
						
					switch (opcion1) {
					case 1:
						String update = "UPDATE alumno SET nombre = ? WHERE dni = ?";
						System.out.println("Introduce el nuevo NOMBRE:");
						String nombre = teclado.nextLine();
						
						try (PreparedStatement psModificar = conexion.prepareStatement(update)) {
							psModificar.setString(1, nombre);
							psModificar.setString(2, dni);
							int filas = psModificar.executeUpdate();
							System.out.println(filas > 0 ? "Nombre actualizado correctamente" : "No existe ningún alumno con ese DNI");
						}
						break;
						
					case 2:
						String update2 = "UPDATE alumno SET apellido1 = ? WHERE dni = ?";
						System.out.println("Introduce el nuevo Apellido1:");
						String nuevoApellido1 = teclado.nextLine();
						
						try (PreparedStatement psModificar2 = conexion.prepareStatement(update2)) {
							psModificar2.setString(1, nuevoApellido1);
							psModificar2.setString(2, dni);
							int filas2 = psModificar2.executeUpdate();
							System.out.println(filas2 > 0 ? "Apellido1 actualizado correctamente" : "No existe ningún alumno con ese DNI");
						}
						break;	
						
					case 3:
						String update3 = "UPDATE alumno SET apellido2 = ? WHERE dni = ?";
						System.out.println("Introduce el nuevo Apellido2:");
						String nuevoApellido3 = teclado.nextLine();
						
						try (PreparedStatement psModificar3 = conexion.prepareStatement(update3)) {
							psModificar3.setString(1, nuevoApellido3);
							psModificar3.setString(2, dni);
							int filas3 = psModificar3.executeUpdate();
							System.out.println(filas3 > 0 ? "Apellido2 actualizado correctamente" : "No existe ningún alumno con ese DNI");
						}
						break;
						
					case 4:
						String update4 = "UPDATE alumno SET ciclo = ? WHERE dni = ?";
						System.out.println("Introduce el nuevo Ciclo:");
						String nuevoCiclo = teclado.nextLine();
						
						try (PreparedStatement psModificar4 = conexion.prepareStatement(update4)) {
							psModificar4.setString(1, nuevoCiclo);
							psModificar4.setString(2, dni);
							int filas4 = psModificar4.executeUpdate();
							System.out.println(filas4 > 0 ? "Ciclo actualizado correctamente" : "No existe ningún alumno con ese DNI");
						}
						break;
						
					case 5:
						String update5 = "UPDATE alumno SET curso = ? WHERE dni = ?";
						System.out.println("Introduce el nuevo Curso:");
						int nuevoCurso = Integer.parseInt(teclado.nextLine());
						
						try (PreparedStatement psModificar5 = conexion.prepareStatement(update5)) {
							psModificar5.setInt(1, nuevoCurso);
							psModificar5.setString(2, dni);
							int filas5 = psModificar5.executeUpdate();
							System.out.println(filas5 > 0 ? "Curso actualizado correctamente" : "No existe ningún alumno con ese DNI");
						}
						break;
						
					case 6:
						String update6 = "UPDATE alumno SET dni = ? WHERE dni = ?";
						System.out.println("Introduce el nuevo DNI:");
						String nuevoDni = teclado.nextLine();
						
						try (PreparedStatement psModificar6 = conexion.prepareStatement(update6)) {
							psModificar6.setString(1, nuevoDni);
							psModificar6.setString(2, dni);
							int filas6 = psModificar6.executeUpdate();
							System.out.println(filas6 > 0 ? "DNI actualizado correctamente" : "No existe ningún alumno con ese DNI");
						}
						break;
						
					case 7:
						String update7 = "UPDATE alumno SET eva1 = ?, eva2 = ?, eva3 = ?, notaFinal = ? WHERE dni = ?";
						System.out.println("Introduce la nota de la evaluación 1:");
						int notaEva1 = Integer.parseInt(teclado.nextLine());
						
						System.out.println("Introduce la nota de la evaluación 2:");
						int notaEva2 = Integer.parseInt(teclado.nextLine());
						
						System.out.println("Introduce la nota de la evaluación 3:");
						int notaEva3 = Integer.parseInt(teclado.nextLine());
						
						int notaFinal = (notaEva1 + notaEva2 + notaEva3) / 3;

						try (PreparedStatement psModificar7 = conexion.prepareStatement(update7)) {
							psModificar7.setInt(1, notaEva1);
							psModificar7.setInt(2, notaEva2);
							psModificar7.setInt(3, notaEva3);
							psModificar7.setInt(4, notaFinal);
							psModificar7.setString(5, dni);
							int filas7 = psModificar7.executeUpdate();
							System.out.println(filas7 > 0 ? "Notas actualizadas correctamente" : "No existe ningún alumno con ese DNI");
						}
						break;

					default:
						System.out.println("Opción no válida.");
						break;
					}
				}
				case 4 -> {
					System.out.println("Introduce el DNI del alumno que deseas eliminar: ");
					String dniEliminar = teclado.nextLine();
					
					String eliminar = "DELETE FROM alumno WHERE dni = ?";
					
					try (PreparedStatement psDelete = conexion.prepareStatement(eliminar)) {
						psDelete.setString(1, dniEliminar); 
						int filasEliminadas = psDelete.executeUpdate();

						if (filasEliminadas > 0) {
							System.out.println("Alumno eliminado correctamente.");
						} else {
							System.out.println("No se encontró ningún alumno con ese DNI.");
						}
					}
				}
				case 0 -> System.out.println("Saliendo del programa...");
				default -> System.out.println("Opción incorrecta.");
				}
				
			} while (opcion != 0);

		} catch (SQLException e) {
			System.err.println("Error en la base de datos: " + e.getMessage());
		}
	}
}
