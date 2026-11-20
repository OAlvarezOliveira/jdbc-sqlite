package miniRetos;

import java.io.Serializable;

public class Alumno implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String nombre;
	private double nota;
	
	public Alumno(String nombre, double nota) {
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
		return String.format("%s, %.2f", nombre, nota);
	}
	
	
	

}
