package main.store;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The {@code DirectoryDB} is class that set connection with DB and
 * create table "Directory" in DB.
 */
public class DirectoryDB {
    public static final DirectoryDB INSTANCE = new DirectoryDB();

    private final String DB_DRIVER = "org.h2.Driver";
    private final String DB_URL = "jdbc:h2:./test";
    private final String DB_LOGIN = "admin";
    private final String DB_PASSWORD = "admin";

    private final Connection connection;

    private final String TABLE_NAME = "DIRECTORY";

    private DirectoryDB() {
        connection = getDBConnection();
        try {
            createTable();
            System.out.println(TABLE_NAME + " IS CREATED.");
        } catch (SQLException e) {
            System.out.println("DB is not created");
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    /**
     * DROP TABLE needs for test. Delete this line to save data.
     * @throws SQLException
     */
    private void createTable() throws SQLException {
        connection.prepareStatement("DROP TABLE IF EXISTS DIRECTORY").execute();
        connection.prepareStatement("CREATE TABLE DIRECTORY(ID INT AUTO_INCREMENT, " +
                "NAME VARCHAR(255)," +
                "PARENT INT, FOREIGN KEY (PARENT) REFERENCES DIRECTORY(ID));").execute();
        connection
                .prepareStatement("INSERT INTO DIRECTORY (ID, NAME, PARENT) VALUES(0, 'ROOT', 0)").execute();
    }

    private Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            dbConnection = DriverManager.getConnection(DB_URL, DB_LOGIN, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dbConnection;
    }

  public static void main(String[] args) {
    INSTANCE.getDBConnection();
  }
}
