package com.acarballeira.eliminaAlumno;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class App {

	private static String url = "jdbc:sqlite:java26.db";
	private static Scanner teclado = new Scanner(System.in);

	public static void main(String[] args) {

		String dni;

		String sql = "DELETE FROM alumno WHERE dni = ?";

		try (Connection conexion = DriverManager.getConnection(url); Statement sentencia = conexion.createStatement()) {

			System.out.println("Introduce el DNI para eliminar el alumno asociado:");
			dni = teclado.nextLine();
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setString(1, dni);

			if (ps.executeUpdate() > 0) {

				System.err.println("Alumno Eliminado");
			} else {

				System.err.println("Alumno no Eliminado");

			}

		} catch (SQLException e) {
			System.err.println("Error en la base de datos: " + e.getMessage());
		}
	}

}
