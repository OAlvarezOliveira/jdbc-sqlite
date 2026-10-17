package gestorProductos;


public class Producto {

	private String codigo;
	private String nombre;
	private String categoria;
	private double precio;
	private int stock;

	public Producto(String codigo, String nombre, String categoria, double precio, int stock) {
		// 1. Validar que el nombre no esté vacío o sea nulo
		if (nombre == null || nombre.trim().isEmpty()) {
			throw new IllegalArgumentException("El nombre no puede estar en blanco");
		}

		// 2. Validar que el código no esté vacío o sea nulo
		if (codigo == null || codigo.trim().isEmpty()) {
			throw new IllegalArgumentException("El código no puede estar en blanco");
		}

		// 3. Validar que el precio sea mayor o igual a cero
		if (precio < 0) {
			throw new IllegalArgumentException("El precio no puede ser negativo");
		}

		// 4. Validar que el stock sea mayor o igual a cero
		if (stock < 0) {
			throw new IllegalArgumentException("El stock no puede ser negativo");
		}
		
		// 2. Validar que el código no esté vacío o sea nulo
		if (categoria == null || categoria.trim().isEmpty()) {
			throw new IllegalArgumentException("El código no puede estar en blanco");
		}

		// Asignación de valores una vez que todo es válido
		this.codigo = codigo;
		this.nombre = nombre;
		this.categoria = categoria;
		this.precio = precio;
		this.stock = stock;
	}


	public String getCodigo() {
		return codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public String getCategoria() {
		return categoria;
	}


	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		if (precio < 0) {
			throw new IllegalArgumentException("El precio no puede ser negativo");
		}
		this.precio = precio;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		if (stock < 0) {
			throw new IllegalArgumentException("El stock no puede ser negativo");
		}
		this.stock = stock;
	}


	@Override
	public String toString() {
		return String.format("Producto : codigo=%s, nombre=%s, categoria=%s, precio=%.2f, stock=%d", codigo, nombre,
				categoria, precio, stock);
	}
	
	

}
