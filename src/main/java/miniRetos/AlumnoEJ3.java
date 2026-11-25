package miniRetos;

import java.io.Serializable;

public class AlumnoEJ3  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String nombre;
	private double nota;
	private transient String password;

	
	public AlumnoEJ3(String nombre, double nota, String password) {
		

		this.nombre = nombre;
		this.nota = nota;
		this.password = password;
		
		
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return String.format("%s ; %.2f, %s", nombre, nota, password);
	}
	
	
	
	
		

}
