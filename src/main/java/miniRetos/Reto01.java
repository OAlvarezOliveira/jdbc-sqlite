package miniRetos;

import java.util.ArrayList;

public class Reto01 {

	public static void main(String[] args) {

		ArrayList<Alumno> lista = new ArrayList<>();
		int aprobados=0;
		double suma = 0;
		double notaMedia = 0;

		lista.add(new Alumno("Ana", 7.5));
		lista.add(new Alumno("Pedro", 4));
		lista.add(new Alumno("Lucía", 8.0));
		lista.add(new Alumno("Marcos", 3.5));
		
		for (Alumno alumno : lista) {
			
			if(alumno.getNota() >= 5) {
				
				System.out.println(alumno);
				aprobados++;
			}
			
			suma += alumno.getNota();
		}

		notaMedia = suma/lista.size();
		
		System.out.printf("Total Aprobados: %d \n",aprobados);
		System.out.printf("Nota Media Clase: %.2f \n",notaMedia);



	}

}
