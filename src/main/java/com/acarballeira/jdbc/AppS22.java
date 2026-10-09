package com.acarballeira.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AppS22 {
	
    private static final String URL = "jdbc:sqlite:java26.db";
    private static ArrayList <Alumno> listaAlumnos = new ArrayList<>();

	public static void main(String[] args) {
		
		System.out.printf("Numero de Alumnos: %d \n",listaAlumnos.size());
		
		cargaDatos();
		
		try (Connection conexion = DriverManager.getConnection(URL);){
			
			String sql = "SELECT * FROM alumno";
			PreparedStatement ps = conexion.prepareStatement(sql);
			ResultSet resultado = ps.executeQuery();
			
			while(resultado.next()) {
				
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
	            
	            listaAlumnos.add(alumnoDB);

			}
			
			System.out.printf("Numero de Alumnos guardados: %d \n",listaAlumnos.size());
			
			
			
			System.out.printf("Numero de Alumnos guardados en el ArrayList: %d \n",listaAlumnos.size());

			for (Alumno alumno : listaAlumnos) {
			
				System.out.println(alumno);
			}
			
			System.out.printf("Alumnos Suspensos\n");
			for (Alumno alumno : listaAlumnos) {
				
				if(alumno.getNotaFinal()<5) {
					
					System.out.println(alumno);

				}
			}
			
			
			
			
			
		} catch (SQLException e) {
			
            System.err.println("Error en la base de datos: " + e.getMessage());

		}


	}

	private static void cargaDatos() {
		Alumno alumno1 = new Alumno("12345678A", "Alejandro", "García", "Martínez", "DAM", 1, 6, 7, 8, 7);
		Alumno alumno2 = new Alumno("87654321B", "Sofía", "Rodríguez", "López", "DAW", 2, 8, 9, 9, 9);
		Alumno alumno3 = new Alumno("56781234C", "Mateo", "Fernández", "Gómez", "ASIR", 1, 5, 6, 5, 5);
		Alumno alumno4 = new Alumno("43218765D", "Lucía", "Sánchez", "Pérez", "DAM", 2, 9, 10, 9, 9);
		Alumno alumno5 = new Alumno("98765432E", "Martín", "Díaz", "Ruiz", "DAW", 1, 7, 5, 6, 6);

			listaAlumnos.add(alumno1);
			listaAlumnos.add(alumno2);
			listaAlumnos.add(alumno3);
			listaAlumnos.add(alumno4);
			listaAlumnos.add(alumno5);
	}

}
