package com.acarballeira.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class AppS23 {
	// Statics
	private static final String URL = "jdbc:sqlite:java26.db";
	private static ArrayList<Alumno> listaAlumnos = new ArrayList<>();
	private static Scanner teclado = new Scanner(System.in);

	// QUERIES 
	private static String sql = "SELECT * FROM alumno";
	private static String sqlBusqueda = "SELECT * FROM alumno WHERE LOWER(apellido1) LIKE LOWER(?) ORDER BY apellido1, nombre";
	private static String sqlInsert = "INSERT INTO alumno (dni, nombre, apellido1, apellido2, ciclo, curso, eva1, eva2, eva3, notaFinal) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	public static void main(String[] args) {
		cargaDatos();
		System.out.printf("Numero de Alumnos iniciales: %d \n", listaAlumnos.size());

		try (Connection conexion = DriverManager.getConnection(URL)) {

			PreparedStatement ps = conexion.prepareStatement(sql);
			ResultSet resultado = ps.executeQuery();

			while (resultado.next()) {
				Alumno alumnoDB = new Alumno(resultado.getString("dni"), resultado.getString("nombre"),
						resultado.getString("apellido1"), resultado.getString("apellido2"),
						resultado.getString("ciclo"), resultado.getInt("curso"), resultado.getInt("eva1"),
						resultado.getInt("eva2"), resultado.getInt("eva3"), resultado.getInt("notaFinal"));
				listaAlumnos.add(alumnoDB);
			}

			System.out.printf("Numero de Alumnos guardados (Total): %d \n", listaAlumnos.size());

			for (Alumno alumno : listaAlumnos) {

				System.out.println(alumno);
			}

			System.out.println("Busqueda por apellido");
			System.out.println("Introduce parte del primer apellido:");
			String textoBuscar = teclado.nextLine();

			PreparedStatement psBusqueda = conexion.prepareStatement(sqlBusqueda);

			psBusqueda.setString(1, "%" + textoBuscar + "%");

			ResultSet rsBusqueda = psBusqueda.executeQuery();

			System.out.println("RESULTADOS:");
			while (rsBusqueda.next()) {

				System.out.printf("%s - %s %s, %s\n", rsBusqueda.getString("dni"), rsBusqueda.getString("apellido1"),
						rsBusqueda.getString("apellido2"), rsBusqueda.getString("nombre"));
			}

		} catch (SQLException e) {
			System.err.println("Error en la base de datos: " + e.getMessage());
		}
	}

	private static void cargaDatos() {
		Alumno alumno1 = new Alumno("45321678A", "Carlos", "Garcia", "Lopez", "DAW", 1, 7, 8, 6, 9);
		Alumno alumno2 = new Alumno("71234567B", "Elena", "Martinez", "Gomez", "DAW", 2, 9, 9, 10, 8);
		Alumno alumno3 = new Alumno("12345678C", "David", "Sanchez", "Perez", "DAW", 1, 5, 6, 5, 7);
		Alumno alumno4 = new Alumno("87654321D", "Lucia", "Rodriguez", "Diaz", "DAW", 2, 8, 9, 9, 9);
		Alumno alumno5 = new Alumno("34567890E", "Javier", "Fernandez", "Ruiz", "DAW", 1, 6, 7, 7, 6);
		Alumno alumno6 = new Alumno("56789012F", "Marta", "Gonzalez", "Sanz", "DAW", 2, 10, 9, 10, 10);
		Alumno alumno7 = new Alumno("23456789G", "Alejandro", "Munoz", "Jimenez", "DAW", 1, 4, 5, 6, 5);
		Alumno alumno8 = new Alumno("90123456H", "Sofia", "Alonso", "Gutierrez", "DAW", 2, 7, 8, 8, 7);
		Alumno alumno9 = new Alumno("67890123J", "Pablo", "Romero", "Navarro", "DAW", 1, 8, 7, 9, 8);
		Alumno alumno10 = new Alumno("45678901K", "Laura", "Torres", "Dominguez", "DAW", 2, 9, 10, 9, 9);
		Alumno alumno11 = new Alumno("12398745L", "Adrian", "Ramos", "Vazquez", "DAW", 1, 6, 5, 6, 7);
		Alumno alumno12 = new Alumno("78945612M", "Ana", "Gil", "Blanco", "DAW", 2, 8, 8, 7, 9);
		Alumno alumno13 = new Alumno("32165498N", "Sergio", "Serrano", "Morales", "DAW", 1, 5, 4, 6, 5);
		Alumno alumno14 = new Alumno("65498732P", "Paula", "Ortega", "Castro", "DAW", 2, 9, 9, 8, 9);
		Alumno alumno15 = new Alumno("98712345Q", "Marcos", "Rubio", "Ortiz", "DAW", 1, 7, 6, 7, 8);


		listaAlumnos.add(alumno1);
		listaAlumnos.add(alumno2);
		listaAlumnos.add(alumno3);
		listaAlumnos.add(alumno4);
		listaAlumnos.add(alumno5);
		listaAlumnos.add(alumno6);
		listaAlumnos.add(alumno7);
		listaAlumnos.add(alumno8);
		listaAlumnos.add(alumno9);
		listaAlumnos.add(alumno10);
		listaAlumnos.add(alumno11);
		listaAlumnos.add(alumno12);
		listaAlumnos.add(alumno13);
		listaAlumnos.add(alumno14);
		listaAlumnos.add(alumno15);

		for (Alumno alumno : listaAlumnos) {

			try (Connection conexion = DriverManager.getConnection(URL);
					PreparedStatement preparacion = conexion.prepareStatement(sqlInsert)) {

				preparacion.setString(1, alumno.getDni());
				preparacion.setString(2, alumno.getNombre());
				preparacion.setString(3, alumno.getApellido1());
				preparacion.setString(4, alumno.getApellido2());
				preparacion.setString(5, alumno.getCiclo());
				preparacion.setInt(6, alumno.getCurso());
				preparacion.setInt(7, alumno.getEva1());
				preparacion.setInt(8, alumno.getEva2());
				preparacion.setInt(9, alumno.getEva3());
				preparacion.setInt(10, alumno.getNotaFinal());

				preparacion.executeUpdate();

			} catch (SQLException e) {
				System.err.println("Error al cargar los datos de la base de datos: " + e.getMessage());
			}

		}

	}
}
