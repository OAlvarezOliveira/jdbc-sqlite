package examenAD_EVA1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class AppS1 {

	private static final String ARCHIVO = "src/main/java/examenAD_EVA1/tareas.csv";
	private static final String URL = "jdbc:sqlite:tareas.db";
	private static final String SQL_INSERTAR_TAREA = "INSERT INTO tarea (titulo, hecha) VALUES (?,?) ";
	private static final String SQL_BUSCAR_DUPLICADA = "SELECT COUNT(*) id FROM tarea WHERE titulo = ?";

	public static void main(String[] args) {

		int rechazada = 0;
		int duplicada = 0;
		int insertada = 0;
		String linea;

		try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO));
				Connection con = DriverManager.getConnection(URL);
				PreparedStatement ps = con.prepareStatement(SQL_INSERTAR_TAREA);
				PreparedStatement psBuscarRepe = con.prepareStatement(SQL_BUSCAR_DUPLICADA)) {

			br.readLine();
			con.setAutoCommit(false);
			try {
				while ((linea = br.readLine()) != null) {

					// System.out.println(linea);

					String[] datos = linea.split(";");

					if (datos.length != 2) {
						System.out.println("Linea rechazada:" + linea);
						rechazada++;
						continue;
					} else {
							if (!datos[1].equalsIgnoreCase("true") && !datos[1].equalsIgnoreCase("false")) {
								rechazada++;
								System.out.println("Linea rechazada (Estado tarea no Valida) : " + linea);
								continue;
							}

							psBuscarRepe.setString(1, datos[0]);
							ResultSet rs = psBuscarRepe.executeQuery();

							if (rs.next() && rs.getInt(1) > 0) {
								duplicada++;
								System.out.println("Linea Duplicada : " + linea);
								continue;
							}
							// si no esta duplicada ni inclumple todo los demas cheks la inserto
							ps.setString(1, datos[0]);
							ps.setBoolean(2, Boolean.parseBoolean(datos[1]));
							ps.executeUpdate();
							insertada++;
							System.out.println("Linea Insertada : " + linea);
					}
				} //Fin While
			} catch (SQLException e) {
				con.rollback();
				System.out.printf("Error: %s \n", e);
			}
			con.commit();

		} catch (IOException | SQLException e) {
			
			System.out.printf("Error: %s \n", e);

		}
		
		System.out.println("=======================");
		System.out.println("Lineas Procesadas:" + (insertada + duplicada + rechazada) );
		System.out.println("Lineas Insertadas:" + insertada );
		System.out.println("Lineas Duplicadas:" + duplicada  );
		System.out.println("Lineas Rechazadas:" + rechazada);

	}

}
