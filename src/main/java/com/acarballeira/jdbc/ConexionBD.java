package com.acarballeira.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionBD {

	private static final String URL = "jdbc:sqlite:gestor.db";

	private static final String creaTablaSQL = """
			CREATE TABLE IF NOT EXISTS alumno (
			dni TEXT PRIMARY KEY,
			nombre TEXT NOT NULL,
			apellido1 TEXT NOT NULL,
			apellido2 TEXT NOT NULL,
			ciclo TEXT NOT NULL,
			curso INTEGER NOT NULL,
			eva1 INTEGER,
			eva2 INTEGER,
			eva3 INTEGER,
			notaFinal INTEGER
			)
			""";

	public static Connection getConexion() throws SQLException {

		return DriverManager.getConnection(URL);

	}

	public static void crearTabla() {

		try (Connection conexion = getConexion(); Statement sentencia = conexion.createStatement()) {
			sentencia.execute(creaTablaSQL);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {

		crearTabla();

	}

}
