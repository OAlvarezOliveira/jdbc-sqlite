package miniRetos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Reto05 {
	
	
	private final static String RUTA_ENTRADA = "documento.pdf";
	
	private final static String RUTA_SALIDA = "copia_documento.pdf";


	public static void main(String[] args) {
		
		 File archivo = new File (RUTA_ENTRADA);
		 File archivoSalida = new File (RUTA_SALIDA);
		 
		 	if(!(archivo.exists()) || !archivo.isFile()){
		 		System.out.println("No existe el archivo de entrada");
		 		return;
		 	}

		 	try (FileInputStream fis = new FileInputStream(archivo);
					FileOutputStream fos = new FileOutputStream(archivoSalida)) {
		 		
		 		int n;
		 		long acumulaBytes = 0;
		 		byte[] buffer = new byte[4096];
		 		
		 		while ((n = fis.read(buffer)) != -1) {
		 			
		 		    fos.write(buffer, 0, n);
		 		    
		 		   acumulaBytes+=n;

		 		}
		 		
		 		System.out.println("Copia de archivo ejecutada con éxito");
		 		System.out.println("Cantidad total de bytes copiada: "+ acumulaBytes);

		 		
		 		
			} catch (IOException e) {
		 		System.out.println("Error en la escritura de archivo ");
				e.printStackTrace();
			}


	}

}
