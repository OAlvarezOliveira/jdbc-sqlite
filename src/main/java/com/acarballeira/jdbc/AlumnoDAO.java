package com.acarballeira.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AlumnoDAO {

	// Querys
	private static final String sqlInsert = "INSERT INTO alumno (dni, nombre, apellido1, apellido2, ciclo, curso, eva1, eva2, eva3, notaFinal) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String sqlBuscarDNI = "SELECT * FROM alumno WHERE dni = ?";
	private static final String sqlListar = "SELECT * FROM alumno ORDER BY apellido1, apellido2, nombre";
	private static final String sqlListarCiclo = "SELECT * FROM alumno WHERE ciclo = ? ORDER BY apellido1, apellido2, nombre";
	private static final String sqlBuscarTexto = "SELECT * FROM alumno WHERE LOWER(nombre) LIKE LOWER(?) OR LOWER(apellido1) LIKE LOWER(?) ORDER BY apellido1, nombre";
	private static final String sqlActualizar = "UPDATE alumno SET nombre = ?, apellido1 = ?, apellido2 = ?, ciclo = ?, curso = ?, eva1 = ?, eva2 = ?, eva3 = ?, notaFinal = ? WHERE dni = ?";
	private static final String sqlEliminar = "DELETE FROM alumno WHERE dni = ?";

	// Methods
	private static Alumno mapear(ResultSet rs) throws SQLException {
		return new Alumno(rs.getString("dni"), rs.getString("nombre"), rs.getString("apellido1"),
				rs.getString("apellido2"), rs.getString("ciclo"), rs.getInt("curso"), rs.getInt("eva1"),
				rs.getInt("eva2"), rs.getInt("eva3"), rs.getInt("notaFinal"));

	}

	public static void insertar(Alumno alumno) throws SQLException {

		try (Connection conexion = ConexionBD.getConexion();
				PreparedStatement preparacion = conexion.prepareStatement(sqlInsert)) {

			preparacion.setString(1, alumno.getDni());
			preparacion.setString(2, alumno.getNombre());
			preparacion.setString(3, alumno.getApellido1());
			preparacion.setString(4, alumno.getApellido2());
			preparacion.setString(5, alumno.getCiclo());
			preparacion.setInt(6, alumno.getCurso());
			preparacion.setInt(7, alumno.getEva1());
			preparacion.setInt(8, alumno.getEva2());
			preparacion.setInt(9, alumno.getEva3());
			preparacion.setInt(10, alumno.getNotaFinal());

			preparacion.executeUpdate();
		}

	}

	public static Alumno buscarPorDni(String dni) throws SQLException {

		try (Connection conexion = ConexionBD.getConexion();
				PreparedStatement preparacion = conexion.prepareStatement(sqlBuscarDNI)) {

			preparacion.setString(1, dni);

			try (ResultSet rs = preparacion.executeQuery()) {
				if (rs.next()) {
					return mapear(rs);
				}
			}
		}

		return null;
	}

	public static ArrayList<Alumno> listarTodos() throws SQLException {

		ArrayList<Alumno> lista = new ArrayList<>();

		try (Connection conexion = ConexionBD.getConexion();
				PreparedStatement preparacion = conexion.prepareStatement(sqlListar)) {

			try (ResultSet rs = preparacion.executeQuery()) {
				while (rs.next()) {
					lista.add(mapear(rs));
				}
			}
		}

		return lista;
	}

	public static ArrayList<Alumno> buscarPorCiclo(String ciclo) throws SQLException {

		ArrayList<Alumno> lista = new ArrayList<>();

		try (Connection conexion = ConexionBD.getConexion();
				PreparedStatement preparacion = conexion.prepareStatement(sqlListarCiclo)) {

			preparacion.setString(1, ciclo);

			try (ResultSet rs = preparacion.executeQuery()) {
				while (rs.next()) {
					lista.add(mapear(rs));
				}
			}
		}

		return lista;
	}

	public static ArrayList<Alumno> buscarPorTexto(String texto) throws SQLException {

		ArrayList<Alumno> lista = new ArrayList<>();

		try (Connection conexion = ConexionBD.getConexion();
				PreparedStatement preparacion = conexion.prepareStatement(sqlBuscarTexto)) {

			preparacion.setString(1, "%" + texto + "%");
			preparacion.setString(2, "%" + texto + "%");

			try (ResultSet rs = preparacion.executeQuery()) {
				while (rs.next()) {
					lista.add(mapear(rs));
				}
			}
		}

		return lista;
	}

	public static boolean actualizar(Alumno alumno) throws SQLException {

		int filasMod = 0;

		try (Connection conexion = ConexionBD.getConexion();
				PreparedStatement preparacion = conexion.prepareStatement(sqlActualizar)) {

			preparacion.setString(1, alumno.getNombre());
			preparacion.setString(2, alumno.getApellido1());
			preparacion.setString(3, alumno.getApellido2());
			preparacion.setString(4, alumno.getCiclo());
			preparacion.setInt(5, alumno.getCurso());
			preparacion.setInt(6, alumno.getEva1());
			preparacion.setInt(7, alumno.getEva2());
			preparacion.setInt(8, alumno.getEva3());
			preparacion.setInt(9, alumno.getNotaFinal());
			preparacion.setString(10, alumno.getDni());

			filasMod = preparacion.executeUpdate();
		}

		return filasMod > 0 ? true : false;

	}

	public static boolean eliminar(String dni) throws SQLException {
		int filasMod = 0;

		try (Connection conexion = ConexionBD.getConexion();
				PreparedStatement preparacion = conexion.prepareStatement(sqlEliminar)) {
			preparacion.setString(1,dni);
			filasMod = preparacion.executeUpdate();
		}

		return (filasMod > 0);

	}

}
