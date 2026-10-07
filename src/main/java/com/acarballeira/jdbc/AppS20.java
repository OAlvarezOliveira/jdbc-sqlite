package com.acarballeira.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AppS20 {
    
    private static final String URL = "jdbc:sqlite:java26.db";
    private static int notaCorte;
    private static final Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.print("Introduce la nota de corte para los filtros: ");
        try {
            notaCorte = Integer.parseInt(teclado.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Nota no válida. Se asignará 5 por defecto.");
            notaCorte = 5;
        }


        try (Connection conexion = DriverManager.getConnection(URL)) {
            
            System.out.println("\n=== LISTADO DE TODOS LOS ALUMNOS ===");
            mostrarTodosAlumnos(conexion);
            
            System.out.println("\n=== ALUMNOS APROBADOS ===");
            mostrarAprobados(conexion);
            
            System.out.println("\n=== ALUMNOS SUSPENSOS ===");
            mostrarSuspensos(conexion);
            
        } catch (SQLException e) {
            System.err.println("Error en la base de datos: " + e.getMessage());
        } finally {
            teclado.close(); 
        }
    }

    private static void mostrarTodosAlumnos(Connection conexion) throws SQLException {
        String sqlTodos = "SELECT * FROM alumno";
        try (PreparedStatement psTodosAlumnos = conexion.prepareStatement(sqlTodos);
             ResultSet resultadoBuscar = psTodosAlumnos.executeQuery()) {
            imprimeResult(resultadoBuscar);
        }
    }

    private static void mostrarSuspensos(Connection conexion) throws SQLException {
        String sqlSuspensos = "SELECT * FROM alumno WHERE notaFinal < ?";
        try (PreparedStatement psSuspensos = conexion.prepareStatement(sqlSuspensos)) {
            psSuspensos.setInt(1, notaCorte);
            try (ResultSet resultadoBuscar = psSuspensos.executeQuery()) {
                imprimeResult(resultadoBuscar);
            }
        }
    }

    private static void mostrarAprobados(Connection conexion) throws SQLException {
        String sqlBuscar = "SELECT * FROM alumno WHERE notaFinal >= ?";
        try (PreparedStatement psBuscar = conexion.prepareStatement(sqlBuscar)) {
            psBuscar.setInt(1, notaCorte);
            try (ResultSet resultadoBuscar = psBuscar.executeQuery()) {
                imprimeResult(resultadoBuscar);
            }
        }
    }

    private static void imprimeResult(ResultSet resultadoBuscar) throws SQLException {
        System.out.printf("%-12s %-20s %-25s %-10s%n", "DNI", "Nombre", "Primer Apellido", "Nota Final");
        System.out.println("-------------------------------------------------------------------------");
        
        boolean tieneDatos = false;
        while (resultadoBuscar.next()) {
            tieneDatos = true;
            System.out.printf("%-12s %-20s %-25s %-10d%n", 
                resultadoBuscar.getString("dni"), 
                resultadoBuscar.getString("nombre"), 
                resultadoBuscar.getString("apellido1"), 
                resultadoBuscar.getInt("notaFinal")
            );
        }
        
        if (!tieneDatos) {
            System.out.println("No se encontraron registros.");
        }
    }
}

