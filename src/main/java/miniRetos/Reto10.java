package miniRetos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Reto10 {

	private static final String URL = "jdbc:sqlite:reto10.db";
	private static final int IMPORTE = 20;
	private static final String SQL_RETIRAR_SALDO = "UPDATE cuentas SET saldo = saldo - ? WHERE id = ?;";
	private static final String SQL_AÑADIR_SALDO = "UPDATE cuentas SET saldo = saldo + ? WHERE id = ?;";
	private static final String SQL_CONSULTA_SALDO = "SELECT id,saldo FROM cuentas ORDER BY id";

	public static void main(String[] args) {

		try (Connection conexion = DriverManager.getConnection(URL);
				PreparedStatement ps = conexion.prepareStatement(SQL_RETIRAR_SALDO);
				PreparedStatement psAñdir = conexion.prepareStatement(SQL_AÑADIR_SALDO);
				PreparedStatement psConsultar = conexion.prepareStatement(SQL_CONSULTA_SALDO)) {

			conexion.setAutoCommit(false);
			boolean retirada = false;
			boolean ingreso = false;

			try {
				ps.setInt(1, IMPORTE);
				ps.setInt(2, 1);

				if (ps.executeUpdate() == 1) {
					retirada = true;

				}

				psAñdir.setInt(1, IMPORTE);
				psAñdir.setInt(2, 2);

				if (psAñdir.executeUpdate() == 1) {
					ingreso = true;

				}

				if (retirada && ingreso) {

					conexion.commit();
					System.out.println("Transferencia realizada correctamente");

				} else {

					conexion.rollback();
					System.out.println("Transferencia cancelada");

				}
			} catch (SQLException e) {
				conexion.rollback();
				System.out.println("Error:");
				System.out.println("La base de datos no ha sido modificada");
				e.printStackTrace();
			}

			try (ResultSet rs = psConsultar.executeQuery()) {

				while (rs.next()) {

					System.out.printf("Cuenta %d: %d  %n", rs.getInt("id"), rs.getInt("saldo"));

				}

			}

		} catch (SQLException e) {
			System.out.println("Error:");
			e.printStackTrace();
		}

	}

}
