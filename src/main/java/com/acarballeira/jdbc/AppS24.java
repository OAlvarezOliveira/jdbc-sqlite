package com.acarballeira.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppS24 {

	// Statics
	private static final String URL = "jdbc:sqlite:java26.db";

	// QUERIES
	private static String sqlTotal = "SELECT COUNT(*) AS total FROM alumno";
	private static String sqlMedia = "SELECT AVG(notaFinal) AS media FROM alumno";
	private static String sqlMax = "SELECT MAX(notaFinal) AS maxima FROM alumno";
	private static String sqlMin = "SELECT MIN(notaFinal) AS minima FROM alumno";
	private static String sqlAprobados = "SELECT COUNT(*) AS aprobados FROM alumno WHERE notaFinal >=5";
	private static String sqlSuspensos = "SELECT COUNT(*) AS suspensos FROM alumno WHERE notaFinal <5";

	public static void main(String[] args) {

		try (Connection conexion = DriverManager.getConnection(URL);) {

			// TOTAL DE ALUMNOS
			System.out.println("\nNÚMERO TOTAL DE ALUMNOS");
			PreparedStatement psTotal = conexion.prepareStatement(sqlTotal);
			ResultSet rsTotal = psTotal.executeQuery();
			if (rsTotal.next()) {
				int total = rsTotal.getInt("total");
				System.out.println("Total de alumnos: " + total);
			}
			// TOTAL DE ALUMNOS

			System.out.println("\nNOTA MEDIA DE LOS ALUMNOS");
			PreparedStatement psMedia = conexion.prepareStatement(sqlMedia);
			ResultSet rsMedia = psMedia.executeQuery();
			if (rsMedia.next()) {
				double media = rsMedia.getDouble("media");
				System.out.println("Nota media: " + media);
			}

			// Nota máxima - MAX()
			System.out.println("\nNOTA MÁXIMA");
			PreparedStatement psMax = conexion.prepareStatement(sqlMax);
			ResultSet rsMax = psMax.executeQuery();
			if (rsMax.next()) {
				int maxima = rsMax.getInt("maxima");
				System.out.println("Nota máxima: " + maxima);
			}

			// Nota mínima - MIN()
			System.out.println("\nNOTA MÍNIMA");
			PreparedStatement psMin = conexion.prepareStatement(sqlMin);
			ResultSet rsMin = psMin.executeQuery();
			if (rsMin.next()) {
				int minima = rsMin.getInt("minima");
				System.out.println("Nota mínima: " + minima);
			}
			// Contar aprobados
			PreparedStatement psAprobados = conexion.prepareStatement(sqlAprobados);
			ResultSet rsAprobados = psAprobados.executeQuery();
			if (rsAprobados.next()) {
				int aprobados = rsAprobados.getInt("aprobados");
				System.out.println("Aprobados: " + aprobados);
			}
			// Contar suspensos
			PreparedStatement psSuspensos = conexion.prepareStatement(sqlSuspensos);
			ResultSet rsSuspensos = psSuspensos.executeQuery();
			if (rsSuspensos.next()) {
				int suspensos = rsSuspensos.getInt("suspensos");
				System.out.println("Suspensos: " + suspensos);
			}

		} catch (SQLException e) {

			System.err.println("Error en la base de datos: " + e.getMessage());

		}

	}

}
