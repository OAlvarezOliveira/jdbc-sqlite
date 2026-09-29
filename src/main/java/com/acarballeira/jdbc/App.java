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
		
		String update = """
				 UPDATE alumno
				 SET apellido1 = ?
				 WHERE dni = ?
				 """;
		
		System.out.println("Introduce el nuevo apellido");
		String apellido1 = teclado.nextLine();
		System.out.println("Introduce el nuevo  DNI");
		String dni = teclado.nextLine();
		
		PreparedStatement psModificar = conexion.prepareStatement(update);
		psModificar.setString(1, apellido1);
		psModificar.setString(2, dni);
		int filas = psModificar.executeUpdate();

		
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
				System.out.println(
				resultado.getString("dni") + " - " +
				resultado.getString("apellido1") + " " +
				resultado.getString("apellido2")+ ", " +
				resultado.getString("nombre")+"-"+
				resultado.getString("ciclo")+ "- Curso "+
				resultado.getInt("curso"));
				}		
	}

	private static void conexionBD(String sql) throws SQLException {
	
		sentencia.execute(sql);
		System.out.println("Tabla alumno creada correctamente");
	}
}