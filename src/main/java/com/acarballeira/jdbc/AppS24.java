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
	private static String sqlResumen = """
			SELECT COUNT(*) AS total,
			AVG(notaFinal) AS media,
			MAX(notaFinal) AS maxima,
			MIN(notaFinal) AS minima,
			COUNT(CASE WHEN notaFinal >= 5 THEN 1 END) AS aprobados,
			COUNT(CASE WHEN notaFinal < 5 THEN 1 END) AS suspensos
			FROM alumno
			""";

	public static void main(String[] args) {

		try (Connection conexion = DriverManager.getConnection(URL);
				PreparedStatement psResumen = conexion.prepareStatement(sqlResumen);
				ResultSet rsResumen = psResumen.executeQuery()) {

			// Una sola consulta devuelve siempre una fila con todos los agregados
			if (rsResumen.next()) {
				int total = rsResumen.getInt("total");
				double media = rsResumen.getDouble("media");
				int maxima = rsResumen.getInt("maxima");
				int minima = rsResumen.getInt("minima");
				int aprobados = rsResumen.getInt("aprobados");
				int suspensos = rsResumen.getInt("suspensos");

				// TOTAL DE ALUMNOS
				System.out.println("\nNÚMERO TOTAL DE ALUMNOS");
				System.out.println("Total de alumnos: " + total);

				System.out.println("\nNOTA MEDIA DE LOS ALUMNOS");
				System.out.println("Nota media: " + media);

				// Nota máxima - MAX()
				System.out.println("\nNOTA MÁXIMA");
				System.out.println("Nota máxima: " + maxima);

				// Nota mínima - MIN()
				System.out.println("\nNOTA MÍNIMA");
				System.out.println("Nota mínima: " + minima);

				// Contar aprobados
				System.out.println("Aprobados: " + aprobados);

				// Contar suspensos
				System.out.println("Suspensos: " + suspensos);
			}

		} catch (SQLException e) {

			System.err.println("Error en la base de datos: " + e.getMessage());

		}

	}

}
