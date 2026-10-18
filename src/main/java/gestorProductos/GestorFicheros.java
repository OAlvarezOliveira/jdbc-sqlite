package gestorProductos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class GestorFicheros {

	private static File archivoProductos = new File("productos.txt");

	public static boolean exportar() {

		boolean completado = false;

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivoProductos))) {

			ArrayList<Producto> lista = ProductoDAO.listarTodos();

			for (Producto producto : lista) {
				// (String codigo, String nombre, String categoria, double precio, int stock)
				String linea = String.format(Locale.US, "%s;%s;%s;%.2f;%d",

						producto.getCodigo(), producto.getNombre(), producto.getCategoria(), producto.getPrecio(),
						producto.getStock()

				);

				bw.write(linea);
				bw.newLine();

			}
			completado = true;

		} catch (IOException e) {

			System.out.println("La operacion Exportar ha fallado" + e);
		}

		return completado;

	}

	public static boolean importar(Scanner sc) {

		boolean completado = false;

		try (BufferedReader br = new BufferedReader(new FileReader(archivoProductos))) {

			String linea;
			int saltadas = 0;
			int insertados = 0;
			int actualizados = 0;

			while ((linea = br.readLine()) != null) {

				// Trocear Linea
				String[] campos = linea.split(";");

				if (campos.length != 5) {
					System.out.println("Linea ignorada: " + linea);
					saltadas++;
				} else {
					try {
						Producto p = new Producto(campos[0], campos[1], campos[2], Double.parseDouble(campos[3]),
								Integer.parseInt(campos[4]));
						if (ProductoDAO.buscarPorCodigo(p.getCodigo()) == null) {
							if (ProductoDAO.insertar(p)) {
								insertados++;
							}
						} else {
							System.out.println("El codigo " + p.getCodigo() + " ya existe. Actualizar? (s/n)");
							String actualiza = sc.nextLine();
							if (actualiza.equalsIgnoreCase("s")) {
								if (ProductoDAO.actualizar(p)) {
									actualizados++;
								}
							}
						}
					} catch (NumberFormatException e1) {
						System.out.println("Error" + e1);
						System.out.println("Linea ignorada: " + linea);
						saltadas++;
					} catch (IllegalArgumentException e2) {
						System.out.println("Error" + e2);
						System.out.println("Linea ignorada: " + linea);
						saltadas++;
					}
				}

			}
			System.out.printf("Lineas Insertadas: %d ;  Lineas Actualizadas: %d ;Lineas Saltadas: %d \n", insertados,
					actualizados, saltadas);
			completado = true;

		} catch (IOException e) {

			System.out.println("La operacion Importar ha fallado" + e);
		}

		return completado;

	}

}
