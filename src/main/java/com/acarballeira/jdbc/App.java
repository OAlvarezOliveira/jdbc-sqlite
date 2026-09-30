package com.acarballeira.jdbc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class App {
	
	private static String url = "jdbc:sqlite:java26.db";
    private static Scanner teclado = new Scanner(System.in);
    private static Connection conexion = null;
    private static Statement sentencia = null;
    
	public static void main(String[] args) {

		String sql = """
				CREATE TABLE IF NOT EXISTS alumno (
				dni TEXT PRIMARY KEY,
				nombre TEXT NOT NULL,
				apellido1 TEXT NOT NULL,
				apellido2 TEXT NOT NULL,
				ciclo TEXT NOT NULL,
				curso INTEGER NOT NULL,
				eva1 INTEGER,
				eva2 INTEGER,
				eva3 INTEGER,
				notaFinal INTEGER
				)
				""";		

		
		try {
			conexion = DriverManager.getConnection(url);
			sentencia = conexion.createStatement();
			System.out.println("Conexión realizada correctamente");
			conexionBD(sql);
			insertar();
			
			actualizar();
            
		} catch (SQLException e) {
			System.out.println("Error en la conexión");
			e.printStackTrace();
		}

		System.out.println("Carpeta de trabajo: " + System.getProperty("user.dir"));

	}

	private static void actualizar() throws SQLException {
		

		System.out.println("Introduce el DNI del alumno a modificar:");
		String dni = teclado.nextLine();
		
		System.out.println(""" 
				¿Qué campo quieres modificar?
				1.Nombre
				2.Primer apellido
				3.Segundo apellido
				4.Ciclo
				5.Curso
				6.DNI
				7.Notas
				""");
		int opcion = Integer.parseInt(teclado.nextLine());
		
		
		
		switch (opcion) {
		case 1:
			
			String update = """
			 UPDATE alumno
			 SET nombre = ?
			 WHERE dni = ?
			 """;
			
			System.out.println("Introduce el nuevo NOMBRE");
			String nombre = teclado.nextLine();
			
			PreparedStatement psModificar = conexion.prepareStatement(update);
			psModificar.setString(1, nombre);
			psModificar.setString(2, dni);
			int filas = psModificar.executeUpdate();
			
			if(filas > 0) {
				
				System.out.println("Nombre actualizado correctamente");
				
			}else {
				
				System.out.println("No existe ningún alumno con ese DNI");
				
			}
			
			break;
			
		case 2:
			
			String update2 = """
			 UPDATE alumno
			 SET apellido1 = ?
			 WHERE dni = ?
			 """;
			
			System.out.println("Introduce el nuevo Apellido1");
			String nuevoApellido1 = teclado.nextLine();
			
			PreparedStatement psModificar2 = conexion.prepareStatement(update2);
			psModificar2.setString(1, nuevoApellido1);
			psModificar2.setString(2, dni);
			int filas2 = psModificar2.executeUpdate();
			
			if(filas2 > 0) {
				
				System.out.println("Apellido1 actualizado correctamente");
				
			}else {
				
				System.out.println("No existe ningún alumno con ese DNI");
				
			}
			break;	
			
		case 3:
			
			String update3 = """
			 UPDATE alumno
			 SET apellido2 = ?
			 WHERE dni = ?
			 """;
			
			System.out.println("Introduce el nuevo Apellido2");
			String nuevoApellido3 = teclado.nextLine();
			
			PreparedStatement psModificar3 = conexion.prepareStatement(update3);
			psModificar3.setString(1, nuevoApellido3);
			psModificar3.setString(2, dni);
			int filas3 = psModificar3.executeUpdate();
			
			if(filas3 > 0) {
				
				System.out.println("Apellido2 actualizado correctamente");
				
			}else {
				
				System.out.println("No existe ningún alumno con ese DNI");
				
			}
			break;
			
		case 4:
			
			String update4 = """
			 UPDATE alumno
			 SET ciclo = ?
			 WHERE dni = ?
			 """;
			
			System.out.println("Introduce el nuevo Ciclo");
			String nuevoCiclo = teclado.nextLine();
			
			PreparedStatement psModificar4 = conexion.prepareStatement(update4);
			psModificar4.setString(1, nuevoCiclo);
			psModificar4.setString(2, dni);
			int filas4 = psModificar4.executeUpdate();
			
			if(filas4 > 0) {
				
				System.out.println("Ciclo actualizado correctamente");
				
			}else {
				
				System.out.println("No existe ningún alumno con ese DNI");
				
			}
			break;
			
		case 5:
			
			String update5 = """
			 UPDATE alumno
			 SET curso = ?
			 WHERE dni = ?
			 """;
			
			System.out.println("Introduce el nuevo Curso");
			int nuevoCurso = Integer.parseInt(teclado.nextLine());
			
			PreparedStatement psModificar5 = conexion.prepareStatement(update5);
			psModificar5.setInt(1, nuevoCurso);
			psModificar5.setString(2, dni);
			int filas5 = psModificar5.executeUpdate();
			
			if(filas5 > 0) {
				
				System.out.println("Curso actualizado correctamente");
				
			}else {
				
				System.out.println("No existe ningún alumno con ese DNI");
				
			}
			break;
			
		case 6:
			
			String update6 = """
			 UPDATE alumno
			 SET dni = ?
			 WHERE dni = ?
			 """;
			
			System.out.println("Introduce el nuevo DNI");
			String nuevoDni = teclado.nextLine();
			
			PreparedStatement psModificar6 = conexion.prepareStatement(update6);
			psModificar6.setString(1, nuevoDni);
			psModificar6.setString(2, dni);
			int filas6 = psModificar6.executeUpdate();
			
			if(filas6 > 0) {
				
				System.out.println("DNI actualizado correctamente");
				
			}else {
				
				System.out.println("No existe ningún alumno con ese DNI");
				
			}
			break;
			
		case 7:
			
			String update7 = """
					UPDATE alumno
					SET eva1 = ?, eva2 = ?, eva3 = ?, notaFinal = ?
					WHERE dni = ?
			 """;
			
			System.out.println("Introduce la nota de la evaluación 1:");
			int notaEva1 = Integer.parseInt(teclado.nextLine());
			
			System.out.println("Introduce la nota de la evaluación 2:");
			int notaEva2 = Integer.parseInt(teclado.nextLine());
			
			System.out.println("Introduce la nota de la evaluación 3:");
			int notaEva3 = Integer.parseInt(teclado.nextLine());
			
			int notaFinal = (notaEva1 + notaEva2 + notaEva3) / 3;

			PreparedStatement psModificar7 = conexion.prepareStatement(update7);
			psModificar7.setInt(1, notaEva1);
			psModificar7.setInt(2, notaEva2);
			psModificar7.setInt(3, notaEva3);
			psModificar7.setInt(4, notaFinal);
			psModificar7.setString(5, dni);
			int filas7 = psModificar7.executeUpdate();

			if (filas7 > 0) {
				System.out.println("Notas actualizadas correctamente");
			} else {
				System.out.println("No existe ningún alumno con ese DNI");
			}

			break;

		default:
			break;
		}

	}

	private static void insertar() throws SQLException {
		boolean continua = true;
		
		while(continua) {
			
			
			System.out.println("Introduce DNI");
			String dni = teclado.nextLine();
			
			System.out.println("Introduce NOMBRE");
			String nombre = teclado.nextLine();
			
			System.out.println("Introduce APELLLIDO1");
			String apellido1 = teclado.nextLine();
			
			System.out.println("Introduce APELLLIDO2");
			String apellido2 = teclado.nextLine();
			
			System.out.println("Introduce CICLO");
			String ciclo = teclado.nextLine();
			
			System.out.println("Introduce CURSO");
			int curso = Integer.parseInt(teclado.nextLine());
			
			
			String insertar = """
					INSERT INTO alumno
					(dni, nombre, apellido1, apellido2, ciclo, curso)
					VALUES (?, ?, ?, ?, ?, ?)
					""";

			PreparedStatement ps = conexion.prepareStatement(insertar);
			ps.setString(1, dni);
			ps.setString(2, nombre);
			ps.setString(3, apellido1);
			ps.setString(4, apellido2);
			ps.setString(5, ciclo);
			ps.setInt(6, curso);
			ps.executeUpdate();
			
			
			System.out.print("Deseas Añadir mas alumnos (Si,No)");
			String añadirSiNO = (teclado.nextLine().trim().toUpperCase());			
			if (añadirSiNO.equals("NO")) {continua= false;break;}

		}
		

		ResultSet resultado = sentencia.executeQuery(
				"SELECT * FROM alumno ORDER BY apellido1, apellido2, nombre");
		
		while (resultado.next()) {
			Object eva1 = resultado.getObject("eva1");
			Object eva2 = resultado.getObject("eva2");
			Object eva3 = resultado.getObject("eva3");
			Object notaFinalBD = resultado.getObject("notaFinal");

			System.out.println(
			resultado.getString("dni") + " - " +
			resultado.getString("apellido1") + " " +
			resultado.getString("apellido2")+ ", " +
			resultado.getString("nombre")+"-"+
			resultado.getString("ciclo")+ "- Curso "+
			resultado.getInt("curso")
			+ "- Notas: "+
			(eva1 == null ?"sin nota": eva1)+" ,"+
			(eva2 == null ?"sin nota": eva2)+" ,"+
			(eva3 == null ?"sin nota": eva3)
			+ " - Final: "+
			(notaFinalBD == null ?"sin calcular": notaFinalBD));
			
		}		
	}

	private static void conexionBD(String sql) throws SQLException {
	
		sentencia.execute(sql);
		System.out.println("Tabla alumno creada correctamente");
	}
}