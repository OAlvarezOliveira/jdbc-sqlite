package miniRetos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

public class Ej2_RepasoExamen {

	private static String RUTA = "/home/oscardev/Estudios/2DAM/AD/REPASO_FINAL_25NOV/ejercicio_ficheros/alumnos.txt";
	private static String RUTA_SALIDA = "/home/oscardev/Estudios/2DAM/AD/REPASO_FINAL_25NOV/ejercicio_ficheros/resumen.txt";


	public static void main(String[] args) {

		resumirNotas();

	}


	private static void resumirNotas() {
		File alumnos = new File(RUTA);
		File resumen = new File(RUTA_SALIDA);

		if (alumnos.exists() && alumnos.isFile()) {
			System.out.println("Archivo existe");

		} else {
			System.out.println("EL archivo no existe");
			return;

		}
		
		try(BufferedReader br = new BufferedReader(new FileReader(alumnos));
				BufferedWriter bw = new BufferedWriter(new FileWriter(resumen))
				){
			
			String linea;
			int total=0;
			int aprobados=0;
			int suspensos=0;


			double nota;
			double suma=0;
			double media=0;
			
			while( ( linea = br.readLine()) != null  ) {
				
				String [] datos = linea.split(";");
				
				if (datos.length == 2) {
					
					String nombre = datos[0];

					try {
						nota = Double.parseDouble(datos[1]);
						
						if(nota >=5) {aprobados++;}else {suspensos++;}
						
					} catch (NumberFormatException e) {
						System.out.println("La nota no tiene un formato válido y no se contabilizará para la media");
						System.out.println(linea);
						e.printStackTrace();
						continue;
					}
					suma += nota;
					total++; 
					
				} else {
					
					System.out.println("Linea con parametros incompletos o mal formada");
					System.out.println(linea);
					continue;
				}
				
			}
			
			//Calculos
			

			if (total>0) {media = suma/total;
					} else {
						System.out.println("No hay registros válidos para calcular");

					}
			
			//salida
			System.out.println("Total: "+ total);
			System.out.println("Aprobados:" +aprobados);
			System.out.println("Suspensos:" +suspensos);
			System.out.println(String.format(Locale.US, "Media: %.2f",media));
			
			bw.write("Total: "+ total);
			bw.newLine();
			bw.write("Aprobados: " +aprobados);
			bw.newLine();
			bw.write("Suspensos: " +suspensos);
			bw.newLine();
			bw.write(String.format(Locale.US, "Media: %.2f",media));
			
		} catch (FileNotFoundException e) {
			System.out.println("EL archivo no existe");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("EL archivo no alcanzable ");
			e.printStackTrace();
		}
	}

}
