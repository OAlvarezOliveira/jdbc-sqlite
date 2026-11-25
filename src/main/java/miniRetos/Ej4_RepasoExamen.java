package miniRetos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Ej4_RepasoExamen {

	private static final String RUTA_CSV = "/home/oscardev/Estudios/2DAM/AD/REPASO_FINAL_25NOV/ejercicio_ficheros/notas.csv";
	private static final String RUTA_JSON_ENTRADA = "/home/oscardev/Estudios/2DAM/AD/REPASO_FINAL_25NOV/ejercicio_ficheros/entrada.json";
	private static final String RUTA_JSON_SALIDA = "/home/oscardev/Estudios/2DAM/AD/REPASO_FINAL_25NOV/ejercicio_ficheros/alumnos_salida.json";

	public static void main(String[] args) {
		File csv = null;
		File jsonSalida = null;
		File jsonEntrada = null;
		String nombre = null;
		double nota = 0;
		ArrayList<AlumnoEj4> lista = new ArrayList<>();

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Type tipo = new TypeToken<List<AlumnoEj4>>() {
		}.getType();

		try {
			csv = new File(RUTA_CSV);
			jsonEntrada = new File(RUTA_JSON_ENTRADA);
			jsonSalida = new File(RUTA_JSON_SALIDA);

			if (!csv.isFile() || !jsonEntrada.isFile()) {
				System.out.println("Faltan ficheros de entrada");
				return;
			}

			try (BufferedReader br = new BufferedReader(new FileReader(csv))

			) {

				String linea;
				br.readLine(); // saltando cabecera

				while ((linea = br.readLine()) != null) {

					String[] datos = linea.split(";");

					if (datos.length == 2) {

						nombre = datos[0].trim();

						try {
							nota = Double.parseDouble(datos[1].trim());
						} catch (NumberFormatException e) {
							System.out.println("La nota no es un valor numerico");
							System.out.println(linea);

							continue;
						}

					} else {

						System.out.println("La linea no contine los parametros adecuados");
						continue;

					}

					AlumnoEj4 nuevo = new AlumnoEj4(nombre, nota);
					lista.add(nuevo);

				}
				try (FileWriter fw = new FileWriter(jsonSalida)) {
					gson.toJson(lista, fw);
				} catch (FileNotFoundException e) {
					System.out.println("Error:");
					throw e;
				} catch (Exception e) {
					System.out.println("Error:");
					e.printStackTrace();
				}

				try (FileReader fr = new FileReader(jsonEntrada)) {

					ArrayList<AlumnoEj4> lista2 = gson.fromJson(fr, tipo);

					if (lista2 == null || lista2.isEmpty()) {
						System.out.println("No hay alumnos en el JSON");
					} else {
						for (AlumnoEj4 a : lista2) {
							System.out.println(a);
						}
					}
				}

			}

		} catch (FileNotFoundException e) {
			System.out.println("Error los archivos no están disponibles");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error los archivos no están disponibles");
			e.printStackTrace();
		}

	}

}
