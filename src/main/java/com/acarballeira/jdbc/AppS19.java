package com.acarballeira.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class AppS19 {
	
	private static String url = "jdbc:sqlite:java26.db";
	private static Scanner teclado = new Scanner(System.in);
	private static int  notaCorte ;
	public static void main(String[] args) {
		
		
		try (Connection conexion = DriverManager.getConnection(url);
				 Statement sentencia = conexion.createStatement()) {
			
			System.out.println("Introduce la nota Corte para mostrar alumnos :");
			notaCorte = Integer.parseInt(teclado.nextLine());
			String sqlBuscar = " SELECT * FROM alumno WHERE notaFinal >= ? ";
			
			PreparedStatement psBuscar = conexion.prepareStatement(sqlBuscar);
			psBuscar.setInt(1, notaCorte); 
			ResultSet resultadoBuscar	= psBuscar.executeQuery();
			
			
			
			System.out.printf("Nota de corte introducida: %d", notaCorte);
	if (resultadoBuscar.next()) {
		
		System.out.println(
				"\nNombre: " +resultadoBuscar.getString("nombre") +
				"\nNotaFinal: " +resultadoBuscar.getInt("notaFinal"));

		
		} else {
			System.out.printf("Nota de corte introducida: %d", notaCorte);
			System.out.println("No se encontró ningún alumno esa nota o superior.");

		}
		
		} catch (SQLException e) {
			System.err.println("Error en la base de datos: " + e.getMessage());
		}

	}

}
