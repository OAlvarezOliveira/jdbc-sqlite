package miniRetos;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Reto06 {
	
	private final static String RUTA_ENTRADA = "alumno.dat";


	public static void main(String[] args) {
		
		Alumno a = new Alumno ("Marta",8.5);
		
	try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RUTA_ENTRADA ))) {
		
		oos.writeObject(a);
		System.out.println("Alumno guardado correctamente.");
		
	} catch (IOException e) {
		System.out.println("Error:");

		e.printStackTrace();
	}

		try( ObjectInputStream ois  =  new ObjectInputStream(new FileInputStream(RUTA_ENTRADA))) {
			
			Alumno recuperado = (Alumno) ois.readObject();
			System.out.println("Alumno recuperado:");
			System.out.println("Nombre: "+recuperado.getNombre());
			System.out.println("Nota: "+recuperado.getNota());

			
		} catch (FileNotFoundException e) {
			System.out.println("Error:");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error:");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Error:");
			e.printStackTrace();
		}
	
	

	}

}
