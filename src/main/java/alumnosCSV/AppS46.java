package alumnosCSV;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;




public class AppS46 {
	
	private static final String ARCHIVO = "C:\\Users\\damdu201\\eclipse-workspace\\jdbc-sqlite\\src\\main\\java\\alumnosCSV\\alumnos.csv";
	
	private static final String SQL_INSERTAR_ALUMNOS="INSERT INTO alumnos (nombre,apellidos,nota) VALUES (?, ?, ?) ";
	
	public static void main(String[] args) {
		

		
		try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
			
			String linea;
			double nota;
			
			int contador= 0;	
			int rechazada= 0;
			br.readLine();
			while((linea = br.readLine()) != null) {
				

			String [] datos = linea.split(";");
			
			if (datos.length != 3) {
				
				System.out.println("Linea incorrecta:" + linea);
				rechazada++;
				continue;
			}
			
			if(datos[0].isBlank() || datos[1].isBlank() || datos[2].isBlank() ) {
				
				System.out.println("Faltan datos:" + linea);
				rechazada++;
				continue;
			}
				
			String nombre = datos[0];
			String apellidos = datos[1];
			try {
				 nota = Double.parseDouble(datos[2]);
				
			} catch (NumberFormatException e) {
				System.out.println("Error:" + e);
				rechazada++;
				continue;
			}
			
//			System.out.println("Nombre:" + nombre);
//			System.out.println("Apellidos:" + apellidos);
//			System.out.println("Nota:" + nota);
//			System.out.println("=======================");
			
			try (Connection conexion = ConexionDB.conectaDB();
					PreparedStatement ps = conexion.prepareStatement(SQL_INSERTAR_ALUMNOS)) {
				
				ps.setString(1, nombre);
				ps.setString(2, apellidos);
				ps.setDouble(3, nota);
				ps.executeUpdate();
				
				System.out.printf("Alumno Insertado: %s %s \n", nombre, apellidos);
				System.out.println("=======================");
				contador++;
				
			} catch (SQLException e) {
				System.out.println("Error:" + e);
			}
			
			} // FIN WHILE
			System.out.printf("Total Alumnos importados: %d \n",contador);
			System.out.printf("Total Alumnos rechazados: %d \n",rechazada);
			System.out.printf("Total procesados: %d \n",(contador+ rechazada));
			
		} catch (IOException e) {
			

		}
	}

}
