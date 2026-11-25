package miniRetos;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

//				


public class Ej3_RepasoExamen {

	public static void main(String[] args) {
		File alumnos = null ;
		ArrayList <AlumnoEJ3> lista = new ArrayList<>();
		AlumnoEJ3 nuevo;

		
		try {
			 alumnos = new File("alumnos.dat");
			
			if(alumnos.exists() && alumnos.isFile()) {
				
				System.out.println("Archivo creado con exito");

			}
			

		} catch (Exception e) {
			System.out.println("Error:");
			e.printStackTrace();
		}

		AlumnoEJ3 a1 = new AlumnoEJ3 ("Oscar",8.5,"quesadilla");
		AlumnoEJ3 a2 = new AlumnoEJ3 ("Sofía",9.5,"pesadilla");
		AlumnoEJ3 a3 = new AlumnoEJ3 ("Marcos",7.5,"peladilla");
		
		lista.add(a1);
		lista.add(a2);
		lista.add(a3);
		
		
		try( ObjectOutputStream oos = new ObjectOutputStream (new FileOutputStream(alumnos));
				ObjectInputStream ois = new ObjectInputStream ( new FileInputStream (alumnos))){

				for (AlumnoEJ3 a : lista) {
					
					oos.writeObject(a);

				}
			
			//lee

			    while (true) {
					
				nuevo = (AlumnoEJ3) ois.readObject();

				System.out.println(nuevo);
			    	
			    }

		} catch (EOFException e) {
		    System.out.println("Fin de fichero");   // sale del bucle: es lo esperado
			
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
