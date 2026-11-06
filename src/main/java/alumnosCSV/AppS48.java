package alumnosCSV;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.acarballeira.jdbc.Alumno;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AppS48 {
	public static void main(String[] args) {
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Alumno alumno = new Alumno("12345678A", "Ana", "García", "López", "DAM", 2, 1, 7, 8, 8);
		ArrayList<Alumno> alumnos = new ArrayList<>();
		
		alumnos.add(alumno);
		alumnos.add(new Alumno("87654321B", "Luis", "Pérez", "Sánchez", "DAM", 2, 1, 6, 7, 7));
		
		// Solución del reto: tercer alumno
		alumnos.add(new Alumno("45678912C", "Marta", "Rodríguez", "Fernández", "DAM", 2, 1, 9, 8, 9));
		
		// Convertimos toda la lista a texto JSON
		String json = gson.toJson(alumnos);
		
		System.out.println(json);
		// Guardamos el texto en alumnos.json
		
		try (FileWriter escritor = new FileWriter("alumnos.json")) {
			escritor.write(json);
			System.out.println("Archivo JSON guardado correctamente.");
		} catch (IOException e) {
			System.out.println("Error al guardar el archivo: " + e.getMessage());
		}
	}
}
