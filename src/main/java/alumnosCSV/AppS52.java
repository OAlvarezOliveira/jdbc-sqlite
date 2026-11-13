package alumnosCSV;

import java.lang.reflect.*;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class AppS52 {
	
	private static Scanner sc = new Scanner(System.in);
	private static String SQL_BUSCAR_TODOS = "SELECT * FROM alumnos";
	private static String SQL_BUSCAR_POR_ID = "SELECT * FROM alumnos WHERE id = ?";
	private static String SQL_BUSCAR_PARA_COPIA = "SELECT id,nombre,apellidos,nota FROM alumnos";
	private static String SQL_INSERTAR_ALUMNO = "INSERT INTO alumnos (nombre,apellidos,nota) VALUES (?,?,?)";
	private static String SQL_MODIFICAR_POR_ID = "UPDATE alumnos SET nombre =? , apellidos=?,nota=? WHERE id = ?";
	private static String SQL_ELIMINA_POR_ID = "DELETE FROM alumnos WHERE id = ?";
	
	private static String RUTA_CSV= "src//main//java//alumnosCSV//alumnos.csv";
	private static String RUTA_JSON= "src/main/java/alumnosCSV/alumnos.json";
	private static String RUTA_BIN= "src//main//java//alumnosCSV//alumnos.bin";


	public static void main(String[] args) {
		int opcion;

		do {

			System.out.println("===== GESTION DE FICHEROS =====");
			System.out.println("1. Exportar Alumnos a CSV");
			System.out.println("2. Exportar Alumnos a JSON");
			System.out.println("3. Impotar  Alumnos desde JSON");
			System.out.println("4. Crear copia de seguridad binaria");
			System.out.println("0. Salir");
			System.out.println("Elige una opcion");

			try {
				opcion = Integer.parseInt(sc.nextLine());
				switch (opcion) {
				case 1 -> {
					System.out.println("Exportar a CSV");
					exportarCSV();
				}
				case 2 -> {
					System.out.println("Exportar a JSON");
					exportarJSON();
				}
				case 3 -> {
					System.out.println("Insertar Alumnos desde JSON");
					importarJSON();
				}
				case 4 -> {
					System.out.println("Copia Binaria");
					copiaBinaria();
				}
				case 0 -> {
					System.out.println("Saliendo");
					break;
				}
				default -> System.out.println("Opcion incorrecta");
				}

			} catch (NumberFormatException e) {
				System.out.println("Introduce un numero valido");
				opcion = -1;
			}

		} while (opcion != 0);

		sc.close();

	}


	private static void copiaBinaria() {
		
		ArrayList<LinkedHashMap<String , Object>> alumnos = new ArrayList<>();
		
		try(Connection conexion = ConexionDB.conectaDB();
				PreparedStatement ps = conexion.prepareStatement(SQL_BUSCAR_PARA_COPIA);
					ResultSet rs = ps.executeQuery()
		){
			
			while(rs.next()) {
				
				LinkedHashMap<String, Object> alumno = new LinkedHashMap<>();
				
				alumno.put("id", rs.getInt("id"));
				alumno.put("nombre", rs.getString("nombre"));
				alumno.put("apellidos", rs.getString("apellidos"));
				alumno.put("nota", rs.getDouble("nota"));
				
				alumnos.add(alumno);
	
			}
			
			try(ObjectOutputStream salida = new ObjectOutputStream(
					new ObjectOutputStream(new FileOutputStream("alumnos.dat"))
					)) {
				
				salida.writeObject(alumnos);
			}
			
			System.out.println("Copia binaria creada correctamente");
			System.out.println("Alumnos guardados:" + alumnos.size());

				
			} catch ( IOException e) {
				System.out.printf("Error: %s\n",e);


		} catch (SQLException e) {
			System.out.printf("Error: %s\n",e);

		}
		
		try(ObjectInputStream entrada = new ObjectInputStream(
				
				new FileInputStream("alumnos.dat"))){
			
			ArrayList<?> recupedaros;
			try {
				recupedaros = (ArrayList<?>) entrada.readObject();
				
				System.out.println(
						"Alumnos recuperados" + recupedaros.size());
			} catch (ClassNotFoundException e) {
				System.out.printf("Error: %s\n",e);

			}
			

			
			
		} catch (FileNotFoundException e) {
			System.out.printf("Error: %s\n",e);

		} catch (IOException e) {
			System.out.printf("Error: %s\n",e);

		}
		
	}
	
	private static void exportarJSON() {
		
	}


	private static void importarJSON() {
		
		Gson gson = new Gson();
		
		Type tipoLista = new TypeToken<
				ArrayList<Map<String, Object>>>() {}.getType();
		
			try(FileReader fr = new FileReader(RUTA_JSON)){
				
				ArrayList<Map<String,Object>> alumnos = gson.fromJson(fr, tipoLista);
				
				if(alumnos == null) {
					
					System.out.println("El archivo JSON está vacío.");
					return;
				}
				
				for (Map<String, Object> alumno : alumnos) {
					
					System.out.printf("ID:%s - nombre: %s %s - Nota:%s \n",
							alumno.get("id"),alumno.get("nombre"),alumno.get("apellidos"),alumno.get("nota"));
				}
				
				System.out.println("Total de alumnos:"+ alumnos.size());
				
			} catch(IOException e) {
			
				System.out.println("Error al leer JSON:"+ e);

		}
		
	}



	private static void exportarCSV() {
		
		try (Connection conexion = ConexionDB.conectaDB();
				PreparedStatement ps = conexion.prepareStatement(SQL_BUSCAR_TODOS);
				ResultSet rs = ps.executeQuery();
				BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_CSV))) {
			
			bw.write("id;nombre;apellidos;nota");
			bw.newLine();
			
			while(rs.next()) {
				
				bw.write(String.format("%d;%s;%s;%.2f", rs.getInt("id"), rs.getString("nombre"),
						rs.getString("apellidos"), rs.getDouble("nota")));
				bw.newLine();
	
			}
			
		
		} catch (SQLException | IOException e) {
			System.out.println("Error:" + e);
			e.printStackTrace();
		}
		
		
		
	}
}
