package miniRetos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

public class Reto08 {
	
	private static final String RUTA_ALUMNOS="alumnos.csv";
	
	private static final String RUTA_ALUMNOS_APROBADOS="aprobados.csv";


	public static void main(String[] args) {
		
		
		try (BufferedReader br = new BufferedReader(new FileReader(RUTA_ALUMNOS));
				BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_ALUMNOS_APROBADOS))
				
				) {
			
			int numAprobados=0;
			double suma=0;
			int numAlumnos = 0;
			
			String linea;
			while((linea = br.readLine())!=null ) {
				
				String [] datos = linea.split(";");
				
				if(datos.length == 2) {
					
					String nombre = datos[0];
					
					try {
						double nota = Double.parseDouble(datos[1]);
						numAlumnos++;
						suma+=nota;
						if(nota >= 5 ) {
							numAprobados++;
							bw.write(nombre + ";" + datos[1]);
							bw.newLine();
						}
						
						System.out.println(nombre + ";" + datos[1]);
						
					} catch (NumberFormatException e) {
						System.out.println("Error:");
						e.printStackTrace();
					}
				
					
					
				} else {
					
					System.out.println("Linea mal formada");
				}
				

				
			}
			
			if(numAlumnos>0) {
				
				System.out.println("Nota media del grupo: " + String.format(Locale.US, "%.2f", suma / numAlumnos));

			} else {
				
				System.out.println("No hay alumnos sobre los que calcular la nota media");

			}
			 
			
			System.out.println("Numero de aprobados: " +numAprobados);

			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
