package store;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DirectoryDaoH2 implements DirectoryDAO {

    private Connection connection;

    public DirectoryDaoH2() {
        connection = DirectoryDB.INSTANCE.getConnection();
    }

    @Override
    public List<Directory> getAllDirectories() {
        List<Directory> directories = new ArrayList<Directory>();
        try{
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM DIRECTORY");
            while (rs.next()) {
                Directory directory = new Directory(rs.getInt("ID"),
                        rs.getString("NAME"),
                        rs.getInt("PARENT"));
                directories.add(directory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return directories;
    }

    @Override
    public Directory getDirectory(int id) {
        Directory directory = new Directory();
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM DIRECTORY WHERE ID=?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                directory = new Directory(rs.getInt("ID"),
                        rs.getString("NAME"),
                        rs.getInt("PARENT"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return directory;
    }

    @Override
    public List<Directory> getAllChilds(int id) {
        List<Directory> directories = new ArrayList<Directory>();
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM DIRECTORY WHERE PARENT=?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Directory directory = new Directory(rs.getInt("ID"),
                        rs.getString("NAME"),
                        rs.getInt("PARENT"));

                if (directory.getId() != id) {
                    directories.add(directory);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return directories;
    }

    @Override
    public void addDirectory(Directory directory) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO DIRECTORY (ID, NAME, PARENT) VALUES(?, ?, ?)");
            preparedStatement.setInt(1, directory.getId());
            preparedStatement.setString(2, directory.getName());
            preparedStatement.setInt(3, directory.getParent());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateDirectoryName(int id, String newName) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE DIRECTORY SET NAME=? WHERE ID=?");
            preparedStatement.setString(1, newName );
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateDirectoryParent(int id, int parent) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE DIRECTORY SET PARENT=? WHERE ID=?");
            preparedStatement.setInt(1,  parent);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteDirectory(int directoryID) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM DIRECTORY WHERE ID=?");
            preparedStatement.setInt(1, directoryID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
