package gestorProductos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductoDAO {

	// BBDD
	private static final String URL = "jdbc:sqlite:tienda.db";
	// QUERYS
	private static final String SQL_CREAR_TABLA = "CREATE TABLE IF NOT EXISTS " + "producto(codigo TEXT PRIMARY KEY, "
			+ "nombre TEXT NOT NULL, " + "categoria TEXT NOT NULL, " + "precio REAL NOT NULL, "
			+ "stock INTEGER NOT NULL) ";

	private static final String SQL_INSERTAR = "INSERT INTO producto(codigo, nombre, categoria, precio, stock) VALUES(?, ?, ?, ?, ?)";

	private static final String SQL_LISTAR_TODOS = "SELECT * FROM producto ORDER BY nombre";

	private static final String SQL_BUSCAR_POR_NOMBRE = "SELECT * FROM producto WHERE LOWER(nombre) LIKE LOWER(?)";

	private static final String SQL_BUSCAR_POR_CATEGORIA = "SELECT * FROM producto WHERE LOWER(categoria) = LOWER(?) AND stock < ?";

	private static final String SQL_ACTUALIZAR_PRECIO = "UPDATE producto SET precio = ? WHERE codigo = ?";

	private static final String SQL_ELIMINAR = "DELETE FROM producto  WHERE codigo = ?";

	private static final String SQL_ESTADISTICAS = "SELECT " + "COUNT(codigo) as NumProductos, "
			+ "AVG(precio) as PrecioMEDIO, " + "MAX(precio) as PrecioMAXIMO, "
			+ "MIN(precio) as PrecioMINIMO   FROM producto ";

	private static final String SQL_ESTADISTICAS_POR_CATEGORIA = "SELECT categoria, COUNT(codigo) AS NumProductos, "
	        + "AVG(precio) AS PrecioMedio, MAX(precio) AS PrecioMaximo, MIN(precio) AS PrecioMinimo "
	        + "FROM producto GROUP BY categoria";

	public static void crearTabla() {

		try (Connection conexion = DriverManager.getConnection(URL);
				PreparedStatement ps = conexion.prepareStatement(SQL_CREAR_TABLA)

		) {
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error: " + e);
		}
	}

	public static boolean insertar(Producto p) {
		boolean ejecutado;
		try (Connection conexion = DriverManager.getConnection(URL);
				PreparedStatement ps = conexion.prepareStatement(SQL_INSERTAR);) {
			// (codigo, nombre, categoria, precio, stock)
			ps.setString(1, p.getCodigo());
			ps.setString(2, p.getNombre());
			ps.setString(3, p.getCategoria());
			ps.setDouble(4, p.getPrecio());
			ps.setInt(5, p.getStock());
			ps.executeUpdate();
			ejecutado = true;

		} catch (SQLException e) {
			System.out.println("Error: " + e);
			ejecutado = false;
		}
		return ejecutado;
	}

	private static Producto mapear(ResultSet rs) throws SQLException {
		return new Producto(rs.getString("codigo"), rs.getString("nombre"), rs.getString("categoria"),
				rs.getDouble("precio"), rs.getInt("stock"));
	}

	public static ArrayList<Producto> listarTodos() {

		ArrayList<Producto> lista = new ArrayList<Producto>();

		try (Connection conexion = DriverManager.getConnection(URL);
				PreparedStatement ps = conexion.prepareStatement(SQL_LISTAR_TODOS);) {

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					lista.add(mapear(rs));
				}
			}
		} catch (SQLException e) {
			System.out.println("Error: " + e);

		}
		return lista;

	}

	public static ArrayList<Producto> buscarPorNombre(String texto) {

		ArrayList<Producto> lista = new ArrayList<Producto>();

		try (Connection conexion = DriverManager.getConnection(URL);
				PreparedStatement ps = conexion.prepareStatement(SQL_BUSCAR_POR_NOMBRE)) {
			ps.setString(1, "%" + texto + "%");

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					lista.add(mapear(rs));
				}
			}
		} catch (SQLException e) {
			System.out.println("Error: " + e);

		}

		return lista;

	}

	public static ArrayList<Producto> buscarPorCategoriaStockMenor(String categoria, int limite) {

		ArrayList<Producto> lista = new ArrayList<Producto>();

		try (Connection conexion = DriverManager.getConnection(URL);
				PreparedStatement ps = conexion.prepareStatement(SQL_BUSCAR_POR_CATEGORIA)) {
			ps.setString(1, categoria);
			ps.setInt(2, limite);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					lista.add(mapear(rs));
				}
			}
		} catch (SQLException e) {
			System.out.println("Error: " + e);

		}

		return lista;

	}

	public static boolean actualizarPrecio(String codigo, double precio) {

	    boolean ejecutado = false;

	    try (Connection conexion = DriverManager.getConnection(URL);
	         PreparedStatement ps = conexion.prepareStatement(SQL_ACTUALIZAR_PRECIO)) {
	        ps.setDouble(1, precio);
	        ps.setString(2, codigo);

	        ejecutado = ps.executeUpdate() > 0;

	    } catch (SQLException e) {
	        System.out.println("Error: " + e);
	    }

	    return ejecutado;
	}
	
	
	public static boolean eliminar(String codigo) {

	    boolean ejecutado = false;

	    try (Connection conexion = DriverManager.getConnection(URL);
	         PreparedStatement ps = conexion.prepareStatement(SQL_ELIMINAR)) {
	        ps.setString(1, codigo);

	        ejecutado = ps.executeUpdate() > 0;

	    } catch (SQLException e) {
	        System.out.println("Error: " + e);
	    }

	    return ejecutado;
	}
	
	
	public static String estadisticas() {
	    String salida = "";

	    try (Connection conexion = DriverManager.getConnection(URL);
	         PreparedStatement ps = conexion.prepareStatement(SQL_ESTADISTICAS);
	         ResultSet rs = ps.executeQuery()) { 

	        if (rs.next()) {
	            int total = rs.getInt("NumProductos");
	            
	            if (total == 0) {
	                salida = "Sin productos";
	            } else {
	                double media   = rs.getDouble("PrecioMEDIO");
	                double maximo  = rs.getDouble("PrecioMAXIMO");
	                double minimo  = rs.getDouble("PrecioMINIMO");
	                
	                salida = String.format("Total: %d, media: %.2f, máximo: %.2f, mínimo: %.2f", total, media, maximo, minimo);
	            }
	        }

	    } catch (SQLException e) {
	        System.out.println("Error: " + e.getMessage());
	        salida = "Error al obtener estadísticas";
	    }
	    
	    return salida;
	}
	
	public static String estadisticasPorCategoria() {
	    String salida = "";

	    try (Connection conexion = DriverManager.getConnection(URL);
	         PreparedStatement ps = conexion.prepareStatement(SQL_ESTADISTICAS_POR_CATEGORIA);
	         ResultSet rs = ps.executeQuery()) { 

	    	while (rs.next()) {
	    			int total = rs.getInt("NumProductos");
	    		    String categoria = rs.getString("categoria");
	                double media   = rs.getDouble("PrecioMEDIO");
	                double maximo  = rs.getDouble("PrecioMAXIMO");
	                double minimo  = rs.getDouble("PrecioMINIMO");
	                
	                salida += String.format("Categoria: %s Total: %d media: %.2f, máximo: %.2f, mínimo: %.2f%n", categoria, total , media, maximo, minimo);
	            }
	    	
		    if (salida.isEmpty()) {
		        salida = "Sin productos";
		    }

	    }catch (SQLException e) {
	        System.out.println("Error: " + e.getMessage());
	        salida = "Error al obtener estadísticas";
	    }

	    return salida;
	}
}
