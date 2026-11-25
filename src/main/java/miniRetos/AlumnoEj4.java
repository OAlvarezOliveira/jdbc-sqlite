package miniRetos;

public class AlumnoEj4 {
	
	private String nombre;
	private double nota;
	
	public AlumnoEj4(String nombre, double nota) {
		super();
		this.nombre = nombre;
		this.nota = nota;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getNota() {
		return nota;
	}

	public void setNota(double nota) {
		this.nota = nota;
	}

	@Override
	public String toString() {
		return String.format("%s,%.2f", nombre, nota);
	}
	
	

}
