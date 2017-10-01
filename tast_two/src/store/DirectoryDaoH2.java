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
            ResultSet rs = preparedStatement.executeQuery();
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
    public void addDirectory(Directory directory) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO DIRECTORY (NAME, PARENT) VALUES(?, ?)");
            preparedStatement.setString(1, directory.getName());
            preparedStatement.setInt(2, directory.getParent());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateDirectory(Directory directory) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE DIRECTORY SET NAME=?, PARENT=? WHERE ID=?");
            preparedStatement.setString(1, directory.getName());
            preparedStatement.setInt(2, directory.getParent());
            preparedStatement.setInt(3, directory.getId());
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
