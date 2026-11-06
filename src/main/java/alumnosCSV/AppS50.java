package alumnosCSV;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.acarballeira.jdbc.Alumno;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class AppS50 {

	private static String SQL_BUSCAR = "SELECT COUNT(*) FROM alumnos WHERE nombre =? AND apellidos =?";
	private static String SQL_INSERTAR = "";

	public static void main(String[] args) {
		Gson gson = new Gson();

		try (Connection conexion = ConexionDB.conectaDB();
				FileReader lector = new FileReader("alumnos.json");
				PreparedStatement psBuscar = conexion.prepareStatement(SQL_BUSCAR);
				PreparedStatement psInsertar = conexion.prepareStatement(SQL_INSERTAR);) {

			conexion.setAutoCommit(false);
			boolean existe;

			ArrayList<Alumno> alumnos = gson.fromJson(lector, new TypeToken<ArrayList<Alumno>>() {
			}.getType());

			System.out.println("Alumnos recuperados del Json:" + alumnos.size());

			try {

				for (Alumno a : alumnos) {

					System.out.println(a.getNombre() + " - NOTA: " + a.getNotaFinal());

					psBuscar.setString(1, a.getNombre());
					psBuscar.setString(2, a.getApellido1());
	//				psBuscar.executeQuery(SQL_BUSCAR);

					ResultSet rs = psBuscar.executeQuery();
					rs.next();
					existe = rs.getInt(1) > 0;

					if (!existe) {

						psInsertar.setString(1, a.getNombre());
						psInsertar.setString(2, a.getApellido1());
						psInsertar.setFloat(3, a.getNotaFinal());
						psInsertar.executeUpdate(SQL_INSERTAR);

					}

				} // fin for
				conexion.commit();

			} catch (SQLException e) {
				conexion.rollback();
				System.out.println("Error:" + e);
				e.printStackTrace();
			}

		}

		// fin try
		catch (SQLException e) {

			System.out.println("Error:" + e);
			e.printStackTrace();
		}

		catch (IOException e) {

			System.out.println("Error:" + e);
			e.printStackTrace();
		}

	}

}
