package miniRetos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Reto09 {

	public static void main(String[] args) {
		
		Map<String, Object> ana = new LinkedHashMap<>();
		ana.put("nombre", "Ana");
		ana.put("nota", 7.5);
		
		Map<String, Object> luis = new LinkedHashMap<>();
		luis.put("nombre", "Luis");
		luis.put("nota", 4.0);
		
		ArrayList<Map<String, Object>>alumnos = new ArrayList<>();
		alumnos.add(ana);
		alumnos.add(luis);
		
		Gson gson = new Gson();
		String json = gson.toJson(alumnos);
		
		System.out.println(json);
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("alumnos.json"))) {
		    bw.write(json);
			System.out.println("Archivo alumnos.json generado correctamente.");
		} catch (IOException e) {
			System.out.println("Error:");
			e.printStackTrace();
		}
		
		try ( BufferedReader br = new BufferedReader(new FileReader("alumnos.json"))){
			
			
			Type tipo = new TypeToken<List<Map<String, Object>>>() {}.getType();		
			List<Map<String, Object>> listaAlumnos = 	gson.fromJson(br, tipo);
			
			for (Map<String, Object> map : listaAlumnos) {
				
			System.out.println(map.get("nombre"));
				
			}
			
		} catch (IOException e) {
			System.out.println("Error:");
			e.printStackTrace();
		}


	}

}
