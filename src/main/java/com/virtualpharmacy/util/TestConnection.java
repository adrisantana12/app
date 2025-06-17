package com.virtualpharmacy.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestConnection {
    public static void main(String[] args) {
        try {
            // Cargar el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Conectar a la base de datos
            String url = "jdbc:mysql://localhost:3307/myvirtualpharmacy?serverTimezone=UTC";
            String user = "root";
            String password = "";

            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("¡Conexión exitosa!");

            // Cerrar la conexión
            conn.close();
        } catch (Exception e) {
            System.out.println("Error de conexión: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
