package com.acarballeira.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class RetoGestionAlumnos {

	// Statics
    private static final String URL = "jdbc:sqlite:java26.db";
    private static Scanner teclado = new Scanner(System.in);

	// QUERIES
    private static String sqlBusquedaApellido = "SELECT * FROM alumno WHERE LOWER(apellido1) LIKE LOWER(?) ORDER BY apellido1, nombre";
    private static String sqlAprobados = "SELECT * FROM alumno WHERE notaFinal >=5";
    private static String sqlSuspensos = "SELECT * FROM alumno WHERE notaFinal <5";

	public static void main(String[] args) {

		try (Connection conexion = DriverManager.getConnection(URL);) {


			// Imprime apellidos 
			System.out.println("Introduce el Apellido del alumno a buscar: "); 
			String apellido1 = teclado.nextLine(); 

			PreparedStatement psApellidos = conexion.prepareStatement(sqlBusquedaApellido); 
			psApellidos.setString(1, "%" + apellido1 + "%");
			ResultSet rsApellidos = psApellidos.executeQuery(); 

			System.out.println("Resultado de la Busqueda por apellido1"); 

			while (rsApellidos.next()) { 
			    System.out.printf("dni: %s \n nombre: %s \n apellido1: %s \n apellido2: %s \n ciclo: %s \n curso: %d \n eva1: %d \n eva2: %d \n eva3: %d \n notaFinal: %d\n\n", 
			        rsApellidos.getString("dni"), 
			        rsApellidos.getString("nombre"), 
			        rsApellidos.getString("apellido1"), 
			        rsApellidos.getString("apellido2"), 
			        rsApellidos.getString("ciclo"), 
			        rsApellidos.getInt("curso"), 
			        rsApellidos.getInt("eva1"), 
			        rsApellidos.getInt("eva2"), 
			        rsApellidos.getInt("eva3"), 
			        rsApellidos.getInt("notaFinal")
			    ); 
			} 

			System.out.println("============================="); 
			System.out.println("======= FIN CONSULTA ========"); 
			System.out.println("=============================");



			// Imprime aprobados
			PreparedStatement psAprobados = conexion.prepareStatement(sqlAprobados);
			ResultSet rsAprobados = psAprobados.executeQuery();
			
			System.out.println("==========================");
			System.out.println("======= APROBADOS ========");
			System.out.println("==========================");
			while (rsAprobados.next()) {
				System.out.printf("dni: %s \n nombre: %s \n apellido1: %s \n apellido2: %s  \n ciclo: %s "
			            + "\n curso: %d \n eva1: %d  \n eva2: %d \n eva3: %d  \n notaFinal: %d\n\n",
			            rsAprobados.getString("dni"), 
			            rsAprobados.getString("nombre"),
			            rsAprobados.getString("apellido1"), 
			            rsAprobados.getString("apellido2"),
			            rsAprobados.getString("ciclo"), 
			            rsAprobados.getInt("curso"), 
			            rsAprobados.getInt("eva1"),
			            rsAprobados.getInt("eva2"), 
			            rsAprobados.getInt("eva3"), 
			            rsAprobados.getInt("notaFinal"));
			}
			System.out.println("=============================");
			System.out.println("======= FIN CONSULTA ========");
			System.out.println("=============================");

			// Contar suspensos
			PreparedStatement psSuspensos = conexion.prepareStatement(sqlSuspensos);
			ResultSet rsSuspensos = psSuspensos.executeQuery();
			System.out.println("==========================");
			System.out.println("======= SUSPENSOS ========");
			System.out.println("==========================");
			while (rsSuspensos.next()) {
				System.out.printf("dni: %s \n nombre: %s \n apellido1: %s \n apellido2: %s  \n ciclo: %s "
			            + "\n curso: %d \n eva1: %d  \n eva2: %d \n eva3: %d  \n notaFinal: %d\n\n",
			            rsSuspensos.getString("dni"), 
			            rsSuspensos.getString("nombre"),
			            rsSuspensos.getString("apellido1"), 
			            rsSuspensos.getString("apellido2"),
			            rsSuspensos.getString("ciclo"), 
			            rsSuspensos.getInt("curso"), 
			            rsSuspensos.getInt("eva1"),
			            rsSuspensos.getInt("eva2"), 
			            rsSuspensos.getInt("eva3"), 
			            rsSuspensos.getInt("notaFinal"));
			}
			System.out.println("=============================");
			System.out.println("======= FIN CONSULTA ========");
			System.out.println("=============================");


		} catch (SQLException e) {

			System.err.println("Error en la base de datos: " + e.getMessage());

		}

	}

}
