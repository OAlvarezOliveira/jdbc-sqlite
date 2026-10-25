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

	public static void main(String[] args) {



		File archivoSalida = new File("src/main/java/gestorTareas/archivoSalida.txt");

		try {
			TareaDAO.crearTabla();
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		}
		escribeArchivo(archivoSalida);
		leeArchivo(archivoSalida);
	}

	private static void escribeArchivo(File archivoSalida) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivoSalida))) {

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

	private static void leeArchivo(File archivoSalida) {
		int cuentaLineaMala = 0;
		int cuentaLineaBuena = 0;
		int cuentaTareasCompletadas = 0;
		String linea;
		String[] campos;
		try (BufferedReader br = new BufferedReader(new FileReader(archivoSalida))) {

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
