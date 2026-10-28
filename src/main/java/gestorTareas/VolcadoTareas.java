package gestorTareas;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class VolcadoTareas {
	
	private static final File ARCHIVO_SALIDA = new File("src/main/java/gestorTareas/archivoSalida.txt");

	public static void main(String[] args) {


		try {
			TareaDAO.crearTabla();
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		}
		escribeArchivo();
		leeArchivo();
	}

	private static void escribeArchivo() {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_SALIDA))) {

			ArrayList<Tarea> lista = TareaDAO.listarTodos();

			for (Tarea t : lista) {
				bw.write(String.format("%d;%s;%b", t.getId(), t.getTitulo(), t.isHecha()));
				bw.newLine();
			}

		} catch (SQLException e) {
			System.out.println("Error:" + e);
		} catch (IOException e) {
			System.out.println("Error:" + e);
		}
	}

	private static void leeArchivo() {
		int cuentaLineaMala = 0;
		int cuentaLineaBuena = 0;
		int cuentaTareasCompletadas = 0;
		String linea;
		String[] campos;
		try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_SALIDA))) {

			while ((linea = br.readLine()) != null) {

				campos = linea.split(";");

				if (campos.length == 3) {

					try {

						Integer.parseInt(campos[0]);

						String valorBooleano = campos[2].trim().toLowerCase();
						if (valorBooleano.equals("true") || valorBooleano.equals("false")) {

							cuentaLineaBuena++;

							if (Boolean.parseBoolean(campos[2])) {
								cuentaTareasCompletadas++;
							}
						} else {
							cuentaLineaMala++;
						}

					} catch (NumberFormatException e) {
						System.out.println("Error en formato numérico del ID: " + e);
						cuentaLineaMala++;
					}

				} else {
					System.out.println("Error en la longitud de la linea");
					cuentaLineaMala++;
				}
			}

			System.out.println("Leídas bien:" + cuentaLineaBuena);
			System.out.println("Líneas malas:" + cuentaLineaMala);
			System.out.println("Hechas:" + cuentaTareasCompletadas);

		} catch (FileNotFoundException e) {
			System.out.println("Error:" + e);
		} catch (IOException e) {
			System.out.println("Error:" + e);
		}
	}
}
