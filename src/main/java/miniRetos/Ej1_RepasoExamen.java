package miniRetos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Ej1_RepasoExamen {
	
	private static String URL="jdbc:sqlite:reto10.db";
	private static final String SQL_RETIRAR_SALDO = "UPDATE cuentas SET saldo = saldo - ? WHERE id = ?;";
	private static final String SQL_AÑADIR_SALDO = "UPDATE cuentas SET saldo = saldo + ? WHERE id = ?;";

	
	public static void main(String[] args) {
		
		int origen =99;
		int destino  =2;
		double importe =50;
		
		transferir(origen,destino,importe);
		
		

	}

	private static void transferir(int origen, int destino, double importe) {
		
		try( Connection conexion = DriverManager.getConnection(URL);
				PreparedStatement psRetirar = conexion.prepareStatement(SQL_RETIRAR_SALDO);
				PreparedStatement psAñadir = conexion.prepareStatement(SQL_AÑADIR_SALDO))
		{
			conexion.setAutoCommit(false);
			int filasRetirar = -1;
			int filasIngreso = -1;
			
			try {
				//retiro Saldo
				psRetirar.setDouble(1, importe);
				psRetirar.setInt(2, origen);
				
				//Añado Saldo
				psAñadir.setDouble(1, importe);
				psAñadir.setInt(2, destino);

				
				 filasRetirar = psRetirar.executeUpdate();
				
				 filasIngreso = psAñadir.executeUpdate();
			} catch (SQLException e) {
				conexion.rollback();
				System.out.println("La operacion ha fallado, se ha realizado un rolback sobre ambos movimientos");
				e.printStackTrace();
				 return;   // salimos: ya está deshecho, no pasamos al if/else

			}

			if((filasRetirar == 1) && (filasIngreso == 1)) {
				System.out.println("Operacion realizada con éxito");
				conexion.commit();
			} else {
				System.out.println("La operacion ha fallado, se ha realizado un rolback sobre ambos movimientos");
		        conexion.rollback();   	
		    }

	
		} catch (SQLException e) {
			System.out.println("Error en la BD");
			e.printStackTrace();
		}
	}

}
