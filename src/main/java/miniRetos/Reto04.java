package miniRetos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Reto04 {

	private static final String TXT = "notas.txt";
	private static final String TXT_RESUMEN = "resumen.txt";

	public static void main(String[] args) {

		try (BufferedReader br = new BufferedReader(new FileReader(TXT));
				BufferedWriter bw = new BufferedWriter(new FileWriter(TXT_RESUMEN))) {

			String line;
			int numNotas = 0;
			double nota = 0;
			double suma = 0;
			double notaMedia = 0;
			int aprobados = 0;
			while ((line = br.readLine()) != null) {

				try {
					nota = Double.parseDouble(line.trim());
					numNotas++;
					suma += nota;
					if (nota >= 5) {
						aprobados++;
					}

				} catch (NumberFormatException e) {
					System.out.println("Linea malformada");
					e.printStackTrace();
				}
			}
			
			notaMedia = suma / numNotas;
			
			try {
				bw.write(String.format("Total de notas: %d", numNotas));
				bw.newLine();
				bw.write(String.format("Alumnos aprobados: %d", aprobados));
				bw.newLine();
				bw.write(String.format("Nota media: %.2f", notaMedia));

				System.out.println("Escritura terminada con éxito");

			} catch (Exception e) {
				System.out.println("Error al escribir en archivo");
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
