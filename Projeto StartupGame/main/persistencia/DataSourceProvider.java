package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSourceProvider {

    private static final String URL = "jdbc:h2:file:./startupdb;INIT=RUNSCRIPT FROM 'classpath:schema.sql';DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASS = "";

    static {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Falha ao carregar o driver H2.", e);
        }
    }

    public static Connection get() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}