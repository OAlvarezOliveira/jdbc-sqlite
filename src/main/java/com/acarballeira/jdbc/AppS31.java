package com.acarballeira.jdbc;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class AppS31 {

	public static void main(String[] args) {

//		try (FileInputStream entrada = new FileInputStream("src/main/java/com/acarballeira/jdbc/datos.txt");
//				FileOutputStream salida = new FileOutputStream("src/main/java/com/acarballeira/jdbc/copia_s31.txt")){
//			
//			byte [] buffer = new byte[8];
//			int cantidad;
//			
//			while ((cantidad = entrada.read(buffer)) != -1 ) {
//				
//				salida.write(buffer, 0, cantidad);
//				
//				System.out.println("Bytes leídos:" + cantidad);
//				
//			}
//			
//			System.out.println("Archivo copiado correctamente" );
//
//			
//		} catch (IOException e) {
//			
//			System.out.println("Error: " + e.getMessage());
//
//		}

		try (BufferedWriter bw = new BufferedWriter(
				new FileWriter("src/main/java/com/acarballeira/jdbc/flush_s31.txt"))) {
			
			bw.write("Primera linea escrita con buffer");
			bw.newLine();
			
//			bw.flush();
			
			System.out.println("FLush!!!!!!!");
			bw.write("Segunda linea escrita con buffer");
			
			bw.close();
			
			bw.write("Intentamos escribir despues de cerrar");


		} catch (IOException e) {

			System.out.println("Error: " + e.getMessage());

		}

	}

}
