package gestorProductos;

public class Main {

	public static void main(String[] args) {
		
//		ProductoDAO.insertar(new Producto("P002", "Raton", "Perifericos", 12.50, 30));
//		ProductoDAO.insertar(new Producto("P003", "Monitor", "Pantallas", 149.99, 5));
		for (Producto p : ProductoDAO.listarTodos()) {
		    System.out.println(p);
		}
		
		System.out.println(ProductoDAO.buscarPorNombre("rat"));   // debe encontrar Raton
		System.out.println(ProductoDAO.buscarPorNombre("TEC"));   // debe encontrar Teclado (mayúsculas)
		System.out.println(ProductoDAO.buscarPorNombre("zzz"));   // lista vacía []
		
		System.out.println(ProductoDAO.buscarPorCategoriaStockMenor("perifericos", 20)); // solo Teclado (stock 15)
		System.out.println(ProductoDAO.buscarPorCategoriaStockMenor("Perifericos", 50)); // Teclado y Raton
		System.out.println(ProductoDAO.buscarPorCategoriaStockMenor("Pantallas", 3));    // []
		
		
		System.out.println(ProductoDAO.actualizarPrecio("P001", 29.90)); // true
		System.out.println(ProductoDAO.actualizarPrecio("P999", 10.0));  // false (no existe)
		System.out.println(ProductoDAO.buscarPorNombre("tec"));          // precio 29,90
		
		ProductoDAO.insertar(new Producto("P999", "Temporal", "Pruebas", 1.0, 1));
		System.out.println(ProductoDAO.eliminar("P999"));   // true
		System.out.println(ProductoDAO.eliminar("P999"));   // false (ya no existe)
		System.out.println(ProductoDAO.listarTodos());      // sin P999
		System.out.println(ProductoDAO.estadisticas());      
		System.out.println(ProductoDAO.estadisticasPorCategoria());

	}

}
