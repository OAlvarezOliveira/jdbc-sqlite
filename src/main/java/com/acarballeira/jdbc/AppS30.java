package com.acarballeira.jdbc;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class AppS30 {
	

	public static void main(String[] args) {
		
		try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/acarballeira/jdbc/datos.txt"));
				BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/java/com/acarballeira/jdbc/datos.txt",true))) {
			
			bw.write("Hola Oscar");
			
			String linea;
			
			while((linea = br.readLine()) != null ) {
				
				System.out.println(linea);
			}
			
			
		} catch (IOException e) {
			
			System.out.println("Error: " + e.getMessage());
			
		}


		
		
		
//		
//		try (FileReader fr = new FileReader("src/main/java/com/acarballeira/jdbc/datos.txt")) {
//			
//			int caracter;
//			
//			while((caracter = fr.read()) != -1 ) {
//				
//				System.out.print( (char) caracter);
//			}
//			
//			
//		} catch (IOException e) {
//			
//			System.out.println("Error: " + e.getMessage());
//			
//		}
//
//	}
		
	}	

}
