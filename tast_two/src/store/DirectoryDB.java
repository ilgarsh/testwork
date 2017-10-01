package store;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DirectoryDB {
    public static final DirectoryDB INSTANCE = new DirectoryDB();

    private final String DB_DRIVER = "org.h2.Driver";
    private final String DB_URL = "jdbc:h2:./test";
    private final String DB_LOGIN = "admin";
    private final String DB_PASSWORD = "admin";

    private final Connection connection;

    private final String TABLE_NAME = "DIRECTORY";
    private final String[] COLUMNS = {"ID", "NAME", "PARENT"};


    private DirectoryDB() {
        connection = getDBConnection();
        try {
            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    private void createTable() throws SQLException {
        connection.prepareStatement("CREATE TABLE IF NOT EXISTS DIRECTORY(ID INT AUTO_INCREMENT, " +
                "NAME VARCHAR(255)," +
                "PARENT INT, FOREIGN KEY (PARENT) REFERENCES DIRECTORY(ID) ON DELETE CASCADE ON UPDATE CASCADE);");
    }

    private Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            dbConnection = DriverManager.getConnection(DB_URL, DB_LOGIN, DB_PASSWORD);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }

  public static void main(String[] args) {
    INSTANCE.getDBConnection();
  }
}
