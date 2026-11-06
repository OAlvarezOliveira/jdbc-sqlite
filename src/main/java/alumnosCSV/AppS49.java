package alumnosCSV;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.acarballeira.jdbc.Alumno;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class AppS49 {
	public static void main(String[] args) {
		
		Gson gson = new Gson();
		
		try (FileReader lector = new FileReader("alumnos.json")) {
			
			ArrayList<Alumno> alumnos = gson.fromJson(lector, new TypeToken<ArrayList<Alumno>>() {}.getType());
			
			System.out.println("Fichero JSON abierto correctamente.");
			System.out.println("Número de alumnos recuperados: " + alumnos.size());
			System.out.println("\nLISTADO DE ALUMNOS:");
			
			for (Alumno a : alumnos) {
				
				System.out.println(
						"DNI: " + a.getDni() + " | Nombre: " + a.getNombre() + " | Nota final: " + a.getNotaFinal());
				
			}
			System.out.println("\nALUMNOS APROBADOS:");
			
			for (Alumno a : alumnos) {
				if (a.getNotaFinal() >= 5) {
					System.out.println(a.getNombre() + " - Nota final: " + a.getNotaFinal());
				}
			}
		} catch (IOException e) {
			System.out.println("Error al leer el fichero: " + e.getMessage());
		}
	}
}
