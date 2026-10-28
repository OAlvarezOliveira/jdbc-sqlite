package gestorTareas;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	private static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {


		int opcion = -1;
		boolean estado = false;

		try {
			TareaDAO.crearTabla();
		} catch (SQLException e) {
			System.out.println("No se pudo crear la tabla: " + e.getMessage());
		}

		do {
			System.out.printf("" + "1. Insertar tarea\n" + "2. Listar todas\n" + "3. Eliminar tarea\n" + "0. Salir\n");

			try {
				System.out.println("Elige una opcion");
				opcion = Integer.parseInt(sc.nextLine());

				switch (opcion) {
				case 1:

					try {
						System.out.println("Introduce el titulo de la Tarea");
						String titulo = sc.nextLine();
						try {
							System.out.println("Introduce estado (true/false)");
							estado = Boolean.parseBoolean(sc.nextLine());
						} catch (Exception e) {
							System.out.println("Error: " + e.getMessage());
						}

						Tarea pInsertar = new Tarea(titulo, estado);
						TareaDAO.insertar(pInsertar);
						System.out.println("Tarea Insertada");

					} catch (SQLException e) {
						System.out.println("Tarea No Insertada: " + e.getMessage());
					}
					break;
				case 2:
					try {
						ArrayList<Tarea> lista = TareaDAO.listarTodos();

						for (Tarea t : lista) {
							System.out.println(t);
						}
					} catch (SQLException e) {
						System.out.println("No se pudo listar: " + e.getMessage());
					}
					break;
				case 3:
					int id;
					try {
						System.out.println("Introduce el ID de la Tarea a eliminar");
						id = Integer.parseInt(sc.nextLine());

						if (TareaDAO.eliminar(id)) {

							System.out.println("Tarea eliminada");

						} else {

							System.out.println("Tarea no eliminada");

						}
					} catch (SQLException e) {
						System.out.println("No se pudo eliminar: " + e.getMessage());
					} catch (NumberFormatException e) {
						System.out.println("Error: " + e.getMessage());
					}
					break;
				case 0:
					System.out.println("Fin de programa");
					break;
				default:
					System.out.println("Opcion no valida");
					break;
				}
			} catch (NumberFormatException e) {

				System.out.println("Opcion no reconocida: " + e.getMessage());
			}
		} while (opcion != 0);
		sc.close();
	}

}
