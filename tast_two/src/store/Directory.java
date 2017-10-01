package store;

public class Directory {
    private int id;
    private String name;
    private int parent;

    Directory() {}

    public Directory(int id, String name, int parent) {
        this.id = id;
        this.name = name;
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public int getParent() {
        return parent;
    }

    public int getId() {
        return id;
    }
}
