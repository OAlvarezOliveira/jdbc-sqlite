package com.acarballeira.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class AppS21 {
	
    private static final String URL = "jdbc:sqlite:java26.db";

	public static void main(String[] args) {

		
		try (Connection conexion = DriverManager.getConnection(URL);){
			
			String sql = "SELECT * FROM alumno";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ResultSet resultado = ps.executeQuery();
			
			if(resultado.next()) {
				
				Alumno alumnoDB = new Alumno (
						resultado.getString("dni"),
						resultado.getString("nombre"),
						resultado.getString("apellido1"),
						resultado.getString("apellido2"),
						resultado.getString("ciclo"),
						resultado.getInt("curso"),
						resultado.getInt("eva1"),
						resultado.getInt("eva2"),
						resultado.getInt("eva3"),
						resultado.getInt("notaFinal")
						);
	            System.err.println("Alumno creado dede la BBDD" );
	            
	            System.err.println(alumnoDB);

			}
			
			
		} catch (SQLException e) {
			
            System.err.println("Error en la base de datos: " + e.getMessage());

		}
		

	}

}
