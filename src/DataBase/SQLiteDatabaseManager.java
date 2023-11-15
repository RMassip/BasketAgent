package DataBase;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteDatabaseManager {

    private static Connection connection;

    public static Connection connect() throws SQLException, IOException {
        File dbFile = new File(System.getProperty("java.io.tmpdir"), "BasketAgentBD.db");
    
        try {
            if (!dbFile.exists()) {
                InputStream in = SQLiteDatabaseManager.class.getResourceAsStream("/Ressource/BD/BasketAgentBD.db");
                if (in != null) {
                    Files.copy(in, dbFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Database copied to: " + dbFile.getAbsolutePath());
                }
            }
    
            Class.forName("org.sqlite.JDBC");
    
            return DriverManager.getConnection("jdbc:sqlite:" + dbFile.getAbsolutePath());
        } catch (ClassNotFoundException e) {
            System.out.println("erreur BD");
            throw new SQLException("SQLite JDBC Driver not found", e);
        } catch (IOException e) {
            System.out.println("erreur BD");
            e.printStackTrace();
            throw new IOException("Error copying database file", e);
        } catch (SQLException e) {
            System.out.println("erreur BD");
            e.printStackTrace();
            throw new SQLException("Error connecting to the database", e);
        }
    }

    public static void close() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection to the database closed.");
            } catch (SQLException e) {
                System.err.println("Error closing the database connection:");
                e.printStackTrace();
            }
        }
    }
}
