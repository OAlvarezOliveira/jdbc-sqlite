package com.acarballeira.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class RetoS23 {
	// Statics
	private static final String URL = "jdbc:sqlite:java26.db";
	private static ArrayList<Alumno> listaAlumnos = new ArrayList<>();
	private static Scanner teclado = new Scanner(System.in);

	// QUERIES 
	private static String sql = "SELECT * FROM alumno";
	private static String sqlBusqueda = "SELECT * FROM alumno WHERE LOWER(nombre) LIKE LOWER(?) OR LOWER(apellido1) LIKE LOWER(?) ORDER BY apellido1, nombre";

	public static void main(String[] args) {
		System.out.printf("Numero de Alumnos iniciales: %d \n", listaAlumnos.size());

		try (Connection conexion = DriverManager.getConnection(URL)) {

			try (PreparedStatement ps = conexion.prepareStatement(sql);
					ResultSet resultado = ps.executeQuery()) {

				while (resultado.next()) {
					Alumno alumnoDB = new Alumno(resultado.getString("dni"), resultado.getString("nombre"),
							resultado.getString("apellido1"), resultado.getString("apellido2"),
							resultado.getString("ciclo"), resultado.getInt("curso"), resultado.getInt("eva1"),
							resultado.getInt("eva2"), resultado.getInt("eva3"), resultado.getInt("notaFinal"));
					listaAlumnos.add(alumnoDB);
				}
			}

			System.out.printf("Numero de Alumnos guardados (Total): %d \n", listaAlumnos.size());

			
//			for (Alumno alumno : listaAlumnos) {
//
//				System.out.println(alumno);
//			}

			ArrayList<Alumno> coincidencias = buscaCoincidencia(conexion);

			imprimeResultados(coincidencias);

		} catch (SQLException e) {
			System.err.println("Error en la base de datos: " + e.getMessage());
		}
	}

	private static ArrayList<Alumno> buscaCoincidencia(Connection conexion) throws SQLException {
		System.out.println("Busqueda por apellido");
		System.out.println("Introduce parte del nombre o primer apellido:");
		String textoBuscar = teclado.nextLine().toLowerCase();

		ArrayList<Alumno> coincidencias = new ArrayList<>();

		try (PreparedStatement psBusqueda = conexion.prepareStatement(sqlBusqueda)) {

			psBusqueda.setString(1, "%" + textoBuscar + "%");
			psBusqueda.setString(2, "%" + textoBuscar + "%");

			try (ResultSet rsBusqueda = psBusqueda.executeQuery()) {
				while (rsBusqueda.next()) {
					coincidencias.add(new Alumno(rsBusqueda.getString("dni"), rsBusqueda.getString("nombre"),
							rsBusqueda.getString("apellido1"), rsBusqueda.getString("apellido2"),
							rsBusqueda.getString("ciclo"), rsBusqueda.getInt("curso"), rsBusqueda.getInt("eva1"),
							rsBusqueda.getInt("eva2"), rsBusqueda.getInt("eva3"), rsBusqueda.getInt("notaFinal")));
				}
			}
		}
		return coincidencias;
	}

	private static void imprimeResultados(ArrayList<Alumno> coincidencias) {
		System.out.println("RESULTADOS:");
		for (Alumno alumno : coincidencias) {

			System.out.printf("%s - %s %s, %s\n", alumno.getDni(), alumno.getApellido1(),
					alumno.getApellido2(), alumno.getNombre());
		}
	}
}