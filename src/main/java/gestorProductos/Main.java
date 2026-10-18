package gestorProductos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		int opcion;
		try {
			ProductoDAO.crearTabla();
		} catch (SQLException e) {
			System.out.println("No se pudo crear la tabla: " + e.getMessage());
		}

		do {
			System.out.printf("" + "1. Insertar producto\n" + "2. Listar todos\n" + "3. Actualizar precio\n"
					+ "4. Exportar DB a Archivo\n" + "5. Importar a DB desde Archivo\n" + "0. Salir\n");

			System.out.println("Elige una opcion");
			opcion = Integer.parseInt(sc.nextLine());

			switch (opcion) {
			case 1:

				try {
					System.out.println("Introduce Codigo");
					String codigo = sc.nextLine();
					System.out.println("Introduce nombre");
					String nombre = sc.nextLine();
					System.out.println("Introduce categoria");
					String categoria = sc.nextLine();
					System.out.println("Elige  stock");
					int stock = Integer.parseInt(sc.nextLine());
					System.out.println("Introduce precio");
					double precio = Double.parseDouble(sc.nextLine());

					// String codigo, String nombre, String categoria, double precio, int stock)
					Producto pInsertar = new Producto(codigo, nombre, categoria, precio, stock);
					ProductoDAO.insertar(pInsertar);
					System.out.println("Producto Insertado");
				} catch (NumberFormatException e) {
					System.out.println("Stock y precio deben ser numeros");
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
				} catch (SQLException e) {
					System.out.println("Producto No Insertado: " + e.getMessage());
				}
				break;
			case 2:
				try {
					ArrayList<Producto> lista = ProductoDAO.listarTodos();

					for (Producto producto : lista) {
						System.out.println(producto);
					}
				} catch (SQLException e) {
					System.out.println("No se pudo listar: " + e.getMessage());
				}
				break;
			case 3:
				System.out.println("Introduce Codigo");
				String codigoNuevo = sc.nextLine();
				System.out.println("Introduce el nuevo Precio");
				double precioNuevo = Double.parseDouble(sc.nextLine());
				try {
					if (ProductoDAO.actualizarPrecio(codigoNuevo, precioNuevo)) {

						System.out.println("Precio actualizado");

					} else {

						System.out.println("Precio no actualizado");

					}
				} catch (SQLException e) {
					System.out.println("No se pudo actualizar: " + e.getMessage());
				}
				break;
			case 4:
				if (GestorFicheros.exportar()) {
					System.out.println("Exportado correctamente");
				} else {
					System.out.println("No se pudo exportar");
				}
				break;

			case 5:
				if (GestorFicheros.importar(sc)) {
					System.out.println("Importado correctamente");
				} else {
					System.out.println("No se pudo importar");
				}
				break;
			case 0:
				System.out.println("Fin de programa");
				break;
			default:
				System.out.println("Opcion no valida");
				break;
			}
		} while (opcion != 0);
		sc.close();
	}

}
