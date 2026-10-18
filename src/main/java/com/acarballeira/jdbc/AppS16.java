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

		try (Connection conexion = DriverManager.getConnection(url)) {
			
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
					
					
						System.out.println("Insertar Alumno");
							
						System.out.println("Introduce DNI");
						String dni = teclado.nextLine();
						
						System.out.println("Introduce NOMBRE");
						String nombre = teclado.nextLine();
						
						System.out.println("Introduce APELLLIDO1");
						String apellido1 = teclado.nextLine();
						
						System.out.println("Introduce APELLLIDO2");
						String apellido2 = teclado.nextLine();
						
						System.out.println("Introduce CICLO");
						String ciclo = teclado.nextLine();
						
						System.out.println("Introduce CURSO");
						int curso = Integer.parseInt(teclado.nextLine());
						
						
						String insertar = """
								INSERT INTO alumno
								(dni, nombre, apellido1, apellido2, ciclo, curso)
								VALUES (?, ?, ?, ?, ?, ?)
								""";

						try (PreparedStatement ps = conexion.prepareStatement(insertar)) {
							ps.setString(1, dni);
							ps.setString(2, nombre);
							ps.setString(3, apellido1);
							ps.setString(4, apellido2);
							ps.setString(5, ciclo);
							ps.setInt(6, curso);
							ps.executeUpdate();
						}
						

				}
				case 2 -> {
					System.out.println("Listado de alumnos");	
					
					try (Statement sentencia = conexion.createStatement();
						 ResultSet resultado = sentencia.executeQuery("SELECT * FROM alumno ORDER BY apellido1, apellido2, nombre")) {
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
						System.out.println("Introduce el nuevo NOMBRE:");
						String nombre = teclado.nextLine();
						ejecutarUpdate(conexion, "nombre = ?", "Nombre actualizado correctamente", nombre, dni);
						break;
						
					case 2:
						System.out.println("Introduce el nuevo Apellido1:");
						String nuevoApellido1 = teclado.nextLine();
						ejecutarUpdate(conexion, "apellido1 = ?", "Apellido1 actualizado correctamente", nuevoApellido1, dni);
						break;	
						
					case 3:
						System.out.println("Introduce el nuevo Apellido2:");
						String nuevoApellido3 = teclado.nextLine();
						ejecutarUpdate(conexion, "apellido2 = ?", "Apellido2 actualizado correctamente", nuevoApellido3, dni);
						break;
						
					case 4:
						System.out.println("Introduce el nuevo Ciclo:");
						String nuevoCiclo = teclado.nextLine();
						ejecutarUpdate(conexion, "ciclo = ?", "Ciclo actualizado correctamente", nuevoCiclo, dni);
						break;
						
					case 5:
						System.out.println("Introduce el nuevo Curso:");
						int nuevoCurso = Integer.parseInt(teclado.nextLine());
						ejecutarUpdate(conexion, "curso = ?", "Curso actualizado correctamente", nuevoCurso, dni);
						break;
						
					case 6:
						System.out.println("Introduce el nuevo DNI:");
						String nuevoDni = teclado.nextLine();
						ejecutarUpdate(conexion, "dni = ?", "DNI actualizado correctamente", nuevoDni, dni);
						break;
						
					case 7:
						System.out.println("Introduce la nota de la evaluación 1:");
						int notaEva1 = Integer.parseInt(teclado.nextLine());
						
						System.out.println("Introduce la nota de la evaluación 2:");
						int notaEva2 = Integer.parseInt(teclado.nextLine());
						
						System.out.println("Introduce la nota de la evaluación 3:");
						int notaEva3 = Integer.parseInt(teclado.nextLine());
						
						int notaFinal = (notaEva1 + notaEva2 + notaEva3) / 3;

						ejecutarUpdate(conexion, "eva1 = ?, eva2 = ?, eva3 = ?, notaFinal = ?",
								"Notas actualizadas correctamente", notaEva1, notaEva2, notaEva3, notaFinal, dni);
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

	// Ejecuta UPDATE alumno SET <asignacion> WHERE dni = ? con los valores dados (el último es el DNI)
	private static void ejecutarUpdate(Connection conexion, String asignacion, String mensajeOk, Object... valores) throws SQLException {
		String update = "UPDATE alumno SET " + asignacion + " WHERE dni = ?";
		
		try (PreparedStatement psModificar = conexion.prepareStatement(update)) {
			for (int k = 0; k < valores.length; k++) {
				if (valores[k] instanceof Integer) {
					psModificar.setInt(k + 1, (Integer) valores[k]);
				} else {
					psModificar.setString(k + 1, (String) valores[k]);
				}
			}
			int filas = psModificar.executeUpdate();
			System.out.println(filas > 0 ? mensajeOk : "No existe ningún alumno con ese DNI");
		}
	}
}
