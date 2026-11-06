package mariaDB;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import alumnosCSV.ConexionDB;

public class AppLibre {
	
	private static final String ARCHIVO = "C:\\Users\\damdu201\\eclipse-workspace\\jdbc-sqlite\\src\\main\\java\\alumnosCSV\\alumnos.csv";
	private static final String SQL_INSERTAR = "INSERT INTO alumnos (nombre,apellidos,nota) VALUES (?,?,?)";
	private static final String SQL_BUSCAR_DUPLICADOS="SELECT COUNT(*) FROM alumnos WHERE nombre =? AND apellidos =?";

	public static void main(String[] args) {
		
		try (BufferedReader br = new BufferedReader (new FileReader(ARCHIVO));
				Connection conexion = ConexionDB.conectaDB();
				PreparedStatement psInsertar = conexion.prepareStatement(SQL_INSERTAR);
				PreparedStatement psBuscar = conexion.prepareStatement(SQL_BUSCAR_DUPLICADOS)
				) {
			
			String linea;
			int duplicado=0;
			int insertado=0;
			br.readLine();
			
			conexion.setAutoCommit(false); // desactivamos el guardado por defecto
			try {
				while( (linea = br.readLine()) != null) {
					
					String [] datos = linea.split(";");
					
									
					psBuscar.setString(1,datos[0]);	
					psBuscar.setString(2,datos [1]);
					ResultSet rsbucar= psBuscar.executeQuery();
					
						if ( rsbucar.next() && rsbucar.getInt(1) > 0    ) {
							duplicado++;
							continue;
						}else {
							
							psInsertar.setString(1,datos[0]);	
							psInsertar.setString(2,datos [1]);
							try {
								psInsertar.setDouble(3,Double.parseDouble(datos[2]));
								psInsertar.executeQuery();
								insertado++;
								continue;
								
							} catch (NumberFormatException e) {
								System.out.printf("ERROR : %s \n",e);
							}

						}
				
				} //FIN WHILE
	
				conexion.commit();
			} catch (SQLException e) {
				System.out.printf("ERROR : %s \n",e);
				conexion.rollback();
				
			}
			
			System.out.printf("Alumnos Insertados : %d \n",insertado);
			System.out.printf("Alumnos Duplicados : %d \n",duplicado);
			
		} catch (Exception e) {
			
			System.out.printf("ERROR : %s \n",e);
			
		}
		
		
	}


}
