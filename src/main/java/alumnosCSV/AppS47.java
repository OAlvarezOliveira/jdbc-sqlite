package alumnosCSV;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppS47 {

	private static final String ARCHIVO = "C:\\Users\\damdu201\\eclipse-workspace\\jdbc-sqlite\\src\\main\\java\\alumnosCSV\\alumnos.csv";
	private static final String SQL_INSERTAR_ALUMNOS = "INSERT INTO alumnos (nombre,apellidos,nota) VALUES (?, ?, ?) ";
	private static final String SQL_BUSCAR_ALUMNOS_DUPLICADOS = "SELECT COUNT(*) FROM alumnos WHERE nombre = ? AND apellidos = ?";

	public static void main(String[] args) {

		try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO));
				Connection conexion = ConexionDB.conectaDB();
				PreparedStatement ps = conexion.prepareStatement(SQL_INSERTAR_ALUMNOS);
				PreparedStatement psBuscar = conexion.prepareStatement(SQL_BUSCAR_ALUMNOS_DUPLICADOS)) {

			conexion.setAutoCommit(false);

			try {

				String linea;
				double nota;

				int contador = 0;
				int rechazada = 0;
				int duplicado = 0;

				br.readLine(); // descarta cabecera del archivo

				while ((linea = br.readLine()) != null) {

					String[] datos = linea.split(";");

					if (datos.length != 3) {

						System.out.println("Linea incorrecta:" + linea);
						rechazada++;
						continue;
					}

					if (datos[0].isBlank() || datos[1].isBlank() || datos[2].isBlank()) {

						System.out.println("Faltan datos:" + linea);
						rechazada++;
						continue;
					}

					String nombre = datos[0];
					String apellidos = datos[1];
					try {
						nota = Double.parseDouble(datos[2]);

					} catch (NumberFormatException e) {
						System.out.println("Error:" + e);
						rechazada++;
						continue;
					}

					boolean existe;

					psBuscar.setString(1, nombre);
					psBuscar.setString(2, apellidos);

					try (ResultSet rs = psBuscar.executeQuery()) {
						rs.next();
						existe = rs.getInt(1) > 0;

					}

					if (existe) {
						System.out.printf("Alumno duplicado: %s %s \n", nombre, apellidos);
						duplicado++;
						continue;

					}

					ps.setString(1, nombre);
					ps.setString(2, apellidos);
					ps.setDouble(3, nota);
					contador = +ps.executeUpdate();
					ps.executeUpdate();

					System.out.printf("Alumno Insertado: %s %s \n", nombre, apellidos);
					System.out.println("=======================");

				} // FIN WHILE
				conexion.commit();
				System.out.printf("Total Alumnos importados: %d \n", contador);
				System.out.printf("Total Alumnos duplicados: %d \n", duplicado);
				System.out.printf("Total Alumnos rechazados: %d \n", rechazada);
				System.out.printf("Total procesados: %d \n", (contador + rechazada + duplicado));

			} catch (SQLException | IOException e) {

				conexion.rollback();
				System.out.println("Error durante la importacion: " + e.getMessage());
				System.out.println("Transaccion deshecha.");

			} 

		} catch (IOException | SQLException e) {
			System.out.println("Error al abrir o cerrar recursos: " + e.getMessage());

		}
		
	}

}
