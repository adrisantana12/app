package com.virtualpharmacy.util;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnection {
    private static final Logger LOGGER = Logger.getLogger(DatabaseConnection.class.getName());
    private static final String JNDI_NAME = "java:comp/env/jdbc/VirtualPharmacyDB";

    private static DataSource dataSource;

    static {
        try {
            Context initContext = new InitialContext();
            dataSource = (DataSource) initContext.lookup(JNDI_NAME);
        } catch (NamingException e) {
            LOGGER.log(Level.SEVERE, "Error inicializando la conexión a la base de datos", e);
            throw new RuntimeException("No se pudo inicializar la conexión a la base de datos", e);
        }
    }

    private DatabaseConnection() {
        // Constructor privado para evitar instanciación
    }

    public static Connection getConnection() throws SQLException {
        try {
            Connection conn = dataSource.getConnection();
            if (conn == null) {
                throw new SQLException("No se pudo obtener una conexión del pool");
            }
            return conn;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error obteniendo conexión de la base de datos", e);
            throw e;
        }
    }
}
