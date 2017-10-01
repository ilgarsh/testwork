package store;

import java.util.List;

public interface DirectoryDAO {
    public List<Directory> getAllDirectories();
    public Directory getDirectory(int id);
    public List<Directory> getAllChilds(int id);
    public void addDirectory(Directory directory);
    public void updateDirectory(Directory directory);
    public void deleteDirectory(int directoryID);
}
