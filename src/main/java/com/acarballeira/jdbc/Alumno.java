package com.acarballeira.jdbc;

public class Alumno {
	
	private String dni;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String ciclo;
	private int curso;
	private int eva1;
	private int eva2;
	private int eva3;
	private int notaFinal;
	
	
	public Alumno(String dni, String nombre, String apellido1, String apellido2, String ciclo, int curso, int eva1,
			int eva2, int eva3, int notFinal) {
		super();
		this.dni = dni;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.ciclo = ciclo;
		this.curso = curso;
		this.eva1 = eva1;
		this.eva2 = eva2;
		this.eva3 = eva3;
		this.notaFinal = notFinal;
	}


	public String getDni() {
		return dni;
	}


	public String getNombre() {
		return nombre;
	}


	public String getApellido1() {
		return apellido1;
	}


	public String getApellido2() {
		return apellido2;
	}


	public String getCiclo() {
		return ciclo;
	}


	public int getCurso() {
		return curso;
	}


	public int getEva1() {
		return eva1;
	}


	public int getEva2() {
		return eva2;
	}


	public int getEva3() {
		return eva3;
	}


	public int getNotaFinal() {
		return notaFinal;
	}


	@Override
	public String toString() {
		return String.format(
				"Alumno [dni=%s, nombre=%s, apellido1=%s, apellido2=%s, ciclo=%s, curso=%d, eva1=%d, eva2=%d, eva3=%d, notaFinal=%d]",
				dni, nombre, apellido1, apellido2, ciclo, curso, eva1, eva2, eva3, notaFinal);
	}
	
	
	

}
