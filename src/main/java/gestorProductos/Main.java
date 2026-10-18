package gestorProductos;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		int opcion;
		ProductoDAO.crearTabla();

		do {
			System.out.printf("1. Insertar producto\n" + "2. Listar todos\n" + "3. Actualizar precio\n" + "0. Salir\n");

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
					if (ProductoDAO.insertar(pInsertar)) {
						System.out.println("Producto Insertado");
					} else {
						System.out.println("Producto No Insertado");
					}
				} catch (NumberFormatException e) {
					System.out.println("Stock y precio deben ser numeros");
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 2:
				ArrayList<Producto> lista = new ArrayList<Producto>();
				lista = ProductoDAO.listarTodos();

				for (Producto producto : lista) {
					System.out.println(producto);
				}
				break;
			case 3:
				System.out.println("Introduce Codigo");
				String codigoNuevo = sc.nextLine();
				System.out.println("Introduce el nuevo Precio");
				double precioNuevo = Double.parseDouble(sc.nextLine());
				if (ProductoDAO.actualizarPrecio(codigoNuevo, precioNuevo)) {

					System.out.println("Precio actualizado");

				} else {

					System.out.println("Precio no actualizado");

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
