package com.acarballeira.jdbc;

import java.util.Scanner;

public class MainDAO {

	private static int opcionMenu;
	private static Scanner teclado = new Scanner(System.in);

	public static void main(String[] args) {

		Alumno alumnoPrueba = new Alumno("7777777A", "Marcos", "Siete", "Siete", "DAW", 2, 7, 7, 7, 7);
		Alumno alumnoPruebaMOD = new Alumno("8888888B", "Marcos", "Siete", "Siete", "DAW", 2, 7, 7, 7, 7);
		String dni = "7777777A";
		String dniMOD = "8888888B";
		String cicloBuscar = "2";
		String textoBuscar = "iete";

		try {
			System.out.println("1.OPCION: insertar");
			System.out.println("2.OPCION: buscarPorDni");
			System.out.println("3.OPCION: actualizar");
			System.out.println("4.OPCION: eliminar");
			System.out.println("5.OPCION: listarTodos");
			System.out.println("6.OPCION: buscarPorCiclo");
			System.out.println("7.OPCION: buscarPorTexto");
			System.out.println("Introduce la opción que deseas realizar:");
			opcionMenu = Integer.parseInt(teclado.nextLine());

			switch (opcionMenu) {

			case 1:
				AlumnoDAO.insertar(alumnoPrueba);
				break;

			case 2:
				AlumnoDAO.buscarPorDni(dni);
				break;

			case 3:
				AlumnoDAO.actualizar(alumnoPruebaMOD);
				break;

			case 4:
				AlumnoDAO.eliminar(dniMOD);
				break;

			case 5:
				AlumnoDAO.listarTodos();
				break;

			case 6:
				AlumnoDAO.buscarPorCiclo(cicloBuscar);
				break;

			case 7:
				AlumnoDAO.buscarPorTexto(textoBuscar);
				break;

			default:
				System.err.println("La opción seleccionada no es válida");
				break;
			}
		} catch (Exception e) {
			System.err.println("Error al listar " + e.getMessage());
		}

	}

}
