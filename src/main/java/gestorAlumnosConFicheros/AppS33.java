package gestorAlumnosConFicheros;

import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class AppS33 {

	public static void main(String[] args) {

		String ruta = "src/main/java/gestorAlumnosConFicheros/alumnos.txt";
		String rutaResumen = "src/main/java/gestorAlumnosConFicheros/resumen.txt";
		String rutaSuspenso = "src/main/java/gestorAlumnosConFicheros/suspensos.txt";
		File archivo = new File(ruta);
		File archivoResumen = new File(rutaResumen);
		File archivoSuspensos = new File(rutaSuspenso);

		int cuentaAlumnos = 0;
		int aprobados = 0;
		int suspensos = 0;
		double acumulaNotas = 0;
		double notaMedia = 0;
		String linea;

		if (archivo.exists() && archivo.isFile()) {
			System.out.println("El archivo existe");
		} else {
			System.out.println("El archivo NO existe");
		}

		try (BufferedReader br = new BufferedReader(new FileReader(archivo));
				BufferedWriter bw = new BufferedWriter(new FileWriter(archivoSuspensos));
				BufferedWriter bw2 = new BufferedWriter(new FileWriter(archivo, true))) {

			// añado nuevo alumno
			bw2.write("Oscar;10.0");// Esta linea duplicará el alumno que ya está añadido
			bw2.newLine();
			while ((linea = br.readLine()) != null) {
				cuentaAlumnos++;
//					System.out.println(linea);

				String[] datosAlumno = linea.split(";");

				double nota = Double.parseDouble(datosAlumno[1].trim());

				if (nota < 5) {

					suspensos++;

					bw.write(linea);
					bw.newLine();

				} else {

					aprobados++;
				}

				acumulaNotas += nota;
			}
			notaMedia = acumulaNotas / cuentaAlumnos;
			System.out.println("RESUMEN DEL GRUPO");
			System.out.println("Total de alumnos: " + cuentaAlumnos);
			System.out.println("Aprobados: " + aprobados);
			System.out.println("Suspensos: " + suspensos);
			System.out.printf("Nota media: %.2f\n", notaMedia);

		} catch (IOException e) {

			System.out.println("Error: " + e.getMessage());

		}

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivoResumen))) {

			bw.write("RESUMEN DEL GRUPO");
			bw.newLine();
			bw.write("Total de alumnos: " + cuentaAlumnos);
			bw.newLine();
			bw.write("Aprobados: " + aprobados);
			bw.newLine();
			bw.write("Suspensos: " + suspensos);
			bw.newLine();
			bw.write("Nota media: " + notaMedia);

		} catch (IOException e) {

			System.out.println("Error: " + e.getMessage());

		}

	}

}
